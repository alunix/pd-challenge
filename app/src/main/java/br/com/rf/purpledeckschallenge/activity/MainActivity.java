package br.com.rf.purpledeckschallenge.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.rf.purpledeckschallenge.R;
import br.com.rf.purpledeckschallenge.adapter.WeatherListAdapter;
import br.com.rf.purpledeckschallenge.event.WeatherEvent;
import br.com.rf.purpledeckschallenge.model.Weather;
import br.com.rf.purpledeckschallenge.util.Constants;
import br.com.rf.purpledeckschallenge.util.PreferencesUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.list)
    RecyclerView mList;

    @Bind(R.id.error_layout)
    View mErrorLayout;

    @Bind(R.id.load_layout)
    View mLoadLayout;

    WeatherListAdapter mAdapter;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        registerForEvent();
        init();
        updateInfos();
    }

    public void init() {
        if (PreferencesUtil.getBooleanPreference(this, Constants.PREF_KEY_FIRST_SETUP, true)) {
            //save default infos and change pref value
            Weather.saveMyCitiesByString(this, Weather.getDefaultCities());
            PreferencesUtil.savePreference(this, Constants.PREF_KEY_FIRST_SETUP, false);
        }
    }

    public void updateInfos() {
        showLoading();
        EventBus.getDefault().post(new WeatherEvent().new RetrieveList());
    }

    public void loadList(List<Weather> list) {

        if (list.size() > 0) {
            showList();
            mAdapter = new WeatherListAdapter(this, list);
            mList.setAdapter(mAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mList.setLayoutManager(layoutManager);
        } else {
            showError();
        }
    }

    public void showList() {
        mLoadLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.GONE);
        mList.setVisibility(View.VISIBLE);
    }

    public void showLoading() {
        mLoadLayout.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);
        mList.setVisibility(View.GONE);
    }

    public void showError() {
        mLoadLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
        mList.setVisibility(View.GONE);
    }

    @OnClick(R.id.error_layout)
    public void onTapErrorLayout() {
        updateInfos();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateList(WeatherEvent.UpdateList event) {
        Toast.makeText(this, "Item removido: " + mAdapter.getItemCount(), Toast.LENGTH_SHORT).show();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void responseSuccessful(WeatherEvent.SuccessRetrieveList event) {
        loadList(event.weatherList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void successCityAdded(WeatherEvent.SuccessCityAdded event) {
        dismissLoading();
        mAdapter.addItem(event.weather);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void errorCityAdded(WeatherEvent.ErrorCityAdded event) {
        dismissLoading();
        Toast.makeText(this, "Sorry, we didn't found the city that you've searched :/", Toast.LENGTH_LONG).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cityAlreadyExists(WeatherEvent.CityAlreadyExists event) {
        dismissLoading();
        Toast.makeText(this, "The city \'" + event.city + "\' already exists!", Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showLoading(WeatherEvent.ShowLoading event) {
        mProgressDialog = ProgressDialog.show(this, null, "Searching city...", true, false);
    }

    public void dismissLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public void registerForEvent() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void unregisterForEvent() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        unregisterForEvent();
    }
}
