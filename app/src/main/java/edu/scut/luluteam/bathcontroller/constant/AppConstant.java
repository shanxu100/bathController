package edu.scut.luluteam.bathcontroller.constant;

import java.util.Observable;

/**
 * @author Guan
 * @date Created on 2019/6/4
 */
public class AppConstant {

    /**
     * 当前是否配置模式
     * true: 当前是配置模式
     * false：当前不是配置模式
     */
    private static volatile boolean CONFIG_MODE = false;

    public static boolean isConfigMode() {
        return CONFIG_MODE;
    }

    public static void setConfigMode(boolean configMode) {
        CONFIG_MODE = configMode;
    }



}
