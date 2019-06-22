package edu.scut.luluteam.bathcontroller.model.msg.setting;

import edu.scut.luluteam.bathcontroller.constant.MsgConstant;
import edu.scut.luluteam.bathcontroller.model.CustomFrame;
import edu.scut.luluteam.bathcontroller.model.msg.BaseMsg;
import edu.scut.luluteam.bathcontroller.utils.ByteUtil;

/**
 * @author Guan
 * @date Created on 2019/4/24
 */
public class SettingWiFiMsg extends BaseMsg.BaseSettingMsg {
    private String ssid;

    private String pwd;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public SettingWiFiMsg() {
        super(MsgConstant.MSG_TYPE_SETTING_WIFI);
    }

    @Override
    public byte[] toByte() {

        byte[] bytes = new byte[5 + ssid.length() + pwd.length()];
        bytes[0] = MsgConstant.MSG_TYPE_SETTING_WIFI;
        int offset = 1;
        System.arraycopy(ByteUtil.toByteArray(ssid.length() + pwd.length() + 2), 0, bytes, offset, 2);
        offset += 2;
        System.arraycopy(ssid.getBytes(), 0, bytes, offset, ssid.length());
        offset += ssid.length();
        bytes[offset] = MsgConstant.SEPARATOR;
        offset++;
        System.arraycopy(pwd.getBytes(), 0, bytes, offset, pwd.length());
        offset += pwd.length();
        bytes[offset] = MsgConstant.SEPARATOR;
        return CustomFrame.encapsulateFrame(bytes);

    }
}
