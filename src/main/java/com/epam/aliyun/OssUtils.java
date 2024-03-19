package com.epam.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class OssUtils {

    private final OSS ossClient;

    private final String bucketName;

    private final String accessId;

    private String endpoint;

    //初始化oss客户端
    public OssUtils(OssConfigProperties ossConfigPro) {
        String endpoint = ossConfigPro.getEndpoint();
        String accessId = ossConfigPro.getAccessKeyId();
        String accessKey = ossConfigPro.getAccessKeySecret();
        String bucketName = ossConfigPro.getBucket();
        if (StringUtils.isBlank(endpoint) || StringUtils.isBlank(accessId)
                || StringUtils.isBlank(accessKey) || StringUtils.isBlank(bucketName)) {
            throw new ServiceException("please check oss config!");
        }
        this.ossClient = new OSSClientBuilder().build(endpoint, accessId, accessKey);
        this.bucketName = bucketName;
        this.accessId = accessId;
        this.endpoint = endpoint;
    }

    /**
     * @program: zhjg-platform
     * @description:
     * @author: 作者名字
     * @create: 2023-10-10 14:43
     **/
    public  PolicyVo getPolicy(String dir) {
        // host的格式为 bucketname.endpoint，即https://你的bucket名.地域节点/文件名.文件后缀
        endpoint = endpoint.replace("https://", "");
        // 用户上传文件时指定的前缀，即存放在以时间命名的文件夹内
        dir = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String host = "https://" + bucketName + "." + endpoint;
        PolicyVo policyVo = new PolicyVo();
        try {
            // 过期时间
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            // 生成签名
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);
            // AK
            policyVo.setAccessId(accessId);
            // 用户表单上传的策略（Policy）
            policyVo.setPolicy(encodedPolicy);
            // 签名
            policyVo.setSignature(postSignature);
            // 上传文件时指定的前缀
            policyVo.setDir(dir);
            // oss保存文件的host
            policyVo.setHost(host);
            // 过期时间
            policyVo.setExpire(String.valueOf(expireEndTime / 1000));
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return policyVo;
    }
}
