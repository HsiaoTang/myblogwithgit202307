package test.myblog.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@Table(name = "draft")

public class Draft implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer d_id;
	private String d_title;
	
	@Column(columnDefinition="MEDIUMTEXT")
	private String d_content;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_id")
	private Tag t;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_id")
    private Member m;

	public Draft() {
		
	}

	public Integer getD_id() {
		return d_id;
	}

	public void setD_id(Integer d_id) {
		this.d_id = d_id;
	}

	public String getD_title() {
		return d_title;
	}

	public void setD_title(String d_title) {
		this.d_title = d_title;
	}

	public String getD_content() {
		return d_content;
	}

	public void setD_content(String d_content) {
		this.d_content = d_content;
	}

	public Tag getT() {
		return t;
	}

	public void setT(Tag t) {
		this.t = t;
	}

	public Member getM() {
		return m;
	}

	public void setM(Member m) {
		this.m = m;
	}
	
}
