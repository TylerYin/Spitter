package com.spitter.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spitter.orm.domain.Product;
import com.spitter.redis.RedisSendMessageService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private RedisSendMessageService redisSendMessageService;

	private final String CART = "cart";

	private final String REDIS_TOPIC = "productQueue";

	@RequestMapping(method = GET)
	public String home(Model model) {
		
		Product product = redisSendMessageService.generateProduct();

		// 点对点模式
		redisSendMessageService.writeProductIntoRedisByP2P(CART, product);

		// 订阅发布模式
		redisSendMessageService.writeProductIntoRedisBySB(REDIS_TOPIC, product);
		
		return "home";
	}
}