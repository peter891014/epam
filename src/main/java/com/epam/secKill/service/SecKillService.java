package com.epam.secKill.service;

import com.epam.secKill.entity.SecOrder;
import com.epam.secKill.entity.SecProductInfo;
import org.springframework.stereotype.Service;

@Service
public interface SecKillService {
 
     long orderProductMockDiffUser(String productId, SecOrder secOrder);
     
     SecProductInfo refreshStock(String productId);
}
