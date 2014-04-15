package de.greenrobot.daogenerator.utils;

import java.io.File;

/**
 * Created by djzhang on 4/14/14.
 */
public class Tools {

    private static String PROJECT_NAME = ".Build_IOSXmlGenerator";

    //.......................................................................................................................................
    //  Config
    //.......................................................................................................................................

    private static File getProfile() {
        File home = new File(System.getenv("HOME"), PROJECT_NAME);
        FileUtils.checkAndCreate(home);
        return home;
    }

    public static File getConfig() {
        File config = new File(Tools.getProfile(), "config");
        FileUtils.checkAndCreate(config);
        return config;
    }


    //.......................................................................................................................................
    //  temp and out
    //.......................................................................................................................................

    public static File getOutFold() {
        File out = new File(Tools.getProfile(), "out");
        FileUtils.checkAndCreate(out);
        return out;
    }

    public static File getDaoGeneratorFold(String foldName) {
        // 1. generate fold
        File generateFold = new File(Tools.getOutFold(), "src-Generate");
        FileUtils.checkAndCreate(generateFold);
        // 2. current project fold
        File srcFold = new File(generateFold, foldName);
        FileUtils.checkAndCreate(srcFold);

        return srcFold;
    }

}
