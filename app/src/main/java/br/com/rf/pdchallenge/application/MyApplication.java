package br.com.rf.pdchallenge.application;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

import br.com.rf.pdchallenge.domain.WeatherUseCase;
import br.com.rf.pdchallenge.util.AnalyticsTrackers;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initializeUseCase();

        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);

    }

    private void initializeUseCase() {
        EventBus.getDefault().register(new WeatherUseCase(getBaseContext()));
    }


}
