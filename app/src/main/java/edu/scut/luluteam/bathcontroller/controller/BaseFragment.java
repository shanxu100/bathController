package edu.scut.luluteam.bathcontroller.controller;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.scut.luluteam.bathcontroller.model.msg.AckMsg;
import edu.scut.luluteam.bathcontroller.model.msg.find.FindDeviceGroupNumMsg;
import edu.scut.luluteam.bathcontroller.model.msg.find.FindMqttClientMsg;
import edu.scut.luluteam.bathcontroller.model.msg.find.FindNetworkMsg;

/**
 * @author guan
 * @date 6/20/17
 */
public class BaseFragment extends Fragment implements ControllerContract.IView {

    protected ControllerContract.IPresenter mPresenter;

    protected String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter.subscribe();
        Log.d(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        mPresenter.unsubscribe();
    }

    @Override
    public void updateDeviceGroupNumView(FindDeviceGroupNumMsg msg) {

    }

    @Override
    public void updateNetworkView(FindNetworkMsg msg) {

    }

    @Override
    public void updateMqttClientView(FindMqttClientMsg msg) {

    }

    @Override
    public void showAckDialog(AckMsg ackMsg) {
        new AlertDialog.Builder(getContext())
                .setTitle("提示")
                .setMessage(ackMsg.isAckState() ? "设置成功" : "设置失败")
                .setPositiveButton("确定", null)
                .show();
    }

    @Override
    public void updateHighOrLowLevel(boolean isHigh) {

    }

    @Override
    public void setPresenter(ControllerContract.IPresenter presenter) {
        this.mPresenter = presenter;
    }


}
