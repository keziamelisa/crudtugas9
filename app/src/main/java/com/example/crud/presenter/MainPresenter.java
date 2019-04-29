package com.example.crud.presenter;

import com.example.crud.connection.BaseApp;
import com.example.crud.model.create.PostResponse;
import com.example.crud.model.get.GetResponse;
import com.example.crud.model.getid.GetItemResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {
    private MainView mainView;
    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    public void getAllItems() {
        BaseApp.service.getAllItems().enqueue(new Callback<GetResponse>() {
            @Override
            public void onResponse(Call<GetResponse> call, Response<GetResponse> response) {
                if (response.isSuccessful())
                    mainView.getSuccess(response.body());
                else
                    mainView.onError(response.message());
            }
            @Override
            public void onFailure(Call<GetResponse> call, Throwable t) {
                mainView.onFailure(t.getMessage());
            }
        });
    }
    public void getItems(String id) {
        BaseApp.service.getItems(id).enqueue(new Callback<GetItemResponse>() {
            @Override
            public void onResponse(Call<GetItemResponse> call, Response<GetItemResponse> response2) {
                if (response2.isSuccessful())
                    mainView.getSuccess2(response2.body());
                else
                    mainView.onError(response2.message());
            }
            @Override
            public void onFailure(Call<GetItemResponse> call, Throwable t) {
                mainView.onFailure(t.getMessage());
            }
        });
    }
    public void updateItems(String id, String name, String description) {
        BaseApp.service.updateDataItems(id,name,description).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful())
                    mainView.setToast(response.message());
                else
                    mainView.onError(response.message());
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mainView.onFailure(t.getMessage());
            }
        });
    }

    public void deleteItems(String id) {BaseApp.service.deleteDataItems(id).enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
            if (response.isSuccessful())
                mainView.setToast(response.message());
            else
                mainView.onError(response.message());
        }
        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
            mainView.onFailure(t.getMessage());
        }
    });
    }

    public void createItems(String name, String description) {
        BaseApp.service.createItems(name,description).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful())
                    mainView.setToast(response.message());
                else
                    mainView.onError(response.message());
            }
            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                mainView.onFailure(t.getMessage());
            }
        });
    }
}

