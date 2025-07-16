package com.report.test;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.Objects;

/**
 * @ClassName:AccessToken
 * @author: qm
 * @Description:
 * @date:2025-06-20
 */
@Data
public class AccessToken {
    @JSONField(
            name = "app_access_token"
    )
    private String appAccessToken;
    private Integer code;
    private Integer expire;
    private String msg;
    @JSONField(
            name = "tenant_access_token"
    )
    private String tenantAccessToken;

    public boolean hasSuccess() {
        return Objects.equals(0, this.code);
    }
}
