package com.haowuyun.code.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.haowuyun.code.loveinlogripple.R;
import com.haowuyun.code.utils.ViewClickSelector;
import com.haowuyun.code.utils.task.XCTaskExecutor;
import com.haowuyun.code.widget.callbacks.DialogUICallback;



public class RippleScanDialog extends Dialog implements DialogUICallback {
    private Context mContext;
    private View mTextViewCancel;
    private ImageView mimscaningroate;
    private RippleView mRippleBg;
    private TextView mTitle;
    private View mCancelText;
    private boolean mIsShowCancle;
    private ScanDialogCallBack mScanDialogCallBack;

    private final static int HANDLE_MSG_BEGIN =0;
    private final static int HANDLE_MSG_END = 1;
    private final static int HANDLE_MSG_STOPANMI = 2;
    private Handler UIHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLE_MSG_END:
                    mTitle.setText("");
                    break;
                case HANDLE_MSG_BEGIN:
                    mTitle.setText("");
                    break;
                case HANDLE_MSG_STOPANMI:
                    mRippleBg.stopRippleAnimation();
                    break;
            }

        }
    };

    public RippleScanDialog(Context context, boolean isShowCancle) {
        super(context);
        mIsShowCancle = isShowCancle;
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new
                ColorDrawable(getContext().getResources().getColor((R.color.half_transparent))));

        setContentView(R.layout.ripple_scan_dialog);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;//窗口的宽和高
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        mTitle = (TextView) findViewById(R.id.scan_dialog_title);
        mTextViewCancel =  findViewById(R.id.radio_scan_cancel);
        mTextViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XCTaskExecutor.executeTask(new Runnable() {
                    @Override
                    public void run() {
                        stopScan();
                    }
                });

            }
        });
        mimscaningroate = (ImageView) findViewById(R.id.imscaningroate);
        mRippleBg =(RippleView) findViewById(R.id.radio_wave);
        mCancelText = findViewById(R.id.cancel_dialog);
        mCancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            XCTaskExecutor.executeTask(new Runnable() {
                @Override
                public void run() {
                    stopScan();
                }
            });
            }
        });
        if (mIsShowCancle){
            mCancelText.setVisibility(View.GONE);
        }

        ViewClickSelector.setAlphaSelector(mCancelText);
    }

    public void stopScan(){
        if (mScanDialogCallBack !=null) {
            mScanDialogCallBack.stopScan();
        }
        UIHandler.sendEmptyMessage(HANDLE_MSG_END);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.scaning_rotate);
        mimscaningroate.startAnimation(anim);
        mRippleBg.startRippleAnimation();
        UIHandler.sendEmptyMessage(HANDLE_MSG_BEGIN);
    }

    @Override
    public void scanEnd() {
        dismiss();
    }

    @Override
    public void preScan() {

    }


    @Override
    public void preStopEnd() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            stopScan();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public interface ScanDialogCallBack{
        void stopScan();
        void startScan();
    }

    public void setScanDialogCallBack(ScanDialogCallBack sdcb){
        this.mScanDialogCallBack = sdcb;

    }

    public RippleView getRippleView(){
        return mRippleBg;
    }
}
