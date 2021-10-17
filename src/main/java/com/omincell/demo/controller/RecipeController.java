package com.omincell.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.omincell.demo.domain.Recipe;
import com.omincell.demo.repo.RecipeRepository;


@RestController
public class RecipeController {
    
	 @Autowired
	 RecipeRepository recipeRepository;
    	
	 
	 @PostMapping( value="/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Recipe> save(@RequestBody Recipe recipe){
		 
		 Recipe recipe1=recipeRepository.save(recipe);
		 
		   if(recipe1!=null) {
		    	return new ResponseEntity<Recipe>(recipe1, HttpStatus.OK);
		    }else {
		    	return  new ResponseEntity<Recipe>(HttpStatus.BAD_REQUEST);
		    } 
		 
	 }
	 
	 @GetMapping( value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id ){
		  
		 try {
			  List<Recipe> recipe=  recipeRepository.findByCustomId(id);
			    
			    return new ResponseEntity<Recipe>(recipe.get(0), HttpStatus.OK);
			
			} catch (Exception e) {
				return new ResponseEntity<Recipe>(HttpStatus.BAD_REQUEST);
			}
	 }
	 
	 @GetMapping( value = "/{id}/show",produces = MediaType.IMAGE_JPEG_VALUE)
	 public ResponseEntity<byte[]> getImageOfRecipe(@PathVariable Long id ){
		  
		 try {
			   
   			          List<Recipe> recipe=  recipeRepository.findByCustomId(id);
			    	  RestTemplate restTemplate=new RestTemplate();
			    	  String url = recipe.get(0).getImage();
			          byte[] imageBytes = restTemplate.getForObject(url, byte[].class);
			          
			    	
			    	return new ResponseEntity<byte[]>(imageBytes,HttpStatus.OK);
			
			} catch (Exception e) {
				return new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
			}
	 }
	 
	@GetMapping( value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<Recipe>> findAll() {
		try {
			return new ResponseEntity<Iterable<Recipe>>(recipeRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Iterable<Recipe>>(HttpStatus.BAD_REQUEST);
		}
  }
}
