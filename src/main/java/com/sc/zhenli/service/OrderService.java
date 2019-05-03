package com.sc.zhenli.service;

import com.sc.zhenli.bean.OrderBean;
import com.sc.zhenli.common.dao.OrderRecordMapper;
import com.sc.zhenli.common.dao.RelOrderProductMapper;
import com.sc.zhenli.common.model.RelOrderProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by songsf on 2019/5/3.
 */
@Service
public class OrderService {
    @Autowired
    private OrderRecordMapper orderMapper;
    @Autowired
    private RelOrderProductMapper relOrderProductMapper;

    public OrderBean addOrder(OrderBean body){
        body.setCreateTime(new Date());
        orderMapper.insertSelective(body);
        List<RelOrderProduct> products = body.getProducts();
        if (products != null && products.size() > 0){
            for (RelOrderProduct product : products) {
                product.setRelOrderId(body.getId());
                product.setCreater(body.getCreater());
                product.setCreateDate(new Date());
                relOrderProductMapper.insertSelective(product);
            }
        }
        return body;
    }
}
