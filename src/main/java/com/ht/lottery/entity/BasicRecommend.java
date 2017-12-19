/**
 * 
 */
package com.ht.lottery.entity;

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
public class BasicRecommend {
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
	private double rateWin;

	@Column(nullable = true, unique = false)
	private double rateDraw;
	
	@Column(nullable = true, unique = false)
	private double rateLose;
	
	@Column(nullable = true, unique = false)
	private Integer forecast;
	
	@Column(nullable = true, unique = false)
	private Integer recmmend;
	
	@Column(nullable = true, unique = false)
	private String report;
	
	@Column(nullable = false, unique = false)
	@CreatedDate
	private Date createDate;
	
	@Column(nullable = true, unique = false)
	private Integer matchResult;

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

	public double getRateWin() {
		return rateWin;
	}

	public void setRateWin(double rateWin) {
		this.rateWin = rateWin;
	}

	public double getRateDraw() {
		return rateDraw;
	}

	public void setRateDraw(double rateDraw) {
		this.rateDraw = rateDraw;
	}

	public double getRateLose() {
		return rateLose;
	}

	public void setRateLose(double rateLose) {
		this.rateLose = rateLose;
	}

	public Integer getForecast() {
		return forecast;
	}

	public void setForecast(Integer forecast) {
		this.forecast = forecast;
	}

	public Integer getRecmmend() {
		return recmmend;
	}

	public void setRecmmend(Integer recmmend) {
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

	public Integer getMatchResult() {
		return matchResult;
	}

	public void setMatchResult(Integer matchResult) {
		this.matchResult = matchResult;
	}
}
