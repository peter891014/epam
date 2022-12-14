package com.epam.secKill.utill;

import com.epam.secKill.entity.ProductInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class tet {
    public static void main(String[] args) {
        List<ProductInfo> list = new ArrayList<>();
        ProductInfo p= new ProductInfo();
        p.setProductId("123");
        p.setProductName("aaa");
        list.add(p);
        p.setProductId("234");
        list.add(p);
        System.out.println(list.get(0).toString());
        System.out.println(list.get(1).toString());


        Optional<ProductInfo> opts =list.stream().filter(s -> s.getProductId().isEmpty()).findAny();
        System.out.println(opts.get() !=null);
    }
}
