package com.example.traininghub.models;

import android.view.View;

import androidx.annotation.NonNull;

public class NetworkState {

    public static final int BACK=1;
    public static final int RETRY=2;

    private boolean loading;
    private String errorMessage;
    private String actionMessage;
    private String page;
    private int action;

    public NetworkState(boolean loading, String errorMessage, String page) {
        this.loading = loading;
        this.errorMessage = errorMessage;
        this.page = page;
    }

    public NetworkState(boolean loading, String errorMessage, String actionMessage, String page) {
        this.loading = loading;
        this.errorMessage = errorMessage;
        this.actionMessage = actionMessage;
        this.page = page;
    }

    public NetworkState(boolean loading, String errorMessage, int action, String actionMessage, String page) {
        this.loading = loading;
        this.errorMessage = errorMessage;
        this.actionMessage = actionMessage;
        this.page = page;
        this.action = action;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getVisibility(){
        return loading? View.VISIBLE:View.GONE;
    }

    public int getErrorViewVisibility(){
        return errorMessage==null? View.GONE:View.VISIBLE;
    }


    @NonNull
    @Override
    public String toString() {
        return
                "page > "+page +" \n"+
                "loading > "+loading +" \n"+
                "errorMessage > "+errorMessage +" \n";
    }
}
