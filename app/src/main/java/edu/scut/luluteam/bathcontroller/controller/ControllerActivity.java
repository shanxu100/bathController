package edu.scut.luluteam.bathcontroller.controller;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import edu.scut.luluteam.bathcontroller.R;
import edu.scut.luluteam.bathcontroller.adapter.DeviceAdapter;
import edu.scut.luluteam.bathcontroller.adapter.SimpleFragmentPagerAdapter;
import edu.scut.luluteam.bathcontroller.base.BaseActivity;
import edu.scut.luluteam.bathcontroller.manager.ConfigModeManager;
import edu.scut.luluteam.bathcontroller.manager.SecretManager;
import edu.scut.luluteam.bathcontroller.manager.SingleSerialPortManager;
import edu.scut.luluteam.bathcontroller.manager.ToastManager;
import edu.scut.luluteam.bathcontroller.ui.ChangeConfigModeDialog;
import edu.scut.luluteam.bathcontroller.ui.ChangePwdDialog;
import edu.scut.luluteam.serialportlibrary.Device;


/**
 * TabLayout 与 ViewPager 结合
 *
 * @author Guan
 */
public class ControllerActivity extends BaseActivity implements Observer {
    private TabLayout content_tablayout;
    private ViewPager content_viewpager;
    private Toolbar toolbar;
    private TextView tv_current_mode;

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();


    private SimpleFragmentPagerAdapter fragmentPagerAdapter;

