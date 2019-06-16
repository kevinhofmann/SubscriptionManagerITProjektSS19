package de.hdm.subscriptionManager.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.subscriptionManager.client.LoginInfo;

public interface LoginServiceAsync {
    
    void login(String requestUrl, AsyncCallback<LoginInfo> callback);

}
