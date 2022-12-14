package com.epam.secKill.repository;

import com.epam.secKill.entity.SecOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecOrderRepository extends JpaRepository<SecOrder,String> {

    List<SecOrder> findByProductId(String productId);


    SecOrder save(SecOrder secOrder);

}
