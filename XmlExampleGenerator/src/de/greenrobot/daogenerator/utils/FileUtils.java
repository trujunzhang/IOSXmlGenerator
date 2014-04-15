package de.greenrobot.daogenerator.utils;

import java.io.*;
import java.util.LinkedList;

/**
 * 文件操作代码
 *
 * @author cn.outofmemory
 * @date 2013-1-7
 */
public class FileUtils {
    /**
     * 将文本文件中的内容读入到buffer中
     *
     * @param buffer   buffer
     * @param filePath 文件路径
     * @throws IOException 异常
     * @author cn.outofmemory
     * @date 2013-1-7
     */
    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }

    public static LinkedList readToList(String filePath) throws IOException {
        LinkedList<String> lines = new LinkedList<String>();

        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            lines.add(line);
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();

        return lines;
    }

    /**
     * 读取文本文件内容
     *
     * @param filePath 文件所在路径
     * @return 文本内容
     * @throws IOException 异常
     * @author cn.outofmemory
     * @date 2013-1-7
     */
    public static String readFile(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        FileUtils.readToBuffer(sb, filePath);
        return sb.toString();
    }

    public static String readFileByReplaceEmptyLine(String filePath) throws IOException {
        return FileUtils.replaceEmptyLiner(readFile(filePath));
    }

    public static String replaceEmptyLiner(String s) {
        return s.replaceAll("(?m)^\\s*$[\n\r]{1,}", "");
    }

    public static void writeFile(String filePath, String content) throws IOException {
        FileOutputStream fop = null;
        File file;
        try {
            file = new File(filePath);
            fop = new FileOutputStream(file);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void deleteFold(File outPath) {
        File[] list = outPath.listFiles();
        for (File file : list) {
            file.delete();
        }
    }


    public static void clearFold(File outPath) {
        if (outPath.exists()) {
            FileUtils.deleteFold(outPath);
        }
        checkAndCreate(outPath);
    }

    public static void checkAndCreate(File outPath) {
        if (outPath.exists() == false) {
            outPath.mkdir();
        }
    }

}
