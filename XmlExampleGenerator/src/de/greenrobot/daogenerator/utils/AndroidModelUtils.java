package de.greenrobot.daogenerator.utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by djzhang on 4/14/14.
 */
public class AndroidModelUtils {


    public void replaceDefine(File activityFold) {
        File[] list = activityFold.listFiles();
        for (File f : list) {
            if (f.isDirectory()) {
                replaceDefine(f);
            } else {
                this.saveReplaceFile(f);
            }
        }

    }

    private void saveReplaceFile(File file) {
        String data = null;
        try {
            data = FileUtils.readFileByReplaceEmptyLine(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (data == null) {
            return;
        }
        data = this.replaceData(data);

        try {
            FileUtils.writeFile(file.getAbsolutePath(), data);
        } catch (IOException e) {
            System.out.println("AndroidModelUtils.saveReplaceFile" + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    private String replaceData(String data) {
        data = data.replace("private String ", "@property (nonatomic, copy) NSString *");
        data = data.replace("private Date ", "@property (nonatomic, copy) NSString *");

        return data;
    }

}
