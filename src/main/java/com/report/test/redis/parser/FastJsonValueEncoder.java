package com.report.test.redis.parser;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.support.spring.data.redis.GenericFastJsonRedisSerializer;
import com.alicp.jetcache.support.AbstractValueEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName:FastJsonValueEncoder
 * @author: qm
 * @Description:
 * @date:2025-08-09
 */
@Component("jsonEncode")
public class FastJsonValueEncoder extends AbstractValueEncoder {
    public FastJsonValueEncoder() {
        super(false);
    }

    public FastJsonValueEncoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    @Override
    public byte[] apply(Object o) {
        GenericFastJsonRedisSerializer serializer1 = new GenericFastJsonRedisSerializer();
        return serializer1.serialize(o);
    }
}
