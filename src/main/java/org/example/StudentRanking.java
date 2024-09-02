package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
//小明来到某学校当老师，需要将学生按考试总分或单科分数进行排名，你能帮帮他吗？
class Student {
    String name;
    int mathScore;
    int chineseScore;
    int englishScore;
    int totalScore;

    public Student(String name, int mathScore, int chineseScore, int englishScore) {
        this.name = name;
        this.mathScore = mathScore;
        this.chineseScore = chineseScore;
        this.englishScore = englishScore;
        this.totalScore = mathScore + chineseScore + englishScore;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Math: " + mathScore + ", Chinese: " + chineseScore + ", English: " + englishScore + ", Total: " + totalScore;
    }
}

public class StudentRanking {

    // 按总分排序的比较器
    public static Comparator<Student> totalScoreComparator = (s1, s2) -> {
        if (s2.totalScore != s1.totalScore) {
            return s2.totalScore - s1.totalScore;
        } else {
            return s1.name.compareTo(s2.name);
        }
    };

    // 按数学分数排序的比较器
    public static Comparator<Student> mathScoreComparator = (s1, s2) -> {
        if (s2.mathScore != s1.mathScore) {
            return s2.mathScore - s1.mathScore;
        } else {
            return s1.name.compareTo(s2.name);
        }
    };

    // 按语文分数排序的比较器
    public static Comparator<Student> chineseScoreComparator = (s1, s2) -> {
        if (s2.chineseScore != s1.chineseScore) {
            return s2.chineseScore - s1.chineseScore;
        } else {
            return s1.name.compareTo(s2.name);
        }
    };

    // 按英语分数排序的比较器
    public static Comparator<Student> englishScoreComparator = (s1, s2) -> {
        if (s2.englishScore != s1.englishScore) {
            return s2.englishScore - s1.englishScore;
        } else {
            return s1.name.compareTo(s2.name);
        }
    };

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", 85, 90, 95));
        students.add(new Student("Bob", 90, 85, 88));
        students.add(new Student("Charlie", 88, 92, 80));
        students.add(new Student("David", 85, 90, 95));

        System.out.println("Sort by total score:");
        Collections.sort(students, totalScoreComparator);
        students.forEach(System.out::println);

        System.out.println("\nSort by math score:");
        Collections.sort(students, mathScoreComparator);
        students.forEach(System.out::println);

        System.out.println("\nSort by Chinese score:");
        Collections.sort(students, chineseScoreComparator);
        students.forEach(System.out::println);

        System.out.println("\nSort by English score:");
        Collections.sort(students, englishScoreComparator);
        students.forEach(System.out::println);
    }
}

