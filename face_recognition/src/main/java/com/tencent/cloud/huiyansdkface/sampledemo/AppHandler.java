package com.tencent.cloud.huiyansdkface.sampledemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.tencent.cloud.huiyansdkface.facelight.process.FaceVerifyStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by leoraylei on 16/9/19.
 */

public class AppHandler {
    public static final int ERROR_DATA = -100;
    public static final int ERROR_LOCAL = -101;
    public static final String DATA_MODE_DESENSE = "data_mode_desense";
    private static final int WHAT_SIGN = 1;
    private static final int ARG1_SUCCESS = 1;
    private static final int ARG1_FAILED = 2;
    private static final int ARG1_TOKEN = 3;
    private static final int ARG1_AUTH = 4;
    private static final String DATA_MODE = "data_mode";
    private static final String DATA_SIGN = "data_sign";
    private static final String DATA_CODE = "data_code";
    private static final String DATA_MSG = "data_msg";
    private FaceVerifyDemoActivity activity;


    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_SIGN) {
                if (msg.arg1 == ARG1_SUCCESS) {
                    String sign = msg.getData().getString(DATA_SIGN);

                    String mode = msg.getData().getString(DATA_MODE);
                    switch (mode) {
                        case DATA_MODE_DESENSE:
                            activity.getFaceId(FaceVerifyStatus.Mode.GRADE, sign);
                            break;
                        default:
                            break;
                    }
                }else if(msg.arg1 == ARG1_TOKEN){
                    String message = msg.getData().getString(DATA_SIGN);
                    String mode = msg.getData().getString(DATA_MODE);
                    Log.e("AppHandler", "??????:[" + mode + "]," + message);


                    activity.hideLoading();
                    activity.getToken(mode,message);
                }else if(msg.arg1 == ARG1_AUTH){

                    String message = msg.getData().getString(DATA_SIGN);
                    String mode = msg.getData().getString(DATA_MODE);
                    Log.e("AppHandler", "??????:[" + mode + "]," + message);
                    activity.hideLoading();
                    activity.getAuth(mode,message);


                } else {
                    int code = msg.getData().getInt(DATA_CODE);
                    String message = msg.getData().getString(DATA_MSG);
                    Log.e("AppHandler", "????????????:[" + code + "]," + message);
                    activity.hideLoading();
                }
            }
        }
    };


    public AppHandler(FaceVerifyDemoActivity activity) {
        this.activity = activity;
    }

    public void sendSignError(int code, String msg) {
        Message message = new Message();
        message.what = WHAT_SIGN;
        message.arg1 = ARG1_FAILED;
        final Bundle data = new Bundle();
        data.putInt(DATA_CODE, code);
        data.putString(DATA_MSG, msg);
        message.setData(data);
        handler.sendMessage(message);
    }

    public void sendSignSuccess(String mode, String sign) {
        Message message = new Message();
        message.what = WHAT_SIGN;
        message.arg1 = ARG1_SUCCESS;
        final Bundle data = new Bundle();
        data.putString(DATA_SIGN, sign);
        data.putString(DATA_MODE, mode);
        message.setData(data);
        handler.sendMessage(message);
    }

    public void sendTokenSuccess(String mode, String sign) {
        Log.e("AppHandler", "????????????:[" + mode + "]," + sign);
        Message message = new Message();
        message.what = WHAT_SIGN;
        message.arg1 = ARG1_TOKEN;
        final Bundle data = new Bundle();
        data.putString(DATA_SIGN, sign);
        data.putString(DATA_MODE, mode);
        message.setData(data);
        handler.sendMessage(message);
    }

    public void sendAuthSuccess(String mode,String sign){
        Log.e("AppHandler", "??????Auth:[" + mode + "]," + sign);
        Message message = new Message();
        message.what = WHAT_SIGN;
        message.arg1 = ARG1_AUTH;
        final Bundle data = new Bundle();
        data.putString(DATA_SIGN, sign);
        data.putString(DATA_MODE, mode);
        message.setData(data);
        handler.sendMessage(message);
    }



}
