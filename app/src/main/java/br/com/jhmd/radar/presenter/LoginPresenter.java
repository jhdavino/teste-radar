package br.com.jhmd.radar.presenter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.jhmd.radar.App;
import br.com.jhmd.radar.model.User;

/**
 * Created by josehenrique on 21/09/17.
 */

public class LoginPresenter implements LoginContract {

    private Context context;
    private View view;

    @Override
    public void saveUserLogin(Context context, JSONObject object) {

        try {

            this.context = context;

            String id = object.getString("id");
            String name = object.getString("name");
            String picture  = object.getJSONObject("picture").getJSONObject("data").getString("url");

            // save user
            App.userLogin = new User(id,name,picture);

        } catch (JSONException e) {
            Toast.makeText(this.context.getApplicationContext(), "Erro ao tentar realizar o login com facebook",
                    Toast.LENGTH_LONG).show();
        }
    }



}
