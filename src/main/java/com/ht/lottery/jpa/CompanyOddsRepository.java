/**
 * 
 */
package com.ht.lottery.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.lottery.entity.CompanyOdds;

/**
 * @author angie
 *
 */
public interface CompanyOddsRepository extends JpaRepository<CompanyOdds, Long>{
	
	
}
