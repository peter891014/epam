package com.epam.secKill.service;

import com.epam.secKill.entity.ProductInfo;

public interface ProductService {
    ProductInfo findOne(String productId);
}
