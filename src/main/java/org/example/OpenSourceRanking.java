package org.example;

import java.util.*;
    /*某个开源社区希望将最近热度比较高的开源项目出一个榜单，推荐给社区里面的开发者。对于每个开源项目，开发者可以进行关注(watch)、收藏(star)、fork、提issue、提交合并请求(MR)等。
    数据库里面统计了每个开源项目关注、收藏、fork、issue、MR的数量，开源项目的热度根据这5个维度的加权求和进行排序。

    表示热度值，分别表示5个统计维度的权重，分别表示5个统计维度的统计值。
    榜单按照热度值降序排序，对于热度值相等的，按照项目名字转换为全小写字母后的字典序排序（'a','b','c',...,'x','y','z')。*/

class OpenSourceProject implements Comparable<OpenSourceProject> {
    String name;
    int watch;
    int star;
    int fork;
    int issue;
    int mr;
    double heat;

    public OpenSourceProject(String name, int watch, int star, int fork, int issue, int mr, double[] weights) {
        this.name = name;
        this.watch = watch;
        this.star = star;
        this.fork = fork;
        this.issue = issue;
        this.mr = mr;
        // 计算热度值
        this.heat = weights[0] * watch + weights[1] * star + weights[2] * fork + weights[3] * issue + weights[4] * mr;
    }

    @Override
    public int compareTo(OpenSourceProject other) {
        // 首先按热度值降序排序
        if (this.heat != other.heat) {
            return Double.compare(other.heat, this.heat);
        }
        // 热度值相同时，按项目名称字典序排序
        return this.name.toLowerCase().compareTo(other.name.toLowerCase());
    }
}

public class OpenSourceRanking {

    public static void main(String[] args) {
        // 示例数据：项目名称、关注数、收藏数、fork数、issue数、MR数
        String[][] projectData = {
                {"ProjectA", "100", "200", "150", "50", "30"},
                {"ProjectB", "120", "180", "160", "40", "25"},
                {"ProjectC", "100", "200", "150", "50", "30"},
                {"ProjectD", "90", "210", "140", "55", "35"}
        };

        // 权重设置：关注、收藏、fork、issue、MR
        double[] weights = {0.3, 0.4, 0.2, 0.05, 0.05};

        // 创建项目列表
        List<OpenSourceProject> projects = new ArrayList<>();
        for (String[] project : projectData) {
            projects.add(new OpenSourceProject(
                    project[0],
                    Integer.parseInt(project[1]),
                    Integer.parseInt(project[2]),
                    Integer.parseInt(project[3]),
                    Integer.parseInt(project[4]),
                    Integer.parseInt(project[5]),
                    weights));
        }

        // 对项目进行排序
        Collections.sort(projects);

        // 输出排序后的项目列表
        for (OpenSourceProject project : projects) {
            System.out.println(project.name + " - Heat: " + project.heat);
        }
    }
}

