package edu.scut.luluteam.bathcontroller.model.msg.setting;

import edu.scut.luluteam.bathcontroller.constant.MsgConstant;
import edu.scut.luluteam.bathcontroller.model.CustomFrame;
import edu.scut.luluteam.bathcontroller.model.msg.BaseMsg;
import edu.scut.luluteam.bathcontroller.utils.ByteUtil;
import edu.scut.luluteam.bathcontroller.utils.TransHelper;

/**
 * @author Guan
 * @date Created on 2019/4/24
 */
public class SettingDeviceGroupNumMsg extends BaseMsg.BaseSettingMsg {

    private String toiletId;

    private String deviceGroup;

    private String deviceNum;

    public String getToiletId() {
        return toiletId;
    }

    public void setToiletId(String toiletId) {
        this.toiletId = toiletId;
    }

    public String getDeviceGroup() {
        return deviceGroup;
    }

    public void setDeviceGroup(String deviceGroup) {
        this.deviceGroup = deviceGroup;
    }

    public String getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(String deviceNum) {
        this.deviceNum = deviceNum;
    }

    public SettingDeviceGroupNumMsg() {
        super(MsgConstant.MSG_TYPE_SETTING_DEVICE_GROUP_NUM);
    }

    @Override
    public byte[] toByte() {
        byte[] data = new byte[13];
        int offset = 0;
        byte msgType = MsgConstant.MSG_TYPE_SETTING_DEVICE_GROUP_NUM;
        data[offset++] = msgType;
        byte[] msgLength = ByteUtil.toByteArray(10);
        System.arraycopy(msgLength, 0, data, offset, 2);
        offset += 2;
        byte[] toiletIdBytes = TransHelper.getToiletIdBytes(this.toiletId);
        System.arraycopy(toiletIdBytes, 0, data, offset, 8);
        offset += 8;
        byte deviceGroupByte = (byte) Integer.parseInt(this.deviceGroup);
        data[offset++] = deviceGroupByte;
        byte deviceNumByte = (byte) Integer.parseInt((this.deviceNum));
        data[offset] = deviceNumByte;
        return CustomFrame.encapsulateFrame(data);
    }
}
