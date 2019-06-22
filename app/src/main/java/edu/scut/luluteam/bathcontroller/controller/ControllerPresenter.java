package edu.scut.luluteam.bathcontroller.controller;

import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import edu.scut.luluteam.bathcontroller.constant.MsgConstant;
import edu.scut.luluteam.bathcontroller.manager.ConfigModeManager;
import edu.scut.luluteam.bathcontroller.manager.SingleSerialPortManager;
import edu.scut.luluteam.bathcontroller.manager.ToastManager;
import edu.scut.luluteam.bathcontroller.model.BaseData;
import edu.scut.luluteam.bathcontroller.model.msg.AckMsg;
import edu.scut.luluteam.bathcontroller.model.msg.find.FindDeviceGroupNumMsg;
import edu.scut.luluteam.bathcontroller.model.msg.find.FindMqttClientMsg;
import edu.scut.luluteam.bathcontroller.model.msg.find.FindNetworkMsg;
import edu.scut.luluteam.bathcontroller.model.msg.setting.SettingDeviceGroupNumMsg;
import edu.scut.luluteam.bathcontroller.model.msg.setting.SettingMqttAdmPwdMsg;
import edu.scut.luluteam.bathcontroller.model.msg.setting.SettingMqttServerMsg;
import edu.scut.luluteam.bathcontroller.model.msg.setting.SettingWiFiMsg;
import edu.scut.luluteam.bathcontroller.ui.ChangeConfigModeDialog;
import edu.scut.luluteam.serialportlibrary.Device;

/**
 * @author Guan
 * @date Created on 2019/4/24
 */
public class ControllerPresenter implements ControllerContract.IPresenter {

    private ControllerContract.IView mView1;
    private ControllerContract.IView mView2;
    private ControllerContract.IView mView3;

    private static final String TAG = "ControllerPresenter";

    /**
     * 一个Presenter设置两个view
     *
     * @param mView1
     * @param mView2
     */
    public ControllerPresenter(ControllerContract.IView mView1, ControllerContract.IView mView2,
                               ControllerContract.IView mView3) {
        this.mView1 = mView1;
        this.mView2 = mView2;
        this.mView3 = mView3;
        this.mView1.setPresenter(this);
        this.mView2.setPresenter(this);
        this.mView3.setPresenter(this);
    }

    @Override
    public void loadData() {

    }


    @Override
    public String getCurrentSerialPort() {
        return SingleSerialPortManager.getInstance().getCurrentSerialPort();
    }

    @Override
    public List<Device> findSerialPort() {
        return SingleSerialPortManager.getInstance().getDevices();
    }

    @Override
    public void openSerialPort(Device device) {
        SingleSerialPortManager.getInstance().openSerialPort(device.getFile().getPath());
    }


    @Override
    public void closeSerialPort() {
        SingleSerialPortManager.getInstance().closeSerialPort();
    }

    @Override
    public void setDeviceGroupNum(SettingDeviceGroupNumMsg msg) {
        SingleSerialPortManager.getInstance().sendBytes(msg.toByte());
    }

    @Override
    public void setWiFiMsg(SettingWiFiMsg msg) {
        SingleSerialPortManager.getInstance().sendBytes(msg.toByte());
    }

    @Override
    public void setMqttServer(SettingMqttServerMsg msg) {
        SingleSerialPortManager.getInstance().sendBytes(msg.toByte());
    }

    @Override
    public void setMqttAdmPwd(SettingMqttAdmPwdMsg msg) {
        SingleSerialPortManager.getInstance().sendBytes(msg.toByte());
    }

    @Override
    public void findDeviceGroupNum() {
        SingleSerialPortManager.getInstance().sendBytes(MsgConstant.CMD_FIND_DEVICE_GROUP_NUM);
    }

    @Override
    public void findMqttClientId() {
        SingleSerialPortManager.getInstance().sendBytes(MsgConstant.CMD_FIND_MQTT_CLIENT);
    }

    @Override
    public void findNetwork() {
        SingleSerialPortManager.getInstance().sendBytes(MsgConstant.CMD_FIND_NETWORK);
    }

    @Override
    public void outputHighOrLowLevel(boolean isHigh) {
        //TODO
        Log.e(TAG, "输出高低电平.....(未定义数据帧)  isHigh：" + isHigh);

    }

    @Override
    public void enterTestMode() {
        //TODO
        Log.e(TAG, "进入测试模式.....(未定义数据帧)");
    }

    @Override
    public void enterConfigMode() {
        SingleSerialPortManager.getInstance().sendBytes(MsgConstant.CMD_ENTER_CONFIG_MODE);
    }

