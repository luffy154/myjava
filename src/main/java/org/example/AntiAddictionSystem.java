package org.example;

import java.util.*;
/*智能手机方便了我们生活的同时，也侵占了我们不少的时间。“手机App防沉迷系统”能够让我们每天合理的规划手机App使用时间，在正确的时间做正确的事。
    它的大概原理是这样的：
            1、在一天24小时内，可注册每个App的允许使用时段；

            2、一个时段只能使用一个App，举例说明：不能同时在09:00-10:00注册App2和App3；

            3、App有优先级，数值越高，优先级越高。注册使用时段时，如果高优先级的App时间和低优先级的时段有冲突，则系统会自动注销低优先级的时段；如果App的优先级相同，则后添加的App不能注册。
    举例1：
            （1）注册App3前：

            （2）App3注册时段和App2有冲突：

            （3）App3优先级高，系统接受App3的注册，自动注销App2的注册：

    举例2：
            （1）注册App4：

            （2）App4和App2及App3都有冲突，优先级比App2高，但比App3低，这种场景下App4注册不上，最终的注册效果如下：


            4、一个App可以在一天内注册多个时段。

    请编程实现，根据输入数据注册App，并根据输入的时间点，返回该时间点可用的App名称，如果该时间点没有注册任何App，请返回字符串"NA"。
*/
public class AntiAddictionSystem {

    static class TimeSlot {
        int startHour;
        int startMinute;
        int endHour;
        int endMinute;

        public TimeSlot(int startHour, int startMinute, int endHour, int endMinute) {
            this.startHour = startHour;
            this.startMinute = startMinute;
            this.endHour = endHour;
            this.endMinute = endMinute;
        }

        // 检查两个时间段是否冲突
        public boolean isConflict(TimeSlot other) {
            int thisStart = startHour * 60 + startMinute;
            int thisEnd = endHour * 60 + endMinute;
            int otherStart = other.startHour * 60 + other.startMinute;
            int otherEnd = other.endHour * 60 + other.endMinute;

            return !(thisEnd <= otherStart || otherEnd <= thisStart);
        }

        // 检查一个时间点是否在该时间段内
        public boolean contains(int hour, int minute) {
            int time = hour * 60 + minute;
            int startTime = startHour * 60 + startMinute;
            int endTime = endHour * 60 + endMinute;
            return time >= startTime && time < endTime;
        }
    }

    static class App {
        String name;
        int priority;
        List<TimeSlot> timeSlots;

        public App(String name, int priority) {
            this.name = name;
            this.priority = priority;
            this.timeSlots = new ArrayList<>();
        }

        // 添加时间段
        public boolean addTimeSlot(TimeSlot newSlot) {
            for (TimeSlot slot : timeSlots) {
                if (slot.isConflict(newSlot)) {
                    return false;
                }
            }
            timeSlots.add(newSlot);
            return true;
        }
    }

    Map<String, App> appMap = new HashMap<>();

    // 注册App时段
    public void registerApp(String appName, int priority, TimeSlot slot) {
        // 检查是否需要移除低优先级的App时段
        List<String> appsToRemove = new ArrayList<>();
        for (Map.Entry<String, App> entry : appMap.entrySet()) {
            App app = entry.getValue();
            if (app.priority < priority) {
                for (TimeSlot existingSlot : app.timeSlots) {
                    if (existingSlot.isConflict(slot)) {
                        appsToRemove.add(app.name);
                        break;
                    }
                }
            }
            // 相同优先级并且已经注册过时段，不允许注册新时段
            if (app.priority == priority && app.name.equals(appName)) {
                return;
            }
        }

        // 移除低优先级的App的冲突时段
        for (String appNameToRemove : appsToRemove) {
            appMap.remove(appNameToRemove);
        }

        // 注册新的App时段
        appMap.putIfAbsent(appName, new App(appName, priority));
        App app = appMap.get(appName);
        app.addTimeSlot(slot);
    }

    // 查询某时间点是否有App可用
    public String query(int hour, int minute) {
        for (App app : appMap.values()) {
            for (TimeSlot slot : app.timeSlots) {
                if (slot.contains(hour, minute)) {
                    return app.name;
                }
            }
        }
        return "NA";
    }

    public static void main(String[] args) {
        AntiAddictionSystem system = new AntiAddictionSystem();

        // 注册App的使用时段
        system.registerApp("App1", 3, new TimeSlot(9, 0, 10, 0));
        system.registerApp("App2", 2, new TimeSlot(9, 30, 10, 30));
        system.registerApp("App3", 4, new TimeSlot(9, 15, 10, 15));  // 高优先级App3覆盖App1、App2

        // 查询某个时间点的可用App
        System.out.println(system.query(9, 45));  // 输出 "App3"
        System.out.println(system.query(10, 30)); // 输出 "NA"
        System.out.println(system.query(9, 10));  // 输出 "App3"
    }
}

