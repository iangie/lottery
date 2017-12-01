package com.ht.lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ht.lottery.service.CrawlerMatchInfoService;

@RestController
@SpringBootApplication
@EnableJpaAuditing
public class LotteryApplication {
	
	@Autowired
	CrawlerMatchInfoService crawlerMatchInfoService;
	
	@RequestMapping("/")
	public String index() {
		String date = "2017-11-30";
		crawlerMatchInfoService.exe(date);
		return "success";
	}

	public static void main(String[] args) {
		SpringApplication.run(LotteryApplication.class, args);
		
	}
}
