package com.infoicon.acim.utils.manualNetworkCall;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.infoicon.acim.R;
import com.infoicon.acim.retrofit.ServiceConstants;
import com.infoicon.acim.utils.UtilsMethods;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

import dmax.dialog.SpotsDialog;

public class WebServiceCall {

    Context context;
    private String errorMsg;
    INetworkResponse iNetworkResponse;
    private SpotsDialog mProgressDialog;

    public WebServiceCall(Context context, INetworkResponse iNetworkResponse) {
        this.context = context;
        this.iNetworkResponse = iNetworkResponse;

    }

    public void execute(JSONObject jsonObject, String url) {
        Call call = new Call(jsonObject);
        call.execute(url);
        mProgressDialog = new SpotsDialog(context, R.style.Custom);
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);
    }


    private class Call extends AsyncTask<String, Void, Boolean> {

        private static final String USER_AGENT = "Mozilla/5.0";
        JSONObject jsonObject;
        private String responseString;

        public Call(JSONObject jsonObject) {
            this.jsonObject = jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(30000);
                urlConnection.setReadTimeout(30000);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Users-Agent", USER_AGENT);
                urlConnection.setRequestProperty("Accept-Encoding", "identity");
                urlConnection.setRequestProperty("content-type", "application/json; charset=UTF-8");

                if (jsonObject.has("fromComapny")) {

                } else {
                    DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                    dataOutputStream.writeBytes(jsonObject.toString());
                    dataOutputStream.flush();
                    dataOutputStream.close();
                }


                InputStream inputStream = urlConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String s = "";
                StringBuilder stringBuilder = new StringBuilder("");
                while ((s = bufferedReader.readLine()) != null) {
                    stringBuilder.append(s);
                }

                responseString = stringBuilder.toString();

                return true;
            } catch (UnknownHostException e) {
                e.printStackTrace();
                errorMsg = "Network Not Available!";
                Log.e("UnknownHostException", e + "");
            } catch (SocketTimeoutException e) {

                e.printStackTrace();
                errorMsg = "Request Time Out!";
                Log.e("SocketTimeoutException", e + "");

            } catch (MalformedURLException e) {
                e.printStackTrace();
                errorMsg = "Error";
                Log.e("MalformedURLException", e + "");
            } catch (IOException e) {
                e.printStackTrace();
                errorMsg = "Error";
                Log.e("IOException", e + "");
            } catch (Exception e) {
                e.printStackTrace();
                errorMsg = "Error";
                Log.e("Exception", e + "");
            }finally {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
                    Log.e("post_exe","post_exe");
            if (result) {
                iNetworkResponse.onSuccess(responseString);
            } else {
                iNetworkResponse.onError(errorMsg);
            }


        }
    }
}
