package test.myblog.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import test.myblog.config.JwtUtils;
import test.myblog.model.Draft;
import test.myblog.model.Member;
import test.myblog.persist.dao.DAOException;
import test.myblog.persist.dao.impl.DraftDAO;
import test.myblog.persist.dao.impl.MemberDAO;
import test.myblog.persist.dao.impl.TagDAO;
import test.myblog.redis.RedisUtils;
import test.myblog.util.PostUtils;

@Service
public class DraftService {
	
	@Autowired
	private MemberDAO md;
	
	@Autowired
	private TagDAO td;
	
	@Autowired
	private DraftDAO dd;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private PostUtils pu;
	
	public Draft draftInfoParser(Map<String, Object> draftInfo) throws DAOException, IOException {
		String mUsername = jwtUtils.extractM_username((String)draftInfo.get("jwt"));
		Map<String, Object> newDraft = (HashMap<String, Object>)(draftInfo.get("article"));
		Member drafter = md.findMemberByUsername(mUsername).get(0);
		Draft d = new Draft();
		d.setD_content(pu.replaceBase64StrWithUrl((String)newDraft.get("a_content")));
		d.setD_title((String)newDraft.get("a_title"));
		d.setM(drafter);
		Integer tId = (Integer)newDraft.get("t_id");
		d.setT(td.findOne(tId));
		return d;
	}
	
	
	public Draft createNewDraft(Map<String, Object> draftInfo) throws DAOException, IOException {
		return dd.create(draftInfoParser(draftInfo));
	}
	
	public List<Map<String, Object>> getDraftListByJwt(String jwt) throws DAOException{
		List<Draft> dl = dd.findDraftListByMid(md.findMemberByUsername(jwtUtils.extractM_username(jwt)).get(0).getM_id());
		List<Map<String, Object>> dml = dl.stream().map(d -> {
													Map<String, Object> dm = new HashMap<>();
													dm.put("d_id", d.getD_id());
													dm.put("d_content", d.getD_content());
													dm.put("d_title", d.getD_title());
													dm.put("m_id", d.getM().getM_id());
													dm.put("t_name", d.getT().getT_name());
													return dm;
												}).collect(Collectors.toList());
		return dml;
		
	}
	
	@Transactional
	public List<Map<String, Object>> deleteDraftAndReturnDraftList(Map<String, Object> draftInfo) throws DAOException{
		String jwt = (String)draftInfo.get("jwt");
		Integer mId = md.findIDByUsername(jwtUtils.extractM_username(jwt));
		Integer dId = (Integer)draftInfo.get("d_id");
		dd.deleteByDidAndMid(dId, mId);
		List<Map<String, Object>> dml = dd.findDraftListByMid(mId).stream().map(d -> {
												Map<String, Object> dm = new HashMap<>();
												dm.put("d_id", d.getD_id());
												dm.put("d_content", d.getD_content());
												dm.put("d_title", d.getD_title());
												dm.put("m_id", d.getM().getM_id());
												dm.put("t_name", d.getT().getT_name());
												return dm;
											}).collect(Collectors.toList());
		return dml;									
	}
}
