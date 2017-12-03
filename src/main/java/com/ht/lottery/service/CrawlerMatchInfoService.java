/**
 * 
 */
package com.ht.lottery.service;

import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ht.lottery.entity.CompanyOdds;
import com.ht.lottery.entity.MatchInfo;
import com.ht.lottery.entity.MatchOdds;
import com.ht.lottery.entity.MatchTeamInfo;
import com.ht.lottery.jpa.CompanyOddsRepository;
import com.ht.lottery.jpa.MatchInfoRepository;
import com.ht.lottery.jpa.MatchOddsRepository;
import com.ht.lottery.jpa.MatchTeamInfoRepository;
import com.ht.lottery.utils.CompanyConstants;
import com.ht.lottery.utils.DateUtils;


/**
 * @author angie
 *
 */
@Service
public class CrawlerMatchInfoService {
	private static final Logger logger = LoggerFactory.getLogger(CrawlerMatchInfoService.class);
	
	private static final String URL = "http://trade.500.com/jczq/";
	
	@Autowired
	private MatchInfoRepository matchInfoRepository;
	
	@Autowired
	private MatchOddsRepository matchOddsRepository;
	
	@Autowired
	private MatchTeamInfoRepository matchTeamInfoRepository;
	
	@Autowired
	private CompanyOddsRepository companyOddsRepository;
	
	public void exe(String date){
		logger.info("开始执行爬取比赛信息");
//		System.out.println("开始执行爬取比赛信息");
		long begin = System.currentTimeMillis();
		String url = URL + "?date="+date;
//		matchInfoRepository.deleteAll();
		
		deal(url);

		
		double d = (double)(System.currentTimeMillis() - begin)/1000;
//		System.out.println("结束执行爬取比赛信息，耗时："+d+"s");
		logger.info("结束执行爬取比赛信息，耗时："+d+"s");
	}
	
