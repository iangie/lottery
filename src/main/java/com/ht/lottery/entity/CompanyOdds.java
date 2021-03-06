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
public class CompanyOdds {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = false)
	private String companyName;
	
	@Column(nullable = false, unique = false)
	private String matchInfoId;
	
	@Column(nullable = true, unique = false)
	private String matchName;
	
	@Column(nullable = true, unique = false)
	private Integer matchResult;
	
	@Column(nullable = true, unique = false)
	private String matchScore;
	
	@Column(nullable = true, unique = false)
	private String vs;
	
	@Column(nullable = true, unique = false)
	private int concede;
	
	@Column(nullable = true, unique = false)
	private double oddsWin;
	
	@Column(nullable = true, unique = false)
	private double oddsDraw;
	
	@Column(nullable = true, unique = false)
	private double oddsLose;
	
	@Column(nullable = true, unique = false)
	private double oddsWin2;
	
	@Column(nullable = true, unique = false)
	private double oddsDraw2;
	
	@Column(nullable = true, unique = false)
	private double oddsLose2;
	
	@Column(nullable = true, unique = false)
	@CreatedDate
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public Integer getMatchResult() {
		return matchResult;
	}

	public void setMatchResult(Integer matchResult) {
		this.matchResult = matchResult;
	}

	public String getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(String matchScore) {
		this.matchScore = matchScore;
		
		if(matchScore != null && matchScore.length() > 0){
			String[] strs = matchScore.split(":");
			int s1 = Integer.valueOf(strs[0]);
			int s2 = Integer.valueOf(strs[1]);
			
			if(s1 > s2){
				this.matchResult = 3;
			}else if (s1 == s2){
				this.matchResult = 1;
			}else{
				this.matchResult = 0;
			}
		}
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

	public double getOddsWin2() {
		return oddsWin2;
	}

	public void setOddsWin2(double oddsWin2) {
		this.oddsWin2 = oddsWin2;
	}

	public double getOddsDraw2() {
		return oddsDraw2;
	}

	public void setOddsDraw2(double oddsDraw2) {
		this.oddsDraw2 = oddsDraw2;
	}

	public double getOddsLose2() {
		return oddsLose2;
	}

	public void setOddsLose2(double oddsLose2) {
		this.oddsLose2 = oddsLose2;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
}
