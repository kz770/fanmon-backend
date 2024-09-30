package com.example.fanmon_be.domain.shop.goods.entity;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.management.entity.Management;
import com.example.fanmon_be.domain.shop.goods.enums.GoodsCategory;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Entity
@Data
@Table(name="goods")
public class Goods {

    @Id
    @Column(name = "goodsuuid", nullable = false)
    private UUID goodsuuid;

    @PrePersist
    public void generateUUID(){
        if(goodsuuid == null){
            goodsuuid = UUID.randomUUID();
        }
    }

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name="managementuuid", nullable = false)
    private Management management;

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name="teamuuid", nullable = false)
    private Team team;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private GoodsCategory category = GoodsCategory.OTHERS;

    private String name;
    private long qty;
    private long price;
    private String fname;

    @Transient
    private MultipartFile uploadfile;
}
