package test.myblog.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import test.myblog.model.Member;

public interface MemberRepository extends JpaRepository<Member, Integer>{
	
	@Query(value="SELECT m FROM Member m WHERE m.m_username = :mUsername")
	List<Member> findMemberByMusername(String mUsername);
	
	@Query(value = "SELECT m_id FROM member WHERE m_username = ?1", nativeQuery=true)
	Integer findIdByName(String mUsername);
	
	@Transactional	
	@Modifying
	@Query(value="UPDATE member SET m_name = :mName, m_password = :mPassword, m_birthday = :mBirthday, m_gender = :mGender, m_pic = :mPic WHERE m_id = :mId", nativeQuery=true)
	void updateMemberInfoWithPasswordPic(String mName, String mPassword, Date mBirthday, String mGender, String mPic, Integer mId);
	
}
