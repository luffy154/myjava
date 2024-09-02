package org.example;

import java.util.HashMap;
import java.util.Map;

/*某个产品的RESTful API集合部署在服务器集群的多个节点上，近期对客户端访问日志进行了采集，需要统计各个API的访问频次，根据热点信息在服务器节点之间做负载均衡，现在需要实现热点信息统计查询功能。
   RESTful API的由多个层级构成，层级之间使用 / 连接，如 /A/B/C/D 这个地址，A属于第一级，B属于第二级，C属于第三级，D属于第四级。
   现在负载均衡模块需要知道给定层级上某个名字出现的频次，未出现过用0次表示，实现这个功能。*/
class ApiFrequencyCounter {
    // 嵌套字典结构，用于存储频次信息
    private Map<String, Node> freqMap;

    // 内部类，表示每个节点
    private static class Node {
        int count;
        Map<String, Node> nextLevel;

        Node() {
            this.count = 0;
            this.nextLevel = new HashMap<>();
        }
    }

    public ApiFrequencyCounter() {
        this.freqMap = new HashMap<>();
    }

    // 添加日志
    public void addLog(String log) {
        String[] parts = log.split("/");
        Map<String, Node> currentLevel = freqMap;

        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            Node node = currentLevel.getOrDefault(part, new Node());
            node.count++;
            currentLevel.put(part, node);
            currentLevel = node.nextLevel;
        }
    }

    // 查询某个层级的名字频次
    public int query(int level, String name) {
        Map<String, Node> currentLevel = freqMap;
        Node node = null;

        for (int i = 1; i <= level; i++) {
            node = currentLevel.get(name);
            if (node == null) {
                return 0; // 名字未出现过，返回0
            }
            currentLevel = node.nextLevel;
        }

        return node.count;
    }

    public static void main(String[] args) {
        ApiFrequencyCounter apiCounter = new ApiFrequencyCounter();

        // 模拟添加日志
        String[] logs = {
                "/A/B/C/D",
                "/A/B/C/D",
                "/A/B/E",
                "/A/B/E/F",
                "/A/G/H",
                "/A/B/C",
                "/I/J/K",
                "/A/B"
        };

        for (String log : logs) {
            apiCounter.addLog(log);
        }

        // 查询各个层级的名字频次
        System.out.println(apiCounter.query(1, "A"));  // 查询第1层级的A，输出: 7
        System.out.println(apiCounter.query(2, "B"));  // 查询第2层级的B，输出: 6
        System.out.println(apiCounter.query(3, "C"));  // 查询第3层级的C，输出: 3
        System.out.println(apiCounter.query(4, "D"));  // 查询第4层级的D，输出: 2
        System.out.println(apiCounter.query(1, "I"));  // 查询第1层级的I，输出: 1
        System.out.println(apiCounter.query(3, "E"));  // 查询第3层级的E，输出: 2
        System.out.println(apiCounter.query(2, "G"));  // 查询第2层级的G，输出: 1
        System.out.println(apiCounter.query(2, "Z"));  // 查询第2层级的Z，输出: 0
    }
}

