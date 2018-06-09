package com.larkintuckerllc.livedata;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TodosViewModel extends AndroidViewModel {


    private MutableLiveData<List<Todo>> mTodos;


    TodosViewModel(Application application) {
        super(application);



    }

    public LiveData<List<Todo>> getTodos() {
        if (mTodos == null) {
            mTodos = new MutableLiveData<List<Todo>>();
            loadTodos();
        }
        return mTodos;
    }

    private void loadTodos() {

            final List<Todo> newTodos = new ArrayList<Todo>();
            if (ConnectivityReceiver.isConnected()) {
                AppController.getInstance().clearAllQueue();
                Log.e("inside", "inside ");
                final ANRequest request = AndroidNetworking.get("http://customerservice.dohrnii.com/api/Customers")
                        .setPriority(Priority.HIGH)
                        .build();
                Log.e("Success", "URL : " + request.getUrl());
                request.getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("Sucess")) {

                                JSONArray jsonArray = response.getJSONArray("CustomerStatusList");
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject = jsonArray.getJSONObject(j);

                                    newTodos.add(new Todo(jsonObject.getLong("Id"), jsonObject.getString("Customer_Name"), jsonObject.getString("Beat")));
                                }
                                mTodos.setValue(newTodos);

                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
                        final Gson gson = builder.create();


                        error.printStackTrace();
                    }
                });
            } else {
                mTodos.setValue(null);
            }
        }




}

