package com.javalab.board.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.javalab.board.dto.*;
import com.javalab.board.entity.*;
import com.javalab.board.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderProductRepository orderProductRepository;

	@Autowired
	private BasketProductRepository basketProductRepository;

	@Override
	public BasketProductDTO createOrderPage(int basketQty, int productNo) {
		Optional<Product> optionalProduct = productRepository.findById(productNo);


		BasketProductDTO basketProductDTO = new BasketProductDTO();
		basketProductDTO.setBasketQty(basketQty);
		basketProductDTO.setProductDTO(ProductDTO.builder()
				.productNo(productNo)
				.productName(optionalProduct.get().getProductName())
				.build());
		basketProductDTO.setBasketProductAmount(optionalProduct.get().getProductPrice() * basketQty);
		return basketProductDTO;
	}

	@Override
	public List<BasketProductDTO> createOrdersPage(int basketProductNo) {
		Optional<BasketProduct> basketProducts = basketProductRepository.findById(basketProductNo);
		BasketDTO basketDTO = new BasketDTO();


		basketProducts.ifPresent(basketProduct -> {
			BasketProductDTO basketProductDTO = new BasketProductDTO();
			basketProductDTO.setBasketProductNo(basketProduct.getBasketProductNo());
			basketProductDTO.setBasketProductAmount(basketProduct.getBasketProductAmount());
			basketProductDTO.setBasketQty(basketProduct.getBasketQty());
			basketProductDTO.setProduct(basketProduct.getProduct());

			basketDTO.setBasketProductDTOs(Collections.singletonList(basketProductDTO));
		});


		List<BasketProductDTO> basketProductDTOS = basketDTO.getBasketProductDTOs().stream()
				.map(basketProductDTO -> {
					BasketProductDTO newbasketProductDTO = new BasketProductDTO();
					newbasketProductDTO.setBasketProductNo(basketProductDTO.getBasketProductNo());
					newbasketProductDTO.setBasketQty(basketProductDTO.getBasketQty());
					newbasketProductDTO.setBasketProductAmount(basketProductDTO.getBasketProductAmount());
					newbasketProductDTO.setProductDTO(ProductDTO.builder()
									.productNo(basketProductDTO.getProduct().getProductNo())
							.build());
					return basketProductDTO;
				}).collect(Collectors.toList());

		return basketProductDTOS;
	}


	@Override
	public void createOrder(OrderDTO orderDTO, OrderProductDTO orderProductDTO, String userId) {
		
		
		
		Optional<Member> member = memberRepository.findById(userId);
		
		Optional<Product> product = productRepository.findById(orderProductDTO.getProduct().getProductNo());

			
		if (member.isPresent()) {
			Order order = Order.builder()
					.orderReceiverName(orderDTO.getOrderReceiverName())
					.orderReceiverAddress(orderDTO.getOrderReceiverAddress())
					.orderPaymentAmount(orderDTO.getOrderPaymentAmount())
					.orderReceiverPhone(orderDTO.getOrderReceiverPhone())
//					.orderDate(orderDTO.getOrderDate()) @Builder.Default 를 사용해서 안해줘도 됨(기본값이라)
					.member(member.get())
					.build();
			
			Order createdOrder = orderRepository.save(order);
			
			
			OrderProduct orderProduct = OrderProduct.builder()
					.orderProductAmount(orderProductDTO.getOrderProductAmount())
					.orderQty(orderProductDTO.getOrderQty())
					.order(createdOrder)
					.product(orderProductDTO.getProduct())
					.build();
			
			orderProductRepository.save(orderProduct);
			
		}
		
		
	}

	@Override
	@Transactional
	public void createOrders(OrderDTO orderDTO, String userId) {
		
		Member member = memberRepository.findByMemberId(userId);
	
		Order order = Order.builder()
				.orderReceiverName(orderDTO.getOrderReceiverName())
				.orderReceiverAddress(orderDTO.getOrderReceiverAddress())
				.orderReceiverPhone(orderDTO.getOrderReceiverPhone())
				.orderPaymentAmount(orderDTO.getOrderPaymentAmount())
				.orderDate(orderDTO.getOrderDate())
				.member(member)
				.build();
		
		Order savedOrder =  orderRepository.save(order);
		
		List<OrderProduct> orderProducts = orderDTO.getOrderProducts().stream()
				.map(product -> {
					OrderProduct orderProduct = dtoToEntity(product);
					orderProduct.setOrder(savedOrder);
					return orderProduct;
				}).collect(Collectors.toList());
		
		orderProductRepository.saveAll(orderProducts);
		

		
	}
	




}