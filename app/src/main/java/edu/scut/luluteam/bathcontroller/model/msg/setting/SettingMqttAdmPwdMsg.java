package edu.scut.luluteam.bathcontroller.model.msg.setting;

import edu.scut.luluteam.bathcontroller.constant.MsgConstant;
import edu.scut.luluteam.bathcontroller.model.CustomFrame;
import edu.scut.luluteam.bathcontroller.model.msg.BaseMsg;
import edu.scut.luluteam.bathcontroller.utils.ByteUtil;

/**
 * @author Guan
 * @date Created on 2019/4/24
 */
public class SettingMqttAdmPwdMsg extends BaseMsg.BaseSettingMsg {
    private String mqttAdm;

    private String mqttPwd;

    public String getMqttAdm() {
        return mqttAdm;
    }

    public void setMqttAdm(String mqttAdm) {
        this.mqttAdm = mqttAdm;
    }

    public String getMqttPwd() {
        return mqttPwd;
    }

    public void setMqttPwd(String mqttPwd) {
        this.mqttPwd = mqttPwd;
    }

    public SettingMqttAdmPwdMsg() {
        super(MsgConstant.MSG_TYPE_SETTING_MQTT_ADM_PWD);
    }

    @Override
    public byte[] toByte() {

        byte[] bytes = new byte[5 + mqttAdm.length() + mqttPwd.length()];
        bytes[0] = MsgConstant.MSG_TYPE_SETTING_MQTT_ADM_PWD;
        int offset = 1;
        System.arraycopy(ByteUtil.toByteArray(mqttAdm.length() + mqttPwd.length() + 2), 0, bytes, offset, 2);
        offset += 2;
        System.arraycopy(mqttAdm.getBytes(), 0, bytes, offset, mqttAdm.length());
        offset += mqttAdm.length();
        bytes[offset] = MsgConstant.SEPARATOR;
        offset++;
        System.arraycopy(mqttPwd.getBytes(), 0, bytes, offset, mqttPwd.length());
        offset += mqttPwd.length();
        bytes[offset] = MsgConstant.SEPARATOR;
        return CustomFrame.encapsulateFrame(bytes);
    }
}
