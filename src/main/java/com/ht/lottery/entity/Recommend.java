/**
 * 
 */
package com.ht.lottery.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author angie
 *
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Recommend {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = true, unique = false)
	private String matchInfoId;
	
	@Column(nullable = true, unique = false)
	private String matchName;
	
	@Column(nullable = true, unique = false)
	private Date matchTime;
	
	@Column(nullable = true, unique = false)
	private String vs;
	
	@Column(nullable = true, unique = false)
	private double rate;
	
	@Column(nullable = true, unique = false)
	private int recmmend;
	
	@Column(nullable = true, unique = false)
	private String report;
	
	@Column(nullable = false, unique = false)
	@CreatedDate
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatchInfoId() {
		return matchInfoId;
	}

	public void setMatchInfoId(String matchInfoId) {
		this.matchInfoId = matchInfoId;
	}

	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public Date getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	public String getVs() {
		return vs;
	}

	public void setVs(String vs) {
		this.vs = vs;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getRecmmend() {
		return recmmend;
	}

	public void setRecmmend(int recmmend) {
		this.recmmend = recmmend;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	

}
