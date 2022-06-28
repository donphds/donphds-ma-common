package com.donphds.ma.common.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.donphds.ma.common.exception.AuthException;
import com.donphds.ma.common.properties.AuthProperties;
import com.donphds.ma.donphdsmaauth.pojo.po.StatePo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.format.DateTimeFormatter;

import static com.donphds.ma.common.constants.CommonConstants.DATE_PATTER;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Slf4j
@Component
public class CommonUtils {

    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern(DATE_PATTER);



    public static String getFileExtension(String filename) {
        String suffix = FileNameUtil.getSuffix(filename);
        return StringUtils.isBlank(suffix) ? suffix : "." + suffix;
    }

    public static String RSA2048Sign(String data, String privateKey) {
        try {
            KeyFactory rsa = KeyFactory.getInstance("RSA");
            InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(privateKey.getBytes()));
            StringWriter writer = new StringWriter();
            IoUtil.copy(reader, writer);
            byte[] privateKeyByte = writer.toString().getBytes();
            privateKeyByte = Base64.decode(privateKeyByte);
            PrivateKey key = rsa.generatePrivate(new PKCS8EncodedKeySpec(privateKeyByte));
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initSign(key);
            signature.update(data.getBytes());
            return new String(Base64.encode(signature.sign()));
        } catch (Exception e) {
            log.info("RSA2048签名失败");
            return "";
        }
    }

    public static Mono<StatePo> validateState(String state, AuthProperties properties, ObjectMapper om) {
        if (StringUtils.isBlank(state)) {
            return Mono.error(new AuthException("state is missing"));
        }
        StatePo statePo;
        try {
            statePo = om.readValue(Base64.decodeStr(state), StatePo.class);
        } catch (Exception e) {
            log.error("valid state");
            return Mono.error(new AuthException("valid state"));
        }
        if (!StringUtils.equals(statePo.getState(), properties.getState())
                || !ResourceUtils.isUrl(statePo.getRedirectUrl())) {
            log.info("valid state params");
            return Mono.error(new AuthException("valid state params"));
        }

        return Mono.just(statePo);
    }
}
