package br.com.jhmd.radar.views.fragments;

/**
 * Created by josehenrique on 22/09/17.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.jhmd.radar.App;
import br.com.jhmd.radar.R;
import br.com.jhmd.radar.adapter.PlacesAdapter;
import br.com.jhmd.radar.model.PlaceMap;


public class ListFragment extends Fragment {
    private View view;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_list, container, false);

        } catch (InflateException e) {
            Log.e("Exception: %s", e.getMessage());
        }


        RecyclerView rvPlacesMap = (RecyclerView) view.findViewById(R.id.rv_places);
        rvPlacesMap.setHasFixedSize(true);

        //Set RecyclerView's LayoutManager to the one given.
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL);
        rvPlacesMap.setLayoutManager(layoutManager);

        // add places
        List<PlaceMap> mapList = App.palces;

        PlacesAdapter adapter = new PlacesAdapter(mapList);
        rvPlacesMap.setAdapter(adapter);

        return view;

    }
}
