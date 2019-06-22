package edu.scut.luluteam.bathcontroller.manager;

import java.util.Observable;

/**
 * @author Guan
 * @date Created on 2019/6/4
 */
public class ConfigModeManager extends Observable {
    /**
     * 当前是否配置模式
     * true: 当前是配置模式
     * false：当前不是配置模式
     */
    private boolean CONFIG_MODE = false;

    private static ConfigModeManager instance = null;

    public static ConfigModeManager getInstance() {
        if (instance == null) {
            synchronized (ConfigModeManager.class) {
                if (instance == null) {
                    instance = new ConfigModeManager();
                }
            }
        }
        return instance;
    }

    public boolean isConfigMode() {
        return CONFIG_MODE;
    }

    public void setConfigMode(boolean configMode) {
        CONFIG_MODE = configMode;
        setChanged();
        notifyObservers();
    }


}
