package com.example.fanmon_be.domain.shop.buy.entity;

import com.example.fanmon_be.domain.shop.goods.entity.Goods;
import com.example.fanmon_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "ordersdetail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ordersdetail {

    @Id
    @Column(name = "ordersdetailuuid", nullable = false)
    private UUID ordersdetailuuid;

    @PrePersist
    public void generateBaseColumns(){
        if(ordersdetailuuid == null){
            ordersdetailuuid = UUID.randomUUID();
        }
    }

    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name="useruuid", nullable = false)
    private User user;

    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name="ordersuuid", nullable = false)
    private Orders orders;

    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name="goodsuuid", nullable = false)
    private Goods goods;

    private long totalcost;
    private long qty;
}
