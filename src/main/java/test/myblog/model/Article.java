package test.myblog.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "article")
public class Article implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer a_id;
	private String a_title;
	
	@Column(columnDefinition="MEDIUMTEXT")
	private String a_content;
	private Integer a_likes;
	private Integer a_views;
	
	@Temporal(TemporalType.DATE)
	private Date a_date;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_id")
	private Tag t;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_id")
    private Member m;
	
	@OneToMany(mappedBy = "a")
	private List<Comment> comments;
	
	public Article() {
		
	}
	
	public Member getM() {
		return m;
	}
	public void setM(Member m) {
		this.m = m;
	}
	public Tag getT() {
		return t;
	}
	public void setT(Tag t) {
		this.t = t;
	}
	public Integer getA_id() {
		return a_id;
	}
	public void setA_id(Integer a_id) {
		this.a_id = a_id;
	}
	public String getA_title() {
		return a_title;
	}
	public void setA_title(String a_title) {
		this.a_title = a_title;
	}
	public String getA_content() {
		return a_content;
	}
	public void setA_content(String a_content) {
		this.a_content = a_content;
	}
	public Integer getA_likes() {
		return a_likes;
	}
	public void setA_likes(Integer a_likes) {
		this.a_likes = a_likes;
	}
	public Integer getA_views() {
		return a_views;
	}
	public void setA_views(Integer a_views) {
		this.a_views = a_views;
	}
	public Date getA_date() {
		return a_date;
	}
	public void setA_date(Date a_date) {
		this.a_date = a_date;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return a_title + "," + a_content + "," + a_likes + "," + a_views + "," + sdf.format(a_date) + ","  + t.getT_id() + "," + m.getM_id();
	}
	
	

}

