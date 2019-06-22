package edu.scut.luluteam.bathcontroller.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.lang.reflect.Field;

import edu.scut.luluteam.bathcontroller.manager.App;

/**
 * Created by guan on 5/19/17.
 */

public class DisplayUtil {

    private static DisplayUtil instance;

    private int screenWidth;
    private int screenHeight;
    private int densityDpi;
    private int statusBarHeight;


    private static String TAG = "DisplayUtil";

    private DisplayUtil() {
        saveScreenInfo(App.getAppContext());
    }

    public static DisplayUtil getInstance() {
        if (instance == null) {
            synchronized (DisplayUtil.class) {
                if (instance == null) {
                    instance = new DisplayUtil();
                }
            }
        }
        return instance;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getDensityDpi() {
        return densityDpi;
    }

    public int getStatusBarHeight() {
        return statusBarHeight;
    }

    /**
     * 获取并保存屏幕大小
     *
     * @param mContext
     */
    public void saveScreenInfo(Context mContext) {
        //通过Application获取屏幕信息
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(metric);
        //通过Activity获取屏幕信息
        //((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);

        // 屏幕宽度（像素）
        screenWidth = metric.widthPixels;
        // 屏幕高度（像素）
        screenHeight = metric.heightPixels;
        densityDpi = metric.densityDpi;

        Log.d(TAG, "screenWidth:\t" + screenWidth +
                "\tscreenHeight:\t" + screenHeight +
                "\tdensityDpi:\t" + densityDpi);
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    private int saveStatusBarHeight(Context mContext) {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = mContext.getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }
}
