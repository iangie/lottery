package com.ht.lottery.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ht.lottery.entity.CompanyOdds;
import com.ht.lottery.entity.CompanyOdds2;
import com.ht.lottery.entity.CompanyYaOdds;
import com.ht.lottery.entity.MatchInfo;
import com.ht.lottery.entity.OddsResultAnalysis;
import com.ht.lottery.jpa.CompanyOddsRepository;
import com.ht.lottery.jpa.CompanyYaOddsRepository;
import com.ht.lottery.jpa.MatchInfoRepository;
import com.ht.lottery.jpa.MatchOddsRepository;
import com.ht.lottery.jpa.MatchTeamInfoRepository;
import com.ht.lottery.jpa.OddsResultAnalysisRepository;
import com.ht.lottery.utils.CompanyConstants;

@Service
public class AnalysisDataService {
	private static final Logger logger = LoggerFactory.getLogger(AnalysisDataService.class);
	
	@Autowired
	private MatchInfoRepository matchInfoRepository;
	
	@Autowired
	private MatchOddsRepository matchOddsRepository;
	
	@Autowired
	private MatchTeamInfoRepository matchTeamInfoRepository;
	
	@Autowired
	private CompanyOddsRepository companyOddsRepository;
	
	@Autowired
	private CompanyYaOddsRepository companyYaOddsRepository;
	
	@Autowired
	private OddsResultAnalysisRepository oddsResultAnalysisRepository;
	
	public void exe(double od1, double od2, int flag, int upOrDown) {
//		oddsResultAnalysisRepository.deleteAll();
		
//		double od1 = 1.5;
//		double od2 = 1.2;
//		int flag = 0;
//		int upOrDown = 2;
		List<MatchInfo> matchInfos = matchInfoRepository.getMatchInfosByReportBetween("20170101", "20171231");
		List<String> list = CompanyConstants.oulist;
		for (String string : list) {
			init(matchInfos, string, od1, od2, flag, upOrDown);
		}
	}
	
	private void init(List<MatchInfo> matchInfos, String name, double od1, double od2, int flag, int upOrDown) {
		List<CompanyOdds> companyOdds = companyOddsRepository.getCompanyOddsByCompanyName(name);
		Map<String, CompanyOdds> companyOddsMap = new HashMap<String, CompanyOdds>();
		for (CompanyOdds co : companyOdds) {
			companyOddsMap.put(co.getMatchInfoId(), co);
		}
		List<CompanyOdds2> list = new ArrayList<CompanyOdds2>();
		for (MatchInfo matchInfo : matchInfos) {
			if(matchInfo.getResult() == null)
				continue;
			CompanyOdds2 odds2 = new CompanyOdds2();
			int result = matchInfo.getResult();
			odds2.setResult(result);
			
			CompanyOdds o1 = companyOddsMap.get(matchInfo.getId());
			if(o1 != null) {
				odds2.setCompanyName(o1.getCompanyName());
				odds2.setMatchName(o1.getMatchName());
				odds2.setOddsWin1(o1.getOddsWin());
				odds2.setOddsDraw1(o1.getOddsDraw());
				odds2.setOddsLose1(o1.getOddsLose());
				odds2.setOddsWin2(o1.getOddsWin2());
				odds2.setOddsDraw2(o1.getOddsDraw2());
				odds2.setOddsLose2(o1.getOddsLose2());
				odds2.setO1(od1);
				odds2.setO2(od2);
				odds2.setFlag(flag);
				list.add(odds2);
			}
		}
		
		Map<String, OddsResultAnalysis> map = new HashMap<String, OddsResultAnalysis>();
		List<OddsResultAnalysis> all = new ArrayList<OddsResultAnalysis>();
		for (CompanyOdds2 companyOdds2 : list) {
			String matchName = companyOdds2.getMatchName();
			String companyName = companyOdds2.getCompanyName();
			OddsResultAnalysis analysis = null;
			if(map.containsKey(matchName)) {
				analysis = map.get(matchName);
			}else {
				analysis = new OddsResultAnalysis();
				analysis.setCompanyName(companyName);
				analysis.setMatchName(matchName);
				analysis.setFlag(flag);
				analysis.setRan(od2+"-"+od1);
				analysis.setUpOrDown(upOrDown);
				map.put(matchName, analysis);
				
				all.add(analysis);
			}
			
			if(upOrDown == 1) {
				analysis.setSuccess(analysis.getSuccess()+companyOdds2.getR5());
				analysis.setTotal(analysis.getTotal() + companyOdds2.getR5_());
			}else {
				analysis.setSuccess(analysis.getSuccess()+companyOdds2.getR6());
				analysis.setTotal(analysis.getTotal() + companyOdds2.getR6_());
			}
			
			
			
			OddsResultAnalysis totalAnalysis = null;
			if(map.containsKey(companyName)) {
				totalAnalysis = map.get(companyName);
			}else {
				totalAnalysis = new OddsResultAnalysis();
				totalAnalysis.setCompanyName(companyName);
				totalAnalysis.setMatchName("合计");
				totalAnalysis.setFlag(flag);
				totalAnalysis.setRan(od2+"-"+od1);
				totalAnalysis.setUpOrDown(upOrDown);
				map.put(companyName, totalAnalysis);
				
				all.add(totalAnalysis);
			}
			
			if(upOrDown == 1) {
				totalAnalysis.setSuccess(totalAnalysis.getSuccess()+companyOdds2.getR5());
				totalAnalysis.setTotal(totalAnalysis.getTotal() + companyOdds2.getR5_());
			}else {
				totalAnalysis.setSuccess(totalAnalysis.getSuccess()+companyOdds2.getR6());
				totalAnalysis.setTotal(totalAnalysis.getTotal() + companyOdds2.getR6_());
			}
		}
		
		oddsResultAnalysisRepository.save(all);
	}
	
	
	public void exeYazhi() {
		List<MatchInfo> matchInfos = matchInfoRepository.getMatchInfosByReportBetween("20170101", "20171231");
		List<String> list = CompanyConstants.yalist;
		for (String name : list) {
			initYa(matchInfos, name);
			break;
		}
	}
	
	private void initYa(List<MatchInfo> matchInfos, String name) {
		List<CompanyYaOdds> companyYaOdds = companyYaOddsRepository.getCompanyYaOddsByCompanyName(name);
		Map<String, CompanyYaOdds> companyYaOddsMap = new HashMap<String, CompanyYaOdds>();
		for (CompanyYaOdds co : companyYaOdds) {
			companyYaOddsMap.put(co.getMatchInfoId(), co);
		}
		
		int total = 0;
		int success =0; 
		for (MatchInfo matchInfo : matchInfos) {
			String id = matchInfo.getId();
			CompanyYaOdds yaOdds =  companyYaOddsMap.get(id);
			if(yaOdds != null) {
				int r = YaAnalysisDataService.analysis(yaOdds);
				if(r != -1) {
					success += r;
					total++;
				}
			}
		}
		
		double rate = (double)success/total;
		System.out.println(name+"=="+rate);
	}
}
