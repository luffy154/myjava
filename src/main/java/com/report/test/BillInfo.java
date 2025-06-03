package com.report.test;

import lombok.Data;

/**
 * @ClassName:BillInfo
 * @author: qm
 * @Description:
 * @date:2025-05-26
 */
@Data
public class BillInfo {
    /**
     * 账单截止时间
     */
    private String endDate;
    /**
     * 贷方金额
     */
    private String debitAmount;

    /**
     * 借方金额
     */
    private String creditAmount;

    /**
     * 备注
     */
    private String remark;
}