    @Override
    public void exitConfigMode(boolean save) {
        if (save) {
            SingleSerialPortManager.getInstance().sendBytes(MsgConstant.CMD_EXIT_CONFIG_MODE_SAVE);
        } else {
            SingleSerialPortManager.getInstance().sendBytes(MsgConstant.CMD_EXIT_CONFIG_MODE_NOT_SAVE);
        }
    }

    @Override
    public void resetConfig() {
        SingleSerialPortManager.getInstance().sendBytes(MsgConstant.CMD_RESET_CONFIG);
    }

    @Override
    public void subscribe() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void unsubscribe() {
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusMessage(BaseData baseData) {

        String json = baseData.getJson();
        if (baseData.getType() == BaseData.EVENT_BUS_MSG_FROM_SERIAL_PORT) {
            //TODO 说明是从串口过来的数据，并且已经解析为json

            Log.i(TAG, "数据Json=" + json);

            //这里说明：byte类型的数据通过Gson转换成json后，会变成数值型
            //所以这里需要将其转换为16进制的byte再处理
            byte msgType = JSONObject.parseObject(json).getInteger("msgType").byteValue();

            if (msgType == MsgConstant.MSG_TYPE_FIND_DEVICE_GROUP_NUM) {
                FindDeviceGroupNumMsg msg = new Gson().fromJson(json, FindDeviceGroupNumMsg.class);
                mView1.updateDeviceGroupNumView(msg);
                mView2.updateDeviceGroupNumView(msg);

            } else if (msgType == MsgConstant.MSG_TYPE_FIND_NETWORK) {
                FindNetworkMsg msg = new Gson().fromJson(json, FindNetworkMsg.class);
                mView1.updateNetworkView(msg);
                mView2.updateNetworkView(msg);

            } else if (msgType == MsgConstant.MSG_TYPE_FIND_MQTT_CLIENT) {
                FindMqttClientMsg msg = new Gson().fromJson(json, FindMqttClientMsg.class);
                mView1.updateMqttClientView(msg);
                mView2.updateMqttClientView(msg);

            } else if (msgType == MsgConstant.MSG_TYPE_ACK) {
                AckMsg msg = new Gson().fromJson(json, AckMsg.class);
                processAckMsg(msg);


            } else {
                Log.e(TAG, "未知msgType：" + json);
            }

        } else {
            Log.e(TAG, "未知的EventBus数据。 Type=" + baseData.getType()
                    + "   json=" + baseData.getJson());
        }


    }

    /**
     * 处理确认帧的方法
     *
     * @param msg
     */
    private void processAckMsg(AckMsg msg) {

        if (msg.getAckType() == MsgConstant.AckType.ENTER_CONFIG_MODE) {
            //进入测试模式
            ToastManager.newInstance("成功进入配置模式").isLog(TAG).setDuration(Toast.LENGTH_LONG).setGravity(Gravity.CENTER).show();
            ConfigModeManager.getInstance().setConfigMode(true);
            ChangeConfigModeDialog.dismissDialog();
        } else if (msg.getAckType() == MsgConstant.AckType.RESET_CONFIG) {
            ToastManager.newInstance("恢复出厂设置成功").isLog(TAG).setDuration(Toast.LENGTH_LONG).setGravity(Gravity.CENTER).show();
//            ConfigModeManager.getInstance().setConfigMode(false);
            ChangeConfigModeDialog.dismissDialog();
//        } else if (msg.getAckType() == MsgConstant.AckType.EXITE_CONFIG_MODE_NOT_SAVE) {
//            //退出测试模式，但不保存
//            ToastManager.newInstance("退出配置模式，但不保存设置").isLog(TAG).setDuration(Toast.LENGTH_LONG).setGravity(Gravity.CENTER).show();
//            ConfigModeManager.getInstance().setConfigMode(false);
//            ChangeConfigModeDialog.dismissDialog();
//
//        } else if (msg.getAckType() == MsgConstant.AckType.EXITE_CONFIG_MODE_SAVE) {
//            //退出测试模式，并且保存
//            ToastManager.newInstance("退出配置模式，同时保存设置").isLog(TAG).setDuration(Toast.LENGTH_LONG).setGravity(Gravity.CENTER).show();
//            ConfigModeManager.getInstance().setConfigMode(false);
//            ChangeConfigModeDialog.dismissDialog();

        } else {
            //这里只显示设置成功或者失败
            mView1.showAckDialog(msg);
        }

    }

}
