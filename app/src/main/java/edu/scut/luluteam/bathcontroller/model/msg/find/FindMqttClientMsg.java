package edu.scut.luluteam.bathcontroller.model.msg.find;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import edu.scut.luluteam.bathcontroller.constant.MsgConstant;
import edu.scut.luluteam.bathcontroller.model.msg.BaseMsg;
import edu.scut.luluteam.bathcontroller.utils.ByteUtil;

/**
 * @author Guan
 * @date Created on 2019/4/24
 */
public class FindMqttClientMsg extends BaseMsg {

    private String mqttClientId;

    public String getMqttClientId() {
        return mqttClientId;
    }

    public void setMqttClientId(String mqttClientId) {
        this.mqttClientId = mqttClientId;
    }

    public FindMqttClientMsg() {
        super(MsgConstant.MSG_TYPE_FIND_MQTT_CLIENT);
    }

    /**
     * TODO 解析byte数据，转换成对象
     *
     * @param msg
     * @return
     */
    public static FindMqttClientMsg parseByte(byte[] msg) {
        int offset = 1;
        int msgLength = ByteUtil.toInteger(msg[offset], msg[offset + 1]);
        offset = 3;
        FindMqttClientMsg findMqttClientMsg = new FindMqttClientMsg();
        if (msgLength == 0) {
            return findMqttClientMsg;
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
        if (msg[offset + msgLength - 1] != '\0') {
            list.add(newBytes);
        }
//        findMqttClientMsg.mqttClientId = ByteUtil.byte2string(list.get(0));
        try {
            findMqttClientMsg.mqttClientId = new String(list.get(0), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // findMqttClientMsg.mqttClientId= TransHelper;

        return findMqttClientMsg;
    }


    /**
     * TODO 生成一个查询MQTT client命令
     *
     * @return
     */
    public static byte[] genCmd() {

        return null;
    }
}
