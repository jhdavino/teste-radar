package br.com.jhmd.radar.views.fragments;

/**
 * Created by josehenrique on 20/09/17.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.jhmd.radar.App;
import br.com.jhmd.radar.R;
import br.com.jhmd.radar.model.User;


public class ProfileFragment extends Fragment
{
    private View view;
    private ImageView imageProfile;
    private TextView labelName;

    public static ProfileFragment newInstance()
    {
        return new ProfileFragment();
    }

    public ProfileFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_profile, container, false);
            imageProfile =(ImageView) view.findViewById(R.id.image_profile);
            labelName = (TextView) view.findViewById(R.id.label_name);

        } catch (InflateException e) {
            Log.e("Exception: %s", e.getMessage());
        }



        loadDataUser();

        return view;
    }

    private void loadDataUser(){
        User currentUser = App.userLogin;
        this.labelName.setText(currentUser.getName());
        this.imageProfile.setImageBitmap(currentUser.getPictureProfile());
    }

   }
