package com.crypty.test;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 门店信息
 * </p>
 *
 * @author lidongxue
 * @since 2022-02-14
 */
@Data
public class ShopDetailMsg {

    /**
     * 门店主键id
     */
    private Long shopId;

    /**
     * 集团id
     */
    private Long groupId;

    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 门店自增id（目前配送用）
     */
    private Integer shopCode;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 运营模式 0：正餐 1：快餐
     */
    private Integer operateSchema;

    /**
     * 经营方式 1直营 2加盟
     */
    private Integer businessMode;

    /**
     * 营业状态 0待营业 1营业中 9停业维护 7已冻结
     */
    private Integer operateState;

    /**
     * 营业时间
     */
    private String openingHours;

    /**
     * 营业面积
     */
    private String operateArea;

    /**
     * 店内电话
     */
    private String shopPhone;

    /**
     * 外卖客服电话
     */
    private String takeoutPhone;

    /**
     * 线上公告
     */
    private String onlineNotice;

    /**
     * 仓库id
     */
    private Long warehouseId;

    /**
     * 国家ID
     */
    private String countryId;

    /**
     * 省ID
     */
    private String provinceId;

    /**
     * 市ID
     */
    private String cityId;

    /**
     * 区ID
     */
    private String districtId;

    /**
     * 镇或街道id
     */
    private String townId;

    /**
     * 地址
     */
    private String address;

    /**
     * 地址扩展
     */
    private String addressExt;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 坐标
     */
    private String coordinate;

    /**
     * 管理组织一级
     */
    private Long orgLevel1;

    /**
     * 管理组织二级
     */
    private Long orgLevel2;

    /**
     * 管理组织三级
     */
    private Long orgLevel3;

    /**
     * 社会信息代码
     */
    private String socialCode;

    /**
     * 法人
     */
    private String legalPerson;

    /**
     * 营业执照名称
     */
    private String businessName;

    /**
     * 营业执照图片
     */
    private String businessImg;

    /**
     * 食品经营许可证图片
     */
    private String foodImg;

    /**
     * 加盟商id
     */
    private Long allianceId;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 合同首次签约时间
     */
    private String contractStartTime;

    /**
     * 合同截止有效期
     */
    private String contractEndTime;

    /**
     * 续约后合同截止有效期
     */
    private String contractExtensionEndTime;

    /**
     * 加盟商操作类型  0无变化 1开业 2续约 3变更 4迁址
     */
    private Integer allianceType;

    /**
     * 是否启用 1是 0否
     */
    private Integer state;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    /**
     * 门店别名
     */
    private String shopAlias;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 商户中心门店ID
     */
    private String shopCenterId;

    /**
     * 集团名称
     */
    private String groupName;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区名称
     */
    private String districtName;

    /**
     * 管理组织级别名称
     */
    private String orgLevel1Name;

    /**
     * 管理组织级别名称
     */
    private String orgLevel2Name;

    /**
     * 管理组织级别名称
     */
    private String orgLevel3Name;

    /**
     * 所属结算主体ID
     */
    private Integer settleId;
    /**
     * 所属结算主体名称
     */
    private String settleName;

    /**
     * 店铺特色服务
     */
    private String serviceFeatures;

    /**
     * 营业时间（新版）
     */
    private String shopOpeningHours;

    /**
     * 开票渠道
     */
    private String invoiceChannel;

    /**
     * 开票信息
     */
    private String invoiceInformation;

    /**
     * 开票信息编码
     */
    private String invoiceInformationCode;
    /**
     * 开票门店税率
     */
    private String invoiceShopTaxrate;
    /**
     * 开票类型
     */
    private String invoiceType;

    /**
     * 斯慧门店ID
     */
    private String poiId;

    private String shopChannelStr;
    /**
     * 支付方式-会员余额消费  0 否 、1 是
     */
    private Integer vipBalance;
    /**
     * 支付方式-会员CRM券  0 否 、1 是
     */
    private Integer vipCrmTicket;

    /**
     * 支付方式-pos支付  0 否 、1 是
     */
    private Integer vipPos;


}
