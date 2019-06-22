package edu.scut.luluteam.bathcontroller.constant;


/**
 * @author Guan
 * @date 2017/9/18
 */
public class MsgConstant {

    /**
     * 帧头和帧尾
     */
    public static final byte FRAME_SOF = 0x5C;
    public static final byte FRAME_EOF = 0x2F;
    public static final byte SEPARATOR = 0x00;


    /**
     * 查询和设置
     */
    public static final byte MSG_TYPE_FIND_DEVICE_GROUP_NUM = 0x01;
    public static final byte MSG_TYPE_FIND_NETWORK = 0x02;
    public static final byte MSG_TYPE_FIND_MQTT_CLIENT = 0x03;
    public static final byte MSG_TYPE_SETTING_DEVICE_GROUP_NUM = 0x06;
    public static final byte MSG_TYPE_SETTING_WIFI = 0x07;
    public static final byte MSG_TYPE_SETTING_MQTT_SERVER = 0x08;
    public static final byte MSG_TYPE_SETTING_MQTT_ADM_PWD = 0x09;
    public static final byte MSG_TYPE_ACK = 0x10;

    //=================================================================
    // 校验输入的固定字段
    //=================================================================
    public static final int CHECK_FIX_LENGTH_TOILET_ID = 8;
    public static final int CHECK_MAX_MQTT_PORT = 65535;
    public static final int CHECK_MIN_MQTT_PORT = 1024;


    //==================================================================
    //DeviceGroup相关字段
    //==================================================================

    public static final byte DEVICE_GROUP_TOILET_PIT_MAN = 0x01;
    public static final byte DEVICE_GROUP_TOILET_PIT_WOMAN = 0x02;
    public static final byte DEVICE_GROUP_TOILET_PIT_DISABLED = 0x03;
    public static final byte DEVICE_GROUP_TOILET_METER_ELEC = 0x04;
    public static final byte DEVICE_GROUP_TOILET_METER_WATER = 0x05;
    /**
     * 气体探测器--男厕
     */
    public static final byte DEVICE_GROUP_TOILET_DETECTOR_ODOR_MAN = 0x06;
    /**
     * 气体探测器--女厕
     */
    public static final byte DEVICE_GROUP_TOILET_DETECTOR_ODOR_WOMAN = 0x09;
    /**
     * 温度湿度探测器
     */
    public static final byte DEVICE_GROUP_TOILET_DETECTOR_TEMP_HUMI = 0x07;
    public static final byte DEVICE_GROUP_TOILET_TIME = 0x08;

    //======================================================================================
    //deviceNum相关自字段
    //======================================================================================

    public static final byte DEVICE_NUM_TOILET_DETECTOR_ODOR_NH3 = 0x01;
    public static final byte DEVICE_NUM_TOILET_DETECTOR_ODOR_H2S = 0x02;


    //=============================================
    //查询相关
    //TODO 按照文档填写查询命令
    //=============================================

    public static final byte[] CMD_FIND_DEVICE_GROUP_NUM = {0x5C, 0x00, 0x07, 0x01, 0x00, 0x04, (byte) 0xAC, (byte) 0xCA, (byte) 0xCC, (byte) 0xFF, 0x38, (byte) 0xBE, 0x2F};
    public static final byte[] CMD_FIND_MQTT_CLIENT = {0x5C, 0x00, 0x07, 0x03, 0x00, 0x04, (byte) 0xAC, (byte) 0xCA, (byte) 0xCC, (byte) 0xFF, (byte) 0xF8, (byte) 0x9D, 0x2F};
    public static final byte[] CMD_FIND_NETWORK = {0x5C, 0x00, 0x07, 0x02, 0x00, 0x04, (byte) 0xAC, (byte) 0xCA, (byte) 0xCC, (byte) 0xFF, 0x38, (byte) 0x8D, 0x2F};

    /**
     * 进入和退出配置模式的数据帧
     * 恢复出厂设置的数据帧
     */
    public static final byte[] CMD_ENTER_CONFIG_MODE = {0x5C, 0x00, 0x07, AckType.ENTER_CONFIG_MODE, 0x00, 0x04, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x48, 0x1B, 0x2F};
    @Deprecated
    public static final byte[] CMD_EXIT_CONFIG_MODE_NOT_SAVE = {0x5C, 0x00, 0x07, AckType.EXITE_CONFIG_MODE_NOT_SAVE, 0x00, 0x04, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x88, 0x0B, 0x2F};
    @Deprecated
    public static final byte[] CMD_EXIT_CONFIG_MODE_SAVE = {0x5C, 0x00, 0x07, AckType.EXITE_CONFIG_MODE_SAVE, 0x00, 0x04, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x88, 0x38, 0x2F};
    public static final byte[] CMD_RESET_CONFIG = {0x5C, 0x00, 0x07, AckType.RESET_CONFIG, 0x00, 0x04, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x88, 0x0B, 0x2F};

    public static final String PATTERN_IP = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))";
    // public static final String PATTERN_PORT = "^[1-9]$|(^[1-9][0-9]$)|(^[1-9][0-9][0-9]$)|(^[1-9][0-9][0-9][0-9]$)|(^[1-6][0-5][0-5][0-3][0-5]$)";

    public static class AckType {
        public static final byte ENTER_CONFIG_MODE = (byte) 0xA0;
        @Deprecated
        public static final byte EXITE_CONFIG_MODE_NOT_SAVE = (byte) 0xA1;
        @Deprecated
        public static final byte EXITE_CONFIG_MODE_SAVE = (byte) 0xA2;
        public static final byte RESET_CONFIG = (byte) 0xA1;

    }
}
