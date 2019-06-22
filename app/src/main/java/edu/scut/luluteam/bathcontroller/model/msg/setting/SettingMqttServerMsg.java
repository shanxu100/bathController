package edu.scut.luluteam.bathcontroller.model.msg.setting;

import edu.scut.luluteam.bathcontroller.constant.MsgConstant;
import edu.scut.luluteam.bathcontroller.model.CustomFrame;
import edu.scut.luluteam.bathcontroller.model.msg.BaseMsg;
import edu.scut.luluteam.bathcontroller.utils.ByteUtil;

/**
 * @author Guan
 * @date Created on 2019/4/24
 */
public class SettingMqttServerMsg extends BaseMsg.BaseSettingMsg {

    private String mqttServer;

    private String mqttPort;

    public String getMqttServer() {
        return mqttServer;
    }

    public void setMqttServer(String mqttServer) {
        this.mqttServer = mqttServer;
    }

    public String getMqttPort() {
        return mqttPort;
    }

    public void setMqttPort(String mqttPort) {
        this.mqttPort = mqttPort;
    }

    public SettingMqttServerMsg() {
        super(MsgConstant.MSG_TYPE_SETTING_MQTT_SERVER);
    }

    @Override
    public byte[] toByte() {
//        byte[] bytes = new byte[11];
//        bytes[0] = MsgConstant.MSG_TYPE_SETTING_MQTT_SERVER;
//        int offset = 1;
//        bytes[1] = 0x00;
//        bytes[2] = 0x08;
//        offset += 2;
//        String[] mqttServerArray = mqttServer.split("\\.");
//        bytes[offset++] = (byte) Integer.parseInt(mqttServerArray[0]);
//        bytes[offset++] = (byte) Integer.parseInt(mqttServerArray[1]);
//        bytes[offset++] = (byte) Integer.parseInt(mqttServerArray[2]);
//        bytes[offset++] = (byte) Integer.parseInt(mqttServerArray[3]);
//        bytes[offset] = MsgConstant.SEPARATOR;
//        offset++;
//        byte[] mqttPortBytes = ByteUtil.toByteArray(Integer.parseInt(mqttPort));
//        System.arraycopy(mqttPortBytes, 0, bytes, offset, 2);
//        offset += 2;
//        bytes[offset] = MsgConstant.SEPARATOR;
//        return CustomFrame.encapsulateFrame(bytes);

        byte[] bytes = new byte[5 + 2 + mqttServer.length()];
        bytes[0] = MsgConstant.MSG_TYPE_SETTING_MQTT_SERVER;
        int offset = 1;
        System.arraycopy(ByteUtil.toByteArray(mqttServer.length() + 2 + 2), 0, bytes, offset, 2);
        offset += 2;
        System.arraycopy(mqttServer.getBytes(), 0, bytes, offset, mqttServer.length());
        offset += mqttServer.length();
        bytes[offset] = MsgConstant.SEPARATOR;
        offset++;
        byte[] mqttPortBytes = ByteUtil.toByteArray(Integer.parseInt(mqttPort));
        try {
            System.arraycopy(mqttPortBytes, 0, bytes, offset, 2);
        }catch (Exception e){
            e.printStackTrace();
        }
        offset += 2;
        bytes[offset] = MsgConstant.SEPARATOR;
        offset++;
        return CustomFrame.encapsulateFrame(bytes);
    }
}
