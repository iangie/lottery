/**
 * 
 */
package com.ht.lottery.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.lottery.entity.MatchInfo;

/**
 * @author angie
 *
 */
public interface MatchInfoRepository extends JpaRepository<MatchInfo, String>{
	List<MatchInfo> getMatchInfosByReportBetween(String report1, String report2);
	
}
