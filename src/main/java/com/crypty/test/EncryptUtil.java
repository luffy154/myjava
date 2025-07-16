package com.crypty.test;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;


public class EncryptUtil {


    private static final String solt1 = "as";       //加盐1
    private static final String solt2 = "zt";       //加盐2

    /**
     * 生成JWT字符串
     *
     * @param data     数据部分
     * @param password DES加密秘钥
     * @return 返回字符串
     */
    public static String GetTokenString2(String data, String password, String timeString) {

        //String timeString = getNowTimestamp().toString();

        if (data == null || data.equals("")) return "";
        String jwtA = timeString;
        String jwtB = "";
        String jwtC = "";

        String str = DESUtil.encrypt(password, data);
        jwtC = EncryptUtil.encrypt16(jwtA + data);

        jwtB = str;
        String jwt = jwtA + "." + jwtB + "." + jwtC;
        return jwt;
    }

    /**
     * 获取精确到秒的时间戳
     *
     * @param
     * @return
     */
    public static Integer getNowTimestamp() {

        Date date = new Date();
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.valueOf(timestamp);
    }

    public static String getFormatDate(long time) {

        long times = time * 1000;
        Date date = new Date(times);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return formatter.format(date);

    }

    /***
     * 解析JWT对象
     * @param data          JWT字符串
     * @param password      DES的秘钥
     * @return 返回appid
     */
    public static Map checkToken(String data, String password) {
        try {
            String jwtA = "A";
            String jwtB = "";
            String jwtC = "";
            String time = "";
            int index = 0;
            index = data.indexOf(".");
            jwtA = data.substring(0, index);
            data = data.substring(index + 1);

            index = data.indexOf(".");
            jwtB = data.substring(0, index);
            jwtC = data.substring(index + 1);

            //time = jwtB.substring(0,7);
            //jwtB = jwtB.substring(2).substring(0, jwtB.length() - 3);
            //jwtB = jwtB.substring(7);
            String userid = DESUtil.decrypt(password, jwtB);
            String signString = EncryptUtil.encrypt16(jwtA + userid);
            //JWTObj jwtObj = new JWTObj();


            HashMap map = new HashMap();
            //如果签名正确返回uid
            if (signString.equals(jwtC)) {

                map.put("userid", userid);
                map.put("time", jwtA);
                return map;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成JWT字符串
     *
     * @param data     数据部分
     * @param password DES加密秘钥
     * @return 返回字符串
     */
    public static String GetTokenString(String data, String password) {

        String timeString = getTimeString();

        if (data == null || data.equals("")) return "";
        String jwtA = "A";
        String jwtB = "";
        String jwtC = "";

        String str = DESUtil.encrypt(password, data);
        jwtC = EncryptUtil.encrypt16(jwtA + data);
        //jwtB = solt1 + str + solt2;
        jwtB = timeString + str;
        String jwt = jwtA + "." + jwtB + "." + jwtC;
        return jwt;
    }

    /**
     * 获取当前的时间戳
     *
     * @return
     */
    private static String getTimeString() {
        Calendar nowtime = new GregorianCalendar();
        Integer minute = nowtime.get(Calendar.MINUTE);
        Integer second = nowtime.get(Calendar.SECOND);
        Integer millisecond = nowtime.get(Calendar.MILLISECOND);

        String minueStr = "";
        String seconStr = "";
        String millisecondStr = "";

        if (minute < 10) minueStr = "0" + minute.toString();
        else minueStr = minute.toString();
        if (second < 10) seconStr = "0" + second.toString();
        else seconStr = second.toString();
        if (millisecond < 10) millisecondStr = "00" + millisecond.toString();
        if (10 < millisecond && millisecond < 100) millisecondStr = "0" + millisecond.toString();
        else millisecondStr = millisecond.toString();
        String timeStr = minueStr + seconStr + millisecondStr;
        return timeStr;
    }

    /**
     * MD5 32位加密
     *
     * @param encryptStr 需要加密的字符串
     * @return 返回加密的字符串
     */
    public static String encrypt32(String encryptStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            encryptStr = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptStr;
    }


    /**
     * MD5 16位加密
     *
     * @param encryptStr 要加密的字符串
     * @return 获取15位的MD5字符串
     */
    public static String encrypt16(String encryptStr) {
        return encrypt32(encryptStr).substring(8, 24);
    }
}
