package de.hdm.subscriptionManager.shared;

import com.google.gwt.user.client.rpc.RemoteService;

import de.hdm.subscriptionManager.client.LoginInfo;

public interface LoginService extends RemoteService {

    public LoginInfo login(String requestUrl);
}
