/**
 * 
 */
package com.ht.lottery.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ht.lottery.entity.BasicRecommend;

/**
 * @author angie
 *
 */
public interface BasicRecommendRepository extends JpaRepository<BasicRecommend, Long>{
	BasicRecommend getBasicRecommendByMatchInfoId(String matchInfoId);
}
