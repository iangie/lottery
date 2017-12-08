/**
 * 
 */
package com.ht.lottery.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ht.lottery.entity.CompanyOdds;
import com.ht.lottery.entity.Recommend;

/**
 * @author angie
 *
 */
public interface RecommendRepository extends JpaRepository<Recommend, Long>{
	@Query("delete from Recommend where report=?1")
	@Modifying
	@Transactional
	void deleteByReport(String report);
	
	List<Recommend> getRecommendsByReport(String report);
}
