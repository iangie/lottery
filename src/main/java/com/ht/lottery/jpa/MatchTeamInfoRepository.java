/**
 * 
 */
package com.ht.lottery.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.lottery.entity.MatchTeamInfo;

/**
 * @author angie
 *
 */
public interface MatchTeamInfoRepository extends JpaRepository<MatchTeamInfo, Long>{
	
	
}
