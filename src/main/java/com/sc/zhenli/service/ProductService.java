package com.sc.zhenli.service;

import com.sc.zhenli.common.dao.ProductMapper;
import com.sc.zhenli.common.model.Product;
import com.sc.zhenli.common.model.ProductExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by songsf on 2019/4/22.
 */
@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    public Product addProduct(Product product){
        product.setCreateDate(new Date());
        //生成产品编码

        productMapper.insert(product);
        return product;
    }

    public List<Product> getProductsByShopId(Integer shopId){
        ProductExample productExample = new ProductExample();
        productExample.createCriteria()
                .andShopIdEqualTo(shopId);
        return productMapper.selectByExample(productExample);
    }
    public int deleteProductById(Integer id){
        return productMapper.deleteByPrimaryKey(id);
    }
}
