package com.epam.secKill.entity;

import com.epam.secKill.utill.KeyUtil;
import lombok.Data;


//import javax.persistence.Entity;
//import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
public class SecOrder  implements Serializable {
 
    private static final long serialVersionUID = 1724254862421035876L;
 
    @Id
    private String id;
    private String userId;
    private String productId;
    private BigDecimal productPrice;
    private BigDecimal amount;
 
    public SecOrder(String productId) {
        String utilId = KeyUtil.getUniqueKey();
        this.id = utilId;
        this.userId = "userId"+utilId;
        this.productId = productId;
    }
 
    public SecOrder() {
    }
 
    @Override
    public String toString() {
        return "SecOrder{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", productId='" + productId + '\'' +
                ", productPrice=" + productPrice +
                ", amount=" + amount +
                '}';
    }
}
