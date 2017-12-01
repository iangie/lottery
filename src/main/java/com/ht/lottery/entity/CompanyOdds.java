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
 * @author hanlei8
 *
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CompanyOdds {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = false)
	private String companyName;
	
	@Column(nullable = false, unique = false)
	private String matchInfoId;
	
	@Column(nullable = false, unique = false)
	private String matchName;
	
	@Column(nullable = false, unique = false)
	private String vs;
	
	@Column(nullable = false, unique = false)
	private int concede;
	
	@Column(nullable = false, unique = false)
	private int status;
	
	@Column(nullable = false, unique = false)
	private double oddsWin;
	
	@Column(nullable = false, unique = false)
	private double oddsDraw;
	
	@Column(nullable = false, unique = false)
	private double oddsLose;
	
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

	public String getVs() {
		return vs;
	}

	public void setVs(String vs) {
		this.vs = vs;
	}

	public int getConcede() {
		return concede;
	}

	public void setConcede(int concede) {
		this.concede = concede;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

	public double getOddsWin() {
		return oddsWin;
	}

	public void setOddsWin(double oddsWin) {
		this.oddsWin = oddsWin;
	}

	public double getOddsDraw() {
		return oddsDraw;
	}

	public void setOddsDraw(double oddsDraw) {
		this.oddsDraw = oddsDraw;
	}

	public double getOddsLose() {
		return oddsLose;
	}

	public void setOddsLose(double oddsLose) {
		this.oddsLose = oddsLose;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	
	
}
