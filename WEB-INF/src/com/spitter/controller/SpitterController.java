package com.spitter.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
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

import com.spitter.activemq.alerts.JMS;
import com.spitter.domain.User;
import com.spitter.orm.domain.Spitter;
import com.spitter.service.rest.SpitterRestClient;

@Controller
@RequestMapping("/spitter")
public class SpitterController {

	private static final Logger Logger = LoggerFactory.getLogger(SpitterController.class);

	/**
	 * Resource注解与Autowired注解的区别： 1.@Autowired与@Resource都可以用来装配bean.
	 * 都可以写在字段上,或写在setter方法上。
	 * 2.@Autowired默认按类型装配（这个注解是属业spring的），默认情况下必须要求依赖对象必须存在，如果要允许null
	 * 值，可以设置它的required属性为false。
	 * 3.@Resource（这个注解属于J2EE的），默认安照名称进行装配，名称可以通过name属性进行指定，
	 * 如果没有指定name属性，当注解写在字段上时，默认取字段名进行按照名称查找，如果注解写在setter方法上默认取属性名进行装配。
	 * 当找不到与名称匹配的bean时才按照类型进行装配。但是需要注意的是，如果name属性一旦指定，就只会按照名称进行装配。
	 */

	@Resource
	private JMS activeMQJMS;

	@Resource
	private JMS amqpJMSImpl;

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

		if (Logger.isInfoEnabled()) {
			Logger.info(spitter.getUsername() + "注册用户");
		}

		spitterRestClient.saveSpitter(spitter);
		activeMQJMS.sendSpittleAlert(spitter);
		amqpJMSImpl.sendSpittleAlert(spitter);
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
