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
public class MatchTeamInfo {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = false)
	private String matchId;
	
	@Column(nullable = true, unique = false)
	private String teamName;
	
	@Column(nullable = true, unique = false)
	private int homeOrAway;
	
	@Column(nullable = true, unique = false)
	private int level;
	
	@Column(nullable = true, unique = false)
	private int matchTotal;
	
	@Column(nullable = true, unique = false)
	private int matchWin;
	
	@Column(nullable = true, unique = false)
	private int matchDraw;
	
	@Column(nullable = true, unique = false)
	private int matchLose;
	
	@Column(nullable = true, unique = false)
	private int matchHomeTotal;
	
	@Column(nullable = true, unique = false)
	private int matchHomeWin;
	
	@Column(nullable = true, unique = false)
	private int matchHomeDraw;
	
	@Column(nullable = true, unique = false)
	private int matchHomeLose;
	
	@Column(nullable = true, unique = false)
	private int matchAwayTotal;
	
	@Column(nullable = true, unique = false)
	private int matchAwayWin;
	
	@Column(nullable = true, unique = false)
	private int matchAwayDraw;
	
	@Column(nullable = true, unique = false)
	private int matchAwayLose;
	
	@Column(nullable = true, unique = false)
	private int goals;
	
	@Column(nullable = true, unique = false)
	private int loseGoals;
	
	@Column(nullable = true, unique = false)
	private int goalsHome;
	
	@Column(nullable = true, unique = false)
	private int loseGoalsHome;
	
	@Column(nullable = true, unique = false)
	private int goalsAway;
	
	@Column(nullable = true, unique = false)
	private int loseGoalsAway;
	
	@Column(nullable = true, unique = false)
	private int vsTotal;
	
	@Column(nullable = true, unique = false)
	private int vsWin;
	
	@Column(nullable = true, unique = false)
	private int vsDraw;
	
	@Column(nullable = true, unique = false)
	private int vsLose;
	
	@Column
	@CreatedDate
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMatchId() {
		return matchId;
	}
	
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public int getHomeOrAway() {
		return homeOrAway;
	}
	
	public void setHomeOrAway(int homeOrAway) {
		this.homeOrAway = homeOrAway;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMatchTotal() {
		return matchTotal;
	}

	public void setMatchTotal(int matchTotal) {
		this.matchTotal = matchTotal;
	}

	public int getMatchWin() {
		return matchWin;
	}

	public void setMatchWin(int matchWin) {
		this.matchWin = matchWin;
	}

	public int getMatchDraw() {
		return matchDraw;
	}

	public void setMatchDraw(int matchDraw) {
		this.matchDraw = matchDraw;
	}

	public int getMatchLose() {
		return matchLose;
	}

	public void setMatchLose(int matchLose) {
		this.matchLose = matchLose;
	}

	public int getMatchHomeTotal() {
		return matchHomeTotal;
	}

	public void setMatchHomeTotal(int matchHomeTotal) {
		this.matchHomeTotal = matchHomeTotal;
	}

	public int getMatchHomeWin() {
		return matchHomeWin;
	}

	public void setMatchHomeWin(int matchHomeWin) {
		this.matchHomeWin = matchHomeWin;
	}

	public int getMatchHomeDraw() {
		return matchHomeDraw;
	}

	public void setMatchHomeDraw(int matchHomeDraw) {
		this.matchHomeDraw = matchHomeDraw;
	}

	public int getMatchHomeLose() {
		return matchHomeLose;
	}

	public void setMatchHomeLose(int matchHomeLose) {
		this.matchHomeLose = matchHomeLose;
	}

	public int getMatchAwayTotal() {
		return matchAwayTotal;
	}

	public void setMatchAwayTotal(int matchAwayTotal) {
		this.matchAwayTotal = matchAwayTotal;
	}

	public int getMatchAwayWin() {
		return matchAwayWin;
	}

	public void setMatchAwayWin(int matchAwayWin) {
		this.matchAwayWin = matchAwayWin;
	}

	public int getMatchAwayDraw() {
		return matchAwayDraw;
	}

	public void setMatchAwayDraw(int matchAwayDraw) {
		this.matchAwayDraw = matchAwayDraw;
	}

	public int getMatchAwayLose() {
		return matchAwayLose;
	}

	public void setMatchAwayLose(int matchAwayLose) {
		this.matchAwayLose = matchAwayLose;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public int getLoseGoals() {
		return loseGoals;
	}

	public void setLoseGoals(int loseGoals) {
		this.loseGoals = loseGoals;
	}

	public int getGoalsHome() {
		return goalsHome;
	}

	public void setGoalsHome(int goalsHome) {
		this.goalsHome = goalsHome;
	}

	public int getLoseGoalsHome() {
		return loseGoalsHome;
	}

	public void setLoseGoalsHome(int loseGoalsHome) {
		this.loseGoalsHome = loseGoalsHome;
	}

	public int getGoalsAway() {
		return goalsAway;
	}

	public void setGoalsAway(int goalsAway) {
		this.goalsAway = goalsAway;
	}

	public int getLoseGoalsAway() {
		return loseGoalsAway;
	}

	public void setLoseGoalsAway(int loseGoalsAway) {
		this.loseGoalsAway = loseGoalsAway;
	}

	public int getVsTotal() {
		return vsTotal;
	}

	public void setVsTotal(int vsTotal) {
		this.vsTotal = vsTotal;
	}

	public int getVsWin() {
		return vsWin;
	}

	public void setVsWin(int vsWin) {
		this.vsWin = vsWin;
	}

	public int getVsDraw() {
		return vsDraw;
	}

	public void setVsDraw(int vsDraw) {
		this.vsDraw = vsDraw;
	}

	public int getVsLose() {
		return vsLose;
	}

	public void setVsLose(int vsLose) {
		this.vsLose = vsLose;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	
}
