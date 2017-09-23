package br.com.jhmd.radar.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import br.com.jhmd.radar.App;
import br.com.jhmd.radar.R;
import br.com.jhmd.radar.http.AsyncDownloadImage;
import br.com.jhmd.radar.http.IAsyncDownloadImage;
import br.com.jhmd.radar.model.User;
import br.com.jhmd.radar.presenter.LoginContract;
import br.com.jhmd.radar.presenter.LoginPresenter;

/**
 * Created by josehenrique on 20/09/17.
 */

public class LoginActivity extends AppCompatActivity implements IAsyncDownloadImage {

    private CallbackManager callbackManager;
    private LoginContract loginContract;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginContract = new LoginPresenter();

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject objectResult, GraphResponse response) {
                                try {

                                    logon(objectResult);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                // set parameters
                Bundle parameters = new Bundle();
                parameters.putString("fields","id, name, picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.i("TAG", "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i("TAG", "onError");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void logon(JSONObject object){
        try {

            loginContract.saveUserLogin(getApplicationContext(), object);
            // get data user
            this.currentUser = App.userLogin;

           //request download image profile
            new AsyncDownloadImage(this).execute(this.currentUser.getUrlPictureProfile());

        } catch (Exception e) {
            Log.i("TAG", "LogonException -->"+ e.getMessage());
            Toast.makeText(getApplicationContext(), "Erro ao tentar realizar o login com facebook", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void downloadImageFinalized(Bitmap bitmap) {
        // set value picture profile
        this.currentUser.setPictureProfile(bitmap);
        // go to MainActivity
        Intent intent = new Intent(getApplication(), MainActivity.class);
        startActivity(intent);
        finish();

    }
}
