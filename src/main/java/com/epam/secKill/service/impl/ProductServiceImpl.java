package com.epam.secKill.service.impl;

import com.epam.secKill.entity.ProductInfo;
import com.epam.secKill.repository.ProductInfoRepository;
import com.epam.secKill.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductInfoRepository productInfoRepository;
    @Override
    public ProductInfo findOne(String productId) {
//        ProductInfo query = new ProductInfo();
//        query.setProductId(productId);
//         productInfoRepository.findOne(query);
        return productInfoRepository.getById(productId);
    }
}
