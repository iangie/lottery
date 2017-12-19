package com.ht.lottery.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ht.lottery.entity.BasicRecommend;
import com.ht.lottery.entity.MatchInfo;
import com.ht.lottery.entity.MatchTeamInfo;
import com.ht.lottery.jpa.BasicRecommendRepository;
import com.ht.lottery.jpa.MatchInfoRepository;
import com.ht.lottery.jpa.MatchTeamInfoRepository;

@Service
public class BasicAnalysisDataService {
	private static final Logger logger = LoggerFactory.getLogger(BasicAnalysisDataService.class);
	
	@Autowired
	private MatchInfoRepository matchInfoRepository;
	
	@Autowired
	private MatchTeamInfoRepository matchTeamInfoRepository;
	
	@Autowired
	private BasicRecommendRepository basicRecommendRepository;
	
	
	public void exe(String report) {
		List<MatchInfo> list = matchInfoRepository.getMatchInfosByReport(report);
		if(list != null && list.size() > 0) {
			List<BasicRecommend> rs = new ArrayList<BasicRecommend>();
			for (MatchInfo matchInfo : list) {
				BasicRecommend r = init(matchInfo);
				if(r != null) {
					rs.add(r);
				}
			}
			
			if(rs.size() >0 ) {
				basicRecommendRepository.save(rs);
			}
		}
	}
	
	private BasicRecommend init(MatchInfo matchInfo) {
		List<MatchTeamInfo> list = matchTeamInfoRepository.getMatchTeamInfosByMatchInfoIdOrderByHomeOrAway(matchInfo.getId());
		if(list != null && list.size() == 2) {
			MatchTeamInfo homeTeam = list.get(0);
			MatchTeamInfo awayTeam = list.get(1);
			
			
//			if(homeTeam.getMatchTotal() == 0 && homeTeam.getVsTotal() == 0) {
//				logger.error(matchInfo.getId() + " is no any info");
//				return null;
//			}
			
			
			double homeWinScore = 0;
			double homeDrawScore = 0;
			double homeLoseScore = 0;
			double awayWinScore = 0;
			double awayDrawScore = 0;
			double awayLoseScore = 0;
			//
			int hl = homeTeam.getLevel();
			int gl = awayTeam.getLevel();

			double levelScore = (gl - hl) * 0.1;

			homeWinScore += levelScore;
			homeDrawScore += levelScore;
			homeLoseScore += levelScore;
			awayWinScore -= levelScore;
			awayDrawScore -= levelScore;
			awayLoseScore -= levelScore;

			//
			if(homeTeam.getMatchHomeTotal() != 0){
				homeWinScore += (double) homeTeam.getMatchHomeWin()/ homeTeam.getMatchHomeTotal() * 0.1;
				homeDrawScore += (double) homeTeam.getMatchHomeDraw() / homeTeam.getMatchHomeTotal() * 0.1;
				homeLoseScore += (double) homeTeam.getMatchHomeLose() / homeTeam.getMatchHomeTotal() * 0.1;
			}
			if(awayTeam.getMatchAwayTotal() != 0){
				awayWinScore += (double) awayTeam.getMatchAwayWin()/ awayTeam.getMatchAwayTotal() * 0.1;
				awayDrawScore += (double) awayTeam.getMatchAwayDraw() / awayTeam.getMatchAwayTotal() * 0.1;
				awayLoseScore += (double) awayTeam.getMatchAwayLose() / awayTeam.getMatchAwayTotal() * 0.1;
			}

			//

			if(homeTeam.getGoalsHome() + homeTeam.getLoseGoalsHome() != 0){
				homeWinScore += (double) homeTeam.getGoalsHome() / (homeTeam.getGoalsHome() + homeTeam.getLoseGoalsHome()) * 0.1;
				homeDrawScore += (double) homeTeam.getGoalsHome() / (homeTeam.getGoalsHome() + homeTeam.getLoseGoalsHome()) * 0.1;
				homeLoseScore += (double) homeTeam.getLoseGoalsHome() / (homeTeam.getGoalsHome() + homeTeam.getLoseGoalsHome()) * 0.1;
			}
			if(awayTeam.getGoalsAway() + awayTeam.getLoseGoalsAway() != 0){
				awayWinScore += (double) awayTeam.getGoalsAway() / (awayTeam.getGoalsAway() + awayTeam.getLoseGoalsAway()) * 0.1;
				awayDrawScore += (double) awayTeam.getGoalsAway() / (awayTeam.getGoalsAway() + awayTeam.getLoseGoalsAway()) * 0.1;
				awayLoseScore += (double) awayTeam.getLoseGoalsAway() / (awayTeam.getGoalsAway() + awayTeam.getLoseGoalsAway()) * 0.1;
			}

			
			//
			if(homeTeam.getVsTotal() != 0){
				homeWinScore += (double) homeTeam.getVsWin() / homeTeam.getVsTotal() * 0.2;
				homeDrawScore += (double) homeTeam.getVsDraw() / homeTeam.getVsTotal() * 0.2;
				homeLoseScore += (double) homeTeam.getVsLose() / homeTeam.getVsTotal() * 0.2;
			}
			
			if(awayTeam.getVsTotal() != 0){
				awayWinScore += (double) awayTeam.getVsWin() / awayTeam.getVsTotal() * 0.2;
				awayDrawScore += (double) awayTeam.getVsDraw() / awayTeam.getVsTotal() * 0.2;
				awayLoseScore += (double) awayTeam.getVsLose() / awayTeam.getVsTotal() * 0.2;
			}
			

			double rateWin = homeWinScore / (homeWinScore + awayWinScore);
			try {
				rateWin = BigDecimal.valueOf(rateWin).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			} catch (Exception e) {
				e.printStackTrace();
			}
			double rateDraw = homeDrawScore / (homeDrawScore + awayDrawScore);
			rateDraw = BigDecimal.valueOf(rateDraw).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			double rateLose = homeLoseScore / (homeLoseScore + awayLoseScore);
			rateLose = BigDecimal.valueOf(rateLose).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			
			int forecast;
			double temp = Math.max(Math.max(rateWin ,rateDraw) , rateLose);
			if(temp == rateWin)
				forecast = 3;
			else if(temp == rateDraw)
				forecast = 1;
			else
				forecast = 0;
			
			BasicRecommend result = basicRecommendRepository.getBasicRecommendByMatchInfoId(matchInfo.getId());
			if(result == null) {
				result = new BasicRecommend();
				result.setMatchInfoId(matchInfo.getId());
				result.setVs(matchInfo.getVs());
				result.setMatchName(matchInfo.getMatchName());
				result.setMatchTime(matchInfo.getMatchTime());
				result.setMatchResult(matchInfo.getResult());
				result.setRateWin(rateWin);
				result.setRateDraw(rateDraw);
				result.setRateLose(rateLose);
				result.setReport(matchInfo.getReport());
				result.setForecast(forecast);
			}else {
				
			}
			return result;
		}
		
		return null;
	}
}
