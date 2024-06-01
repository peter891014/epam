package com.epam.aliyun;


public class OssConfigProperties {
    private String endpoint ="https://oss-cn-hangzhou.aliyuncs.com";

    private String accessKeyId ="";
 //LTAI4G1utM9GBmaJm4M6fmR1
    private String accessKeySecret ="";
    //s6FQeyumnuj72Dc6c0yyHq2RslFZeV
    private String bucket ="'yannan'";

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public OssConfigProperties(String endpoint, String accessKeyId, String accessKeySecret, String bucket) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucket = bucket;
    }

    public OssConfigProperties() {
    }
}
