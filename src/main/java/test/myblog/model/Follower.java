package test.myblog.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "follower")
public class Follower implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer f_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_id")
    private Member m;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", referencedColumnName = "m_id")
    private Member f;

	
	public Follower() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getF_id() {
		return f_id;
	}


	public void setF_id(Integer f_id) {
		this.f_id = f_id;
	}


	public Member getM() {
		return m;
	}


	public void setM(Member m) {
		this.m = m;
	}


	public Member getF() {
		return f;
	}


	public void setF(Member f) {
		this.f = f;
	}

	
	
}
