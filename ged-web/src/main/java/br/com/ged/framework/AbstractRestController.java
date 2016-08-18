package br.com.ged.framework;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;

public abstract class AbstractRestController {

	protected ResponseEntity<String> toJson(Object obj){
		
		return new ResponseEntity<String>(new Gson().toJson(obj), HttpStatus.OK);
	}
	
	protected ResponseEntity<String> status (HttpStatus obj){
		
		return new ResponseEntity<String>(obj);
	} 
}
