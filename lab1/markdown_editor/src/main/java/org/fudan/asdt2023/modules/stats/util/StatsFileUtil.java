package org.fudan.asdt2023.modules.stats.util;

import org.fudan.asdt2023.utils.FileUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StatsFileUtil {
    public static String STATS_FILE_NAME = "stats.txt";
    private static File file;

    public static void setStatsFileName(String name) {
        STATS_FILE_NAME = name;
    }

    // 统计文件初始化
    public static void initFile() {
        file = FileUtil.initFile(STATS_FILE_NAME);
    }

    // 向统计文件中写入一行
    public static void writeLine(String line) {
        FileUtil.writeLine(line, file);
    }

    // 读取统计文件中最近一次session的所有统计信息
    public static List<String> readSessionStats() {
        List<String> sessionStats = new ArrayList<>();
        if (!file.exists()) {
            return sessionStats;
        }

        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile(file, "r");
            long fileLength = raf.length();

            // 从文件末尾开始读
            long pointer = fileLength - 1;

            while (pointer >= 0) {
                raf.seek(pointer);
                char c = (char) raf.readByte();
                if (c == '\n') {
                    long lineStart = pointer + 1;
                    long lineLength = fileLength - lineStart;

                    byte[] lineBytes = new byte[(int) lineLength];
                    raf.seek(lineStart);
                    raf.read(lineBytes);

                    String line = new String(lineBytes, StandardCharsets.UTF_8);
                    if (line.contains("\r\n")) {
                        line = line.substring(0, line.indexOf("\r\n"));
                    }

                    if (!line.equals("")){
                        // 添加到List开头
                        sessionStats.add(0, line);
                        if (line.contains("session start")) {
                            return sessionStats;
                        }
                    }
                    pointer--;
                }

                pointer--;
            }

            // 到第一行
            raf.seek(0);
            byte[] firstLineBytes = new byte[(int) fileLength];
            raf.read(firstLineBytes);
            String firstLine = new String(firstLineBytes, StandardCharsets.UTF_8);
            if (firstLine.contains("\r\n")) {
                firstLine = firstLine.substring(0, firstLine.indexOf("\r\n"));
            }
            if (!firstLine.equals("")) sessionStats.add(0, firstLine);
            raf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return sessionStats;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionStats;
    }
}
