package com.example.crud.presenter;

import com.example.crud.model.get.GetResponse;
import com.example.crud.model.getid.GetItemResponse;

public interface MainView {
    void getSuccess(GetResponse list);
    void setToast(String message);
    void onError(String errorMessage);
    void onFailure(String failureMessage);
    void getSuccess2(GetItemResponse listItem);
}