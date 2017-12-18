/**
 * 
 */
package com.ht.lottery.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ht.lottery.entity.Recommend;

/**
 * @author angie
 *
 */
public interface RecommendRepository extends JpaRepository<Recommend, Long>{
	@Modifying
	@Query("delete from Recommend where report=?1")
	@Transactional
	void deleteByReport(String report);
	
	List<Recommend> getRecommendsByReport(String report);
	
	@Query("update Recommend set matchResult=?2 where matchInfoId=?1")
	@Modifying
	@Transactional
	void updateMatchResult(String matchInfoId, int matchResult);
}
