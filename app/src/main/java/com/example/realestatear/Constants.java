package com.example.realestatear;

import android.content.Context;

import java.io.File;

public class Constants {

    public static File cur_project_dir;

    public static File getProjectDir(Context c) {
        File dir = new File(c.getDataDir(), "/projects");
        return dir;
    }

    public static File getIndoorDir(Context c) {
        File dir = new File(cur_project_dir, "/indoor");
        return dir;
    }

    public static File getCommonDir(Context c) {
        File dir = new File(cur_project_dir, "/common");
        return dir;
    }

    public static File getOutdoorDir(Context c) {
        File dir = new File(cur_project_dir, "/outdoor");
        return dir;
    }

}
