package com.crypty.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.support.spring.data.redis.GenericFastJsonRedisSerializer;
import com.google.common.hash.Hashing;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @ClassName:HashTest
 * @author: qm
 * @Description:
 * @date:2025-07-25
 */
public class HashTest {
    public static void main(String[] args) {
        Long memberId = 3292635482279837001L;
        System.out.println(memberId % 1000);
        int hashCode = memberId.hashCode();
        System.out.println(hashCode);
        System.out.println(Hashing.sha256().hashString(memberId.toString(), StandardCharsets.UTF_8).asLong());
        System.out.println(Hashing.md5().hashString(memberId.toString(), StandardCharsets.UTF_8).asInt());

        String json1 = "{\n" +
                "    \"groupId\": 267392,\n" +
                "    \"modifier\": \"张星星\",\n" +
                "    \"businessName\": \"\",\n" +
                "    \"cityId\": \"120100\",\n" +
                "    \"shopAlias\": \"天津中海环宇城店\",\n" +
                "    \"townId\": \"\",\n" +
                "    \"invoiceInformation\": \"天津珅府餐饮管理有限公司\",\n" +
                "    \"modifyTime\": \"2025-07-25 02:05:56.347\",\n" +
                "    \"serviceFeatures\": \"takeout_order,takeaway_order\",\n" +
                "    \"invoiceChannel\": \"PT\",\n" +
                "    \"invoiceType\": \"fully_digitalized_invoice\",\n" +
                "    \"shopId\": 32090816,\n" +
                "    \"state\": 1,\n" +
                "    \"invoiceInformationCode\": \"91120101MA05MC565H\",\n" +
                "    \"longitude\": \"117.241031\",\n" +
                "    \"brandName\": \"和府\",\n" +
                "    \"coordinate\": \"{\\\"bd\\\":{\\\"latitude\\\":39.049199,\\\"longitude\\\":117.241031},\\\"gd\\\":{\\\"latitude\\\":39.042857,\\\"longitude\\\":117.234629}}\",\n" +
                "    \"operateState\": 1,\n" +
                "    \"provinceId\": \"120000\",\n" +
                "    \"settleName\": \"11090788和府天津中海环宇城店\",\n" +
                "    \"contractEndTime\": \"\",\n" +
                "    \"invoiceShopTaxrate\": \"6\",\n" +
                "    \"groupName\": \"测试集团\",\n" +
                "    \"shopOpeningHours\": \"[{\\\"id\\\":1948444553426014208,\\\"openingHours\\\":\\\"10:00-21:30\\\",\\\"remark\\\":\\\"1\\\",\\\"shopId\\\":32090816,\\\"timeId\\\":1948444552600367104,\\\"type\\\":1,\\\"week\\\":\\\"0\\\"}]\",\n" +
                "    \"warehouseId\": 0,\n" +
                "    \"brandId\": 379517,\n" +
                "    \"name\": \"和府788店（天津中海环宇城店）\",\n" +
                "    \"orgLevel2\": -8233267053436186994,\n" +
                "    \"orgLevel3\": 111313188817685842,\n" +
                "    \"openingHours\": \"10:00-21:30\",\n" +
                "    \"orgLevel1\": 1,\n" +
                "    \"provinceName\": \"天津市\",\n" +
                "    \"socialCode\": \"\",\n" +
                "    \"contractStartTime\": \"\",\n" +
                "    \"code\": \"11090788\",\n" +
                "    \"orgLevel3Name\": \"华北八区\",\n" +
                "    \"settleId\": 572,\n" +
                "    \"contractNo\": \"\",\n" +
                "    \"latitude\": \"39.049199\",\n" +
                "    \"operateSchema\": 1,\n" +
                "    \"countryId\": \"0000000\",\n" +
                "    \"allianceId\": 0,\n" +
                "    \"onlineNotice\": \"\",\n" +
                "    \"orgLevel2Name\": \"华北大区营运部\",\n" +
                "    \"cityName\": \"天津市\",\n" +
                "    \"legalPerson\": \"\",\n" +
                "    \"shopPhone\": \"\",\n" +
                "    \"poiId\": \"7229231731096684579\",\n" +
                "    \"operateArea\": \"\",\n" +
                "    \"businessMode\": 1,\n" +
                "    \"shopCode\": 750,\n" +
                "    \"creator\": \"\",\n" +
                "    \"address\": \"天津市河西区解放南路689号天津中海环宇城B156\",\n" +
                "    \"addressExt\": \"\",\n" +
                "    \"businessImg\": \"\",\n" +
                "    \"districtName\": \"河西区\",\n" +
                "    \"shopCenterId\": \"\",\n" +
                "    \"districtId\": \"120103\",\n" +
                "    \"createTime\": \"2023-09-18 10:52:05\",\n" +
                "    \"foodImg\": \"\",\n" +
                "    \"takeoutPhone\": \"\"\n" +
                "}";
        String json2 = "{\n" +
                "    \"groupId\": 267392,\n" +
                "    \"modifier\": \"朱月琴\",\n" +
                "    \"businessName\": \"\",\n" +
                "    \"cityId\": \"410100\",\n" +
                "    \"shopAlias\": \"郑州大卫城\",\n" +
                "    \"vipBalance\": 1,\n" +
                "    \"invoiceInformation\": \"郑州和府餐饮管理有限公司太康路分公司\",\n" +
                "    \"serviceFeatures\": \"takeout_order,takeaway_order\",\n" +
                "    \"invoiceChannel\": \"PT\",\n" +
                "    \"invoiceType\": \"fully_digitalized_invoice\",\n" +
                "    \"shopId\": 3005609,\n" +
                "    \"invoiceInformationCode\": \"91410105MA9F5NTJ73\",\n" +
                "    \"longitude\": \"113.672392\",\n" +
                "    \"vipPos\": 1,\n" +
                "    \"brandName\": \"和府品牌\",\n" +
                "    \"coordinate\": \"{\\\"bd\\\":{\\\"latitude\\\":34.763447,\\\"longitude\\\":113.672392},\\\"gd\\\":{\\\"latitude\\\":34.757379,\\\"longitude\\\":113.665929}}\",\n" +
                "    \"operateState\": 1,\n" +
                "    \"orgLevel1Name\": \"和府品牌\",\n" +
                "    \"provinceId\": \"410000\",\n" +
                "    \"allianceType\": 0,\n" +
                "    \"invoiceShopTaxrate\": \"6\",\n" +
                "    \"groupName\": \"测试集团\",\n" +
                "    \"shopOpeningHours\": \"[{\\\"id\\\":1948564179229110272,\\\"openingHours\\\":\\\"10:00-21:30\\\",\\\"remark\\\":\\\"1\\\",\\\"shopId\\\":3005609,\\\"timeId\\\":1948564179129094144,\\\"type\\\":1,\\\"week\\\":\\\"0\\\"}]\",\n" +
                "    \"brandId\": 379517,\n" +
                "    \"name\": \"和府370店（郑州大卫城）\",\n" +
                "    \"orgLevel2\": 5019134309499817756,\n" +
                "    \"orgLevel3\": -5225420746905400305,\n" +
                "    \"vipCrmTicket\": 1,\n" +
                "    \"openingHours\": \"10:00-21:30\",\n" +
                "    \"orgLevel1\": 1,\n" +
                "    \"provinceName\": \"河南省\",\n" +
                "    \"socialCode\": \"\",\n" +
                "    \"code\": \"12690370\",\n" +
                "    \"orgLevel3Name\": \"浙豫四区\",\n" +
                "    \"latitude\": \"34.763447\",\n" +
                "    \"operateSchema\": 1,\n" +
                "    \"countryId\": \"0000000\",\n" +
                "    \"onlineNotice\": \"\",\n" +
                "    \"orgLevel2Name\": \"浙豫大区营运部\",\n" +
                "    \"cityName\": \"郑州市\",\n" +
                "    \"legalPerson\": \"\",\n" +
                "    \"shopPhone\": \"\",\n" +
                "    \"poiId\": \"6860694147536340996\",\n" +
                "    \"operateArea\": \"\",\n" +
                "    \"businessMode\": 1,\n" +
                "    \"address\": \"郑州市金水区二七路太康路丹尼斯大卫城10层10F-B001号\",\n" +
                "    \"addressExt\": \"\",\n" +
                "    \"businessImg\": \"\",\n" +
                "    \"districtName\": \"金水区\",\n" +
                "    \"shopCenterId\": \"\",\n" +
                "    \"districtId\": \"410105\",\n" +
                "    \"foodImg\": \"\",\n" +
                "    \"takeoutPhone\": \"\"\n" +
                "}";
        Map map1 = JSON.parseObject(json1, Map.class);
        ShopDetailMsg msg = JSON.parseObject(json2, ShopDetailMsg.class);

        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        byte[] bytes = serializer.serialize( msg);

        System.out.println(new String(bytes, StandardCharsets.UTF_8));

        Object obj = serializer.deserialize( bytes);

        System.out.println(obj);


        GenericFastJsonRedisSerializer serializer1 = new GenericFastJsonRedisSerializer();
        byte[] bytes1 = serializer1.serialize( msg);

        System.out.println(new String(bytes1, StandardCharsets.UTF_8));

        Object obj1 = serializer1.deserialize( bytes1);

        System.out.println(obj1);

    }
}
