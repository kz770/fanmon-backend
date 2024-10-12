package com.example.fanmon_be.domain.shop.goods.dto;

import com.example.fanmon_be.domain.shop.goods.entity.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDTO {
    private Goods goods;
    private Long volume; //sum(qty)의 결과
}
