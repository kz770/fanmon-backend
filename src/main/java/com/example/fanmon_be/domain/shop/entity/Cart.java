package com.example.fanmon_be.domain.shop.entity;

import com.example.fanmon_be.domain.goods.entity.Goods;
import com.example.fanmon_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartsequence", updatable = false, nullable = false)
    private Long cartsequence;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "useruuid", referencedColumnName = "useruuid", insertable = true, updatable = true)
    private User user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "goodsuuid", referencedColumnName = "goodsuuid", insertable = true, updatable = true)
    private Goods goods;
    private LocalDateTime createdat;

}
