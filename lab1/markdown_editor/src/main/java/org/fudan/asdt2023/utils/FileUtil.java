package org.fudan.asdt2023.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    public static File initFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println(filename+"文件已创建");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static void writeLine(String line, File file) {
        try {
            if (!file.exists()) {
                file.createNewFile(); // 如果文件不存在，则初始化文件
            }
            FileWriter fileWriter = new FileWriter(file, true); // 使用true以启用追加模式
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(line);
            bufferedWriter.newLine(); // 添加换行符
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
