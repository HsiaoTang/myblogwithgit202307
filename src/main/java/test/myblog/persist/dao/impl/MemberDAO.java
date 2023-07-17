package test.myblog.persist.dao.impl;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import test.myblog.mapper.MemberMapper;
import test.myblog.model.Member;
import test.myblog.persist.dao.DAO;
import test.myblog.persist.dao.DAOException;
import test.myblog.repository.MemberRepository;

@Repository
public class MemberDAO implements DAO<Member, Integer>{
	
//	@Autowired
//	private MemberRepository mr;
	
	
	@Autowired
	private MemberMapper mm;
	
	@Override
	public Member findOne(Integer mId) throws DAOException {
		// TODO Auto-generated method stub
		return mm.findById(mId);
	}

	@Override
	public List<Member> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return mm.findAll();
//		return mr.findAll();
		
	}

	@Override
	public Member create(Member m) throws DAOException {
		// TODO Auto-generated method stub
//		String m_about = m.getM_about();
//		String m_authority = m.getM_authority();
//		Date m_birthday = m.getM_birthday();
//		LocalDateTime m_ctime = m.getM_ctime();
//		String m_gender = m.getM_gender();
//		LocalDateTime m_ltime = m.getM_ltime();
//		String m_name = m.getM_name();
//		String m_password = m.getM_password();
//		String m_pic = m.getM_pic();
//		String m_username = m.getM_username();
		return mm.findById(mm.save(m));
//		return mm.findById(mm.save(m_about, m_authority, m_birthday, m_ctime, m_gender, m_ltime, m_name, m_password, m_pic, m_username));
//		return mr.save(m);
	}

	@Override
	public void update(Member m) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Member m) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	public List<Member> findMemberByUsername(String mUsername) {
		// TODO Auto-generated method stub
		return mm.findMemberByMusername(mUsername);
//		return mr.findMemberByMusername(mUsername);
	}

	public Integer findIDByUsername(String mUsername) {
		// TODO Auto-generated method stub
		return mm.findIdByUsername(mUsername);
//		return mr.findIdByName(mUsername);
	}

	public void updateMemberInfoWithPasswordPic(String mName, String mPassword, Date mBirthday, String mGender, String mPic,
			Integer mId) {
		// TODO Auto-generated method stub
		mm.updateMemberInfoWithPasswordPic(mName, mPassword, mBirthday, mGender, mPic, mId);
//		mr.updateMemberInfoWithPasswordPic(mName, mPassword, mBirthday, mGender, mPic, mId);
	}

//	public Optional<Member> findById(Integer mId) {
//		// TODO Auto-generated method stub
//		return mr.findById(mId);
//	}
	
}
