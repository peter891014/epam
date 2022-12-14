package com.epam.secKill.service.impl;

import com.epam.secKill.entity.SecOrder;
import com.epam.secKill.repository.SecOrderRepository;
import com.epam.secKill.service.SecOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecOrderServiceImpl implements SecOrderService {
 
    @Autowired
    private SecOrderRepository secOrderRepository;
 
    @Override
    public List<SecOrder> findByProductId(String productId) {
 
        return secOrderRepository.findByProductId(productId);
    }
 
    public SecOrder save(SecOrder secOrder){
 
        return secOrderRepository.save(secOrder);
    }
 
}
