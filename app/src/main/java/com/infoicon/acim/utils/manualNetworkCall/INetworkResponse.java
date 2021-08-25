package com.infoicon.acim.utils.manualNetworkCall;

public interface INetworkResponse {

    public void onSuccess(String response);

    public void onError(String error);
}
