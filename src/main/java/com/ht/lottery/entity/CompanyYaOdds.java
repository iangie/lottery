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
public class CompanyYaOdds {
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
	private Integer concedeResult;
	
	@Column(nullable = true, unique = false)
	private String matchScore;
	
	@Column(nullable = true, unique = false)
	private String vs;
	
	@Column(nullable = true, unique = false)
	private double waterlevelUpper1;
	
	@Column(nullable = true, unique = false)
	private String concede1;
	
	@Column(nullable = true, unique = false)
	private double waterlevelLower1;
	
	@Column(nullable = true, unique = false)
	private double waterlevelUpper2;
	
	@Column(nullable = true, unique = false)
	private String concede2;
	
	@Column(nullable = true, unique = false)
	private double waterlevelLower2;
	 
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

	public Integer getConcedeResult() {
		if(this.concede2.equals("平手")) {
		}
		return concedeResult;
	}

	public void setConcedeResult(Integer concedeResult) {
		this.concedeResult = concedeResult;
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

	public double getWaterlevelUpper1() {
		return waterlevelUpper1;
	}

	public void setWaterlevelUpper1(double waterlevelUpper1) {
		this.waterlevelUpper1 = waterlevelUpper1;
	}

	public String getConcede1() {
		return concede1;
	}

	public void setConcede1(String concede1) {
		this.concede1 = concede1;
	}

	public double getWaterlevelLower1() {
		return waterlevelLower1;
	}

	public void setWaterlevelLower1(double waterlevelLower1) {
		this.waterlevelLower1 = waterlevelLower1;
	}

	public double getWaterlevelUpper2() {
		return waterlevelUpper2;
	}

	public void setWaterlevelUpper2(double waterlevelUpper2) {
		this.waterlevelUpper2 = waterlevelUpper2;
	}

	public String getConcede2() {
		return concede2;
	}

	public void setConcede2(String concede2) {
		this.concede2 = concede2;
	}

	public double getWaterlevelLower2() {
		return waterlevelLower2;
	}

	public void setWaterlevelLower2(double waterlevelLower2) {
		this.waterlevelLower2 = waterlevelLower2;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	

	
}
