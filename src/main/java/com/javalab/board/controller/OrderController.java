package com.javalab.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javalab.board.dto.OrderDTO;
import com.javalab.board.dto.OrderProductDTO;
import com.javalab.board.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@PostMapping("/create")
	public String createOrder(OrderDTO orderDTO, OrderProductDTO orderProductDTO, HttpSession session) {
		String userId = (String) session.getAttribute("loggedInUser");
		orderService.createOrder(orderDTO, orderProductDTO, userId);
		return "redirect:/";
	}
	
	@PostMapping("/creates")
	public String createOrders(@RequestBody OrderDTO orderDTO, HttpSession session) {
		String userId = (String) session.getAttribute("loggedInUser");
		orderService.createOrders(orderDTO, userId);
		return "redirect:/";
	}
}