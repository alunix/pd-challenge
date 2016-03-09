package br.com.rf.purpledeckschallenge.activity;

import android.os.Bundle;
import android.os.Handler;
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
import java.util.Date;
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

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.list)
    RecyclerView mList;

    @Bind(R.id.error_layout)
    View mErrorLayout;

    @Bind(R.id.load_layout)
    View mLoadLayout;

    WeatherListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        registerForEvent();

        init();

        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(1457465564);
        Log.d("data servico", "HoraAqui: "+ calendar.get(Calendar.HOUR)+":"+ calendar.get(Calendar.MINUTE));

        showLoading();
        EventBus.getDefault().post(new WeatherEvent().new RetrieveList());
    }

    public void init() {
        if (PreferencesUtil.getBooleanPreference(this, Constants.PREF_KEY_FIRST_SETUP, true)) {
            //save default infos and change pref value
            Weather.saveMyCitiesByString(this, Weather.getDefaultCities());
            PreferencesUtil.savePreference(this, Constants.PREF_KEY_FIRST_SETUP, false);
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateList(WeatherEvent.UpdateList event) {
        Toast.makeText(this, "Item removido: " + mAdapter.getItemCount(), Toast.LENGTH_SHORT).show();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void responseSuccessful(WeatherEvent.RetrieveListSuccess event) {
        loadList(event.weatherList);
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
