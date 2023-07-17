package com.javalab.board.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import com.javalab.board.entity.Product;
import com.javalab.board.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalab.board.dto.BasketDTO;
import com.javalab.board.dto.BasketProductDTO;

import com.javalab.board.dto.MemberDTO;
import com.javalab.board.dto.UpdateBasketDTO;
import com.javalab.board.entity.Basket;
import com.javalab.board.entity.BasketProduct;
import com.javalab.board.entity.Member;
import com.javalab.board.repository.BasketProductRepository;
import com.javalab.board.repository.BasketRepository;
import com.javalab.board.repository.MemberRepository;

@Service
public class BasketServiceImpl implements BasketService{

	
	@Autowired
	private BasketRepository basketRepository;
	
	@Autowired 
	private BasketProductRepository basketProductRepository;
	
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ProductRepository productRepository;


	@Override
	public List<BasketProductDTO> getBasketProducts(Object memberId) {
//		MemberDTO memberDTO =  (MemberDTO) member;
		Optional<Basket> basket =  basketRepository.findBasketByMemberMemberId(memberId);
		
		if (basket.isPresent()) {
			List<BasketProduct> basketProducts = basketProductRepository.findByBasketBasketNo(basket.get().getBasketNo());
			
			return basketProducts.stream()
					.map(entity -> BasketProductDTO.builder()
							.basketProductNo(entity.getBasketProductNo())
							.basketQty(entity.getBasketQty())
							.basketProductAmount(entity.getBasketProductAmount())
							.basket(entity.getBasket())
							.product(entity.getProduct())
							.build()).collect(Collectors.toList());
		} else {
			return null;
		}
		
	}

	@Override
	public void updateBasket(UpdateBasketDTO updateBasketDTO) {
		// 업데이트 할 basketProduct 가져옴
		Optional<BasketProduct> basketProduct = basketProductRepository.findById(updateBasketDTO.getBasketProductNo());
		Optional<Basket> basket = basketRepository.findById(updateBasketDTO.getBasketNo());
		Optional<Product> product = productRepository.findById(basketProduct.get().getProduct().getProductNo());

		if (basketProduct.isPresent()) {

			BasketProduct existingProduct = basketProduct.get();
			Basket existingBasket = basket.get();
			Integer cartItemPrice = updateBasketDTO.getBasketQty() * product.get().getProductPrice();
			existingProduct.setBasketQty(updateBasketDTO.getBasketQty());
			existingProduct.setBasketProductAmount(updateBasketDTO.getBasketQty() * product.get().getProductPrice());

			// 장바구니의 basketAmount 계산

			List<BasketProduct> basketProducts = basketProductRepository.findByBasket(existingBasket);
			int basketAmount = 0;
			for (BasketProduct basketProduct2 : basketProducts) {
				basketAmount += basketProduct2.getBasketProductAmount();
			}
			existingBasket.setBasketAmount(basketAmount);

			basketRepository.save(existingBasket);
			basketProductRepository.save(existingProduct);
		}
	}



	@Override
	public BasketDTO getBasket(Object memberId) {
//		MemberDTO memberDTO =  (MemberDTO) member;
		Optional<Basket> basket =  basketRepository.findBasketByMemberMemberId(memberId);
		
		BasketDTO basketDTO = BasketDTO.builder()
				.basketAmount(basket.get().getBasketAmount())
				.build();
		
		return basketDTO;
	}

	@Override
	public void deleteBasketProduct(Integer basketProductNo) {
		basketProductRepository.deleteById(basketProductNo);
	}
	
	@Override
	public Integer getCurrentTotalPrice(Integer basketNo) {
	    Integer totalPrice = 0;
	    List<BasketProduct> basketProducts = basketProductRepository.findByBasketBasketNo(basketNo);
	      
	    for (BasketProduct basketProduct : basketProducts) {
			totalPrice += basketProduct.getBasketProductAmount();
		}   
	    return totalPrice;
	}

	@Override
	public void updateTotalPrice(Integer totalPrice, Integer basketNo) {
		Optional<Basket> basket = basketRepository.findById(basketNo);
		Basket updateBasket = basket.get();
		updateBasket.setBasketAmount(totalPrice);
		basketRepository.save(updateBasket);
	}


	


	@Override
	public void createBasket2(BasketDTO basketDTO, String memberId) {
		Integer productNo = basketDTO.getBasketProductDTOs().get(0).getProductDTO().getProductNo();


		Optional<Basket> basket =  basketRepository.findBasketByMemberMemberId(memberId);

		Optional<Product> optionalProduct = productRepository.findById(productNo);
		Integer productPrice = optionalProduct.get().getProductPrice();

		if (basket.isPresent()) {


			BasketProduct basketProduct = BasketProduct.builder()
					.basketProductAmount(productPrice * basketDTO.getBasketProductDTOs().get(0).getBasketQty())
					.basketQty(basketDTO.getBasketProductDTOs().get(0).getBasketQty())
					.product(Product.builder()
							.productNo(basketDTO.getBasketProductDTOs().get(0).getProductDTO().getProductNo())
							.build())
					.basket(basket.get())
					.build();

			Basket existingBasket = basket.get();
			existingBasket.setBasketAmount(existingBasket.getBasketAmount() + productPrice * basketDTO.getBasketProductDTOs().get(0).getBasketQty());

			basketRepository.save(existingBasket);

			basketProductRepository.save(basketProduct);

		} else {

			Member member = memberRepository.findByMemberId((String) memberId);


			Basket newBasket = Basket.builder()
					.basketNo(basketDTO.getBasketNo())
					.basketAmount(productPrice * basketDTO.getBasketProductDTOs().get(0).getBasketQty())
					.member(member)
					.build();

			Basket createdBasket = basketRepository.save(newBasket);

			BasketProduct basketProduct = BasketProduct.builder()
					.basketProductAmount(productPrice * basketDTO.getBasketProductDTOs().get(0).getBasketQty())
					.basketQty(basketDTO.getBasketProductDTOs().get(0).getBasketQty())
					.product(Product.builder()
							.productNo(basketDTO.getBasketProductDTOs().get(0).getProductDTO().getProductNo())
							.build())
					.basket(createdBasket)
					.build();

			basketProductRepository.save(basketProduct);
		}

	}


}