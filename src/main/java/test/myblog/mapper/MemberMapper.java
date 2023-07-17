package test.myblog.mapper;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import test.myblog.model.Member;

public interface MemberMapper {

	Member findById(@Param("m_id") Integer mId);
//	Integer save(@Param("m_about") String m_about, @Param("m_authority") String m_authority, @Param("m_birthday") Date m_birthday, @Param("m_ctime") LocalDateTime m_ctime, @Param("m_gender") String m_gender, @Param("m_ltime") LocalDateTime m_ltime, @Param("m_name") String m_name, @Param("m_password") String m_password, @Param("m_pic") String m_pic, @Param("m_username") String m_username);
	List<Member> findAll();
	Integer save(Member m);
	List<Member> findMemberByMusername(@Param("m_username") String m_username);
	Integer findIdByUsername(@Param("m_username") String m_username);
	void updateMemberInfoWithPasswordPic(@Param("m_name") String m_name, @Param("m_password") String m_password, @Param("m_birthday") Date m_birthday, @Param("m_gender") String m_gender ,@Param("m_pic") String m_pic, @Param("m_id") Integer m_id);
	
}
