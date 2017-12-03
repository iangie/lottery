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
	private static List<String> list;
	static {
		list = new ArrayList<String>();
		list.add("Pinnacle平博");
		list.add("澳门");
		list.add("威廉希尔");
		list.add("立博");
		list.add("伟德");
		list.add("必发");
		list.add("皇冠");
		list.add("易胜博");
		list.add("SportingBet (博天堂)");
		list.add("Bet365");
		list.add("Bwin");
		list.add("Eurobet");
		list.add("SNAI");
		list.add("Interwetten");
		list.add("竞彩官方");
	}
	
	public static boolean constains(String company) {
		return list.contains(company);
	}
}