    private ControllerPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_controller);

        initData();
        initUI();
        SingleSerialPortManager.getInstance().openSerialPort(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_set) {
            showConfigDialog();
//            enterConfigMode();
        } else if (item.getItemId() == R.id.menu_change_serial_port) {
            changeSerialPort();
        } else if (item.getItemId() == R.id.menu_action_change_manager_pwd) {
            attempChangePwd();
        } else if (item.getItemId() == R.id.menu_action_exit_app) {
            exitApp();
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SingleSerialPortManager.getInstance().closeSerialPort();
    }

    private void initData() {
        fragmentList.add(ControllerSettingFragment.newInstance());
        fragmentList.add(NetworkSettingFragment.newInstance());
        fragmentList.add(IOTestFragment.newInstance());
        titleList.add("控制器设置");
        titleList.add("网络设置");
        titleList.add("IO测试");
    }

    private void initUI() {
        content_tablayout = (TabLayout) findViewById(R.id.buttom_tablayout);
        content_viewpager = (ViewPager) findViewById(R.id.content_viewpager);
        tv_current_mode = (TextView) findViewById(R.id.tv_current_mode);
        toolbar = (Toolbar) findViewById(R.id.content_toolbar);
        toolbar.setTitle("克劳迪智能厕所");
        toolbar.setSubtitle("蹲位控制器配置工具");
        setSupportActionBar(toolbar);
        //初始化
        fragmentPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        //为ViewPager设置Adapter
        content_viewpager.setAdapter(fragmentPagerAdapter);
        content_viewpager.setOffscreenPageLimit(3);
        //tabLayOut和ViewPager关联
        content_tablayout.setupWithViewPager(content_viewpager, true);
        /**
         * MODE_FIXED:固定tabs，并同时显示所有的tabs。
         * MODE_SCROLLABLE：可滚动tabs，显示一部分tabs，在这个模式下能包含长标签和大量的tabs，最好用于用户不需要直接比较tabs。
         */
        content_tablayout.setTabMode(TabLayout.MODE_FIXED);

        if (ConfigModeManager.getInstance().isConfigMode()) {
            tv_current_mode.setText("当前状态：配置模式");
        } else {
            tv_current_mode.setText("当前状态：普通模式，不可操作");
        }
        ConfigModeManager.getInstance().addObserver(this);

        //给Presenter设置Fragment
        mPresenter = new ControllerPresenter((ControllerContract.IView) fragmentList.get(0)
                , (ControllerContract.IView) fragmentList.get(1), (ControllerContract.IView) fragmentList.get(2));

    }

    /**
     * 配置模式选项
     */
    private void showConfigDialog() {

        ChangeConfigModeDialog.getInstance(this)
                .setCallback(new ChangeConfigModeDialog.ChangeConfigModeCallback() {
                    @Override
                    public void onEnterConfigMode() {
                        mPresenter.enterConfigMode();
                    }

                    @Override
                    public void onExitConfigModeNotSave() {
                        mPresenter.exitConfigMode(false);
                    }

                    @Override
                    public void onExitConfigModeSave() {
                        mPresenter.exitConfigMode(true);
                    }

                    @Override
                    public void onResetConfig() {
                        new AlertDialog.Builder(context)
                                .setMessage("确认恢复出厂设置吗？")
                                .setTitle("提示")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mPresenter.resetConfig();
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                })
                .show();
    }

    /**
     * 点击进入配置模式
     */
    private void enterConfigMode() {
        mPresenter.enterConfigMode();
    }

    /**
     * TODO
     * 更改打开的串口
     */
    private void changeSerialPort() {

        Log.i(TAG, "changeSerialPort---------");

        final Dialog dialog = new Dialog(context);
        //获取串口列表view，并嵌入在dialog里
        View contentView = View.inflate(context, R.layout.content_dialog, null);
        dialog.setContentView(contentView);
        //获取listview
        ListView listView = (ListView) contentView.findViewById(R.id.listview);
        final TextView tv_current_serial_port_name = (TextView) contentView.findViewById(R.id.tv_current_serial_port_name);
        tv_current_serial_port_name.setText("当前串口：\t" + mPresenter.getCurrentSerialPort());

        final List<Device> devices = mPresenter.findSerialPort();
        //获取adapter
        DeviceAdapter deviceAdapter = new DeviceAdapter(context, devices);
        //Toast.makeText(getContext(), "devices的个数为" + devices.size(), Toast.LENGTH_SHORT).show();
        //对listview设置adapter
        listView.setAdapter(deviceAdapter);
        //设置监听事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Device device = devices.get(position);
                mPresenter.openSerialPort(device);
//                Toast.makeText(getContext(), "串口的路径为" + device.getFile().getPath(), Toast.LENGTH_SHORT).show();
                tv_current_serial_port_name.setText("当前串口：\t" + mPresenter.getCurrentSerialPort());
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * 修改管理者密码
     */
    private void attempChangePwd() {
        ChangePwdDialog changePwdDialog = new ChangePwdDialog(context);
//        changePwdDialog.setRealOldPwd(userInfo.getPassword());
        changePwdDialog.setChangePwdCallback(new ChangePwdDialog.ChangePwdCallback() {
            @Override
            public void onChange(String oldPwd, String newPwd, Dialog dialog) {
                if (SecretManager.getInstance().changePwd(oldPwd, newPwd)) {
                    ToastManager.newInstance("管理者密码修改成功").show();
                    dialog.dismiss();
                } else {
                    ToastManager.newInstance("密码错误,或者为空").setGravity(Gravity.CENTER).show();
                }
            }
        });
        changePwdDialog.show();
    }


    private void exitApp() {
//        if (ConfigModeManager.getInstance().isConfigMode()) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this)
//                    .setTitle("提示")
//                    .setMessage("是否保存设置?")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            mPresenter.exitConfigMode(true);
//                            getCurrentActivity().finish();
//
//                        }
//                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            mPresenter.exitConfigMode(false);
//                            getCurrentActivity().finish();
//                        }
//                    });
//            builder.create().show();
//        } else {
//            this.finish();
//        }
        ConfigModeManager.getInstance().setConfigMode(false);
        this.finish();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (ConfigModeManager.getInstance().isConfigMode()) {
            tv_current_mode.setText("当前状态：配置模式");
        } else {
            tv_current_mode.setText("当前状态：普通模式，不可操作");
        }
    }
}
