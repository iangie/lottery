/**
 * 
 */
package com.ht.lottery.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ht.lottery.entity.MatchInfo;
import com.ht.lottery.entity.MatchOdds;
import com.ht.lottery.jpa.MatchInfoRepository;
import com.ht.lottery.jpa.MatchOddsRepository;


/**
 * @author angie
 *
 */
@Service
public class CrawlerMatchInfoService {
	private static final String URL = "http://trade.500.com/jczq/";
	
	String u = "http://odds.500.com/fenxi/ouzhi-704398.shtml";
	
	@Autowired
	private MatchInfoRepository matchInfoRepository;
	
	@Autowired
	private MatchOddsRepository matchOddsRepository;
	
	public void exe(String date){
		System.out.println("开始执行爬取比赛信息");
		long begin = System.currentTimeMillis();
		String url = URL + "?date="+date;
//		matchInfoRepository.deleteAll();
		deal(url);
		
//		Map<String, List> map = this.get(url);
//		if(map != null){
//			List<MatchInfo> matchList = map.get("matchList");
//			List<MatchOdds> matchOddsList = map.get("matchOddsList");
//			if(matchList != null && matchList.size() > 0) {
////				matchInfoRepository.deleteAll();
////				matchInfoRepository.save(matchList);
//			}
//			
////			if(matchOddsList != null && matchOddsList.size() > 0) {
////				matchOddsRepository.deleteAll();
////				matchOddsRepository.save(matchOddsList);
////			}
//			
//		}
		
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
//					 MatchInfo match = getMatch(tr);
//					 match.setDate(date);
//					 match.setId(date+"_"+match.getId());
					 
					 
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
		}
		return true;
	}

	private Map<String, List> get(String url){
		Map<String, List> map = new HashMap<String, List>();
		List<MatchInfo> matchList = new ArrayList<MatchInfo>();
		List<MatchOdds> matchOddsList = new ArrayList<MatchOdds>();
		
		map.put("matchList", matchList);
		map.put("matchOddsList", matchOddsList);
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
					 MatchInfo match = getMatch(tr);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	private MatchInfo getMatch(Element element){
		MatchInfo match = new MatchInfo();
		String id = element.select("a.game_num").first().text();
//		String zid = element.attr("zid");
		String lg = element.attr("lg");
		String homesxname = element.select("td.left_team").select("a").first().attr("title");
		String awaysxname = element.select("td.right_team").select("a").first().attr("title");
//		String homesxname = element.attr("homesxname");
//		String awaysxname = element.attr("awaysxname");
		match.setVs(homesxname + " vs "+awaysxname);
		match.setId(id);
		match.setMatchName(lg);
		//获取分析页的ID
		String fid = element.attr("fid");
//		getFenXi(match, fid);
		
//		match.getHome().setName(homesxname);
//		match.getAway().setName(awaysxname);
		return match;
	}
	
	private List<MatchOdds> getMatchOdds(Element tr){
		List<MatchOdds> result = new ArrayList<MatchOdds>();
		MatchOdds matchOdds0 = new MatchOdds();
		MatchOdds matchOdds1 = new MatchOdds();
		String id = tr.select("a.game_num").first().text();
		String lg = tr.attr("lg");
		String homesxname = tr.select("td.left_team").select("a").first().attr("title");
		String awaysxname = tr.select("td.right_team").select("a").first().attr("title");
		matchOdds0.setVs(homesxname + " vs "+awaysxname);
		matchOdds0.setMatchInfoId(id);
		matchOdds0.setMatchName(lg);
		
		matchOdds1.setVs(homesxname + " vs "+awaysxname);
		matchOdds1.setMatchInfoId(id);
		matchOdds1.setMatchName(lg);

		
		Elements concede = tr.select("td.border_left").select("p");
		Elements next = tr.select("td.border_left").next().select("div");
		if(concede.size() == 2) {
			Element e0 = concede.get(0);
			int concede0 = Integer.valueOf(e0.text());
			matchOdds0.setConcede(concede0);
			
			Element e1 = concede.get(1);
			int concede1 = Integer.valueOf(e1.text());
			matchOdds1.setConcede(concede1);
			
			Elements span0 = next.get(0).select("span");
			if(span0.size() == 3) {
				matchOdds0.setOddsWin(Double.valueOf(span0.get(0).text()));
				matchOdds0.setOddsDraw(Double.valueOf(span0.get(1).text()));
				matchOdds0.setOddsLose(Double.valueOf(span0.get(2).text()));
				
				result.add(matchOdds0);
			}
			
			Elements span1 = next.get(1).select("span");
			if(span1.size() == 3) {
				matchOdds1.setOddsWin(Double.valueOf(span1.get(0).text()));
				matchOdds1.setOddsDraw(Double.valueOf(span1.get(1).text()));
				matchOdds1.setOddsLose(Double.valueOf(span1.get(2).text()));
				
				result.add(matchOdds1);
			}
		}
		
		return result;
	}
	
//	private void getFenXi(Match match, String fid){
//		try {
//			String url = "http://odds.500.com/fenxi/shuju-" + fid + ".shtml";
//			Connection con = Jsoup.connect(url);
//			Document doc = con.get();
//			Team a = getTeamA(doc);
//			Team b = getTeamB(doc);
//			match.setHome(a);
//			match.setAway(b);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	private void getOuzhi(String fid){
		try {
			String url = "http://odds.500.com/fenxi/ouzhi-" + fid + ".shtml";
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	private Team getTeamA(Document doc){
//		Team team = new Team();
//		
//		Element e = doc.getElementById("team_jiaozhan");
//		if(e.getElementsMatchingOwnText("双方暂无交战历史").size() == 0){
//			team.setVsTotal(Integer.valueOf(e.select("span.fb2").text()));
//			team.setVsWin(Integer.valueOf(number(e.select("em.red").text())));
//			team.setVsDraw(Integer.valueOf(number(e.select("em.green").text())));
//			team.setVsLose(Integer.valueOf(number(e.select("em.blue").text())));
//		}
//		
//		
//		e = doc.getElementById("team_zhanji_1");
//		int win = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.ying").text()));
//		int draw = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.ping").text()));
//		int lose = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.shu").text()));
//		team.setMatchHomeTotal(win+draw+lose);
//		team.setMatchHomeWin(win);
//		team.setMatchHomeDraw(draw);
//		team.setMatchHomeLose(lose);
//		
//		int goalsHome = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(1).select("span.ying").text()));
//		int loseGoalsHome = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(1).select("span.shu").text()));
//		
//		team.setGoalsHome(goalsHome);
//		team.setLoseGoalsHome(loseGoalsHome);
//		
//		e = doc.getElementById("team_zhanji2_1");
//		win = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.ying").text()));
//		draw = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.ping").text()));
//		lose = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.shu").text()));
//		team.setMatchAwayTotal(win+draw+lose);
//		team.setMatchAwayWin(win);
//		team.setMatchAwayDraw(draw);
//		team.setMatchAwayLose(lose);
//		
//		int goalsAway = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(1).select("span.ying").text()));
//		int loseGoalsAway = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(1).select("span.shu").text()));
//		
//		team.setGoalsAway(goalsAway);
//		team.setLoseGoalsAway(loseGoalsAway);
//		
//		return team;
//	}
	
//	private Team getTeamB(Document doc){
//		Team team = new Team();
//		Element e = doc.getElementById("team_jiaozhan");
//		if(e.getElementsMatchingOwnText("双方暂无交战历史").size() == 0){
//			team.setVsTotal(Integer.valueOf(e.select("span.fb2").text()));
//			team.setVsWin(Integer.valueOf(number(e.select("em.blue").text())));
//			team.setVsDraw(Integer.valueOf(number(e.select("em.green").text())));
//			team.setVsLose(Integer.valueOf(number(e.select("em.red").text())));
//		}
//		
//		
//		e = doc.getElementById("team_zhanji_0");
//		int win = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.ying").text()));
//		int draw = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.ping").text()));
//		int lose = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.shu").text()));
//		team.setMatchHomeTotal(win+draw+lose);
//		team.setMatchHomeWin(win);
//		team.setMatchHomeDraw(draw);
//		team.setMatchHomeLose(lose);
//		
//		int goalsHome = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(1).select("span.ying").text()));
//		int loseGoalsHome = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(1).select("span.shu").text()));
//		
//		team.setGoalsHome(goalsHome);
//		team.setLoseGoalsHome(loseGoalsHome);
//		
//		e = doc.getElementById("team_zhanji2_0");
//		win = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.ying").text()));
//		draw = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.ping").text()));
//		lose = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(0).select("span.shu").text()));
//		team.setMatchAwayTotal(win+draw+lose);
//		team.setMatchAwayWin(win);
//		team.setMatchAwayDraw(draw);
//		team.setMatchAwayLose(lose);
//		
//		int goalsAway = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(1).select("span.ying").text()));
//		int loseGoalsAway = Integer.valueOf(number(e.select("div.bottom_info").select("span.mar_left20").get(1).select("span.shu").text()));
//		
//		team.setGoalsAway(goalsAway);
//		team.setLoseGoalsAway(loseGoalsAway);
//		
//		return team;
//	}
	
	private String number(String text) {
        return Pattern.compile("[^0-9]").matcher(text).replaceAll("");
    }

}
