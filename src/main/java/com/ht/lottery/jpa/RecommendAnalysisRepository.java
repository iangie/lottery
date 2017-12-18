/**
 * 
 */
package com.ht.lottery.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ht.lottery.entity.RecommendAnalysis;

/**
 * @author angie
 *
 */
public interface RecommendAnalysisRepository extends JpaRepository<RecommendAnalysis, Long>{
	@Modifying
	@Query("delete from RecommendAnalysis where report=?1")
	@Transactional
	void deleteByReport(String report);
}
