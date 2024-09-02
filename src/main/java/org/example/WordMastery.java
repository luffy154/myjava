package org.example;

import java.util.HashMap;
import java.util.Map;
/*有一个字符串数组words和一个字符串chars。
 假如可以用chars中的字母拼写出words中的某个“单词”（字符串），那么我们就认为你掌握了这个单词。
 words的字符仅由 a-z 英文小写字母组成。 例如: abc
 chars 由 a-z 英文小写字母和 “?”组成。其中英文问号“?”表示万能字符，
 能够在拼写时当做任意一个英文字母。 例如： "?" 可以当做 "a"等字母。
 注意：每次拼写时，chars中的每个字母和万能字符都只能使用一次。
 输出词汇表words中你掌握的所有单词的个数。 没有掌握任何单词，则输出0。*/
public class WordMastery {

    // 计算字符频率
    private static Map<Character, Integer> countCharacters(String str) {
        Map<Character, Integer> countMap = new HashMap<>();
        for (char ch : str.toCharArray()) {
            countMap.put(ch, countMap.getOrDefault(ch, 0) + 1);
        }
        return countMap;
    }

    // 检查单词是否可以由 chars 拼写
    private static boolean canSpellWord(String word, Map<Character, Integer> charsMap, int questionMarks) {
        Map<Character, Integer> wordMap = countCharacters(word);
        int requiredQuestions = 0;

        for (Map.Entry<Character, Integer> entry : wordMap.entrySet()) {
            char letter = entry.getKey();
            int required = entry.getValue();
            int available = charsMap.getOrDefault(letter, 0);

            if (required > available) {
                requiredQuestions += (required - available);
            }
        }

        return requiredQuestions <= questionMarks;
    }

    public static int countMasteredWords(String[] words, String chars) {
        Map<Character, Integer> charsMap = countCharacters(chars);
        int questionMarks = charsMap.getOrDefault('?', 0);
        charsMap.remove('?'); // 移除问号，因为它可以替代任何字符

        int masteredCount = 0;

        for (String word : words) {
            if (canSpellWord(word, charsMap, questionMarks)) {
                masteredCount++;
            }
        }

        return masteredCount;
    }

    public static void main(String[] args) {
        String[] words = {"abc", "def", "gih"};
        String chars = "a?b?c";

        int result = countMasteredWords(words, chars);
        System.out.println(result); // 输出：1，因为只有 "abc" 可以用 "a?b?c" 拼写
    }
}

