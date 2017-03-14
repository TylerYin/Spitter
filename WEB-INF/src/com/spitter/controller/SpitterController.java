package com.spitter.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spitter.activemq.alerts.ActiveMQJMS;
import com.spitter.domain.User;
import com.spitter.orm.domain.Spitter;
import com.spitter.service.rest.SpitterRestClient;

@Controller
@RequestMapping("/spitter")
public class SpitterController {

	private static final Logger Logger = LoggerFactory.getLogger(SpitterController.class);

	@Autowired
	private ActiveMQJMS alertService;

	@Autowired
	private SpitterRestClient spitterRestClient;

	@RequestMapping(value = "/register", method = GET)
	public String showRegistrationForm(Model model) {
		model.addAttribute("spitter", new Spitter());
		return "registerUser";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processRegistration(@Valid Spitter spitter, Errors errors) {
		if (errors.hasErrors()) {
			return "registerUser";
		}

		if(Logger.isInfoEnabled())
		{
			Logger.info(spitter.getUsername() + "注册用户");
		}
		
		spitterRestClient.saveSpitter(spitter);
		alertService.sendSpittleAlert(spitter);
		return "redirect:/spitter/" + spitter.getUsername();
	}

	@RequestMapping(value = "/downUserListExcel")
	public String showUserList(ModelMap modelMap) {
		modelMap.addAttribute("userList", getUserList());
		return "userListExcel";
	}

	@RequestMapping(value = "/downUserListPDF")
	public String showUserListInPdf(ModelMap modelMap) {
		modelMap.addAttribute("userList", getUserList());
		return "userListPDF";
	}

	@RequestMapping(value = "/downUserListXML")
	public String showUserListInXml(ModelMap modelMap) {
		modelMap.addAttribute("userList", getUserList());
		return "userListXML";
	}

	@RequestMapping(value = "/downUserListJSON")
	public String showUserListInJson(ModelMap modelMap) {
		modelMap.addAttribute("userList", getUserList());
		return "userListJSON";
	}

	private List<User> getUserList() {
		List<User> users = new ArrayList<>();
		User user1 = new User();
		user1.setName("Zhangsan");
		user1.setPhone("123");
		users.add(user1);

		User user2 = new User();
		user2.setName("Lisi");
		user2.setPhone("456");
		users.add(user2);

		User user3 = new User();
		user3.setName("Wangwu");
		user3.setPhone("789");
		users.add(user3);
		return users;
	}

	@RequestMapping(value = "/me", method = GET)
	public String me() {
		System.out.println("ME ME ME ME ME ME ME ME ME ME ME");
		return "home";
	}

	@RequestMapping(value = "/{username}", method = GET)
	public String showSpitterProfile(@PathVariable String username, Model model) {
		Spitter spitter = spitterRestClient.findByUserName(username);
		model.addAttribute(spitter);
		return "profile";
	}
}
