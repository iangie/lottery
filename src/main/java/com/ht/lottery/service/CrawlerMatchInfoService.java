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

import com.ht.lottery.entity.MatchInfo;
import com.ht.lottery.entity.MatchTeamInfo;
import com.ht.lottery.jpa.MatchInfoRepository;
import com.ht.lottery.jpa.MatchOddsRepository;
import com.ht.lottery.jpa.MatchTeamInfoRepository;


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
	
	public void exe(String date){
		System.out.println("开始执行爬取比赛信息");
		long begin = System.currentTimeMillis();
		String url = URL + "?date="+date;
		matchInfoRepository.deleteAll();
		
		deal(url);

		
		double d = (double)(System.currentTimeMillis() - begin)/1000;
		System.out.println("结束执行爬取比赛信息，耗时："+d+"s");
	
	}
	
	private boolean deal(String url){
		try {
			 Document doc = Jsoup.connect(url).get();
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private boolean dealMatch(String date, Element element){
		MatchInfo match = new MatchInfo();
		String id = date + "_" + element.select("a.game_num").first().text();
		String lg = element.attr("lg");
		String homesxname = element.select("td.left_team").select("a").first().attr("title");
		String awaysxname = element.select("td.right_team").select("a").first().attr("title");
		match.setVs(homesxname + " vs "+awaysxname);
		match.setId(id);
		match.setMatchName(lg);
		//获取分析页的ID
		String fid = element.attr("fid");
		
		if(!matchInfoRepository.exists(id)) {
			matchInfoRepository.save(match);
			
			dealFenXi(match, fid);
		}
		return true;
	}

	private void dealFenXi(MatchInfo match, String fid){
		try {
		String vs = match.getVs();
		String[] str = vs.split(" vs ");
		String url = "http://odds.500.com/fenxi/shuju-" + fid + ".shtml";
		Connection con = Jsoup.connect(url);
		Document doc = null;
			
				doc = con.get();
			
		
		MatchTeamInfo a = getTeamA(doc);
		a.setMatchId(match.getId());
		a.setTeamName(str[0].trim());
		matchTeamInfoRepository.save(a);
		
		MatchTeamInfo b = getTeamB(doc);
		b.setMatchId(match.getId());
		b.setTeamName(str[1].trim());
		matchTeamInfoRepository.save(b);
		} catch (IOException e) {
			logger.error("抓取错误"+fid+":"+e.getMessage());
		}
	}
	
	private void dealOuzhi(String fid){
		try {
			String url = "http://odds.500.com/fenxi/ouzhi-" + fid + ".shtml";
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			
			
		} catch (IOException e) {
			e.printStackTrace();
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
