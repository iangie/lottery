package com.ht.lottery;


import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ht.lottery.service.AnalysisDataService;
import com.ht.lottery.service.BasicAnalysisDataService;
import com.ht.lottery.service.CrawlerMatchInfoService;
import com.ht.lottery.service.RecommendService;
import com.ht.lottery.utils.DateUtils;

@RestController
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class LotteryApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(LotteryApplication.class);
	
	@Autowired
	private CrawlerMatchInfoService crawlerMatchInfoService;
	
	@Autowired
	private AnalysisDataService analysisDataService;
	
	@Autowired
	private RecommendService recommendService;
	
	@Autowired
	private BasicAnalysisDataService basicAnalysisDataService; 
	
	@RequestMapping("/ba")
	public String ba() {
		String report = "20171218";
		for (int i = 0; i < 2; i++) {
			try {
				String d = DateUtils.getDate(report, i);
				logger.info("处理："+d);
				basicAnalysisDataService.exe(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		System.out.println("完成");
		return "success";
	}
	
	@RequestMapping("/f")
	public String f() {
		logger.info("开始");
		double od1 = 3.5;
		double od2 = 2.5;
		int flag = 3;
		int upOrDown = 1;
		analysisDataService.exe(od1, od2, flag, upOrDown);
		
		logger.info("完成");
		return "success";
	}
	
	@RequestMapping("/ya")
	public String ya() {
		logger.info("开始");
		analysisDataService.exeYazhi();
		
		logger.info("完成");
		return "success";
	}
	
	@RequestMapping("/s")
	public String start() {
		logger.info("开始");
		String yestoday = DateUtils.getYesterday();
		crawlerMatchInfoService.crawlerMatchResult(yestoday);
		
		recommendService.exeAnalysis(yestoday);
		
		String today = DateUtils.getToday("yyyy-MM-dd");
		crawlerMatchInfoService.exe(today);
		
		today = DateUtils.getToday();
		
		recommendService.exe(today);
		
		logger.info("完成");
		return "success";
	}
	
//	@Scheduled(fixedRate = 3600000)
	public String schedul() {
		logger.info("开始");
		String today = DateUtils.getToday();
		crawlerMatchInfoService.updateOuzhi(today);
		logger.info("完成");
		return "success";
	}
	
	
	@RequestMapping("/")
	public String index() {
		logger.info("开始");
//		String date = "2017-12-12";
		String today = DateUtils.getToday("yyyy-MM-dd");
		crawlerMatchInfoService.exe(today);
		logger.info("完成");
		return "success";
	}
	
	@RequestMapping("/r/{report}")
	public String r(@PathVariable String report) {
//		String report = "20171207";
		for (int i = 0; i < 1; i++) {
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
	
	@RequestMapping("updateYazhi/{report}")
	public String updateYazhi(@PathVariable String report) {
		logger.info("处理："+report);
		crawlerMatchInfoService.updateYazhi(report);
		logger.info("完成");
		return "success";
	}
	
	@RequestMapping("/updateOuzhi/{report}")
	public String updateOuzhi(@PathVariable String report) {
		logger.info("处理："+report);
		this.crawlerMatchInfoService.updateOuzhi(report);
		logger.info("完成");
		return "success";
	}
	
	@RequestMapping("/updateResult/{report}")
	public String updateResult(@PathVariable String report) {
		logger.info("处理："+report);
		this.crawlerMatchInfoService.crawlerMatchResult(report);
		logger.info("完成");
		return "success";
	}
	
	@RequestMapping("/exeAnalysis/{report}")
	public String exeAnalysis(@PathVariable String report) {
		for (int i = 0; i < 1; i++) {
			try {
				String d = DateUtils.getDate(report, i);
				logger.info("处理："+d);
				recommendService.exeAnalysis(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		System.out.println("完成");
		return "success";
	}

	public static void main(String[] args) {
		SpringApplication.run(LotteryApplication.class, args);
		
	}
}
