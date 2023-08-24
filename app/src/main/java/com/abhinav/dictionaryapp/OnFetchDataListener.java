package com.abhinav.dictionaryapp;

import com.abhinav.dictionaryapp.Models.APIResponse;

public interface OnFetchDataListener {

    void onFetchData(APIResponse apiResponse, String message);
    void onError(String message);
}
