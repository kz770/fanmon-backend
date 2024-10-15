package com.example.fanmon_be.domain.shop.buy.service;

import com.example.fanmon_be.domain.shop.buy.dao.OrdersdetailDAO;
import com.example.fanmon_be.domain.shop.buy.entity.Ordersdetail;
import com.example.fanmon_be.domain.shop.goods.dto.GoodsDTO;
import com.example.fanmon_be.domain.shop.goods.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrdersdetailService {

    @Autowired
    private OrdersdetailDAO ordersdetailDAO;

    //굿즈 판매량 순 정렬
    public List<GoodsDTO> goodsVolumeOrderby(UUID managementuuid){
        return ordersdetailDAO.goodsVolumeOrderby(managementuuid);
    }

    //management 별 총판매량
    public Long sumQtyByManagementuuid(UUID managementuuid){
        return ordersdetailDAO.sumQtyByManagementUuid(managementuuid);
    }

    //management 별 총판매액
    public Long sumTotalCostByManagementuuid(UUID managementuuid){
        return ordersdetailDAO.sumTotalCostByManagementUuid(managementuuid);
    }

    // 굿즈 결제 디테일
    public void save(Ordersdetail ordersdetail){
        ordersdetailDAO.save(ordersdetail);
    }
}
