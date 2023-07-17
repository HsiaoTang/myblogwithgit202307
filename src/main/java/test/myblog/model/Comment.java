package test.myblog.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "comment")
public class Comment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer c_id;
	private LocalDateTime c_time;
	
	@Column(columnDefinition="TEXT")
	private String c_content;
	private Integer c_likes;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "a_id")
	private Article a;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_id")
    private Member m;
	
	
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getC_id() {
		return c_id;
	}


	public void setC_id(Integer c_id) {
		this.c_id = c_id;
	}


	public LocalDateTime getC_time() {
		return c_time;
	}


	public void setC_time(LocalDateTime c_time) {
		this.c_time = c_time;
	}


	public String getC_content() {
		return c_content;
	}


	public void setC_content(String c_content) {
		this.c_content = c_content;
	}


	public Integer getC_likes() {
		return c_likes;
	}


	public void setC_likes(Integer c_likes) {
		this.c_likes = c_likes;
	}


	public Article getA() {
		return a;
	}


	public void setA(Article a) {
		this.a = a;
	}


	public Member getM() {
		return m;
	}


	public void setM(Member m) {
		this.m = m;
	}
	
	

}


