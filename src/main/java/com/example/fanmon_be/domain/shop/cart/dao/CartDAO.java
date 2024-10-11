package com.example.fanmon_be.domain.shop.cart.dao;

import com.example.fanmon_be.domain.shop.cart.entity.Cart;
import com.example.fanmon_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartDAO extends JpaRepository<Cart, UUID> {

    //장바구니 목록 조회
    List<Cart> findByUser(User user);

    //장바구니에 이미 있는 상품인지 확인 : true-있음, false-없음
    boolean existsByUserUseruuidAndGoodsGoodsuuid(UUID useruuid, UUID goodsuuid);

    //useruuid와 goodsuuid로 장바구니 레코드를 조회
    Cart findByUserUseruuidAndGoodsGoodsuuid(UUID useruuid, UUID goodsuuid);

    //장바구니 목록에서 유저의 uuid와 장바구니 시퀀스로 레코드 조회
    Optional<Cart> findByUserAndCartsequence(User user, Long cartsequence);

    //장바구니에 저장한 상품 삭제
    int deleteByUseruuid(UUID useruuid);
}
