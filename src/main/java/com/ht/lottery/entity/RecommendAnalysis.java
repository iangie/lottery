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
public class RecommendAnalysis {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = true, unique = false)
	private int success;
	
	@Column(nullable = true, unique = false)
	private int total;
	
	@Column(nullable = true, unique = false)
	private double rate;
	
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

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
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
