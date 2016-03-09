package br.com.rf.purpledeckschallenge.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

public class PreferencesUtil {
    private static Map<Context, SharedPreferences> preferences = new HashMap<Context, SharedPreferences>();

    public static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences pref = preferences.get(context);
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
            preferences.put(context, pref);
        }

        return pref;
    }

    public static boolean getBooleanPreference(Context context, int resId, boolean defaultValue) {
        return getSharedPreferences(context).getBoolean(context.getString(resId), defaultValue);
    }

    public static boolean getBooleanPreference(Context context, String prefKey, boolean defaultValue) {
        return getSharedPreferences(context).getBoolean(prefKey, defaultValue);
    }

    public static int getIntPreference(Context context, int resId, int defaultValue) {
        return getSharedPreferences(context).getInt(context.getString(resId), defaultValue);
    }

    public static int getIntPreference(Context context, String prefKey, int defaultValue) {
        return getSharedPreferences(context).getInt(prefKey, defaultValue);
    }

    public static String getStringPreference(Context context, int resId, String defaultValue) {
        return getSharedPreferences(context).getString(context.getString(resId), defaultValue);
    }

    public static String getStringPreference(Context context, String prefKey, String defaultValue) {
        return getSharedPreferences(context).getString(prefKey, defaultValue);
    }

    public static void savePreference(Context context, int resId, int newValue) {
        SharedPreferences mPreferences = PreferencesUtil.getSharedPreferences(context);
        Editor editor = mPreferences.edit();
        editor.putInt(context.getString(resId), newValue);
        editor.commit();
    }

    public static void savePreference(Context context, String prefKey, int newValue) {
        SharedPreferences mPreferences = PreferencesUtil.getSharedPreferences(context);
        Editor editor = mPreferences.edit();
        editor.putInt(prefKey, newValue);
        editor.commit();
    }

    public static void savePreference(Context context, int resId, String newValue) {
        SharedPreferences mPreferences = PreferencesUtil.getSharedPreferences(context);
        Editor editor = mPreferences.edit();
        editor.putString(context.getString(resId), newValue);
        editor.commit();
    }

    public static void savePreference(Context context, String prefKey, String newValue) {
        SharedPreferences mPreferences = PreferencesUtil.getSharedPreferences(context);
        Editor editor = mPreferences.edit();
        editor.putString(prefKey, newValue);
        editor.commit();
    }

    public static void savePreference(Context context, int resId, Boolean newValue) {
        SharedPreferences mPreferences = PreferencesUtil.getSharedPreferences(context);
        Editor editor = mPreferences.edit();
        editor.putBoolean(context.getString(resId), newValue);
        editor.commit();
    }

    public static void savePreference(Context context, String prefKey, Boolean newValue) {
        SharedPreferences mPreferences = PreferencesUtil.getSharedPreferences(context);
        Editor editor = mPreferences.edit();
        editor.putBoolean(prefKey, newValue);
        editor.commit();
    }

    public static void removePreference(Context context, int resId) {
        SharedPreferences mPreferences = PreferencesUtil.getSharedPreferences(context);
        Editor editor = mPreferences.edit();
        editor.remove(context.getString(resId));
        editor.commit();
    }

    public static void removePreference(Context context, String prefKey) {
        SharedPreferences mPreferences = PreferencesUtil.getSharedPreferences(context);
        Editor editor = mPreferences.edit();
        editor.remove(prefKey);
        editor.commit();
    }
}