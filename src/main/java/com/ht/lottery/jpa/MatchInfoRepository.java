/**
 * 
 */
package com.ht.lottery.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ht.lottery.entity.MatchInfo;

/**
 * @author angie
 *
 */
public interface MatchInfoRepository extends JpaRepository<MatchInfo, String>{
	List<MatchInfo> getMatchInfosByReportBetween(String report1, String report2);
	List<MatchInfo> getMatchInfosByReport(String report);
	
	@Query("update MatchInfo set score=?2,result=?3 where id=?1")
	@Modifying
	@Transactional
	void updateResult(String id, String score, int result);
}
