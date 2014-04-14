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
//        System.out.println(".........................................................................");
//        System.out.println("# " + file.getName());
//        System.out.println(".........................................................................");

        String replaceData = this.replaceData(data);

        try {
            if (replaceData.equals(data)) {
//                System.out.println("ActitivyReplaceUtils.saveReplaceFile" + file.getAbsolutePath());
            } else {
//                System.out.println("ActitivyReplaceUtils.saveReplaceFile" + file.getAbsolutePath());
                FileUtils.writeFile(file.getAbsolutePath(), replaceData);
            }
        } catch (Exception e) {
            System.out.println("<***>Exception: ActitivyReplaceUtils.saveReplaceFile" + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    private String replaceData(String data) {
        List<RegMatcherHelper.RegMatchModel> regMatchModels = RegMatcherHelper.getRegExpImgs(data, "params.add.*value.*;");
        for (RegMatcherHelper.RegMatchModel model : regMatchModels) {
            data = this.setObjectPara(data, model);
        }
        return data;
    }

    private String setObjectPara(String data, RegMatcherHelper.RegMatchModel model) {
        int mStart = model.mStart;
        int mEnd = model.mEnd;
        String mMatch = model.mMatch;
        String before = data.substring(0, mStart);
        String after = data.substring(mEnd);

        String replaceData = this.getReplaceObject(mMatch);

        System.out.println(replaceData);

        return String.format("%s%s%s", before, replaceData, after);
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
            System.out.println("leftStr = " + leftStr);
//            System.out.println("rightStr = " + rightStr);
            System.out.println("s = " + s);
            return s;
        }
        return line;
    }


    private String setLeft(String left) {
        left = left.replace("params.add(new BasicNameValuePair(", "[AppNetAPIClient putToDictionary:dic forKey:@");
//        System.out.println("left = " + left);
        return left;
    }

    private String setRight(String right) {
        right = right.replace("))", "").replace(" ", "").replace(";", "];");
//        if (right.contains(".")) {
//            return right;
//        }
        String s = " forString:self.info." + right;

        System.out.println("rightStr = " + s);
//        System.out.println("s = " + s);
        return s;
    }


}
