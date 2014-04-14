package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.utils.ActitivyReplaceUtils;
import de.greenrobot.daogenerator.utils.AndroidModelUtils;

import java.io.File;

/**
 * Created by djzhang on 4/14/14.
 */
public class ReplaceAndroidFilesHelper {

    public static final String ANDROID_SRC = "/Volumes/macshare/Home/SHARE/developing/svn/xmobile_sa/android/移动外勤/xinmabaseall/src-object";

    public static final String MODEL_PATH = "com/xinma/xmobile/attendance/model";
    public static final String ACTIVITY_PATH = "com/xinma/yidongwaiqin";

    public static void main(String[] args) {
        // 1. convert model's variable's Defintion to object-c's Definition
        new AndroidModelUtils().replaceDefine(new File(ANDROID_SRC, MODEL_PATH));

        // 2. replace server request's parameter to object-c's format.(put)
        new ActitivyReplaceUtils().replacePut(new File(ANDROID_SRC, ACTIVITY_PATH));
    }

}
