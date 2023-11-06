package org.fudan.asdt2023.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public static final String PATTERN = "yyyyMMdd HH:mm:ss";

    // 获取当前时间并格式化
    public static String getCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        return currentTime.format(formatter);
    }

    // 计算两个格式化时间相隔的时长
    public static String getDuration(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);

        LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endTime, formatter);

        Duration duration = Duration.between(startDateTime, endDateTime);
        return formatDuration(duration);
    }

    // 格式化时长
    public static String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();

        long days = seconds / 86400; // 1 day = 24 * 60 * 60 seconds
        seconds %= 86400;

        long hours = seconds / 3600; // 1 hour = 60 * 60 seconds
        seconds %= 3600;

        long minutes = seconds / 60;
        seconds %= 60;

        StringBuilder formattedDuration = new StringBuilder();

        if (days > 0) {
            formattedDuration.append(days).append(" 天 ");
        }
        if (hours > 0) {
            formattedDuration.append(hours).append(" 小时 ");
        }
        if (minutes > 0) {
            formattedDuration.append(minutes).append(" 分钟 ");
        }
        if (seconds > 0) {
            formattedDuration.append(seconds).append(" 秒");
        }

        return formattedDuration.toString().trim();
    }
}
