package test.myblog.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
public class Tag implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer t_id;
	private String t_name;
	private String t_pic;
	
	@OneToMany(mappedBy = "t")
	private List<Article> articles;
	
	public Integer getT_id() {
		return t_id;
	}
	public void setT_id(Integer t_id) {
		this.t_id = t_id;
	}
	public String getT_name() {
		return t_name;
	}
	public void setT_name(String t_name) {
		this.t_name = t_name;
	}
	public String getT_pic() {
		return t_pic;
	}
	public void setT_pic(String t_pic) {
		this.t_pic = t_pic;
	}
	@Override
	public String toString() {
		return "Tag [t_id=" + t_id + ", t_name=" + t_name + ", t_pic=" + t_pic + "]";
	}
	

}
