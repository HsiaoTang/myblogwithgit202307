package test.myblog.model;


import java.time.LocalDateTime;
import java.util.List;
import java.io.Serializable;
import java.sql.Date;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "member")
public class Member implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer m_id;
	private String m_name;
	private String m_username;
	private String m_password;
	private String m_about;
	private Date m_birthday;
	private String m_gender;
	private String m_pic;
	private String m_authority;
	private LocalDateTime m_ctime;
	private LocalDateTime m_ltime;
	
	@OneToMany(mappedBy = "m")
	private List<Article> articles;
	
	@OneToMany(mappedBy = "m")
	private List<Comment> comments;
	
	
	public Member() {
		
	}

	public Integer getM_id() {
		return m_id;
	}

	public void setM_id(Integer m_id) {
		this.m_id = m_id;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_username() {
		return m_username;
	}

	public void setM_username(String m_username) {
		this.m_username = m_username;
	}

	public String getM_password() {
		return m_password;
	}

	public void setM_password(String m_password) {
		this.m_password = m_password;
	}

	public String getM_about() {
		return m_about;
	}

	public void setM_about(String m_about) {
		this.m_about = m_about;
	}

	public Date getM_birthday() {
		return m_birthday;
	}

	public void setM_birthday(Date m_birthday) {
		this.m_birthday = m_birthday;
	}

	public String getM_gender() {
		return m_gender;
	}

	public void setM_gender(String m_gender) {
		this.m_gender = m_gender;
	}

	public String getM_pic() {
		return m_pic;
	}

	public void setM_pic(String m_pic) {
		this.m_pic = m_pic;
	}

	public String getM_authority() {
		return m_authority;
	}

	public void setM_authority(String m_authority) {
		this.m_authority = m_authority;
	}

	public LocalDateTime getM_ctime() {
		return m_ctime;
	}

	public void setM_ctime(LocalDateTime m_ctime) {
		this.m_ctime = m_ctime;
	}

	public LocalDateTime getM_ltime() {
		return m_ltime;
	}

	public void setM_ltime(LocalDateTime m_ltime) {
		this.m_ltime = m_ltime;
	}

	@Override
	public String toString() {
		return "Member [m_id=" + m_id + ", m_name=" + m_name + ", m_username=" + m_username + ", m_password="
				+ m_password + ", m_about=" + m_about + ", m_birthday=" + m_birthday + ", m_gender=" + m_gender
				+ ", m_pic=" + m_pic + ", m_authority=" + m_authority + ", m_ctime=" + m_ctime + ", m_ltime=" + m_ltime
				+ "]";
	}

}