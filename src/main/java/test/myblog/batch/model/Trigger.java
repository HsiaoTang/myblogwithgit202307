package test.myblog.batch.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "triggers")
public class Trigger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tr_id;
	private String tr_name;
	private String tr_year;
	private String tr_month;
	private String tr_day;
	private String tr_hour;
	private String tr_min;
	private String tr_sec;
	
	public Trigger() {
		
	}

	public Integer getTr_id() {
		return tr_id;
	}

	public void setTr_id(Integer tr_id) {
		this.tr_id = tr_id;
	}

	public String getTr_name() {
		return tr_name;
	}

	public void setTr_name(String tr_name) {
		this.tr_name = tr_name;
	}

	public String getTr_year() {
		return tr_year;
	}

	public void setTr_year(String tr_year) {
		this.tr_year = tr_year;
	}

	public String getTr_month() {
		return tr_month;
	}

	public void setTr_month(String tr_month) {
		this.tr_month = tr_month;
	}

	public String getTr_day() {
		return tr_day;
	}

	public void setTr_day(String tr_day) {
		this.tr_day = tr_day;
	}

	public String getTr_hour() {
		return tr_hour;
	}

	public void setTr_hour(String tr_hour) {
		this.tr_hour = tr_hour;
	}

	public String getTr_min() {
		return tr_min;
	}

	public void setTr_min(String tr_min) {
		this.tr_min = tr_min;
	}

	public String getTr_sec() {
		return tr_sec;
	}

	public void setTr_sec(String tr_sec) {
		this.tr_sec = tr_sec;
	}
	
	
}
