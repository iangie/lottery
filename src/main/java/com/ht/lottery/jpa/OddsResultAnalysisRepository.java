/**
 * 
 */
package com.ht.lottery.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.lottery.entity.OddsResultAnalysis;

/**
 * @author angie
 *
 */
public interface OddsResultAnalysisRepository extends JpaRepository<OddsResultAnalysis, Long>{
//	List<OddsResultAnalysis> getOddsResultAnalysisByMatchName(String matchName);
	List<OddsResultAnalysis> getOddsResultAnalysisByMatchNameAndTotalGreaterThanEqualOrderByRateDesc(String matchName, int total);
}
