/**
 * 
 */
package com.ht.lottery.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hanlei
 *
 */
public class CompanyConstants {
	public static List<String> oulist;
	public static List<String> yalist;
	public static Map<String, Double> waterlist;
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
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		waterlist = new HashMap<String, Double>();
		waterlist.put("平手",0.0);
		waterlist.put("平手/半球",0.25);
		waterlist.put("半球",0.5);
		waterlist.put("半球/一球",0.75);
		waterlist.put("受半球/一球",-0.75);
		waterlist.put("受半球 ",-0.5d);
		waterlist.put("球半/两球",1.75);
		waterlist.put("球半",1.5);
		waterlist.put("两球/两球半",2.25);
		waterlist.put("一球/球半",1.25);
		waterlist.put("一球",1.0);
		waterlist.put("受平手/半球",-0.25);
		waterlist.put("两球",2.0);
		waterlist.put("受一球/球半",-1.25);
		waterlist.put("受一球 ",-1.0);
		waterlist.put("受球半 ",-1.5);
		waterlist.put("受球半/两球",-1.75);
		waterlist.put("受两球半 ",-2.5);
		waterlist.put("三球/三球半",-3.25);
		waterlist.put("三球",3.0);
		waterlist.put("两球半 ",2.5);
		waterlist.put("受两球 ",-2.0);
		waterlist.put("受两球/两球半",-2.25);
		waterlist.put("两球半/三球",2.75);
		waterlist.put("受两球半/三球",-2.75);
		waterlist.put("受三球半/四球",-3.75);
		waterlist.put("三球半/四球",3.75);
		waterlist.put("受三球 ",-3.0);
		waterlist.put("四球",4.0);
		waterlist.put("受三球半 ",-3.5);
		waterlist.put("四球半 ",4.5);
		waterlist.put("五球",5.0);
		waterlist.put("受三球/三球半",-3.25);
		waterlist.put("受四球 ",-4.0);
		waterlist.put("三球半 ",3.5);
		waterlist.put("五球半 ",5.5);
		waterlist.put("五球/五球半",5.25);
		waterlist.put("五球半/六球",5.75);
		waterlist.put("六球",6.0);
		waterlist.put("四球/四球半",4.25);
		waterlist.put("七球/七球半",7.25);
		waterlist.put("七球",7.0);
		waterlist.put("四球半/五球",4.25);
		waterlist.put("受四球半 ",-4.5);
		waterlist.put("受五球/五球半",-5.25);
		waterlist.put("受五球 ",-5.0);
		waterlist.put("六球半 ",6.5);
		waterlist.put("六球/六球半",6.25);
		waterlist.put("受四球半/五球",-4.25);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	public static boolean constainsOu(String company) {
		return oulist.contains(company);
	}
	
	public static boolean constainsYa(String company) {
		return yalist.contains(company);
	}
}
