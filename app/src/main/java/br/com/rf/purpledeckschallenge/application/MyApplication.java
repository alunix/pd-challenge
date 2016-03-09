package br.com.rf.purpledeckschallenge.application;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

import br.com.rf.purpledeckschallenge.domain.WeatherUseCase;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initializeUseCase();

    }

    private void initializeUseCase() {
        EventBus.getDefault().register(new WeatherUseCase(getBaseContext()));
    }


}
