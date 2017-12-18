package com.ht.lottery.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.ht.lottery.entity.Recommend;
import com.ht.lottery.entity.RecommendAnalysis;
import com.ht.lottery.jpa.CompanyOddsRepository;
import com.ht.lottery.jpa.MatchInfoRepository;
import com.ht.lottery.jpa.MatchOddsRepository;
import com.ht.lottery.jpa.MatchTeamInfoRepository;
import com.ht.lottery.jpa.OddsResultAnalysisRepository;
import com.ht.lottery.jpa.RecommendAnalysisRepository;
import com.ht.lottery.jpa.RecommendRepository;
import com.ht.lottery.utils.CompanyConstants;

@Service
public class RecommendService {
	private static final Logger logger = LoggerFactory.getLogger(RecommendService.class);
	
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
	
	@Autowired
	private RecommendRepository recommendRepository;
	
	@Autowired
	private RecommendAnalysisRepository recommendAnalysisRepository;
	
	public void exe2(String report) {
//		recommendRepository.deleteByReport(report);
		
		List<MatchInfo> list = matchInfoRepository.getMatchInfosByReport(report);
		if(list != null && list.size() > 0 ) {
			Map<String, List<OddsResultAnalysis>> resultsMap = new HashMap<String, List<OddsResultAnalysis>>();
			
			List<Recommend> result = new ArrayList<Recommend>();
			for (MatchInfo matchInfo : list) {
				String matchName = matchInfo.getMatchName();
				String matchInfoId = matchInfo.getId();
				String vs = matchInfo.getVs();
				Date matchTime = matchInfo.getMatchTime();
				
				List<OddsResultAnalysis> results = resultsMap.get(matchName);
				if(results == null) {
					results = oddsResultAnalysisRepository.getOddsResultAnalysisByMatchNameAndTotalGreaterThanEqualOrderByRateDesc(matchName, 10);
					resultsMap.put(matchName, results);
				}
				
				Map<String, CompanyOdds> map = new HashMap<String, CompanyOdds>();
				
				if(results != null && results.size() > 0) {
					double max = 0;
					int rec = -1;
					for (OddsResultAnalysis oddsResultAnalysis : results) {
						String companyName = oddsResultAnalysis.getCompanyName();
						CompanyOdds companyOdd = map.get(companyName+"_"+matchInfoId);
						if(companyOdd == null) {
							companyOdd = companyOddsRepository.getCompanyOddsByCompanyNameAndMatchInfoId(companyName, matchInfoId);
							map.put(companyName+"_"+matchInfoId, companyOdd);
						}
						
						if(companyOdd != null) {
							if(oddsResultAnalysis.getFlag() == 3) {
								if(oddsResultAnalysis.getUpOrDown() == 1) {
									double min = Math.min(Math.min(companyOdd.getOddsWin2(), companyOdd.getOddsDraw2()), companyOdd.getOddsLose2());
									if(min == companyOdd.getOddsWin2()) {
										String range = oddsResultAnalysis.getRan();
										double o1 = Double.valueOf(range.split("-")[1]);
										double o2 = Double.valueOf(range.split("-")[0]);
										if(companyOdd.getOddsWin2() >= o2 && companyOdd.getOddsWin() <= o1) {
											double r = (double)oddsResultAnalysis.getSuccess()/oddsResultAnalysis.getTotal();
											if(max < r) {
//												max = r;
												rec = 3;
												break;
											}
										}
									}
								}else if(oddsResultAnalysis.getUpOrDown() == 2) {
									double min = Math.min(Math.min(companyOdd.getOddsWin2(), companyOdd.getOddsDraw2()), companyOdd.getOddsLose2());
									if(min == companyOdd.getOddsWin2()) {
										String range = oddsResultAnalysis.getRan();
										double o1 = Double.valueOf(range.split("-")[1]);
										double o2 = Double.valueOf(range.split("-")[0]);
										if(companyOdd.getOddsWin() >= o2 && companyOdd.getOddsWin2() <= o1) {
											double r = (double)oddsResultAnalysis.getSuccess()/oddsResultAnalysis.getTotal();
											if(max < r) {
												max = r;
												rec = 3;
												break;
											}
										}
									}
								}
								
							}else if(oddsResultAnalysis.getFlag() == 0) {
								if(oddsResultAnalysis.getUpOrDown() == 1) {
									double min = Math.min(Math.min(companyOdd.getOddsWin2(), companyOdd.getOddsDraw2()), companyOdd.getOddsLose2());
									if(min == companyOdd.getOddsLose2()) {
										String range = oddsResultAnalysis.getRan();
										double o1 = Double.valueOf(range.split("-")[1]);
										double o2 = Double.valueOf(range.split("-")[0]);
										if(companyOdd.getOddsLose2() >= o2 && companyOdd.getOddsLose() <= o1) {
											double r = (double)oddsResultAnalysis.getSuccess()/oddsResultAnalysis.getTotal();
											if(max < r) {
												max = r;
												rec = 0;
											}
										}
									}
								}else if(oddsResultAnalysis.getUpOrDown() == 2) {
									double min = Math.min(Math.min(companyOdd.getOddsWin2(), companyOdd.getOddsDraw2()), companyOdd.getOddsLose2());
									if(min == companyOdd.getOddsLose2()) {
										String range = oddsResultAnalysis.getRan();
										double o1 = Double.valueOf(range.split("-")[1]);
										double o2 = Double.valueOf(range.split("-")[0]);
										if(companyOdd.getOddsLose() >= o2 && companyOdd.getOddsLose2() <= o1) {
											double r = (double)oddsResultAnalysis.getSuccess()/oddsResultAnalysis.getTotal();
											if(max < r) {
												max = r;
												rec = 0;
											}
										}
									}
								}
							}
						}
					}
					
					if(max >= 0.7) {
						System.out.println(matchInfoId + ":rate is " + max +" result is " + rec + " and match result is :"+matchInfo.getResult());
						Recommend recommend = new Recommend();
						recommend.setMatchInfoId(matchInfoId);
						recommend.setMatchName(matchName);
						recommend.setVs(vs);
						recommend.setMatchTime(matchTime);
						BigDecimal b = new BigDecimal(max);  
						max = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();  
						recommend.setRate(max);
						recommend.setReport(report);
						recommend.setRecmmend(rec);
						
						result.add(recommend);
					}
				}
			}
			
			if(result.size() > 0) {
				recommendRepository.save(result);
			}
		}
	}
	
