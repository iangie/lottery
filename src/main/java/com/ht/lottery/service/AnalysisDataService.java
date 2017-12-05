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
import com.ht.lottery.entity.MatchInfo;
import com.ht.lottery.entity.OddsResultAnalysis;
import com.ht.lottery.jpa.CompanyOddsRepository;
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
	private OddsResultAnalysisRepository oddsResultAnalysisRepository;
	public void exe() {
//		oddsResultAnalysisRepository.deleteAll();
		
		double od1 = 2.5;
		double od2 = 1.9;
		int flag = 0;
		List<MatchInfo> matchInfos = matchInfoRepository.getMatchInfosByReportBetween("20170101", "20171231");
		List<String> list = CompanyConstants.list;
		for (String string : list) {
			init(matchInfos, string, od1, od2, flag);
		}
	}
	
	private void init(List<MatchInfo> matchInfos, String name, double od1, double od2, int flag) {
		List<CompanyOdds> companyOdds = companyOddsRepository.getCompanyOddsByCompanyName(name);
		Map<String, CompanyOdds> companyOddsMap = new HashMap<String, CompanyOdds>();
		for (CompanyOdds co : companyOdds) {
			companyOddsMap.put(co.getMatchInfoId()+"_"+co.getStatus(), co);
		}
		List<CompanyOdds2> list = new ArrayList<CompanyOdds2>();
		for (MatchInfo matchInfo : matchInfos) {
			if(matchInfo.getResult() == null)
				continue;
			CompanyOdds2 odds2 = new CompanyOdds2();
			int result = matchInfo.getResult();
			odds2.setResult(result);
			
			CompanyOdds o1 = companyOddsMap.get(matchInfo.getId()+"_0");
			CompanyOdds o2 = companyOddsMap.get(matchInfo.getId()+"_1");
			if(o1 != null && o2 != null) {
				odds2.setCompanyName(o1.getCompanyName());
				odds2.setMatchName(o1.getMatchName());
				odds2.setOddsWin1(o1.getOddsWin());
				odds2.setOddsDraw1(o1.getOddsDraw());
				odds2.setOddsLose1(o1.getOddsLose());
				odds2.setOddsWin2(o2.getOddsWin());
				odds2.setOddsDraw2(o2.getOddsDraw());
				odds2.setOddsLose2(o2.getOddsLose());
				odds2.setO1(od1);
				odds2.setO2(od2);
				odds2.setFlag(flag);
				list.add(odds2);
			}
		}
		
		Map<String, OddsResultAnalysis> map = new HashMap<String, OddsResultAnalysis>();
		List<OddsResultAnalysis> all = new ArrayList<OddsResultAnalysis>();
		for (CompanyOdds2 companyOdds2 : list) {
			String cname = companyOdds2.getMatchName();
			OddsResultAnalysis analysis = null;
			if(map.containsKey(cname)) {
				analysis = map.get(cname);
			}else {
				analysis = new OddsResultAnalysis();
				analysis.setCompanyName(companyOdds2.getCompanyName());
				analysis.setMatchName(cname);
				analysis.setFlag(flag);
				analysis.setRan(od2+"-"+od1);
				analysis.setUpOrDown(1);
				map.put(cname, analysis);
				
				all.add(analysis);
			}
			
			
			
			analysis.setSuccess(analysis.getSuccess()+companyOdds2.getR5());
			analysis.setTotal(analysis.getTotal() + companyOdds2.getR5_());
		}
		
		oddsResultAnalysisRepository.save(all);
		
//		int r = 0;
//		int r_= 0;
//		int r2 = 0;
//		int r2_= 0;
//		int r3 = 0;
//		int r3_ = 0;
//		int r4 = 0;
//		int r4_ = 0;
//		int r5 = 0;
//		int r5_ = 0;
//		int r6 = 0;
//		int r6_ = 0;
//		for (CompanyOdds2 companyOdds2 : list) {
//			String cname = companyOdds2.getMatchName();
//			r += companyOdds2.getR();
//			r_ += companyOdds2.getR_();
//			r2 += companyOdds2.getR2();
//			r2_ += companyOdds2.getR2_();
//			r3 += companyOdds2.getR3();
//			r3_ += companyOdds2.getR3_();
//			r4 += companyOdds2.getR4();
//			r4_ += companyOdds2.getR4_();
//			r5 += companyOdds2.getR5();
//			r5_ += companyOdds2.getR5_();
//			r6 += companyOdds2.getR6();
//			r6_ += companyOdds2.getR6_();
//		}
//		name="1";
//		System.out.println(name+"==="+r+"/"+r_+"====="+(double)r/r_);
//		System.out.println(name+"==="+r2+"/"+r2_+"====="+(double)r2/r2_);
//		System.out.println(name+"==="+r3+"/"+r3_+"====="+(double)r3/r3_);
//		System.out.println(name+"==="+r4+"/"+r4_+"====="+(double)r4/r4_);
//		System.out.println(name+"==="+r5+"/"+r5_+"====="+(double)r5/r5_);
//		System.out.println(name+"==="+r6+"/"+r6_+"====="+(double)r6/r6_);
//		System.out.println("===================");
	}
}
