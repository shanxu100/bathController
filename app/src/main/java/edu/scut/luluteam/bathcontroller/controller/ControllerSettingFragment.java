package edu.scut.luluteam.bathcontroller.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.apache.commons.lang.StringUtils;

import edu.scut.luluteam.bathcontroller.R;
import edu.scut.luluteam.bathcontroller.constant.MsgConstant;
import edu.scut.luluteam.bathcontroller.filter.InputFilterMinMax;
import edu.scut.luluteam.bathcontroller.manager.ToastManager;
import edu.scut.luluteam.bathcontroller.model.msg.find.FindDeviceGroupNumMsg;
import edu.scut.luluteam.bathcontroller.model.msg.setting.SettingDeviceGroupNumMsg;


/**
 * @author Guan
 */
public class ControllerSettingFragment extends BaseFragment {

    private View rootView;

    private EditText et_toilet_id;
    private EditText et_device_group;
    private EditText et_device_num;
    private Button btn_find;
    private Button btn_set;


    public ControllerSettingFragment() {
        // Required empty public constructor
    }

    public static ControllerSettingFragment newInstance() {
        ControllerSettingFragment fragment = new ControllerSettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_controller_setting_v2, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //        mPresenter.unsubscribe();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView(View rootView) {
        et_toilet_id = (EditText) rootView.findViewById(R.id.et_toilet_id);
        et_device_group = (EditText) rootView.findViewById(R.id.et_device_group);
        et_device_num = (EditText) rootView.findViewById(R.id.et_device_num);
        btn_find = (Button) rootView.findViewById(R.id.btn_find);
        btn_set = (Button) rootView.findViewById(R.id.btn_set);

        et_device_group.setFilters(new InputFilter[]{new InputFilterMinMax(0, 255)});
        et_device_num.setFilters(new InputFilter[]{new InputFilterMinMax(0, 255)});

        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送查找命令
                mPresenter.findDeviceGroupNum();
            }
        });
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取到用户设置的值后生成设置命令，并发送
                if (!StringUtils.isEmpty(et_toilet_id.getText().toString()) && !StringUtils.isEmpty(et_device_group.getText().toString()) && !StringUtils.isEmpty(et_device_num.getText().toString())) {
                    String toiletId = et_toilet_id.getText().toString();
                    String deviceGroup = et_device_group.getText().toString();
                    String deviceNum = et_device_num.getText().toString();
                    if (toiletId.length() != MsgConstant.CHECK_FIX_LENGTH_TOILET_ID) {
                        ToastManager.newInstance("输入错误：厕所ID长度固定8位").setGravity(Gravity.CENTER).isLog(TAG).show();
                    }
                    SettingDeviceGroupNumMsg settingDeviceGroupNumMsg = new SettingDeviceGroupNumMsg();
                    settingDeviceGroupNumMsg.setToiletId(toiletId);
                    settingDeviceGroupNumMsg.setDeviceGroup(deviceGroup);
                    settingDeviceGroupNumMsg.setDeviceNum(deviceNum);
                    mPresenter.setDeviceGroupNum(settingDeviceGroupNumMsg);
                } else {
                    ToastManager.newInstance("输入内容不能为空！").show();
                }


            }
        });
    }


    @Override
    public void updateDeviceGroupNumView(FindDeviceGroupNumMsg msg) {
        ToastManager.newInstance("查找成功！").isLog(TAG).show();
        String toiletId = msg.getToiletId();
        String deviceGroup = msg.getDeviceGroup();
        String deviceNum = msg.getDeviceNum();
        et_toilet_id.setText(toiletId);
        et_device_group.setText(deviceGroup);
        et_device_num.setText(deviceNum);
    }


}
