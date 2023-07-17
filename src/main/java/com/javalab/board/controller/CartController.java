package com.javalab.board.controller;

import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.javalab.board.dto.*;
import com.javalab.board.repository.BasketProductRepository;
import com.javalab.board.repository.BasketRepository;
import com.javalab.board.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalab.board.entity.BasketProduct;
import com.javalab.board.entity.Member;
import com.javalab.board.entity.Product;
import com.javalab.board.service.BasketService;
import com.javalab.board.service.MemberService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private BasketService basketService;
	
	@Autowired
	private MemberService memberService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private BasketProductRepository basketProductRepository;
	
    @GetMapping("/cart")
    public String cartView(HttpSession session, Model model, BasketProductDTO basketProductDTO) {
    	Object member = session.getAttribute("loggedInUser");
    	List<BasketProductDTO> basketProducts = basketService.getBasketProducts(member);
    	if (basketProducts != null) {
    		
    		BasketDTO basket = basketService.getBasket(member);

        	model.addAttribute("basketProducts" , basketProducts);
        	model.addAttribute("basket", basket);
    	} else {
    		model.addAttribute("msg", "장바구니가 비어있습니다");
    	}
    	
    	return "account/cart";
    }

	@PostMapping("/create")
	public ResponseEntity<?> basketCreate(@RequestBody BasketDTO basketDTO, HttpSession session) {
		System.out.println("basketDTO :: " + basketDTO);
		String memberId = (String) session.getAttribute("loggedInUser");
		basketService.createBasket2(basketDTO, memberId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	@GetMapping("/order")
	public String productOrder(@RequestParam("basketQty") int basketQty, @RequestParam("productNo") int productNo, Model model) {
		BasketProductDTO basketProductDTO = orderService.createOrderPage(basketQty, productNo);
		model.addAttribute("basketProductDTO", basketProductDTO);
		return "account/order";
	}

	
	@PostMapping("/update")
	// json으로 받아서 자동 바인딩됨.
	public String updateCart(@RequestBody UpdateBasketDTO updateBasketDTO) {
		basketService.updateBasket(updateBasketDTO);
		return "redirect:/user/cart";
	}
	
	@GetMapping("/delete/{basketNo}/{basketProductNo}")
	public String deleteBasketProduct(@PathVariable Integer basketProductNo, @PathVariable Integer basketNo) {
		basketService.deleteBasketProduct(basketProductNo);
		
	    // Basket 엔티티의 총 가격에서 삭제한 상품의 가격을 차감합니다.
	    Integer totalPrice = basketService.getCurrentTotalPrice(basketNo);
//	    Integer updatedTotalPrice = currentTotalPrice - deletedProductPrice;
	    basketService.updateTotalPrice(totalPrice, basketNo);
//	    basketService.updateTotalPrice(updatedTotalPrice);
	    
	    
		return "redirect:/cart/cart";
	}

	@PostMapping("/buy")
	public ResponseEntity<?> buy(@RequestBody BasketDTO basketDTO) throws JsonProcessingException {
		System.out.println("basketDTO :: " +basketDTO.getBasketProductDTOs());
		Map<Integer, Integer> item = new HashMap<>();

		for (BasketProductDTO basketProductDTO : basketDTO.getBasketProductDTOs()) {
			Integer basketProductNo = basketProductDTO.getBasketProductNo();
			Optional<BasketProduct> basketProduct = basketProductRepository.findById(basketProductNo);
			item.put(basketProduct.get().getBasketProductNo(),basketProduct.get().getBasketQty());
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonItem = objectMapper.writeValueAsString(item);
		String encodedItem = URLEncoder.encode("item[]", StandardCharsets.UTF_8)
				+ "="
				+ URLEncoder.encode(jsonItem, StandardCharsets.UTF_8);

		return ResponseEntity.status(HttpStatus.OK).body(encodedItem);
	}

	@GetMapping("/buy")
	public String buy(@RequestParam("item[]") String encodedItem, Model model) {
		String decodedItem = URLDecoder.decode(encodedItem, StandardCharsets.UTF_8);

		Gson gson = new Gson();
		Map<Integer, Integer> item = gson.fromJson(decodedItem, new TypeToken<Map<Integer, Integer>>() {}.getType());


		List<BasketProductDTO> basketProductDTOs = new ArrayList<>();
		Integer totalAmount = 0;

		for (Map.Entry<Integer, Integer> entry : item.entrySet()) {
			System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
			List<BasketProductDTO> basketProductDTO = orderService.createOrdersPage(entry.getKey());
			basketProductDTOs.addAll(basketProductDTO);
			totalAmount += basketProductDTO.get(0).getBasketProductAmount();
		}

		model.addAttribute("basketProductDTOs", basketProductDTOs);
		model.addAttribute("totalAmount",totalAmount );

		return "account/orders";
	}





}