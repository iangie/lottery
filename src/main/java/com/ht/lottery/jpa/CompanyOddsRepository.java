/**
 * 
 */
package com.ht.lottery.jpa;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ht.lottery.entity.CompanyOdds;

/**
 * @author angie
 *
 */
public interface CompanyOddsRepository extends JpaRepository<CompanyOdds, Long>{
	CompanyOdds getCompanyOddsByCompanyNameAndMatchInfoId(String companyName, String matchInfoId);
	List<CompanyOdds> getCompanyOddsByCompanyName(String CompanyName);
	
	@Query("update CompanyOdds set odds_win2=?2,odds_draw2=?3,odds_lose2=?4 where id=?1")
	@Modifying
	@Transactional
	void update(Long id, double oddsWin2, double oddsDraw2, double oddsLose2);
	
	@Query("update CompanyOdds set matchScore=?2,matchResult=?3 where matchInfoId=?1")
	@Modifying
	@Transactional
	void updateMatchResult(String matchInfoId, String matchScore, Integer matchResult);
}
