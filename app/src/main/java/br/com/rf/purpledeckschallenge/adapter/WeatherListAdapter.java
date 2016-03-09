package br.com.rf.purpledeckschallenge.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import br.com.rf.purpledeckschallenge.R;
import br.com.rf.purpledeckschallenge.event.WeatherEvent;
import br.com.rf.purpledeckschallenge.http.RestFacade;
import br.com.rf.purpledeckschallenge.model.FlickrPhoto;
import br.com.rf.purpledeckschallenge.model.Weather;
import br.com.rf.purpledeckschallenge.util.GUIUtils;
import br.com.rf.purpledeckschallenge.util.StringUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Rodrigo Ferreira on 5/03/2016.
 */
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.ViewHolder> {

    private List<Weather> mList;

    private Activity mActivity;

    private boolean mEditEnabled = false;

    private ViewHolder mViewHolder;

    public WeatherListAdapter(Activity activity, List<Weather> weatherList) {
        mList = weatherList;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_weather_first, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_weather_any, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Context context = holder.itemView.getContext();
        final Weather weather = mList.get(position);

        holder.mTextCity.setText(weather.city);
        holder.mTextTime.setText(weather.time);
        holder.mTextTempture.setText(weather.tempture);
        holder.mImgOverlay.setImageResource(Weather.getTypeOverlayRes(weather.weatherType));

        if (weather.photoUrl != null) {
            Glide.with(mActivity)
                    .load(weather.photoUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .centerCrop()
                    .into(holder.mImgBg);
        } else {
            RestFacade.searchPhotoByTag(weather.city, new Callback<FlickrPhoto>() {
                @Override
                public void success(FlickrPhoto flickrPhoto, Response response) {
                    if ("ok".equals(flickrPhoto.stat) && flickrPhoto.photos.photo.size() > 0) {
                        FlickrPhoto.Photo photo = flickrPhoto.photos.photo.get(0);
                        String photoUrl = context.getString(R.string.url_flikr_photo, photo.farm, photo.server, photo.id, photo.secret);
                        Log.d("photoUrl", photoUrl);
                        weather.photoUrl = photoUrl;
                        Glide.with(mActivity)
                                .load(photoUrl)
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .centerCrop()
                                .into(holder.mImgBg);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }

        if (mEditEnabled) {
            holder.mImgType.setImageResource(R.drawable.remove);
            holder.mImgType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getItemCount() == 1) {
                        mEditEnabled = false;
                        Toast.makeText(context, "You cannot remove the last item", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    } else {
                        mList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount());
                        Weather.saveMyCitiesByWeather(context, mList);
                    }
                }
            });
        } else {
            holder.mImgType.setImageResource(Weather.getTypeIconRes(weather.weatherType));
            holder.mImgType.setOnClickListener(null);
        }

        if (holder.mImgRemove != null && getItemCount() > 1) {
            holder.mImgRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditEnabled = !mEditEnabled;
                    notifyDataSetChanged();
                }
            });
        }

        if (holder.mEditAddCity != null) {
            holder.mEditAddCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId,
                                              KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEND && v.getText().toString().trim().length() > 0) {
                        GUIUtils.hideKeyboard(mActivity);
                        if (!Weather.cityAlreadyExists(context, StringUtils.removeAccents(v.getText().toString().trim()))) {
                            EventBus.getDefault().post(new WeatherEvent().new AddCity(StringUtils.removeAccents(v.getText().toString().trim())));
                            holder.mEditAddCity.setText("");
                            return true;
                        } else {
                            EventBus.getDefault().post(new WeatherEvent().new CityAlreadyExists(StringUtils.removeAccents(v.getText().toString().trim())));
                            holder.mEditAddCity.setText("");
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }

    public void addItem(Weather weather) {
        mList.add(weather);
        Weather.saveMyCitiesByWeather(mActivity, mList);
        notifyItemInserted(getItemCount());
        notifyItemRangeChanged(getItemCount() - 1, getItemCount());

    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.item_edit_add_city)
        EditText mEditAddCity;
        @Nullable
        @Bind(R.id.item_img_remove)
        ImageView mImgRemove;
        @Bind(R.id.item_img_bg)
        ImageView mImgBg;
        @Bind(R.id.item_img_overlay)
        ImageView mImgOverlay;
        @Bind(R.id.item_text_city)
        TextView mTextCity;
        @Bind(R.id.item_text_time)
        TextView mTextTime;
        @Bind(R.id.item_text_tempture)
        TextView mTextTempture;
        @Bind(R.id.item_img_type)
        ImageView mImgType;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
