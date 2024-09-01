package org.example;

import java.util.*;

/*实现一个简易的重复内容识别系统，通过给定的两个内容名称，和相似内容符号，判断两个内容是否相似；如果相似，返回相似的内容；如果不相似，返回不相似的内容

初始化：给出两个字符串，一些相似字符对，如顿号和逗号相似，的和de相似，猪和潴，给出两个字符串的相似判断结果
输入：两条语句，给出是否相似，对于相似的语句，返回True和相似的字符对；对于不相似的内容，则返回第一个内容的不相似信息，方便后续补充

注意：
        1、相似关系是 具有 传递性的。例如，如果 "顿号" 和 "逗号" 是相似的，"逗号" 和 "分号" 是相似的，则 "顿号" 和 "逗号" 是相似的。
        2、为了过滤一些无意义的信息，这里***可以匹配任意长度的内容，
例如：给出相似对 "(***)",""时，'异世邪君（人气玄幻作家）'和'异世邪君' 认为是相似，此时相似符号返回 *** 即可
3、不相似的内容，需要给出不相似的字符串，多处不相似的字符串用空格分隔
*/
public class SimilarityChecker {

    // 相似字符对字典
    private static Map<String, Set<String>> similarityMap = new HashMap<>();

    // 添加相似字符对
    private static void addSimilarityPair(String a, String b) {
        similarityMap.computeIfAbsent(a, k -> new HashSet<>()).add(b);
        similarityMap.computeIfAbsent(b, k -> new HashSet<>()).add(a);
    }

    // 初始化相似字符对
    private static void initialize() {
        addSimilarityPair("、", ",");
        addSimilarityPair("的", "de");
        addSimilarityPair("猪", "潴");
        // 添加更多的相似字符对
    }

    // 查找字符的所有相似字符（包括传递性）
    private static Set<String> getSimilarCharacters(String ch) {
        Set<String> result = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(ch);
        result.add(ch);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (similarityMap.containsKey(current)) {
                for (String similar : similarityMap.get(current)) {
                    if (!result.contains(similar)) {
                        result.add(similar);
                        queue.add(similar);
                    }
                }
            }
        }
        return result;
    }

    // 判断两个字符串是否相似
    private static boolean areSimilar(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }

        for (int i = 0; i < str1.length(); i++) {
            String ch1 = str1.substring(i,i+1);
            String ch2 = str2.substring(i,i+1);
            if (!getSimilarCharacters(ch1).contains(ch2)) {
                return false;
            }
        }
        return true;
    }

    // 查找字符串中所有不相似的部分
    private static List<String> getNonSimilarParts(String str1, String str2) {
        List<String> nonSimilarParts = new ArrayList<>();
        int minLength = Math.min(str1.length(), str2.length());

        for (int i = 0; i < minLength; i++) {
            String ch1 = str1.substring(i,i+1);
            String ch2 = str2.substring(i,i+1);
            if (!getSimilarCharacters(ch1).contains(ch2)) {
                nonSimilarParts.add(String.valueOf(ch1));
            }
        }
        if (str1.length() > minLength) {
            nonSimilarParts.add(str1.substring(minLength));
        }
        if (str2.length() > minLength) {
            nonSimilarParts.add(str2.substring(minLength));
        }

        return nonSimilarParts;
    }

    public static void main(String[] args) {
        initialize();

        String content1 = "异世邪君（人气玄幻作家）";
        String content2 = "异世邪君";

        if (areSimilar(content1, content2)) {
            System.out.println("True");
            // 输出相似的字符对
            // 此处可以根据具体需求输出更多信息
        } else {
            List<String> nonSimilarParts = getNonSimilarParts(content1, content2);
            System.out.println(String.join(" ", nonSimilarParts));
        }
    }
}
