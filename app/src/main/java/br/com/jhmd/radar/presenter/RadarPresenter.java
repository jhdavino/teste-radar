package br.com.jhmd.radar.presenter;

import android.util.Log;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.jhmd.radar.App;
import br.com.jhmd.radar.model.PlaceMap;
import br.com.jhmd.radar.model.User;

/**
 * Created by josehenrique on 22/09/17.
 */

public class RadarPresenter implements RadarContract {

    @Override
    public void savePlaces(List<PlaceMap> places) {

        if (places != null) {
            // save places

            //List<PlaceMap> arrPlaces = new ArrayList<PlaceMap>();
            App.palces = places;
        }

    }

    @Override
    public List<PlaceMap> getPlaces() {
        List<PlaceMap> arrPlaces = null;

        if (App.palces != null) {
            arrPlaces = App.palces;
        }

        return arrPlaces;
    }

    @Override
    public String setTypePlaces(List types) {

        if ((types.contains(Place.TYPE_HOSPITAL) || types.contains(Place.TYPE_DOCTOR) || types.contains(Place.TYPE_DENTIST) || types.contains(Place.TYPE_VETERINARY_CARE))) {
            return "Hospital";

        } else if (types.contains(Place.TYPE_PHARMACY)) {
            return "Farmacia";

        } else if (types.contains(Place.TYPE_CAFE) || (types.contains(Place.TYPE_RESTAURANT) || types.contains(Place.TYPE_NIGHT_CLUB))) {
            return "Bares e Restaurantes";

        } else if ((types.contains(Place.TYPE_MOSQUE) || types.contains(Place.TYPE_CHURCH))) {
            return "Igrejas e mesquitas";

        } else if (types.contains(Place.TYPE_UNIVERSITY) || types.contains(Place.TYPE_SCHOOL)) {
            return "Escolas e Universidades";
        } else {
            return "Outros";
        }

    }

    @Override
    public BitmapDescriptor getPictureUserLogon() {
        User currentUser = App.userLogin;

        return BitmapDescriptorFactory.fromBitmap(currentUser.getPictureProfile());
    }

    @Override
    public void addMarkersOnMap(GoogleMap googleMap) {

        try {

            List<PlaceMap> placeMapList = this.getPlaces();

            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
            LatLngBounds bounds;


            if (placeMapList != null && placeMapList.size() > 0) {

                for (PlaceMap p : placeMapList) {

                    // Add a marker in map
                    googleMap.addMarker(new MarkerOptions()
                            .position(p.getLatLng())
                            .title(p.getName()+ "Tipo: " + p.getTypePlace())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                            .snippet(p.getAdrress()+" - Fone: "+ p.getPhoneAddress()));


                    // pega todas a s latlng
                    boundsBuilder.include(p.getLatLng());
                }

                // agrupa todos
                bounds = boundsBuilder.build();

                //move the camera for initial position
                CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(bounds.getCenter());
                googleMap.animateCamera(updatePosition);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            }

        } catch (Exception e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
