package com.omincell.demo.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.omincell.demo.domain.Recipe;
import com.omincell.demo.repo.RecipeRepository;

@Component
public class DatabaseMigration {
	
	@Autowired
	RecipeRepository recipeRepository;

	private List<Recipe> getRecipesFromClient(String url){
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Recipe>> rateResponse =
		        restTemplate.exchange(url,
		                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Recipe>>() {
		            });

		List<Recipe> recipeList = rateResponse.getBody();
		
		return recipeList;
	}
	
	@EventListener(ApplicationReadyEvent.class)
	private void MigrateToDB() {
        
          String url="https://s3-ap-southeast-1.amazonaws.com/he-public-data/reciped9d7b8c.json";
          
          List<Recipe> recipesList=	getRecipesFromClient(url);

          recipeRepository.saveAll(recipesList);
		
	}
	

	 
	
	
	
	
	
	 
}
