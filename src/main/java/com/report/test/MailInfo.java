package com.report.test;

import lombok.Data;

import java.util.List;

/**
 * @ClassName:MailInfo
 * @author: qm
 * @Description:
 * @date:2025-05-26
 */
@Data
public class MailInfo {
    private String codeNum;
    private String receiveCompanyName;
    private List<BillInfo>  billInfo;
    private String sendCompanyName;
    private String sendDate;
}
