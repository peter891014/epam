package com.epam.secKill.repository;

import com.epam.secKill.entity.ProductInfo;
import com.epam.secKill.entity.SecOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
 

 
}