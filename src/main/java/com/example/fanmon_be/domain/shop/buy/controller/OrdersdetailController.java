package com.example.fanmon_be.domain.shop.buy.controller;

import com.example.fanmon_be.domain.shop.buy.service.OrdersdetailService;
import com.example.fanmon_be.domain.shop.goods.dto.GoodsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/management/ordersdetail")
public class OrdersdetailController {

    @Autowired
    private OrdersdetailService ordersdetailService;

    //굿즈 판매량 순 정렬
    @GetMapping("/qtyOrderby/{managementuuid}")
    public ResponseEntity<List<GoodsDTO>> getGoodsListCount(@PathVariable UUID managementuuid){
        List<GoodsDTO> list = ordersdetailService.goodsVolumeOrderby(managementuuid);
        for(GoodsDTO objects : list){
            System.out.println(objects);
        }
        return ResponseEntity.ok(list);
    }

    //management 별 총판매량
    @GetMapping("/qtySum/{managementuuid}")
    public ResponseEntity<Long> sumQtyByManagement(@PathVariable UUID managementuuid){
        Long sum = ordersdetailService.sumQtyByManagementuuid(managementuuid);
        return ResponseEntity.ok(sum);
    }
    //management 별 총판매액
    @GetMapping("/totalcostSum/{managementuuid}")
    public ResponseEntity<Long> sumTotalCostByManagement(@PathVariable UUID managementuuid){
        Long sum = ordersdetailService.sumTotalCostByManagementuuid(managementuuid);
        return ResponseEntity.ok(sum);
    }
}
