package com.epam.secKill.service;

import com.epam.secKill.entity.SecOrder;

import java.util.List;

public interface SecOrderService {
 
     List<SecOrder> findByProductId(String productId);
 
     SecOrder save(SecOrder secOrder);
 
}