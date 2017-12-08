package com.ht.lottery;


import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ht.lottery.service.AnalysisDataService;
import com.ht.lottery.service.CrawlerMatchInfoService;
import com.ht.lottery.service.RecommendService;
import com.ht.lottery.utils.DateUtils;

@RestController
@SpringBootApplication
@EnableJpaAuditing
public class LotteryApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(LotteryApplication.class);
	
	@Autowired
	private CrawlerMatchInfoService crawlerMatchInfoService;
	
	@Autowired
	private AnalysisDataService analysisDataService;
	
	@Autowired
	private RecommendService recommendService;
	
	@RequestMapping("/")
	public String index() {
		String date = "2017-01-01";
//		for (int i = 0; i < 1; i++) {
//			try {
//				String d = DateUtils.getDate(date, i);
//				logger.info("处理："+d);
//				crawlerMatchInfoService.exe(d);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
//		analysisDataService.exe();
		System.out.println("完成");
		return "success";
	}
	
	@RequestMapping("/r/{report}")
	public String r(@PathVariable String report) {
//		String report = "20171207";
		for (int i = 0; i < 200; i++) {
			try {
				String d = DateUtils.getDate(report, i);
				logger.info("处理："+d);
				recommendService.exe(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		System.out.println("完成");
		return "success";
	}
	
	@RequestMapping("/updateOuzhi/{report}")
	public String updateOuzhi(@PathVariable String report) {
		this.crawlerMatchInfoService.updateOuzhi(report);
		System.out.println("完成");
		return "success";
	}
	
	@RequestMapping("/updateResult/{report}")
	public String updateResult(@PathVariable String report) {
		this.crawlerMatchInfoService.crawlerMatchResult(report);
		System.out.println("完成");
		return "success";
	}

	public static void main(String[] args) {
		SpringApplication.run(LotteryApplication.class, args);
		
	}
}
