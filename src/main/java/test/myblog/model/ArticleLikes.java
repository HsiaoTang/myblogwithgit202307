package test.myblog.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "articlelikes")
public class ArticleLikes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer al_id;

	@ManyToOne
    @JoinColumn(name = "m_id")
    private Member m;
	
	@ManyToOne
    @JoinColumn(name = "a_id")
    private Article a;

	
	public ArticleLikes() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getAl_id() {
		return al_id;
	}


	public void setAl_id(Integer al_id) {
		this.al_id = al_id;
	}


	public Member getM() {
		return m;
	}


	public void setM(Member m) {
		this.m = m;
	}


	public Article getA() {
		return a;
	}


	public void setA(Article a) {
		this.a = a;
	}

	
	
	
}