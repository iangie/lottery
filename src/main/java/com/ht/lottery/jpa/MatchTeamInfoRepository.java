/**
 * 
 */
package com.ht.lottery.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.lottery.entity.MatchTeamInfo;

/**
 * @author angie
 *
 */
public interface MatchTeamInfoRepository extends JpaRepository<MatchTeamInfo, Long>{
	List<MatchTeamInfo> getMatchTeamInfosByMatchInfoIdOrderByHomeOrAway(String matchInfoId);
	
}
