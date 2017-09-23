package br.com.jhmd.radar.presenter;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;

import java.util.List;

import br.com.jhmd.radar.model.PlaceMap;

/**
 * Created by josehenrique on 22/09/17.
 */

public interface RadarContract {
    void savePlaces(List<PlaceMap> places);
    List<PlaceMap> getPlaces();
    String setTypePlaces(List types);
    BitmapDescriptor getPictureUserLogon();
    void addMarkersOnMap(GoogleMap googleMap);

}
