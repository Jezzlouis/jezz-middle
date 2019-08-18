package com.jezz.session.api;
import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @Author Hugo.Wwg
 * @Since 2017-06-06
 */
public class APIRequest<T> implements Serializable {

    private static final long serialVersionUID = -1L;

    private Integer channel; //请求源. 具体请看: RequestChannelEnum 枚举类相关定义

    private Integer platform; //请求平台.

    private String signature; //签名.

    private String version; //版本号.

    private T data; //请求参数

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
