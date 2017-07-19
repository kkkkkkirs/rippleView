package com.haowuyun.code.loveinlogripple;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.haowuyun.code.widget.RippleScanDialog;

public class MainActivity extends AppCompatActivity implements RippleScanDialog.ScanDialogCallBack,View.OnClickListener{
    private RippleScanDialog mRippleScanDialog;
    private Button mRippleScan;
    private Handler mUIHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private class UIHandler extends Handler {
        public static final int SCAN_START = 1;
        public static final int SCAN_UPDATE = 2;
        public static final int SCAN_END = 3;
        public static final int PRE_SCAN = 5;
        public static final int PREVIEW_END = 6;

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SCAN_START:
                    break;
                case SCAN_UPDATE:
                    break;
                case SCAN_END:
                    break;
                case PRE_SCAN:
                    showDialog();
                    break;
                case PREVIEW_END:
                    break;
                default:
                    break;
            }
        }
    }

    public void initData(){
        mUIHandler = new UIHandler();
    }
    public void initView(){
        mRippleScan = (Button) findViewById(R.id.ripple_scan_btn);
        mRippleScan.setOnClickListener(this);
    }

    public void showDialog() {

        if (mRippleScanDialog == null) {
            mRippleScanDialog = new RippleScanDialog(this, false);
            mRippleScanDialog.setScanDialogCallBack(this);
        }
        mRippleScanDialog.show();
    }

    public void dismissDialog() {
        if (mRippleScanDialog != null && mRippleScanDialog.isShowing()) {
            mRippleScanDialog.dismiss();
        }
    }

    @Override
    public void stopScan() {
        mUIHandler.removeMessages(UIHandler.SCAN_END);
        mUIHandler.sendEmptyMessage(UIHandler.SCAN_END);
        dismissDialog();

    }

    @Override
    public void startScan() {

    }

    @Override
    protected void onDestroy() {
        if (mRippleScanDialog != null) {
            mRippleScanDialog.dismiss();
            if (mRippleScanDialog.getRippleView() != null) {
                mRippleScanDialog.getRippleView().pippleViewDestory();
            }
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ripple_scan_btn:
                showDialog();
                break;
        }
    }
}
