package com.example.traininghub.models;

import android.view.View;

import androidx.annotation.NonNull;

public class NetworkState {

    private boolean loading;
    private String errorMessage;
    private String page;

    public NetworkState(boolean loading, String errorMessage, String page) {
        this.loading = loading;
        this.errorMessage = errorMessage;
        this.page = page;
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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getVisibility(){
        return loading? View.VISIBLE:View.GONE;
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
