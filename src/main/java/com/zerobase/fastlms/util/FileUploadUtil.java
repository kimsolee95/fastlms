package com.zerobase.fastlms.util;

import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

public class FileUploadUtil {

    /**
     * [0]local Path, [1]url Path 반환
     * {newFilename, newUrlFilename}
     * */
    public static String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFilename) {

        LocalDate now = LocalDate.now();
        String separator = System.getProperty("file.separator"); //os 맞춤 구분자

        String[] dirs = {
                String.format("%s%s%d", baseLocalPath, separator, now.getYear())
                , String.format("%s%s%d%s%02d%s", baseLocalPath, separator, now.getYear(), separator,  now.getMonthValue(), separator)
                , String.format("%s%s%d%s%02d%s%02d%s", baseLocalPath, separator, now.getYear(), separator, now.getMonthValue(), separator,  now.getDayOfMonth(), separator)};

        String urlDir = String.format("%s%s%d%s%02d%s%02d%s", baseUrlPath, separator, now.getYear(), separator, now.getMonthValue(), separator, now.getDayOfMonth(), separator);

        for (String dir : dirs) {

            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension = "";

        if (originalFilename != null) {

            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newFilename = String.format("%s%s", dirs[2], uuid); //local path
        String newUrlFilename = String.format("%s%s", urlDir, uuid); //url path

        if (fileExtension.length() > 0) {
            newFilename += "." + fileExtension;
            newUrlFilename += "." + fileExtension;
        }

        String[] returnFilename = {newFilename, newUrlFilename};

        return returnFilename;
    }

}
