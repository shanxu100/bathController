package edu.scut.luluteam.bathcontroller.controller;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

import edu.scut.luluteam.bathcontroller.R;
import edu.scut.luluteam.bathcontroller.constant.MsgConstant;
import edu.scut.luluteam.bathcontroller.filter.InputFilterMinMax;
import edu.scut.luluteam.bathcontroller.manager.SecretManager;
import edu.scut.luluteam.bathcontroller.manager.ToastManager;
import edu.scut.luluteam.bathcontroller.model.msg.find.FindMqttClientMsg;
import edu.scut.luluteam.bathcontroller.model.msg.find.FindNetworkMsg;
import edu.scut.luluteam.bathcontroller.model.msg.setting.SettingMqttAdmPwdMsg;
import edu.scut.luluteam.bathcontroller.model.msg.setting.SettingMqttServerMsg;
import edu.scut.luluteam.bathcontroller.model.msg.setting.SettingWiFiMsg;


/**
 * @author Guan
 */
public class NetworkSettingFragment extends BaseFragment implements View.OnClickListener {


    private View rootView;

    /**
     * 管理者密码的相关控件
     */
    private LinearLayout ll_manager_pwd;
    private EditText et_manager_pwd;
    private Button btn_manager_pwd;
    private ScrollView scrollView;

    private EditText etSsid;
    private EditText etSsidPassword;
    private Button btnSetSsid;
    private EditText etMqttServer;
    private EditText etMqttPort;
    private Button btnSetMqttServer;
    private EditText etMqttUser;
    private EditText etMqttPassword;
    private Button btnSetMqttUser;
    private Button btnFindClient;
    private Button btnFindAll;


    public NetworkSettingFragment() {
        // Required empty public constructor
    }

    public static NetworkSettingFragment newInstance() {
        NetworkSettingFragment fragment = new NetworkSettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_network_setting_v2, container, false);
        initView(rootView);
        showPwdView(true);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView(View rootView) {

        ll_manager_pwd = (LinearLayout) rootView.findViewById(R.id.ll_manager_pwd);
        et_manager_pwd = (EditText) rootView.findViewById(R.id.et_manager_pwd);
        btn_manager_pwd = (Button) rootView.findViewById(R.id.btn_manager_pwd);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);

        etSsid = (EditText) rootView.findViewById(R.id.et_ssid);

        etSsidPassword = (EditText) rootView.findViewById(R.id.et_ssid_password);
        btnSetSsid = (Button) rootView.findViewById(R.id.btn_set_ssid);

        etMqttServer = (EditText) rootView.findViewById(R.id.et_mqtt_server);

        etMqttPort = (EditText) rootView.findViewById(R.id.et_mqtt_port);
        btnSetMqttServer = (Button) rootView.findViewById(R.id.btn_set_mqtt_server);

        etMqttUser = (EditText) rootView.findViewById(R.id.et_mqtt_user);

        etMqttPassword = (EditText) rootView.findViewById(R.id.et_mqtt_password);
        btnSetMqttUser = (Button) rootView.findViewById(R.id.btn_set_mqtt_user);
        btnFindClient = (Button) rootView.findViewById(R.id.btn_find_client);
        btnFindAll = (Button) rootView.findViewById(R.id.btn_find_all);

        etMqttPort.setFilters(new InputFilter[]{new InputFilterMinMax(0, 65535)});

