package de.greenrobot.daogenerator.utils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by djzhang on 4/14/14.
 */
public class ActitivyReplaceUtils {

    public void replacePut(File activityFold) {
        File[] list = activityFold.listFiles();
        for (File f : list) {
            if (f.isDirectory()) {
                replacePut(f);
            } else {
                this.saveReplaceFile(f);
            }
        }

    }

    private void saveReplaceFile(File file) {
        LinkedList<String> lines = null;
        try {
            lines = FileUtils.readToList(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lines == null) {
            return;
        }
//        System.out.println(".........................................................................");
//        System.out.println("# " + file.getName());
//        System.out.println(".........................................................................");

        boolean hasChanged = false;
        StringBuffer buffer = new StringBuffer();
        for (String line : lines) {
            String data = this.replaceLine(line);
            if (data.equals(line) == false) {
                hasChanged = true;
                System.out.println("data = " + data);
            }
//            if (line.equals("\n") || line.equals("\r") || line.equals("\n\r") || line.equals("\r\n")) {
//                continue;
//            }
            if (line.contains("System.out.println")) {
                continue;
            }
            buffer.append(data); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
        }
        try {
            if (hasChanged) {
                String fileData = FileUtils.replaceEmptyLiner(buffer.toString());
                FileUtils.writeFile(file.getAbsolutePath(), fileData);
            } else {
            }
        } catch (Exception e) {
            System.out.println("<***>Exception: ActitivyReplaceUtils.saveReplaceFile" + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    private String replaceLine(String line) {
        if (line.contains("params.add") && line.contains("BasicNameValuePair")) {
            return getReplaceObject(line);
        }
        return line;
    }


    /**
     * params.add(new BasicNameValuePair("leave_reason", leave_reason_str));
     *
     * @param line
     * @return
     */
    private String getReplaceObject(String line) {
        String[] split = line.split(",");
        if (split.length > 1) {
            String left = split[0];
            String right = split[1];
            String leftStr = setLeft(left);
            String rightStr = setRight(right);
            String s = String.format("%s%s\n\r", leftStr, rightStr);
            s.replace("\"\"+", " ");
            return s;
        }
        return line;
    }


    private String setLeft(String left) {
        left = left.replace("params.add(new BasicNameValuePair(", "[AppNetAPIClient putToDictionary:dic forKey:@");
        return left;
    }

    private String setRight(String right) {
        right = right.replace("))", "").replace(" ", "").replace(";", "];");
        String s = " forString:self.info." + right;
        return s;
    }


}