	private boolean deal(String url){
		Document doc = null;
		while(true) {
			try {
				doc = Jsoup.connect(url).get();
				break;
			} catch (IOException e1) {
				logger.error("抓取失败："+url);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
		
		Elements contents =  doc.select("div.bet_content");
		Elements contents_date = contents.select("div.bet_date");
		Elements contents_table = contents.select("table.bet_table");
		for (int i = 0; i < contents_date.size(); i++) {
			Element e = contents_date.get(i);
			String date = e.attr("date");

			Element table = contents_table.get(i);

			Elements trs = table.getElementsByTag("tr");
			for (Element tr : trs) {
				dealMatch(date, tr);
			}
		}
			 
		return true;
	}
	
	private boolean dealMatch(String date, Element tr){
		MatchInfo match = new MatchInfo();
		String id = date + "_" + tr.select("a.game_num").first().text();
		String lg = tr.attr("lg");
		String homesxname = tr.select("td.left_team").select("a").first().attr("title");
		String awaysxname = tr.select("td.right_team").select("a").first().attr("title");
		String matchTime = tr.select("span.match_time").attr("title").substring(5).trim()+":00";
		String score = tr.select("a.score").text();
		
		//获取分析页的ID
		String dataId = tr.attr("fid");
		
		match.setVs(homesxname + " vs "+awaysxname);
		match.setId(id);
		match.setMatchName(lg);
		match.setDataId(dataId);
		match.setMatchTime(DateUtils.getDate(matchTime));
		match.setReport(date);
		match.setScore(score);
		
		if(!matchInfoRepository.exists(id)) {
			matchInfoRepository.save(match);
			
			dealMatchOdds(match, tr);
			
			dealFenXi(match);
			
			dealOuzhi(match);
		}else {
			
		}
		
		
		
		return true;
	}
	
	private void dealMatchOdds(MatchInfo match, Element tr){
		MatchOdds mo0 = new MatchOdds();
		MatchOdds mo1 = new MatchOdds();
		Elements tds = tr.select("td.border_left").select("p");
		String concede0 = tds.get(0).text();
		if("0".equals(concede0)) {
			mo0.setConcede(0);
		}
		
		int concede1 = Integer.valueOf(tds.get(1).text());
		mo1.setConcede(concede1);
		
		Elements divs = tr.select("td.border_left").next().select("div");
		Element div = divs.get(0);
		double oddsWin ; 
		double oddsDraw ;
		double oddsLose ;
		if(div.select("span").get(0).text().equals("未开售")) {
			oddsWin = 0;
			oddsDraw = 0;
			oddsLose = 0;
		}else {
			oddsWin = Double.valueOf(div.select("span").get(0).text());
			oddsDraw = Double.valueOf(div.select("span").get(1).text());
			oddsLose = Double.valueOf(div.select("span").get(2).text());
		}
		
		
		mo0.setMatchInfoId(match.getId());
		mo0.setMatchName(match.getMatchName());
		mo0.setOddsWin(oddsWin);
		mo0.setOddsDraw(oddsDraw);
		mo0.setOddsLose(oddsLose);
		mo0.setVs(match.getVs());
		
		matchOddsRepository.save(mo0);
		
		div = divs.get(1);
		if(div.select("span").get(0).text().equals("未开售")) {
			oddsWin = 0;
			oddsDraw = 0;
			oddsLose = 0;
		}else {
			oddsWin = Double.valueOf(div.select("span").get(0).text());
			oddsDraw = Double.valueOf(div.select("span").get(1).text());
			oddsLose = Double.valueOf(div.select("span").get(2).text());
		}
		
		mo1.setMatchInfoId(match.getId());
		mo1.setMatchName(match.getMatchName());
		mo1.setOddsWin(oddsWin);
		mo1.setOddsDraw(oddsDraw);
		mo1.setOddsLose(oddsLose);
		mo1.setVs(match.getVs());
		
		matchOddsRepository.save(mo1);
	}

	private void dealFenXi(MatchInfo match){
		String vs = match.getVs();
		String[] str = vs.split(" vs ");
		String url = "http://odds.500.com/fenxi/shuju-" + match.getDataId() + ".shtml";
		Connection con = Jsoup.connect(url);
		Document doc = null;
		while(true) {
			try {
				doc = con.get();
				break;
			} catch (IOException e) {
				logger.error("抓取分析失败："+match.getVs());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
				}
			}
		}
				
			
		
		MatchTeamInfo a = getTeamA(doc);
		a.setMatchId(match.getId());
		a.setTeamName(str[0].trim());
		matchTeamInfoRepository.save(a);
		
		MatchTeamInfo b = getTeamB(doc);
		b.setMatchId(match.getId());
		b.setTeamName(str[1].trim());
		matchTeamInfoRepository.save(b);
	}
	
	public static void main(String[] args) {
		CrawlerMatchInfoService s = new CrawlerMatchInfoService();
		MatchInfo m = new MatchInfo();
		m.setDataId("648107");
		s.dealOuzhi(m);
		
	}
	
	public void dealOuzhi(MatchInfo match){
		String url = "http://odds.500.com/fenxi/ouzhi-" + match.getDataId() + ".shtml";
		Connection con = Jsoup.connect(url);
		Document doc = null;
		while(true) {
			try {
				doc = con.get();
				break;
			} catch (IOException e) {
				logger.error("抓取欧指失败："+match.getVs());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
				}
			}
		}
		
		Elements trs = doc.getElementsByAttributeValue("xls","row");
		for (Element tr : trs) {
			String companyName = tr.select("td.tb_plgs").attr("title");
			if(!CompanyConstants.constains(companyName)) {
				continue;
			}
			Elements _trs= tr.select("td.tb_plgs").next().first().select("tr");
			Element tr0 = _trs.get(0);
			
			double oddsWin = Double.valueOf(tr0.select("td").get(0).text());
			double oddsDraw = Double.valueOf(tr0.select("td").get(1).text());
			double oddsLose = Double.valueOf(tr0.select("td").get(2).text());
			
			CompanyOdds co1 = new CompanyOdds();
			co1.setCompanyName(companyName);
			co1.setMatchInfoId(match.getId());
			co1.setMatchName(match.getMatchName());
			co1.setVs(match.getVs());
			co1.setStatus(0);
			co1.setOddsWin(oddsWin);
			co1.setOddsDraw(oddsDraw);
			co1.setOddsLose(oddsLose);
			co1.setConcede(0);
			
			companyOddsRepository.save(co1);
			
			Element tr1 = _trs.get(1);
			oddsWin = Double.valueOf(tr1.select("td").get(0).text());
			oddsDraw = Double.valueOf(tr1.select("td").get(1).text());
			oddsLose = Double.valueOf(tr1.select("td").get(2).text());
			
			co1 = new CompanyOdds();
			co1.setCompanyName(companyName);
			co1.setMatchInfoId(match.getId());
			co1.setMatchName(match.getMatchName());
			co1.setVs(match.getVs());
			co1.setStatus(1);
			co1.setOddsWin(oddsWin);
			co1.setOddsDraw(oddsDraw);
			co1.setOddsLose(oddsLose);
			co1.setConcede(0);
			
			companyOddsRepository.save(co1);
		}
	}
	
