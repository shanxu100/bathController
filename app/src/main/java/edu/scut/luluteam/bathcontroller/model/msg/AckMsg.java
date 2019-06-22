package edu.scut.luluteam.bathcontroller.model.msg;

import edu.scut.luluteam.bathcontroller.constant.MsgConstant;
import edu.scut.luluteam.bathcontroller.utils.ByteUtil;

/**
 * @author Guan
 * @date Created on 2019/5/2
 */
public class AckMsg extends BaseMsg {


    /**
     * ackType以String的形式
     */
    private String ackTypeHex;
    private byte ackType;

    private boolean ackState;


    public String getAckTypeHex() {
        return ackTypeHex;
    }

    public void setAckTypeHex(String ackTypeHex) {
        this.ackTypeHex = ackTypeHex;
    }

    public byte getAckType() {
        return ackType;
    }

    public void setAckType(byte ackType) {
        this.ackType = ackType;
    }

    public boolean isAckState() {
        return ackState;
    }

    public void setAckState(boolean ackState) {
        this.ackState = ackState;
    }

    public AckMsg() {
        super(MsgConstant.MSG_TYPE_ACK);
    }


    public static AckMsg parseByte(byte[] msg) {
        int offset = 1;
        int msgLength = ByteUtil.toInteger(msg[offset], msg[offset + 1]);
        offset = 3;
        AckMsg ackMsg = new AckMsg();
        if (msgLength == 2) {
            ackMsg.ackTypeHex = ByteUtil.byte2hex(new byte[]{msg[offset]});
            ackMsg.ackType = msg[offset];
            offset++;
            if (msg[offset] == 0x00) {
                ackMsg.ackState = false;
            } else if (msg[offset] == 0x01) {
                ackMsg.ackState = true;
            }
            offset++;
        }
        return ackMsg;

    }

}
