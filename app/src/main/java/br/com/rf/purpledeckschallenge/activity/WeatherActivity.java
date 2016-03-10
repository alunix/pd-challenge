package br.com.rf.purpledeckschallenge.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import br.com.rf.purpledeckschallenge.R;
import br.com.rf.purpledeckschallenge.adapter.WeatherListAdapter;
import br.com.rf.purpledeckschallenge.event.WeatherEvent;
import br.com.rf.purpledeckschallenge.helper.WeatherHelper;
import br.com.rf.purpledeckschallenge.model.Weather;
import br.com.rf.purpledeckschallenge.util.PrefKeys;
import br.com.rf.purpledeckschallenge.util.PreferencesUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        registerForEvent();
        init();
        updateInfos();
    }

    public void init() {
        if (PreferencesUtil.getBooleanPreference(this, PrefKeys.PREF_KEY_FIRST_SETUP, true)) {
            WeatherHelper.saveMyCitiesByStringList(this, WeatherHelper.getDefaultCities());
            PreferencesUtil.savePreference(this, PrefKeys.PREF_KEY_FIRST_SETUP, false);
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
        Toast.makeText(this, event.msg, Toast.LENGTH_LONG).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cityAlreadyExists(WeatherEvent.CityAlreadyExists event) {
        dismissLoading();
        Toast.makeText(this, getString(R.string.msg_city_already_exists, event.city), Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showLoading(WeatherEvent.ShowLoading event) {
        mProgressDialog = ProgressDialog.show(this, null, getString(R.string.msg_searching_city), true, false);
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
