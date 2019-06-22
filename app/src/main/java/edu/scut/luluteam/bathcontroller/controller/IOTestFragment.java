package edu.scut.luluteam.bathcontroller.controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import edu.scut.luluteam.bathcontroller.R;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Guan
 */
public class IOTestFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private Button btnEnterTest;
    private ImageView ivLevelDisplay;
    private Button btnPutHighLevel;
    private Button btnPutLowLevel;

    public IOTestFragment() {
        // Required empty public constructor
    }

    public static IOTestFragment newInstance() {
        IOTestFragment fragment = new IOTestFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         rootView = inflater.inflate(R.layout.fragment_iotest, container, false);
         initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        btnEnterTest = (Button)rootView.findViewById( R.id.btn_enter_test );
        ivLevelDisplay = (ImageView)rootView.findViewById( R.id.iv_level_display );
        btnPutHighLevel = (Button)rootView.findViewById( R.id.btn_put_high_level );
        btnPutLowLevel = (Button)rootView.findViewById( R.id.btn_put_low_level );

        btnEnterTest.setOnClickListener( this );
        btnPutHighLevel.setOnClickListener( this );
        btnPutLowLevel.setOnClickListener( this );
    }

    @Override
    public void updateHighOrLowLevel(boolean isHigh) {
        super.updateHighOrLowLevel(isHigh);
        //TODO
        Log.e(TAG, "更新高低电平view....(未完成)");
    }

    @Override
    public void onClick(View v) {
        if ( v == btnEnterTest ) {
           mPresenter.enterTestMode();
        } else if ( v == btnPutHighLevel ) {
            mPresenter.outputHighOrLowLevel(true);
        } else if ( v == btnPutLowLevel ) {
            mPresenter.outputHighOrLowLevel(false);
        }
    }
}
