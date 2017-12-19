/**
 * 
 */
package com.ht.lottery.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ht.lottery.entity.CompanyYaOdds;


/**
 * @author angie
 *
 */
public interface CompanyYaOddsRepository extends JpaRepository<CompanyYaOdds, Long>{
	CompanyYaOdds getCompanyYaOddsByCompanyNameAndMatchInfoId(String companyName, String matchInfoId);
	List<CompanyYaOdds> getCompanyYaOddsByCompanyName(String CompanyName);
	
	@Query("update CompanyYaOdds set concede2=?2,waterlevelUpper2=?3,waterlevelLower2=?4 where id=?1")
	@Modifying
	@Transactional
	void update(Long id, String concede2, double waterlevelUpper2, double waterlevelLower2);
	
	@Query("update CompanyYaOdds set matchScore=?2,matchResult=?3 where matchInfoId=?1")
	@Modifying
	@Transactional
	void updateMatchResult(String matchInfoId, String matchScore, Integer matchResult);
}
