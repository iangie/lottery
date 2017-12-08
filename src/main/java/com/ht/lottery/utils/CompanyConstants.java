/**
 * 
 */
package com.ht.lottery.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanlei
 *
 */
public class CompanyConstants {
	public static List<String> oulist;
	public static List<String> yalist;
	static {
		oulist = new ArrayList<String>();
		oulist.add("Pinnacle平博");
		oulist.add("澳门");
		oulist.add("威廉希尔");
		oulist.add("立博");
		oulist.add("伟德");
		oulist.add("必发");
		oulist.add("皇冠");
		oulist.add("易胜博");
		oulist.add("SportingBet (博天堂)");
		oulist.add("Bet365");
		oulist.add("Bwin");
		oulist.add("Eurobet");
		oulist.add("SNAI");
		oulist.add("Interwetten");
		oulist.add("竞彩官方");
		
		yalist = new ArrayList<String>();
		
		yalist.add("澳门");
		yalist.add("Bet365");
		yalist.add("皇冠");
		yalist.add("易胜博");
		yalist.add("伟德");
		yalist.add("Pinnacle平博");
		yalist.add("10BET");
		yalist.add("利记");
		yalist.add("Unibet (优胜客)");
		yalist.add("Mansion88 (明升)");
		yalist.add("金宝博");
		yalist.add("香港马会");
	}
	
	public static boolean constainsOu(String company) {
		return oulist.contains(company);
	}
	
	public static boolean constainsYa(String company) {
		return yalist.contains(company);
	}
}
