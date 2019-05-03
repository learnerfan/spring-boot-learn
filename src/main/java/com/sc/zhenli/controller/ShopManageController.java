package com.sc.zhenli.controller;

import com.alibaba.fastjson.JSONObject;
import com.sc.zhenli.common.model.Product;
import com.sc.zhenli.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by songsf on 2019/4/22.
 */
@RestController
@RequestMapping("/api/v1.0/product")
public class ShopManageController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "",method = RequestMethod.POST)
    public Product addProduct(@RequestBody() Product body){
        productService.addProduct(body);
        return body;
    }

    @RequestMapping(value = "/{shopId}",method = RequestMethod.GET)
    public JSONObject getProductsByShopId(@PathVariable()Integer shopId){
        JSONObject response = new JSONObject();
        response.put("products",productService.getProductsByShopId(shopId));
        return response;
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public JSONObject deleteProductsId(@PathVariable()Integer id){
        JSONObject response = new JSONObject();
        response.put("num",productService.deleteProductById(id));
        return response;
    }

}
