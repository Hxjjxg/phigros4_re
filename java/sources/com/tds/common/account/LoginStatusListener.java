package com.tds.common.account;

/* JADX INFO: loaded from: classes.dex */
public interface LoginStatusListener {
    void onBindAccount(AccountUser accountUser);

    void onLoginSuccess(AccountUser accountUser);

    void onLogout(TdsAccount.AccountType accountType);

    void onUnBindAccount(String str);
}
