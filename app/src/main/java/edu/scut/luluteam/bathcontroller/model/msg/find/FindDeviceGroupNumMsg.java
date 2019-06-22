package edu.scut.luluteam.bathcontroller.model.msg.find;

import edu.scut.luluteam.bathcontroller.constant.MsgConstant;
import edu.scut.luluteam.bathcontroller.model.msg.BaseMsg;
import edu.scut.luluteam.bathcontroller.utils.TransHelper;

/**
 * @author Guan
 * @date Created on 2019/4/24
 */
public class FindDeviceGroupNumMsg extends BaseMsg {

    private String toiletId;

    private String deviceGroup;

    private String deviceNum;

    public FindDeviceGroupNumMsg(byte msgType, String toiletId, String deviceGroup, String deviceNum) {
        super(msgType);
        this.toiletId = toiletId;
        this.deviceGroup = deviceGroup;
        this.deviceNum = deviceNum;
    }

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

    public FindDeviceGroupNumMsg() {
        super(MsgConstant.MSG_TYPE_FIND_DEVICE_GROUP_NUM);
    }


    /**
     * TODO 解析byte数据，转换成对象
     *
     * @param msg
     * @return
     */
    public static FindDeviceGroupNumMsg parseByte(byte[] msg) {
        int offset = 3;
        String toiletId = TransHelper.getToiletId(msg, offset, 8);
        offset += 8;
        String deviceGroup = TransHelper.getIntValue(msg, offset, 1) + "";
        offset += 1;
        String deviceNum = TransHelper.getIntValue(msg, offset, 1) + "";
        FindDeviceGroupNumMsg findDeviceGroupNumMsg = new FindDeviceGroupNumMsg();
        findDeviceGroupNumMsg.toiletId = toiletId;
        findDeviceGroupNumMsg.deviceGroup = deviceGroup;
        findDeviceGroupNumMsg.deviceNum = deviceNum;
        return findDeviceGroupNumMsg;

    }

    /**
     * 生成一个查询DeviceGroup和Num的命令
     *
     * @return
     */
    public static byte[] genCmd() {

        return MsgConstant.CMD_FIND_DEVICE_GROUP_NUM;
    }
}
