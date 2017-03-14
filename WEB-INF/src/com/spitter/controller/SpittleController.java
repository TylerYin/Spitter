package com.spitter.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spitter.orm.domain.Spittle;
import com.spitter.service.SpittleRepositoryService;
import com.spitter.service.rest.SpitterRestClient;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

	@Autowired
	private SpitterRestClient spitterRestClient;

	private SpittleRepositoryService spittleRepositoryService;

	@Autowired
	public SpittleController(SpittleRepositoryService spittleRepositoryService) {
		this.spittleRepositoryService = spittleRepositoryService;
	}

	/*
	 * 下面方法没有显式的往model对象中set值，Spring MVC会根据返回值的类型往model中set值。
	 * 如下面的情况，程序就会给model中set一个名为spittleList，值为查询结果的对象，这样前台页面通过这个值就能拿到该对象。
	 */
	// @RolesAllowed("ROLE_USER")
	@RequestMapping(method = RequestMethod.GET)
	public List<Spittle> spittles() {
		return spitterRestClient.spittles();
	}

	@RequestMapping(value = "/{spittleId}", method = RequestMethod.GET)
	public String spittle(@PathVariable("spittleId") long spittleId, Model model) {
		model.addAttribute(spittleRepositoryService.findOne(spittleId));
		return "spittle";
	}

	@RequestMapping(value = "/addSpittle", method = RequestMethod.GET)
	public String addSpittle(Model model) throws Exception {
		model.addAttribute(new Spittle());
		return "addSpittle";
	}

	@RequestMapping(value = "/addSpittle", method = RequestMethod.POST)
	public String saveSpittle(@Valid Spittle spittle, Errors errors, Model model) throws Exception {
		if (errors.hasErrors()) {
			return "addSpittle";
		}
		// spittleRepository.save(new Spittle(null, spittle.getMessage(), new Date(),
		// spittle.getLongitude(), spittle.getLatitude()));
		return "redirect:/spittles";
	}
}
