package edu.scut.luluteam.bathcontroller.manager.business;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.scut.luluteam.bathcontroller.constant.MsgConstant;
import edu.scut.luluteam.bathcontroller.manager.ToastManager;
import edu.scut.luluteam.bathcontroller.model.BaseData;
import edu.scut.luluteam.bathcontroller.model.CustomFrame;
import edu.scut.luluteam.bathcontroller.model.msg.AckMsg;
import edu.scut.luluteam.bathcontroller.model.msg.BaseMsg;
import edu.scut.luluteam.bathcontroller.model.msg.find.FindDeviceGroupNumMsg;
import edu.scut.luluteam.bathcontroller.model.msg.find.FindMqttClientMsg;
import edu.scut.luluteam.bathcontroller.model.msg.find.FindNetworkMsg;
import edu.scut.luluteam.bathcontroller.utils.ByteUtil;
import edu.scut.luluteam.bathcontroller.utils.CheckCRC;

/**
 * 多线程处理业务逻辑
 */
public class FrameProcessor {
    private static final String TAG = "FrameProcessor";

    private static ExecutorService executorService = Executors.newFixedThreadPool(2);


    public static void process(final CustomFrame customFrame) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                if (customFrame == null) {
                    return;
                }
                if (!checkCRC(customFrame)) {
                    reportError(customFrame.toBytes(), "CRC16 Check Error");
                    return;
                }
                //处理数据部分
                onMessage(customFrame);
            }
        });


    }

    /**
     * 识别一个帧后，做具体的业务处理
     *
     * @param customFrame
     */
    private static void onMessage(CustomFrame customFrame) {

        Log.i(TAG, "识别一个帧：" + ByteUtil.byte2hex(customFrame.toBytes()));

        byte[] msg = customFrame.getData();
        if (msg.length < 1) {
            //如果data<1，说明连msgType都没有
            return;
        }
        //TODO 走到这里说明识别了一个帧
        byte msgType = customFrame.getData()[0];
        BaseMsg baseMsg = null;
        if (msgType == MsgConstant.MSG_TYPE_FIND_DEVICE_GROUP_NUM) {
            baseMsg = FindDeviceGroupNumMsg.parseByte(msg);
        } else if (msgType == MsgConstant.MSG_TYPE_FIND_NETWORK) {
            baseMsg = FindNetworkMsg.parseByte(msg);
        } else if (msgType == MsgConstant.MSG_TYPE_FIND_MQTT_CLIENT) {
            baseMsg = FindMqttClientMsg.parseByte(msg);
        } else if (msgType == MsgConstant.MSG_TYPE_ACK) {
            baseMsg = AckMsg.parseByte(msg);
        } else {
            ToastManager.newInstance("未知msgType：" + ByteUtil.byte2hex(customFrame.toBytes()));
        }

        if (baseMsg != null) {
            EventBus.getDefault().post(new BaseData(baseMsg.toJson(), BaseData.EVENT_BUS_MSG_FROM_SERIAL_PORT));
        } else {
            Log.e(TAG, "解析数据帧出错");
        }


    }

    /**
     * 校验CRC
     *
     * @param customFrame
     * @return
     */
    private static boolean checkCRC(CustomFrame customFrame) {
        byte[] length_data = new byte[CustomFrame.LENGTH_SIZE + customFrame.getIntLength()];
        System.arraycopy(customFrame.getLength(), 0, length_data, 0, CustomFrame.LENGTH_SIZE);
        System.arraycopy(customFrame.getData(), 0, length_data, CustomFrame.LENGTH_SIZE, customFrame.getIntLength());
        return CheckCRC.check(length_data, customFrame.getCrc16());
    }

    /**
     * 报告错误，并将 帧 打印出来
     *
     * @param data
     * @param errInfo
     */
    protected static void reportError(byte[] data, String errInfo) {
        String frame_str = ByteUtil.byte2hex(data);
        ToastManager.newInstance("CustomFrame Error... " + errInfo + " Frame: " + frame_str)
                .isLog(TAG).show();
    }
}
