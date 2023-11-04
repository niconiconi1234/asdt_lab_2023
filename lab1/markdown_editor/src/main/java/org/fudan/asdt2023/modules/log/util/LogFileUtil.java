package org.fudan.asdt2023.modules.log.util;

import org.fudan.asdt2023.utils.FileUtil;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LogFileUtil {
    public static String LOG_FILE_NAME = "log.txt";
    private static File file;

    public static void setLogFileName(String name) {
        LOG_FILE_NAME = name;
    }

    // 日志文件初始化
    public static void initFile() {
        file = FileUtil.initFile(LOG_FILE_NAME);
    }

    // 向日志文件中写入一行
    public static void writeLine(String line) {
        FileUtil.writeLine(line, file);
    }

    // 读取日志文件中最近一次session的所有history
    public static List<String> readSessionHistory() {
        List<String> sessionHistory = new ArrayList<>();
        if (!file.exists()) {
            return sessionHistory;
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
                    String line = raf.readLine();
                    if (line != null) {
                        if (line.contains("session start")) {
                            return sessionHistory;
                        }

                        sessionHistory.add(line);
                    }
                    pointer--;
                }

                pointer--;
            }

            // 到第一行
            raf.seek(0);
            sessionHistory.add(raf.readLine());

            raf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return sessionHistory;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionHistory;
    }
}
