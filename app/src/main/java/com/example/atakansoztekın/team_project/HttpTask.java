package com.example.atakansoztekin.team_project;

import android.os.AsyncTask;

import com.google.gson.Gson;



import com.example.atakansoztekin.team_project.WebService;

/**
 * Created by Kerem on 18.12.2017.
 */

public class HttpTask extends AsyncTask<String,String,String> {

    private ResponseHandler responseHandler;



    public HttpTask(ResponseHandler responseHandler) {

        this.responseHandler = responseHandler;
    }

    @Override
    protected String doInBackground(String... url) {

        return WebService.getRequest(url[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        if(s != null || !s.equals("")){
            Gson gson = new Gson();
            responseHandler.onFinish(gson.fromJson(s,TranslationListModel.class));
        }else{
            responseHandler.onFailure();
        }

    }
}
