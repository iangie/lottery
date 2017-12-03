package com.ht.lottery;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ht.lottery.service.CrawlerMatchInfoService;
import com.ht.lottery.utils.DateUtils;

@RestController
@SpringBootApplication
@EnableJpaAuditing
public class LotteryApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(LotteryApplication.class);
	
	@Autowired
	CrawlerMatchInfoService crawlerMatchInfoService;
	
	@RequestMapping("/")
	public String index() {
		String date = "2017-01-01";
		for (int i = 0; i < 100; i++) {
			try {
				String d = DateUtils.getDate(date, i);
				logger.info("处理："+d);
				crawlerMatchInfoService.exe(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return "success";
	}

	public static void main(String[] args) {
		SpringApplication.run(LotteryApplication.class, args);
		
	}
}
