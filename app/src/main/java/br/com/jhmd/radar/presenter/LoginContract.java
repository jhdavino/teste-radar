package br.com.jhmd.radar.presenter;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by josehenrique on 21/09/17.
 */

public interface LoginContract {

    void saveUserLogin(Context context, JSONObject object);

}
