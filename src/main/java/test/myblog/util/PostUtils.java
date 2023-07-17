package test.myblog.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import test.myblog.config.JwtUtils;
import test.myblog.model.Article;
import test.myblog.model.Member;
import test.myblog.persist.dao.DAOException;
import test.myblog.persist.dao.impl.MemberDAO;
import test.myblog.persist.dao.impl.TagDAO;

@Component
public class PostUtils {
	
	@Autowired
    private ServletContext servletContext;
	
	@Autowired
	private TagDAO td;
	
	@Autowired
	private MemberDAO md;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	public String savePostImg(String postImg) throws IOException {
		byte[] imageBytes = Base64.getDecoder().decode(postImg);
		String fileName = UUID.randomUUID().toString() + ".jpg";
		//getRealPath 可能是null
		String staticPath = servletContext.getResource("/article/postImgs").getPath();
	    String imagePath = staticPath + File.separator + fileName;
	    String imageURL = "http://localhost:8080/article/postImgs" + File.separator + fileName;
	    BufferedOutputStream stream = new BufferedOutputStream(
				new FileOutputStream(new File(imagePath)));
		stream.write(imageBytes);
		stream.flush();
		stream.close();
		return imageURL;
	}
	
	//圖片存在資料庫，select回來很佔記憶體
	public String replaceBase64StrWithUrl(String postContent) throws IOException {
		String imgUrl = null;
		int startIndex = postContent.indexOf("<img src=\"data:image/");
		while (startIndex != -1) {
			int endIndex = postContent.indexOf("\"", startIndex + 32);
			if (endIndex != -1) {
				imgUrl = savePostImg(postContent.substring(startIndex + 32, endIndex));
				postContent = postContent.substring(0, startIndex + 10) + imgUrl + postContent.substring(endIndex);
				startIndex = postContent.indexOf("<img src=\"data:image/");
			}
		}
		return postContent;
	}
	
	public Article postInfoParser(Map<String, Object> postInfo) throws IOException, DAOException {
		String mUsername = jwtUtils.extractM_username((String)postInfo.get("jwt"));
		Map<String, Object> newPost = (HashMap<String, Object>)(postInfo.get("article"));
		Member poster = md.findMemberByUsername(mUsername).get(0);
		Article a = new Article();
		a.setA_content(replaceBase64StrWithUrl((String)newPost.get("a_content")));
		a.setA_date(new Date());
		a.setA_likes(0);
		a.setA_title((String)newPost.get("a_title"));
		a.setA_views(0);
		a.setM(poster);
		a.setT(td.findOne((Integer)newPost.get("t_id")));
		return a;
	}
    
    public Map<String, Object> articleTransformer(Article a){
    	Map<String, Object> articleMap = new HashMap<>();
    	articleMap.put("a_id", a.getA_id());
    	articleMap.put("a_content", a.getA_content());
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	String aDate = formatter.format(a.getA_date());
    	articleMap.put("a_date", aDate);
    	articleMap.put("a_likes", a.getA_likes());
    	articleMap.put("a_title", a.getA_title());
    	articleMap.put("a_views", a.getA_views());
    	articleMap.put("m_id", a.getM().getM_id());
    	articleMap.put("t_id", a.getT().getT_id());
    	return articleMap;
    }
}
