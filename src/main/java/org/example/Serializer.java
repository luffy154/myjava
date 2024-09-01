package org.example;

import java.util.*;

    /*模拟一套简化的序列化传输方式，请实现下面的数据编码与解码过程
1、编码前数据格式为[位置,类型，值]，多个数据的时候用逗号分隔，位置仅支持数字，不考虑重复等场景；类型仅支持：Integer/String/Compose（Compose的数据类型表示该存储的数据也需要编码）。
            2、编码后数据参考图示，数据区的格式是：位置#类型#长度#数，类型存储需要编码，Integer->0;String->1;Compose->2 长度是指数据的字符长度；数据仅允许数字、大小写字母、空格。



            3、输入的编码字符长度不能超过1000，一个数据的格式错误，则解析剩下数据，其他错误输出ENCODE_ERROR。
            4、输入的解码字符不能超过1000，数据区异常则跳过继续解析剩余数据区，其他异常输出DECODE_ERROR*/

public class Serializer {

    public static String encode(List<Object[]> data) {
        StringBuilder sb = new StringBuilder();
        for (Object[] item : data) {
            try {
                int pos = (int) item[0];
                String type = (String) item[1];
                Object value = item[2];

                String encodedType;
                if (type.equals("Integer")) {
                    encodedType = "0";
                } else if (type.equals("String")) {
                    encodedType = "1";
                } else if (type.equals("Compose")) {
                    encodedType = "2";
                } else {
                    return "ENCODE_ERROR";
                }

                String encodedValue;
                if (encodedType.equals("2")) {
                    encodedValue = encode((List<Object[]>) value);
                } else {
                    encodedValue = value.toString();
                }

                if (sb.length() > 0) sb.append(",");
                sb.append(pos).append("#").append(encodedType).append("#").append(encodedValue.length()).append("#").append(encodedValue);

                if (sb.length() > 1000) {
                    return "ENCODE_ERROR";
                }
            } catch (Exception e) {
                return "ENCODE_ERROR";
            }
        }
        return sb.toString();
    }

    public static List<Object[]> decode(String encodedStr) {
        List<Object[]> result = new ArrayList<>();
        String[] items = encodedStr.split(",");

        for (String item : items) {
            try {
                String[] parts = item.split("#");
                if (parts.length < 4) {
                    continue;
                }

                int pos = Integer.parseInt(parts[0]);
                String type;
                switch (parts[1]) {
                    case "0":
                        type = "Integer";
                        break;
                    case "1":
                        type = "String";
                        break;
                    case "2":
                        type = "Compose";
                        break;
                    default:
                        return Collections.singletonList(new Object[]{"DECODE_ERROR"});
                }

                int len = Integer.parseInt(parts[2]);
                String value = item.substring(item.indexOf("#", item.indexOf("#", item.indexOf("#") + 1) + 1) + 1);
                value = value.substring(0, len);

                if (type.equals("Compose")) {
                    result.add(new Object[]{pos, type, decode(value)});
                } else if (type.equals("Integer")) {
                    result.add(new Object[]{pos, type, Integer.parseInt(value)});
                } else if (type.equals("String")) {
                    result.add(new Object[]{pos, type, value});
                }

            } catch (Exception e) {
                result.add(new Object[]{"DECODE_ERROR"});
            }
        }

        return result;
    }

    public static void main(String[] args) {
        List<Object[]> data = new ArrayList<>();
        data.add(new Object[]{1, "Integer", 123});
        data.add(new Object[]{2, "String", "hello"});
        data.add(new Object[]{3, "Compose", Arrays.asList(new Object[]{4, "String", "world"})});

        String encoded = encode(data);
        System.out.println("Encoded: " + encoded);

        List<Object[]> decoded = decode(encoded);
        System.out.println("Decoded: " + decoded);
    }
}
