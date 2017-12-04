/**
 * 
 */
package com.ht.lottery.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.lottery.entity.CompanyOdds;

/**
 * @author angie
 *
 */
public interface CompanyOddsRepository extends JpaRepository<CompanyOdds, Long>{
	List<CompanyOdds> getCompanyOddsByCompanyNameAndMatchInfoIdOrderByStatus(String CompanyName, String matchInfoId);
	List<CompanyOdds> getCompanyOddsByCompanyNameAndMatchInfoIdStartingWith(String CompanyName, String matchInfoId);
	List<CompanyOdds> getCompanyOddsByCompanyName(String CompanyName);
}
