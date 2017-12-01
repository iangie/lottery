/**
 * 
 */
package com.ht.lottery.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.lottery.entity.MatchInfo;

/**
 * @author angie
 *
 */
public interface MatchInfoRepository extends JpaRepository<MatchInfo, String>{
	
	
}
