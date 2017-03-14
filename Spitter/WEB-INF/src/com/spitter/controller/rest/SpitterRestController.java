package com.spitter.controller.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.spitter.error.Error;
import com.spitter.exception.SpittleNotFoundException;
import com.spitter.orm.domain.Spitter;
import com.spitter.orm.domain.Spittle;
import com.spitter.service.SpitterRepositoryService;
import com.spitter.service.SpittleRepositoryService;

@RestController
@RequestMapping("/rest")
public class SpitterRestController {

	/**
	 * @ResponseBody与@RestController註解的區別 
	 * 1.@ResponseBody和@RequestBody是启用消息转换的一种简洁和强大的方式。
	 * 2.使用@RestController来代替@Controller的话，Spring将会为该控制器的所有方法应用消息转换器功能
	 */

	@Autowired
	private SpittleRepositoryService spittleRepositoryService;

	@Autowired
	private SpitterRepositoryService spitterRepositoryService;

	@RequestMapping(value = "/{userName}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Spitter> findByUserName(@PathVariable String userName) {
		return new ResponseEntity<Spitter>(spitterRepositoryService.findByUserName(userName), HttpStatus.FOUND);
	}

	@ResponseStatus(HttpStatus.FOUND)
	@RequestMapping(value = "/spittles", method = RequestMethod.GET, produces = "application/json")
	public List<Spittle> spittles() {
		return spittleRepositoryService.findAll();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/saveSpitter", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Spitter> saveSpitter(@RequestBody Spitter spitter, UriComponentsBuilder ucb) {
		Spitter saved = spitterRepositoryService.save(spitter);

		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path("/spittles/").path(String.valueOf(saved.getId())).build().toUri();
		headers.setLocation(locationUri);

		ResponseEntity<Spitter> responseEntity = new ResponseEntity<Spitter>(saved, headers, HttpStatus.CREATED);
		return responseEntity;
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(SpittleNotFoundException.class)
	public @ResponseBody Error spittleNotFound(SpittleNotFoundException e) {
		long spittleId = e.getSpittleId();
		return new Error(4, "Spittle [" + spittleId + "] not found");
	}
}