package edu.scut.luluteam.bathcontroller.model.msg;

import com.google.gson.Gson;

/**
 * @author Guan
 * @date Created on 2019/4/24
 */
public abstract class BaseMsg {

    public byte msgType;

    public int msgLength;

    public BaseMsg(byte msgType) {

        this.msgType = msgType;

    }

    public String toJson() {
        return new Gson().toJson(this);
    }


    /**
     * 将toByte抽象出来，让其子类全部实现toByte()方法
     */
    public static abstract class BaseSettingMsg extends BaseMsg {

        public BaseSettingMsg(byte msgType) {
            super(msgType);
        }

        public abstract byte[] toByte();
    }

}
