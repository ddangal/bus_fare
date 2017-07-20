package com.pawan.busfarebeta.network;

/**
 * Created by Pawan on 6/27/2017.
 */

public interface OnReceivedData {
    void onSuccess(String s);
    void onError(String message);
}
