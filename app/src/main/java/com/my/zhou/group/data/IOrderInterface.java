package com.my.zhou.group.data;

import com.my.zhou.group.bean.Order;
import com.my.zhou.group.bean.OrderItem;

import java.util.List;

/**
 * Created by laobai on 2017/4/26.
 */

public interface IOrderInterface {
    void setOrder(List<Order> list, List<OrderItem> list1);
}
