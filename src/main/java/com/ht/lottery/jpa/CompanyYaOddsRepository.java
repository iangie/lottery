/**
 * 
 */
package com.ht.lottery.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.lottery.entity.CompanyYaOdds;


/**
 * @author angie
 *
 */
public interface CompanyYaOddsRepository extends JpaRepository<CompanyYaOdds, Long>{
	CompanyYaOdds getCompanyYaOddsByCompanyNameAndMatchInfoId(String companyName, String matchInfoId);
	List<CompanyYaOdds> getCompanyYaOddsByCompanyName(String CompanyName);
}
