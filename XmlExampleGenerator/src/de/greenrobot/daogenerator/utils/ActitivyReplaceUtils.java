package de.greenrobot.daogenerator.utils;

import java.io.File;
import java.io.IOException;
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
        String data = null;
        try {
            data = FileUtils.readFile(file.getAbsolutePath());
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
            System.out.println("ActitivyReplaceUtils.saveReplaceFile" + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    private String replaceData(String data) {
        List<RegMatcherHelper.RegMatchModel> regMatchModels = RegMatcherHelper.getRegExpImgs(data, "params.*;");
        for (RegMatcherHelper.RegMatchModel model : regMatchModels) {
            String mMatch = model.mMatch;
            System.out.println("mMatch = " + mMatch);
        }

//        int index = data.indexOf("params.add(new BasicNameValuePair(");

//        data = data.replace("private String ", "@property (nonatomic, copy) NSString *");
//        data = data.replace("private Date ", "@property (nonatomic, copy) NSString *");

        return data;
    }


}
