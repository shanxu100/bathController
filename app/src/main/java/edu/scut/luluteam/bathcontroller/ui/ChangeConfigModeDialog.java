package edu.scut.luluteam.bathcontroller.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.scut.luluteam.bathcontroller.R;
import edu.scut.luluteam.bathcontroller.manager.App;
import edu.scut.luluteam.bathcontroller.utils.DisplayUtil;

/**
 * @author Guan
 * @date Created on 2019/6/4
 */
public class ChangeConfigModeDialog extends Dialog {

    private Context context;

    private Button btn_enter_config_mode;
    private Button btn_exit_config_mode_not_save;
    private Button btn_exit_config_mode_save;
    private Button btn_reset_config;

    private ChangeConfigModeCallback callback = null;
    private static ChangeConfigModeDialog instance = null;

    public static ChangeConfigModeDialog getInstance(Context context) {
        if (instance == null) {
            synchronized (ChangeConfigModeDialog.class) {
                if (instance == null) {
                    instance = new ChangeConfigModeDialog(context);
                }
            }
        }
        return instance;
    }

    public static void dismissDialog() {
        if (instance != null) {
            instance.dismiss();
        }
    }

    private ChangeConfigModeDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
        setCustomDialog();
        this.setCanceledOnTouchOutside(false);
        getWindow().setLayout((int) (DisplayUtil.getInstance().getScreenWidth() * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                instance = null;
            }
        });
    }

    private void setCustomDialog() {
        //View view = LayoutInflater.from(context).inflate(R.layout.set_pattern_dialog, null);
        View contentView = View.inflate(App.getAppContext(), R.layout.set_pattern_dialog, null);
        btn_enter_config_mode = (Button) contentView.findViewById(R.id.btn_enter_config_mode);
        btn_exit_config_mode_not_save = (Button) contentView.findViewById(R.id.btn_exit_config_mode_not_save);
        btn_exit_config_mode_save = (Button) contentView.findViewById(R.id.btn_exit_config_mode_save);
        btn_reset_config = (Button) contentView.findViewById(R.id.btn_reset_config);

        btn_enter_config_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onEnterConfigMode();
                }

            }
        });
        btn_exit_config_mode_not_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onExitConfigModeNotSave();
                }

            }
        });
        btn_exit_config_mode_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onExitConfigModeSave();
                }

            }
        });

        btn_reset_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onResetConfig();
                }
            }
        });

        this.setContentView(contentView);
    }

    public ChangeConfigModeDialog setCallback(ChangeConfigModeCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void show() {
        if (!this.isShowing()) {
            super.show();
        }
    }

    public interface ChangeConfigModeCallback {
        void onEnterConfigMode();

        @Deprecated
        void onExitConfigModeNotSave();

        @Deprecated
        void onExitConfigModeSave();

        void onResetConfig();


    }

}
