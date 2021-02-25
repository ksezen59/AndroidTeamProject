package com.example.atakansoztekin.team_project;

/**
 * Created by Kerem on 18.12.2017.
 */

public abstract class ResponseHandler {
    public void onSuccess(String response) {


    }
    public void onFailure (){

    }

    public <T> void onFinish(T result) {


    }
}

