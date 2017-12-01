/**
 * 
 */
package com.ht.lottery.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author angie
 *
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MatchInfo {
	@Id
	private String id;
	
	@Column(nullable = false, unique = false)
	private String matchName;
	
	@Column(nullable = true, unique = false)
	private String vs;
	
	@Column(nullable = true, unique = false)
	private String score;
	
	@Column(nullable = true, unique = false)
	private Integer result;
	
	@Column
	@CreatedDate
	private Date createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	
}
