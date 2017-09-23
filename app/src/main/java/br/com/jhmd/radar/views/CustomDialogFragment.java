package br.com.jhmd.radar.views;

/**
 * Created by josehenrique on 22/09/17.
 */


import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.jhmd.radar.R;


public class CustomDialogFragment extends DialogFragment {


    public static CustomDialogFragment newInstance() {
        return new CustomDialogFragment();
    }

    public CustomDialogFragment()
    {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.i("Script", "onCreate()");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i("Script", "onCreateView()");

        View view = inflater.inflate(R.layout.dialog_fragment_layout, container);
        Button btExit = (Button) view.findViewById(R.id.btn_getRoute);
        btExit.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                //dismiss();

                //((MainActivity) getActivity()).turnOffDialogFragment();
            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.i("Script", "onActivityCreated()");

        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }




    @Override
    public void onCancel(DialogInterface dialog){
        super.onCancel(dialog);
        Log.i("Script", "onCancel()");
    }


    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.i("Script", "onDestroyView()");
    }


    @Override
    public void onDetach(){
        super.onDetach();
        Log.i("Script", "onDetach()");
    }


    @Override
    public void onDismiss(DialogInterface dialog){
        super.onDismiss(dialog);
        Log.i("Script", "onDismiss()");
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.i("Script", "onSaveInstanceState()");
    }


    @Override
    public void onStart(){
        super.onStart();
        Log.i("Script", "onStart()");
    }


    @Override
    public void onStop(){
        super.onStop();
        Log.i("Script", "onStop()");
    }
}
