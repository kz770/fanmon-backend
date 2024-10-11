package com.example.fanmon_be.domain.shop.cart.service;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.shop.cart.dao.CartDAO;
import com.example.fanmon_be.domain.shop.cart.entity.Cart;
import com.example.fanmon_be.domain.shop.goods.dao.GoodsDAO;
import com.example.fanmon_be.domain.shop.goods.entity.Goods;
import com.example.fanmon_be.domain.user.dao.UserDAO;
import com.example.fanmon_be.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GoodsDAO goodsDAO;

    // 장바구니 목록 출력
    public List<Cart> findByUser(UUID useruuid) {

        User user = userDAO.findById(useruuid).orElse(null);
        if(useruuid != null) {
            return cartDAO.findByUser(user);
        }
        return Collections.emptyList();
    }

    //장바구니 담기 전 이미 담겨있는 상품인지 확인 : true-있음, false-없음
    public boolean existsInCart(UUID useruuid, UUID goodsuuid) {
        return cartDAO.existsByUserUseruuidAndGoodsGoodsuuid(useruuid, goodsuuid);
    }

    //장바구니에 새 상품 추가
    public boolean inputQty(UUID useruuid, UUID goodsuuid, long qty) {
        // 사용자 엔티티와 상품 엔티티를 UUID로 찾아옵니다.
        User user = userDAO.findById(useruuid).orElse(null);
        Goods goods = goodsDAO.findById(goodsuuid).orElse(null);

        // 사용자나 상품이 존재하지 않으면 false 반환
        if (user == null || goods == null) {
            return false; // 추가할 수 없음
        }
        Cart cart = new Cart();
        cart.setUser(user); // 사용자 UUID 설정
        cart.setGoods(goods); // 상품 UUID 설정
        cart.setQty(qty); // 수량 설정
        cartDAO.save(cart);

        return true;
    }

    //장바구니에 있는 특정 상품의 수량 업데이트
    public boolean plusQty(UUID useruuid, UUID goodsuuid, long qty) {
        Cart cart = cartDAO.findByUserUseruuidAndGoodsGoodsuuid(useruuid, goodsuuid);

        if (cart != null) {
            cart.setQty(cart.getQty() + qty); // 기존 수량에 전달받은 qty만큼 더하기
            System.out.println("엔티티 저장 전");
            cartDAO.save(cart); // 변경된 엔티티 저장
            System.out.println("엔티티에 저장함");
            return true;
        } else {
            return false; // 레코드가 없는 경우 업데이트 실패
        }
    }

    //장바구니 리스트에 담긴 목록 수량 변경
    public boolean updateQty(UUID useruuid, UUID goodsuuid, long qty) {
        Cart cart = cartDAO.findByUserUseruuidAndGoodsGoodsuuid(useruuid, goodsuuid);

        if (cart != null) {
            cart.setQty(qty); // 기존 수량에 전달받은 qty만큼 더하기
            System.out.println("엔티티 저장 전");
            cartDAO.save(cart); // 변경된 엔티티 저장
            System.out.println("엔티티에 저장함");
            return true;
        } else {
            return false; // 레코드가 없는 경우 업데이트 실패
        }
    }

    //장바구니 특정 상품 삭제
//    트랜잭션 허용해주시면 쓸게용.. 근데 이 어노테이션 없이도 잘만 돌아갑니덩
//    @Transactional
    public boolean deleteCartItem(UUID useruuid, Long cartsequence) {
        boolean result = false;
        User user = userDAO.findById(useruuid).orElse(null);

        try {
            //사용자 UUID와 cartSequence로 카트를 조회
            Optional<Cart> optionalCart = cartDAO.findByUserAndCartsequence(user, cartsequence);

            //카트가 존재하고, 사용자가 해당 카트의 소유자인지 확인
            if (optionalCart.isPresent()) {
                cartDAO.deleteByCartsequence(cartsequence);
                result = true;
            } else {
                System.out.println("해당 카트가 존재하지 않거나, 사용자가 소유하지 않는 카트입니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //결제 후 장바구니 비우기
    public boolean deleteByUseruuid(UUID useruuid) {
        boolean result = false;


        return result;
    }

}
