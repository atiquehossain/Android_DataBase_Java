package com.example.java_database_advance;

import android.content.Context;
import android.graphics.Color;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class PopUpAlert {
    Context c;
    public SweetAlertDialog alert, progress;
    public AlertListener customAlertClickListener = null;

    public PopUpAlert(Context c) {
        this.c = c;
    }

    public interface AlertListener {
        void onAlertClick(boolean isCancel);
    }

    public void showWarning(String message){
        alert = new SweetAlertDialog(c, SweetAlertDialog.WARNING_TYPE);
        alert.setCancelable(false);
        alert.setCanceledOnTouchOutside(false);
        alert.setTitleText(c.getString(R.string.common_warning_title));
        alert.setContentText(message);
        alert.setCancelText(c.getString(R.string.no));
        alert.setConfirmText(c.getString(R.string.yes));
        alert.show();
    }

    public SweetAlertDialog showProgress(String title) {
        try {
                progress = new SweetAlertDialog(c, SweetAlertDialog.PROGRESS_TYPE);
                progress.setCancelable(false);
                progress.setCanceledOnTouchOutside(false);
                progress.getProgressHelper().setBarColor(Color.parseColor("#137EF0"));
                progress.setTitleText(title);
                progress.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return progress;
    }



    public void setAlertListener(AlertListener alertClickListener) {
        this.customAlertClickListener = alertClickListener;
        alert.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                customAlertClickListener.onAlertClick(false);
            }
        });
        alert.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                customAlertClickListener.onAlertClick(true);
            }
        });
    }
}