	private MatchTeamInfo getTeamA(Document doc){
		MatchTeamInfo team = new MatchTeamInfo();
		team.setHomeOrAway(0);
		
		Element e = doc.getElementById("team_jiaozhan");
		if(e.getElementsMatchingOwnText("双方暂无交战历史").size() == 0){
			team.setVsTotal(Integer.valueOf(e.select("span.fb2").text()));
			team.setVsWin(Integer.valueOf(number(e.select("em.red").text())));
			team.setVsDraw(Integer.valueOf(number(e.select("em.green").text())));
			team.setVsLose(Integer.valueOf(number(e.select("em.blue").text())));
		}
		
		
		e = doc.getElementById("team_zhanji_1");
		int win = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.ying").text()));
		int draw = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.ping").text()));
		int lose = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.shu").text()));
		team.setMatchHomeTotal(win+draw+lose);
		team.setMatchHomeWin(win);
		team.setMatchHomeDraw(draw);
		team.setMatchHomeLose(lose);
		
		int goalsHome = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(1).select("span.ying").text()));
		int loseGoalsHome = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(1).select("span.shu").text()));
		
		team.setGoalsHome(goalsHome);
		team.setLoseGoalsHome(loseGoalsHome);
		
		e = doc.getElementById("team_zhanji2_1");
		win = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.ying").text()));
		draw = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.ping").text()));
		lose = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.shu").text()));
		team.setMatchAwayTotal(win+draw+lose);
		team.setMatchAwayWin(win);
		team.setMatchAwayDraw(draw);
		team.setMatchAwayLose(lose);
		
		int goalsAway = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(1).select("span.ying").text()));
		int loseGoalsAway = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(1).select("span.shu").text()));
		
		team.setGoalsAway(goalsAway);
		team.setLoseGoalsAway(loseGoalsAway);
		
		team.setMatchTotal(team.getMatchHomeTotal()+team.getMatchAwayTotal());
		team.setMatchWin(team.getMatchHomeWin()+team.getMatchAwayWin());
		team.setMatchDraw(team.getMatchHomeDraw()+team.getMatchAwayDraw());
		team.setMatchLose(team.getMatchHomeLose()+team.getMatchAwayLose());
		team.setGoals(goalsHome+goalsAway);
		team.setLoseGoals(loseGoalsHome+loseGoalsAway);
		return team;
	}
	
	private MatchTeamInfo getTeamB(Document doc){
		MatchTeamInfo team = new MatchTeamInfo();
		team.setHomeOrAway(1);
		Element e = doc.getElementById("team_jiaozhan");
		if(e.getElementsMatchingOwnText("双方暂无交战历史").size() == 0){
			team.setVsTotal(Integer.valueOf(e.select("span.fb2").text()));
			team.setVsWin(Integer.valueOf(number(e.select("em.blue").text())));
			team.setVsDraw(Integer.valueOf(number(e.select("em.green").text())));
			team.setVsLose(Integer.valueOf(number(e.select("em.red").text())));
		}
		
		
		e = doc.getElementById("team_zhanji_0");
		int win = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.ying").text()));
		int draw = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.ping").text()));
		int lose = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.shu").text()));
		team.setMatchHomeTotal(win+draw+lose);
		team.setMatchHomeWin(win);
		team.setMatchHomeDraw(draw);
		team.setMatchHomeLose(lose);
		
		int goalsHome = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(1).select("span.ying").text()));
		int loseGoalsHome = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(1).select("span.shu").text()));
		
		team.setGoalsHome(goalsHome);
		team.setLoseGoalsHome(loseGoalsHome);
		
		e = doc.getElementById("team_zhanji2_0");
		win = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.ying").text()));
		draw = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.ping").text()));
		lose = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.shu").text()));
		team.setMatchAwayTotal(win+draw+lose);
		team.setMatchAwayWin(win);
		team.setMatchAwayDraw(draw);
		team.setMatchAwayLose(lose);
		
		int goalsAway = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(1).select("span.ying").text()));
		int loseGoalsAway = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(1).select("span.shu").text()));
		
		team.setGoalsAway(goalsAway);
		team.setLoseGoalsAway(loseGoalsAway);
		
		team.setMatchTotal(team.getMatchHomeTotal()+team.getMatchAwayTotal());
		team.setMatchWin(team.getMatchHomeWin()+team.getMatchAwayWin());
		team.setMatchDraw(team.getMatchHomeDraw()+team.getMatchAwayDraw());
		team.setMatchLose(team.getMatchHomeLose()+team.getMatchAwayLose());
		team.setGoals(goalsHome+goalsAway);
		team.setLoseGoals(loseGoalsHome+loseGoalsAway);
		return team;
	}
	
	private String number(String text) {
        return Pattern.compile("[^0-9]").matcher(text).replaceAll("");
    }

}