        btnSetSsid.setOnClickListener(this);
        btnSetMqttServer.setOnClickListener(this);
        btnSetMqttUser.setOnClickListener(this);
        btnFindClient.setOnClickListener(this);
        btnFindAll.setOnClickListener(this);
        btn_manager_pwd.setOnClickListener(this);

    }

    @Override
    public void updateNetworkView(FindNetworkMsg msg) {
        ToastManager.newInstance("查找成功！").setGravity(Gravity.TOP).isLog(TAG).show();
        etSsid.setText(msg.getSsid());
        etSsidPassword.setText(msg.getPwd());
        etMqttServer.setText(msg.getMqttServer());
        etMqttPort.setText(msg.getMqttPort());
        etMqttUser.setText(msg.getMqttAdm());
        etMqttPassword.setText(msg.getMqttPwd());
    }

    @Override
    public void updateMqttClientView(FindMqttClientMsg msg) {
        String mqttClientId = msg.getMqttClientId();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("MQTT的clientId为")
                .setMessage(mqttClientId)
                .setPositiveButton("确定", null);
        builder.create().show();


    }

    @Override
    public void onClick(View v) {
        if (v == btnSetSsid) {

            onClickSsidMsg(etSsid.getText().toString().trim(), etSsidPassword.getText().toString().trim());

        } else if (v == btnSetMqttServer) {

            onClickMqttServerMsg(etMqttServer.getText().toString().trim(), etMqttPort.getText().toString().trim());

        } else if (v == btnSetMqttUser) {

            onClickMqttAdmPwdMsg(etMqttUser.getText().toString().trim(), etMqttPassword.getText().toString().trim());

        } else if (v == btnFindClient) {
            mPresenter.findMqttClientId();
        } else if (v == btnFindAll) {
            mPresenter.findNetwork();
        } else if (v.getId() == btn_manager_pwd.getId()) {
            if (SecretManager.getInstance().checkPwd(et_manager_pwd.getText().toString().trim())) {
                showPwdView(false);
                ToastManager.newInstance("密码正确").setGravity(Gravity.CENTER).show();
            } else {
                ToastManager.newInstance("密码错误").setGravity(Gravity.CENTER).show();
            }
        }
    }


    /**
     * 点击设置WiFi信息的ssid和pwd
     *
     * @param ssid
     * @param pwd
     */
    private void onClickSsidMsg(String ssid, String pwd) {
        if (!StringUtils.isEmpty(ssid) && !StringUtils.isEmpty(pwd)) {
            SettingWiFiMsg settingWiFiMsg = new SettingWiFiMsg();
            settingWiFiMsg.setSsid(ssid);
            settingWiFiMsg.setPwd(pwd);
            mPresenter.setWiFiMsg(settingWiFiMsg);
        } else {
            ToastManager.newInstance("输入内容不能为空！").setGravity(Gravity.CENTER).isLog(TAG).show();
        }
    }


    /**
     * 设置Mqtt的Server和Port
     *
     * @param mqttServer
     * @param port
     */
    private void onClickMqttServerMsg(String mqttServer, String port) {
        if (Pattern.matches(MsgConstant.PATTERN_IP, mqttServer) && !TextUtils.isEmpty(port)) {

            if (Integer.parseInt(port) < MsgConstant.CHECK_MIN_MQTT_PORT
                    && Integer.parseInt(port) > MsgConstant.CHECK_MAX_MQTT_PORT) {
                ToastManager.newInstance("端口输入错误：合理的端口号范围是1024~65535").setGravity(Gravity.CENTER).isLog(TAG).show();
                return;
            }

            SettingMqttServerMsg settingMqttServerMsg = new SettingMqttServerMsg();
            settingMqttServerMsg.setMqttServer(mqttServer);
            settingMqttServerMsg.setMqttPort(port);
            mPresenter.setMqttServer(settingMqttServerMsg);
        } else {
            ToastManager.newInstance("输入格式错误！").setGravity(Gravity.CENTER).isLog(TAG).show();
        }

    }


    /**
     * 设置Mqtt的username和pwd
     *
     * @param adm
     * @param pwd
     */
    private void onClickMqttAdmPwdMsg(String adm, String pwd) {
        if (!StringUtils.isEmpty(adm) && !StringUtils.isEmpty(pwd)) {
            SettingMqttAdmPwdMsg settingMqttAdmPwdMsg = new SettingMqttAdmPwdMsg();
            settingMqttAdmPwdMsg.setMqttAdm(adm);
            settingMqttAdmPwdMsg.setMqttPwd(pwd);
            mPresenter.setMqttAdmPwd(settingMqttAdmPwdMsg);
        } else {
            ToastManager.newInstance("输入内容不能为空！").setGravity(Gravity.CENTER).isLog(TAG).show();
        }
    }

    /**
     * 密码栏和内容栏的显示和隐藏
     *
     * @param showPwd true：显示密码栏，隐藏内容栏；false：相反
     */
    private void showPwdView(boolean showPwd) {

        if (showPwd) {
            ll_manager_pwd.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            ll_manager_pwd.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }

    }

}
