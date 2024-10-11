package com.example.fanmon_be.domain.shop.buy.entity;

import com.example.fanmon_be.domain.shop.buy.enums.OrdersStatus;
import com.example.fanmon_be.domain.shop.goods.entity.Goods;
import com.example.fanmon_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    @Column(name = "ordersuuid", nullable = false)
    private UUID ordersuuid;

    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name="useruuid", nullable = false)
    private User user;

    private String applynum;
    private String merchantuid;
    private String address;
    private long totalcost;
    private LocalDateTime createdat;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrdersStatus status; //구매완료, 배송중, 배송완료, 환불중, 환불완료

    private long qty;

}
