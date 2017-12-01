/**
 * 
 */
package com.ht.lottery.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.lottery.entity.MatchInfo;

/**
 * @author hanlei8
 *
 */
public interface MatchInfoRepository extends JpaRepository<MatchInfo, String>{
	
	
}
