package edu.scut.luluteam.bathcontroller.controller;

import java.util.List;

import edu.scut.luluteam.bathcontroller.base.IBasePresenter;
import edu.scut.luluteam.bathcontroller.base.IBaseView;
import edu.scut.luluteam.bathcontroller.model.msg.AckMsg;
import edu.scut.luluteam.bathcontroller.model.msg.find.FindDeviceGroupNumMsg;
import edu.scut.luluteam.bathcontroller.model.msg.find.FindMqttClientMsg;
import edu.scut.luluteam.bathcontroller.model.msg.find.FindNetworkMsg;
import edu.scut.luluteam.bathcontroller.model.msg.setting.SettingDeviceGroupNumMsg;
import edu.scut.luluteam.bathcontroller.model.msg.setting.SettingMqttAdmPwdMsg;
import edu.scut.luluteam.bathcontroller.model.msg.setting.SettingMqttServerMsg;
import edu.scut.luluteam.bathcontroller.model.msg.setting.SettingWiFiMsg;
import edu.scut.luluteam.serialportlibrary.Device;

/**
 * @author Guan
 * @date Created on 2019/1/14
 */
public class ControllerContract {

    public interface IView extends IBaseView<IPresenter> {

        /**
         * 更新蹲位控制器的DeviceGroup和DeviceNum的view
         *
         * @param msg
         */
        void updateDeviceGroupNumView(FindDeviceGroupNumMsg msg);

        /**
         * 更新网络参数的view
         *
         * @param msg
         */
        void updateNetworkView(FindNetworkMsg msg);


        /**
         * 更新mqtt的client信息的View
         *
         * @param msg
         */
        void updateMqttClientView(FindMqttClientMsg msg);

        void showAckDialog(AckMsg ackMsg);

        /**
         * 收到高低电平信号后更新页面指示
         */
        void updateHighOrLowLevel(boolean isHigh);
    }


    public interface IPresenter extends IBasePresenter {

        /**
         * 一些初始化操作
         */
        void loadData();


        /**
         * 当前串口
         *
         * @return
         */
        String getCurrentSerialPort();


        /**
         * 查找串口
         *
         * @return
         */
        List<Device> findSerialPort();


        /**
         * 打开串口
         */
        void openSerialPort(Device device);


        /**
         * 关闭串口
         */
        void closeSerialPort();


        /**
         * 设置蹲位控制器的DeviceGroup和DeviceNum
         *
         * @param msg
         */
        void setDeviceGroupNum(SettingDeviceGroupNumMsg msg);


        /**
         * 设置WiFi的参数
         *
         * @param msg
         */
        void setWiFiMsg(SettingWiFiMsg msg);

        /**
         * 设置mqtt的server 和 port
         *
         * @param msg
         */
        void setMqttServer(SettingMqttServerMsg msg);


        /**
         * 设置mqtt的username和pwd
         *
         * @param msg
         */
        void setMqttAdmPwd(SettingMqttAdmPwdMsg msg);

        void findDeviceGroupNum();

        void findMqttClientId();

        void findNetwork();


        /**
         * 输出高、低电平。
         *
         * @param isHigh true：输出高电平；false：输出低电平
         */
        void outputHighOrLowLevel(boolean isHigh);


        /**
         * 进入测试模式
         */
        void enterTestMode();


        /**
         * 进入配置模式
         */
        void enterConfigMode();

        /**
         * 退出配置模式
         *
         * @param save true：保存配置；false：不保存配置
         */
        @Deprecated
        void exitConfigMode(boolean save);


        /**
         * 恢复出厂设置
         */
        void resetConfig();

    }


}
