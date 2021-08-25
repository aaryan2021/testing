package com.infoicon.acim.retrofit;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.infoicon.acim.R;
import com.infoicon.acim.utils.UtilsMethods;

import java.io.IOException;

import dmax.dialog.SpotsDialog;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ServiceCreator {


    private Activity mActivity;
    private SpotsDialog mProgressDialog;

    public ServiceCreator(Activity activity) {
        this.mActivity = activity;
    }

    public <T> void enqueueCall(final Call<T> call,boolean showProgress, final Callback<T> callback) {

        mProgressDialog = new SpotsDialog(mActivity, R.style.Custom);
        if(showProgress){
            mProgressDialog.show();
            mProgressDialog.setCancelable(false);
        }



        call.enqueue(new Callback<T>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Response<T> response, Retrofit retrofit) {
                Log.e("Response param ", response.raw().request().url().toString());
                if (response.body() == null) {
                    try {
                        if (response.errorBody() != null)
                            Log.e("Response message - Error:", response.errorBody().string());
                        ServiceConstants.ERROR_CODE = response.errorBody().string();
                        callback.onFailure(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        ServiceConstants.ERROR_CODE = e.toString();
                        callback.onFailure(new Throwable("IOException"));
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        ServiceConstants.ERROR_CODE = e.toString();
                        callback.onFailure(new Throwable("NullPointerException"));
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                        UtilsMethods.serverRequestError(mActivity, ServiceConstants.ERROR_CODE);
                    }

                } else {
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    callback.onResponse(response, retrofit);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.e("Response param - onFailure ", t.toString());
                ServiceConstants.ERROR_CODE = t.toString();
                if (mProgressDialog != null){
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                }

                callback.onFailure(t);
                serverRequestError(mActivity, ServiceConstants.ERROR_CODE);
            }
        });
    }

    public void serverRequestError(Context context, String errorMessage) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage(errorMessage);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (mProgressDialog != null)
                    mProgressDialog.dismiss();

            }
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }


}
