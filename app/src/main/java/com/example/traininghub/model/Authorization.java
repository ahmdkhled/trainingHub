package com.example.traininghub.model;

import com.google.gson.annotations.SerializedName;

public class Authorization {

    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
