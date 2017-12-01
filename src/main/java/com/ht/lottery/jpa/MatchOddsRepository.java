/**
 * 
 */
package com.ht.lottery.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.lottery.entity.MatchOdds;

/**
 * @author angie
 *
 */
public interface MatchOddsRepository extends JpaRepository<MatchOdds, Long>{
	
	
}
