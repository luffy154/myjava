package com.report.test.redis.parser;

import com.alibaba.fastjson2.support.spring.data.redis.GenericFastJsonRedisSerializer;
import com.alicp.jetcache.support.AbstractValueDecoder;
import org.springframework.stereotype.Component;

/**
 * @ClassName:FastJsonValueDecoder
 * @author: qm
 * @Description:
 * @date:2025-08-09
 */
@Component("jsonDecode")
public class FastJsonValueDecoder extends AbstractValueDecoder {
    public FastJsonValueDecoder() {
        super(false);
    }

    public FastJsonValueDecoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    @Override
    protected Object doApply(byte[] buffer) throws Exception {
        GenericFastJsonRedisSerializer serializer1 = new GenericFastJsonRedisSerializer();
        return serializer1.deserialize(buffer);
    }
}
