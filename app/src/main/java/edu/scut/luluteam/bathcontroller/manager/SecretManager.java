package edu.scut.luluteam.bathcontroller.manager;

import android.util.Base64;
import android.util.Log;

import org.apache.commons.lang.StringUtils;

import edu.scut.luluteam.bathcontroller.utils.SharedPreferencesUtil;

/**
 * @author Guan
 * @date Created on 2019/5/10
 */
public class SecretManager {

    /**
     * 策略：第一次使用App，有一个初始密码。之后便可以修改这个密码。
     * 假如某一天忘了密码，可以重新安装App，即可恢复初始密码
     */

    private static final String INIT_PWD = "password";
    private static final String KEY_SAVE_SECRET_MANAGER_PWD = "key_save_secret_manager_pwd";
    private static final String DEFAULT_CHARSET_NAME = "UTF-8";
    private static final String TAG = "SecretManager";
    private static SecretManager instance;

    private String pwd;

    private SecretManager() {

        pwd = loadPwd();
    }

    public static SecretManager getInstance() {
        if (instance == null) {
            synchronized (SecretManager.class) {
                if (instance == null) {
                    instance = new SecretManager();
                }
            }
        }
        return instance;
    }

    /**
     * 验证密码
     *
     * @param pwd
     * @return
     */
    public boolean checkPwd(String pwd) {
        return this.pwd.equals(pwd);
    }

    /**
     * 修改密码
     *
     * @param oldPwd
     * @param newPwd
     * @return
     */
    public boolean changePwd(String oldPwd, String newPwd) {
        if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)) {
            Log.e(TAG, "密码不允许为空");
            return false;
        }
        if (!pwd.equals(oldPwd)) {
            Log.e(TAG, "修改失败：旧密码错误");
            return false;
        }
        savePwd(newPwd);
        pwd = newPwd;
        return true;
    }


    //=======================================================================================
    //
    //=======================================================================================

    private String loadPwd() {
        try {
            String result;
            String loadPwd = SharedPreferencesUtil.getString(App.getAppContext(), KEY_SAVE_SECRET_MANAGER_PWD);
            if (StringUtils.isEmpty(loadPwd)) {
                result = INIT_PWD;
            } else {
                result = new String(Base64.decode(loadPwd, Base64.DEFAULT), DEFAULT_CHARSET_NAME);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return INIT_PWD;
        }
    }

    private void savePwd(String newPwd) {
        try {
            String pwdEncodedString = Base64.encodeToString(newPwd.getBytes(DEFAULT_CHARSET_NAME), Base64.DEFAULT);
            SharedPreferencesUtil.putString(App.getAppContext(), KEY_SAVE_SECRET_MANAGER_PWD, pwdEncodedString);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
