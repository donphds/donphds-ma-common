package com.donphds.ma.common.utils;

import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
public class ServerHttpUtil {
    public static void setResHeader(ServerHttpResponse response, String key, String value) {
        HttpHeaders headers = response.getHeaders();
        headers.add(key, value);
    }

    public static void setReqHeader(ServerHttpRequest request, String key, String value) {
        HttpHeaders headers = request.getHeaders();
        headers.add(key, value);
    }

    public static String getClientIp(ServerHttpRequest req) {
        Map<String, String> reqHeaders = req.getHeaders().toSingleValueMap();
        String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        String ip = "";
        for (String header : headers) {
            ip = reqHeaders.get(header);
            if (BooleanUtils.isNotTrue(NetUtil.isUnknown(ip))) {
                return NetUtil.getMultistageReverseProxyIp(ip);
            }
        }
        InetSocketAddress remoteAddress = req.getRemoteAddress();
        log.info("ip:{}", ip);
        return NetUtil.getMultistageReverseProxyIp(Objects.isNull(remoteAddress) ? "" : remoteAddress.getHostString());
    }
}
