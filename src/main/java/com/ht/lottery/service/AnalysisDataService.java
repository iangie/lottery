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
import com.ht.lottery.jpa.CompanyOddsRepository;
import com.ht.lottery.jpa.MatchInfoRepository;
import com.ht.lottery.jpa.MatchOddsRepository;
import com.ht.lottery.jpa.MatchTeamInfoRepository;

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
	
	public void exe() {
		List<MatchInfo> matchInfos = matchInfoRepository.getMatchInfosByReportBetween("20170101", "20171231");
//		List<CompanyOdds> companyOdds = companyOddsRepository.getCompanyOddsByCompanyNameAndMatchInfoIdStartingWith("澳门", "201701");
		List<CompanyOdds> companyOdds = companyOddsRepository.getCompanyOddsByCompanyName("澳门");
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
				odds2.setOddsWin1(o1.getOddsWin());
				odds2.setOddsDraw1(o1.getOddsDraw());
				odds2.setOddsLose1(o1.getOddsLose());
				odds2.setOddsWin2(o2.getOddsWin());
				odds2.setOddsDraw2(o2.getOddsDraw());
				odds2.setOddsLose2(o2.getOddsLose());
				
				list.add(odds2);
			}
		}
		
		int r = 0;
		for (CompanyOdds2 companyOdds2 : list) {
			r += companyOdds2.getR();
		}
		int r2 = 0;
		for (CompanyOdds2 companyOdds2 : list) {
			r2 += companyOdds2.getR2();
		}
		int r3 = 0;
		int r3_ = 0;
		for (CompanyOdds2 companyOdds2 : list) {
			r3 += companyOdds2.getR3();
			r3_ += companyOdds2.getR3_();
		}
		
		int r4 = 0;
		int r4_ = 0;
		for (CompanyOdds2 companyOdds2 : list) {
			r4 += companyOdds2.getR4();
			r4_ += companyOdds2.getR4_();
		}
		
		System.out.println(r+"/"+list.size()+"====="+(double)r/list.size());
		System.out.println(r2+"/"+list.size()+"====="+(double)r2/list.size());
		System.out.println(r3+"/"+r3_+"====="+(double)r3/r3_);
		System.out.println(r4+"/"+r4_+"====="+(double)r4/r4_);
	}
}
