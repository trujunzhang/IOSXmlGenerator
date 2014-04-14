package de.greenrobot.daogenerator.utils;

import java.io.File;

/**
 * Created by djzhang on 4/14/14.
 */
public class Tools {

    private static String PROJECT_NAME = ".Build_IOSXmlGenerator";

    public static File getProfile() {
        File home = new File(System.getenv("HOME"), PROJECT_NAME);
        if (home.exists() == false) {
            home.mkdir();
        }
        return home;
    }
}
