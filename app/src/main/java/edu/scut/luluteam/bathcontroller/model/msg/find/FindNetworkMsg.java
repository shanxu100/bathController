package edu.scut.luluteam.bathcontroller.model.msg.find;

import java.util.LinkedList;
import java.util.List;

import edu.scut.luluteam.bathcontroller.constant.MsgConstant;
import edu.scut.luluteam.bathcontroller.model.msg.BaseMsg;
import edu.scut.luluteam.bathcontroller.utils.ByteUtil;

/**
 * @author Guan
 * @date Created on 2019/4/24
 */
public class FindNetworkMsg extends BaseMsg {

    private String ssid;

    private String pwd;

    private String mqttServer;

    private String mqttPort;

    private String mqttAdm;

    private String mqttPwd;

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

    public FindNetworkMsg() {
        super(MsgConstant.MSG_TYPE_FIND_NETWORK);
    }

    /**
     * TODO 解析byte数据，转换成对象
     *
     * @param msg
     * @return
     */
    public static FindNetworkMsg parseByte(byte[] msg) {
        int offset = 1;
        int msgLength = ByteUtil.toInteger(msg[offset], msg[offset + 1]);
        offset = 3;
        FindNetworkMsg msgObject = new FindNetworkMsg();

        if (msgLength == 0) {
            return msgObject;
        }
        List<byte[]> list = new LinkedList<>();
        byte[] newBytes = new byte[0];
        int startIndex = 0;
        for (int i = 0; i < msgLength; i++) {
            if (msg[offset + i] == '\0') {
                newBytes = new byte[i - startIndex];
                System.arraycopy(msg, offset + startIndex, newBytes, 0, i - startIndex);
                startIndex = i + 1;
                list.add(newBytes);
            }
        }
        if (msg[offset + msgLength - 1] != MsgConstant.SEPARATOR) {
            list.add(newBytes);
        }
        msgObject.ssid = ByteUtil.byte2string(list.get(0));
        msgObject.pwd = ByteUtil.byte2string(list.get(1));

//        msgObject.mqttServer = ByteUtil.toInteger(list.get(2)[0]) + "." + ByteUtil.toInteger(list.get(2)[1]) + "." + ByteUtil.toInteger(list.get(2)[2]) + "." + ByteUtil.toInteger(list.get(2)[3]);
        msgObject.mqttServer = new String(list.get(2));

        msgObject.mqttPort = String.valueOf(ByteUtil.toInteger(list.get(3)[0], list.get(3)[1]));

        msgObject.mqttAdm = ByteUtil.byte2string(list.get(4));
        msgObject.mqttPwd = ByteUtil.byte2string(list.get(5));

        return msgObject;
    }

    /**
     * 解析字节的第二种方法
     *
     * @param msg
     * @return
     */
    public static FindNetworkMsg parseByte2(byte[] msg) {
        int offset = 1;
        int msgLength = ByteUtil.toInteger(msg[offset], msg[offset + 1]);
        offset = 3;
        FindNetworkMsg msgObject = new FindNetworkMsg();
        if (msgLength == 0) {
            return msgObject;
        }

        byte[] bytes = new byte[msgLength];
        System.arraycopy(msg, offset, bytes, 0, msgLength);

        String data = new String(bytes);
        String[] ss = data.split("\\\0");


        msgObject.ssid = "";
        msgObject.pwd = "";

        return null;
    }

    /**
     * TODO 生成一个查询Network的命令
     *
     * @return
     */
    public static byte[] genCmd() {

        return null;
    }
}
