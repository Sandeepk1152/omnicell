package com.omincell.demo.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.omincell.demo.domain.Recipe;

@Repository
@Transactional
public interface RecipeRepository extends  CrudRepository<Recipe, Long> {
	
	List<Recipe> findAll();
 
	@Query("FROM Recipe u WHERE u.id = ?1")
	List<Recipe> findByCustomId(Long id);
	
	

	
}