	public void exe(String report) {
		recommendRepository.deleteByReport(report);
		List<MatchInfo> list = matchInfoRepository.getMatchInfosByReport(report);
		if(list != null && list.size() > 0 ) {
			Map<String, List<OddsResultAnalysis>> resultsMap = new HashMap<String, List<OddsResultAnalysis>>();
			
			List<Recommend> result = new ArrayList<Recommend>();
			for (MatchInfo matchInfo : list) {
				String matchName = matchInfo.getMatchName();
				String matchInfoId = matchInfo.getId();
				String vs = matchInfo.getVs();
				Date matchTime = matchInfo.getMatchTime();
				
				List<OddsResultAnalysis> results = resultsMap.get(matchName);
				if(results == null) {
					results = oddsResultAnalysisRepository.getOddsResultAnalysisByMatchNameAndTotalGreaterThanEqualOrderByRateDesc(matchName, 10);
					if(results == null || results.size() == 0) {
						results = oddsResultAnalysisRepository.getOddsResultAnalysisByMatchNameAndTotalGreaterThanEqualOrderByRateDesc(matchName, 5);
					}
					if(results == null || results.size() == 0) {
						results = oddsResultAnalysisRepository.getOddsResultAnalysisByMatchNameAndTotalGreaterThanEqualOrderByRateDesc("合计", 10);
					}
					resultsMap.put(matchName, results);
				}
				
				Map<String, CompanyOdds> map = new HashMap<String, CompanyOdds>();
				
				if(results != null && results.size() > 0) {
					double max = 0;
					int rec = -1;
					for (OddsResultAnalysis oddsResultAnalysis : results) {
						String companyName = oddsResultAnalysis.getCompanyName();
						CompanyOdds companyOdd = map.get(companyName+"_"+matchInfoId);
						if(companyOdd == null) {
							companyOdd = companyOddsRepository.getCompanyOddsByCompanyNameAndMatchInfoId(companyName, matchInfoId);
							map.put(companyName+"_"+matchInfoId, companyOdd);
						}
						
						if(companyOdd != null) {
							if(oddsResultAnalysis.getFlag() == 3) {
								if(oddsResultAnalysis.getUpOrDown() == 1) {
									double min = Math.min(Math.min(companyOdd.getOddsWin2(), companyOdd.getOddsDraw2()), companyOdd.getOddsLose2());
									if(min == companyOdd.getOddsWin2()) {
										String range = oddsResultAnalysis.getRan();
										double o1 = Double.valueOf(range.split("-")[1]);
										double o2 = Double.valueOf(range.split("-")[0]);
										if(o2 <= companyOdd.getOddsWin2() && companyOdd.getOddsWin2() < companyOdd.getOddsWin() && companyOdd.getOddsWin() <= o1) {
											double r = oddsResultAnalysis.getRate();
											if(max < r) {
												max = r;
												rec = 3;
												break;
											}
										}
									}
								}else if(oddsResultAnalysis.getUpOrDown() == 2) {
									double min = Math.min(Math.min(companyOdd.getOddsWin2(), companyOdd.getOddsDraw2()), companyOdd.getOddsLose2());
									if(min == companyOdd.getOddsWin2()) {
										String range = oddsResultAnalysis.getRan();
										double o1 = Double.valueOf(range.split("-")[1]);
										double o2 = Double.valueOf(range.split("-")[0]);
										if(o2 <= companyOdd.getOddsWin() && companyOdd.getOddsWin() < companyOdd.getOddsWin2() && companyOdd.getOddsWin2() <= o1) {
											double r = oddsResultAnalysis.getRate();
											if(max < r) {
												max = r;
												rec = 3;
												break;
											}
										}
									}
								}
								
							}else if(oddsResultAnalysis.getFlag() == 0) {
								if(oddsResultAnalysis.getUpOrDown() == 1) {
									double min = Math.min(Math.min(companyOdd.getOddsWin2(), companyOdd.getOddsDraw2()), companyOdd.getOddsLose2());
									if(min == companyOdd.getOddsLose2()) {
										String range = oddsResultAnalysis.getRan();
										double o1 = Double.valueOf(range.split("-")[1]);
										double o2 = Double.valueOf(range.split("-")[0]);
										if(o2 <= companyOdd.getOddsLose2() && companyOdd.getOddsLose2() < companyOdd.getOddsLose() && companyOdd.getOddsLose() <= o1) {
											double r = oddsResultAnalysis.getRate();
											if(max < r) {
												max = r;
												rec = 0;
												break;
											}
										}
									}
								}else if(oddsResultAnalysis.getUpOrDown() == 2) {
									double min = Math.min(Math.min(companyOdd.getOddsWin2(), companyOdd.getOddsDraw2()), companyOdd.getOddsLose2());
									if(min == companyOdd.getOddsLose2()) {
										String range = oddsResultAnalysis.getRan();
										double o1 = Double.valueOf(range.split("-")[1]);
										double o2 = Double.valueOf(range.split("-")[0]);
										if(o2 <= companyOdd.getOddsLose() && companyOdd.getOddsLose() < companyOdd.getOddsLose2()  && companyOdd.getOddsLose2() <= o1) {
											double r = oddsResultAnalysis.getRate();
											if(max < r) {
												max = r;
												rec = 0;
												break;
											}
										}
									}
								}
							}
						}
					}
					
//					if(max >= 0.7) {
						System.out.println(matchInfoId + ":rate is " + max +" result is " + rec + " and match result is :"+matchInfo.getResult());
						Recommend recommend = new Recommend();
						recommend.setMatchInfoId(matchInfoId);
						recommend.setMatchName(matchName);
						recommend.setVs(vs);
						recommend.setMatchTime(matchTime);
						BigDecimal b = new BigDecimal(max);  
						max = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();  
						recommend.setRate(max);
						recommend.setReport(report);
						recommend.setRecmmend(rec);
						
						result.add(recommend);
//					}
				}
			}
			
			if(result.size() > 0) {
				recommendRepository.save(result);
			}
		}
	}
	
	
	public void exeAnalysis(String report) {
		List<Recommend> recommends = recommendRepository.getRecommendsByReport(report);
		if(recommends != null && recommends.size() > 0) {
			List<MatchInfo> matchInfos  = matchInfoRepository.getMatchInfosByReport(report);
			if(matchInfos != null && matchInfos.size() > 0) {
				Map<String, MatchInfo> map = new HashMap<String, MatchInfo>();
				for (MatchInfo matchInfo : matchInfos) {
					map.put(matchInfo.getId(), matchInfo);
				}
				
				int success = 0;
				int total = 0;
				
				for (Recommend recommend : recommends) {
					if(recommend.getRate()<0.7)
						continue;
					MatchInfo matchInfo = map.get(recommend.getMatchInfoId());
					if(matchInfo != null && matchInfo.getResult() != null) {
						if(matchInfo.getResult().equals(recommend.getRecmmend())){
							success ++;
						}
						total ++;
					}
				}
				if(total > 0) {
					RecommendAnalysis analysis = new RecommendAnalysis();
					double r = (double)success/total;
					BigDecimal b = new BigDecimal(r);  
					r = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();  
					analysis.setRate(r);
					analysis.setSuccess(success);
					analysis.setTotal(total);
					analysis.setReport(report);
					
					recommendAnalysisRepository.deleteByReport(report);
					
					recommendAnalysisRepository.save(analysis);
				}
			}
		}
	}
}
