package com.ht.lottery.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ht.lottery.entity.CompanyYaOdds;
import com.ht.lottery.utils.CompanyConstants;

@Service
public class YaAnalysisDataService {
	private static final Logger logger = LoggerFactory.getLogger(YaAnalysisDataService.class);
	
	public static int analysis(CompanyYaOdds yaOdds) {
		int r = ao(yaOdds);
		if(r == -1)
			return -1;
		if(yaOdds.getMatchResult() != null) {
			if(r == yaOdds.getMatchResult()) {
				return 1;
			}else {
				return 0;
			}
		}
		return -1;
	}
	
	private static int ao(CompanyYaOdds yaOdds) {
		String c1 = yaOdds.getConcede1();
		String c2 = yaOdds.getConcede2();
		double d1 = -999;
		double d2 = -999;
		if(CompanyConstants.waterlist.containsKey(c1)) {
			d1 = CompanyConstants.waterlist.get(c1);
		}
		if(CompanyConstants.waterlist.containsKey(c2)) {
			d2 = CompanyConstants.waterlist.get(c2);
		}
		
		double u1 = yaOdds.getWaterlevelUpper1();
		double l1 = yaOdds.getWaterlevelLower1();
		double u2 = yaOdds.getWaterlevelUpper2();
		double l2 = yaOdds.getWaterlevelLower2();
		if(d2 > d1) {
			if(d1 > 0) {
				if(u2 > u1) {
					return 3;
				}else if( u2 < u1){
					return 0;
				}else {
					return 1;
				}
			}else if(d2 < 0){
				if(l2 > l1) {
					return 3;
				}else if( l2 < l1){
					return 0;
				}else {
					return 1;
				}
			}
			
		}else if(d2 < d1) {
			if(d2 > 0) {
				if(u2 > u1) {
					return 0;
				}else if( u2 < u1){
					return 3;
				}else {
					return 1;
				}
			}else if(d1 < 0) {
				if(l2 > l1) {
					return 0;
				}else if( l2 < l1){
					return 3;
				}else {
					return 1;
				}
			}
		}else if(d2 == d1 && d2 > -999) {
			if(u2 > u1) {
				return 0;
			}else if( u2 < u1){
				return 3;
			}else {
				return 1;
			}
		}
		
		return -1;
	}
}
