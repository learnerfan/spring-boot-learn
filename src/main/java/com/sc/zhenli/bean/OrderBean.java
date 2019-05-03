package com.sc.zhenli.bean;

import com.sc.zhenli.common.model.OrderRecord;
import com.sc.zhenli.common.model.RelOrderProduct;
import lombok.Data;

import java.util.List;

/**
 * Created by songsf on 2019/5/3.
 */
@Data
public class OrderBean extends OrderRecord{
    private List<RelOrderProduct> products;
}
