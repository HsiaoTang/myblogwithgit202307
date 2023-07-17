package test.myblog.service;

import java.io.BufferedOutputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import test.myblog.config.JwtUtils;
import test.myblog.kafka.KafkaArticleConsumer;
import test.myblog.kafka.KafkaArticleProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import test.myblog.model.Article;
import test.myblog.model.Member;
import test.myblog.model.Tag;
import test.myblog.persist.dao.DAOException;
import test.myblog.persist.dao.impl.ArticleDAO;
import test.myblog.persist.dao.impl.MemberDAO;
import test.myblog.persist.dao.impl.TagDAO;
import test.myblog.redis.RedisUtils;
import test.myblog.util.PostUtils;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleDAO ad;
	
	@Autowired
	private PostUtils pu;
	
	@Autowired
    private ServletContext servletContext;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private MemberDAO md;
	
	@Autowired
	private TagDAO td;
	
	public List<List<Map<String, Object>>> getArticleListByTid() throws DAOException{
		List<Tag> tl = td.findAll();
		List<List<Map<String, Object>>> articleListByTid = tl.stream()
									  .map(t -> {return ad.findArticleListByTid(t.getT_id());})
									  .map(a -> {
										  List<Map<String, Object>> articleList = new ArrayList<>();
										  for(int i = 0; i < a.size(); i++) {
											  articleList.add(i, pu.articleTransformer(a.get(i)));
										  }
										  return articleList;
									  })
									  .collect(Collectors.toList());
		return articleListByTid;
	}
	
	
	
	public Article createNewPost(Map<String, Object> postInfo) throws IOException, DAOException {
		String mUsername = jwtUtils.extractM_username((String)postInfo.get("jwt"));
		Map<String, Object> newPost = (HashMap<String, Object>)(postInfo.get("article"));
		Member poster = md.findMemberByUsername(mUsername).get(0);
		Article a = new Article();
		a.setA_content(pu.replaceBase64StrWithUrl((String)newPost.get("a_content")));
		a.setA_date(new Date());
		a.setA_likes(0);
		a.setA_title((String)newPost.get("a_title"));
		a.setA_views(0);
		a.setM(poster);
		a.setT(td.findOne((Integer)newPost.get("t_id")));
		return ad.create(a);
	}
	
	public Map<String, Object> getArticleMap(Integer aId) throws DAOException{
		Article a = ad.findOne(aId);
		Map<String, Object> articleMap = new HashMap<>();
		articleMap.put("a_id", a.getA_id());
		articleMap.put("a_date", a.getA_date());
		articleMap.put("a_title", a.getA_title());
		articleMap.put("a_content", a.getA_content());
		articleMap.put("a_likes", a.getA_likes());
		articleMap.put("a_views", a.getA_views());
		articleMap.put("m_id", a.getM().getM_id());
		articleMap.put("m_username", a.getM().getM_username());
		articleMap.put("t_name", a.getT().getT_name());
		
		return articleMap;
	}
	
	public Integer addOneView(Integer aId) {
		ad.setNewViews(ad.getCurrentViews(aId) + 1, aId);
		return ad.getCurrentViews(aId);
	}
	
	public String retrieveFirstPicInArticle(Article a) throws DAOException {
		String postContent = a.getA_content();
		int startIndex = postContent.indexOf("<img src=\"");
		if(startIndex == -1) {
			return a.getM().getM_pic();
		}	
		String mPostContent = postContent.substring(startIndex + 9, postContent.length());
		int endIndex = mPostContent.indexOf("\">");
		String firstPicUrl = postContent.substring(startIndex + 10, endIndex + startIndex + 9);
		return firstPicUrl;
	}
	
	public List<Map<String, Object>> getArticleListByMid(Integer mId){
		List<Map<String, Object>>articleListMap = ad.findArticleByMid(mId).stream()
				.map(a -> {
					Map<String, Object> articleMap = new HashMap<>();
					articleMap.put("a_id",  a.getA_id());
					articleMap.put("a_title",  a.getA_title());
					try {
						articleMap.put("a_pic",  retrieveFirstPicInArticle(a));
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					articleMap.put("a_likes",  a.getA_likes());
					articleMap.put("a_views",  a.getA_views());
					return articleMap;
				})
				.collect(Collectors.toList());
		return articleListMap;
	}
	
	public List<Article> getArticleListByAid(List<Integer> aIdExpList){
		List<Article> toBeExpList = aIdExpList.stream()
				.map(aId -> {
					Article a = null;
					try {
						a = ad.findOne(aId);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return a;
				}).filter(a -> a != null)
				.collect(Collectors.toList());
		return toBeExpList;	
	}
	
	public Integer importArticlesFromCsv(MultipartFile csvFile, Integer mId) throws ParseException, IOException, DAOException {
		Integer createdCount = 0;
		byte[] fileBytes = csvFile.getBytes();
		String csvContent = new String(fileBytes, StandardCharsets.UTF_8);
		String[] articles = csvContent.split("\n");
		Article a = new Article();
		for(String article: articles) {
			if(!article.equals("")) {
				String[] articleInfo = article.split("</p>,");
				String aContent = articleInfo[0] + "</p>";
				String[] otherArticleInfo = articleInfo[1].split(",");
				Date aDate = new SimpleDateFormat("yyyy-MM-dd").parse(otherArticleInfo[0]);
				String aTitle = otherArticleInfo[1];
				Integer tId = Integer.parseInt(otherArticleInfo[3]);
				a.setA_content(aContent);
				a.setA_date(aDate);
				a.setA_likes(0);
				a.setA_title(aTitle);
				a.setA_views(0);
				a.setM(md.findOne(mId));
				a.setT(td.findOne(tId));
				ad.create(a);
				createdCount += 1;
			}
		}
		return createdCount;
	}

	public List<String> genToBeExpList(List<Integer> aIdExpList) {
		List<String> toBeExpList= aIdExpList.stream()
				.map(a -> {
					if(a != null) {
						Article toBeExp = null;
						try {
							toBeExp = ad.findOne(a);
						} catch (DAOException e) {
							e.printStackTrace();
						}
						String articleInfo = toBeExp.getA_id() + ","
								+ toBeExp.getA_content() + ","
								+ new SimpleDateFormat("yyyy-MM-dd").format(toBeExp.getA_date()) + ","
								+ toBeExp.getA_likes() + ","
								+ toBeExp.getA_title() + ","
								+ toBeExp.getA_views() + ","
								+ toBeExp.getM().getM_username()+ ","
								+ toBeExp.getT().getT_name() + "\n";
						return articleInfo;
					} else {
						return "";
					}
				}).collect(Collectors.toList());
		return toBeExpList;
	}
	
	public String saveAsCsv(List<String> toBeExpList) throws IOException {
		String folderPath = servletContext.getResource("/download/csv").getPath();
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
        String fileName = UUID.randomUUID().toString();
		FileWriter fw = new FileWriter(servletContext.getResource("/download/csv").getPath() + fileName + ".csv");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("a_id" + ","
				+ "a_content" + ","
				+ "a_date" + ","
				+ "a_likes" + ","
				+ "a_title" + ","
				+ "a_views" + ","
				+ "m_username" + ","
				+ "t_name" + "\n");
		for(int i = 0; i < toBeExpList.size(); i++) {
			if(!toBeExpList.get(i).equals("")) {
				bw.write(toBeExpList.get(i));
			}
		}
		bw.close();
		return "http://localhost:8080/download/csv/" + fileName + ".csv";
	}
	
	public String genExpListPdf(List<Integer> aIdExpList) throws JRException, IOException {
		String folderPath = servletContext.getResource("/download/pdf").getPath();
		File folder = new File(folderPath);
		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles();
			if (files != null) {
				for (File file : files) {
					file.delete();
				}
			}
		}
		String jrxmlPath = servletContext.getResource("/jasperreport/ArticleList.jrxml").getPath();
		String picPath = servletContext.getResource("/jasperreport/codingstray-website-favicon-color.png").getPath();
		JRBeanCollectionDataSource pdfSource = new JRBeanCollectionDataSource(getArticleListByAid(aIdExpList)); 
		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(jrxmlPath));
		 
		byte[] imageData = null;
		FileInputStream fis = null;
		ByteArrayInputStream bis = null;
		File imageFile = new File(picPath);
		fis = new FileInputStream(imageFile);
		imageData = new byte[(int) imageFile.length()];
		fis.read(imageData);
		bis = new ByteArrayInputStream(imageData);
		if (fis != null) {
			fis.close();
		}
		 
		HashMap<String, Object> map = new HashMap<>();
		map.put("LOGO_IMG", bis);
		JasperPrint report = JasperFillManager.fillReport(compileReport, map, pdfSource);
		
        String fileName = UUID.randomUUID().toString();
		JasperExportManager.exportReportToPdfFile(report, servletContext.getResource("/download/pdf").getPath() + fileName + ".pdf");
		return "http://localhost:8080/download/pdf/"+ fileName + ".pdf";
	}
	
	public String genExpListCsv(List<Integer> aIdExpList) throws IOException {
		return saveAsCsv(genToBeExpList(aIdExpList));
	}
	
    

}
