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
@Table(name = "commentlikes")
public class CommentLikes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cl_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_id")
    private Member m;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_id")
    private Comment c;

	public Integer getCl_id() {
		return cl_id;
	}

	public void setCl_id(Integer cl_id) {
		this.cl_id = cl_id;
	}

	public Member getM() {
		return m;
	}

	public void setM(Member m) {
		this.m = m;
	}

	public Comment getC() {
		return c;
	}

	public void setC(Comment c) {
		this.c = c;
	}
	

	
}
