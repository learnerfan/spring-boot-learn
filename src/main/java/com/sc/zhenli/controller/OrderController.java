package com.sc.zhenli.controller;

import com.alibaba.fastjson.JSONObject;
import com.sc.zhenli.bean.OrderBean;
import com.sc.zhenli.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by songsf on 2019/5/3.
 */
@RestController
@RequestMapping("/api/v1.0/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "",method = RequestMethod.POST)
    public JSONObject addOrder(@RequestBody()OrderBean body){
        JSONObject response = new JSONObject();
        response.put("data",orderService.addOrder(body));
        return response;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONObject getOrderList(@RequestParam()Integer shopId,
                                   @RequestParam()Integer pageNum,
                                   @RequestParam()Integer pageSize){
        JSONObject response = new JSONObject();

        return response;
    }
}
