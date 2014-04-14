package de.greenrobot.daogenerator.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: djzhang
 * Date: 5/2/13
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public final class RegMatcherHelper {
    /**
     * Find the image tags
     *
     * @param content The original text
     * @return Parameters' list of IMG tag, 0 is whole IMG tag, 1 is IMG URL
     */
    public static List<RegMatchModel> getRegExpImgs(String content, String regEx) {
        List<RegMatchModel> regMatchModels = new ArrayList<RegMatchModel>();
        try {
            Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);//表示整体都忽略大小写
            Matcher m = p.matcher(content);
            while (m.find()) {
                RegMatchModel regMatchModel = new RegMatchModel();
                regMatchModel.mMatch = m.group(0);
                regMatchModel.mStart = m.start(0);
                regMatchModel.mEnd = m.end(0);
                regMatchModels.add(regMatchModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return regMatchModels;
    }


    public static class RegMatchModel {
        public String mMatch;
        public String mSubMatch;
        public int mStart;
        public int mEnd;
    }
}
