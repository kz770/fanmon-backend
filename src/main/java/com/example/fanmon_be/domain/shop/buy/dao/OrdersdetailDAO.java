package com.example.fanmon_be.domain.shop.buy.dao;

import com.example.fanmon_be.domain.shop.buy.entity.Ordersdetail;
import com.example.fanmon_be.domain.shop.goods.dto.GoodsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrdersdetailDAO extends JpaRepository<Ordersdetail, UUID> {

    @Query("select new com.example.fanmon_be.domain.shop.goods.dto.GoodsDTO(goods, sum(qty)) " +
            "from Ordersdetail where goods.management.managementuuid = :managementuuid " +
            "group by goods.goodsuuid order by sum(qty) desc")
    List<GoodsDTO> goodsVolumeOrderby(@Param("managementuuid") UUID managementuuid);

    @Query("select sum(qty) from Ordersdetail where goods.management.managementuuid=:managementuuid")
    Long sumQtyByManagementUuid(@Param("managementuuid") UUID managementuuid);

    @Query("select sum(totalcost) from Ordersdetail where goods.management.managementuuid=:managementuuid")
    Long sumTotalCostByManagementUuid(@Param("managementuuid") UUID managementuuid);
}
