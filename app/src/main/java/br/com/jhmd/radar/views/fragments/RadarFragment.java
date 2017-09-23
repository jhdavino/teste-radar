package br.com.jhmd.radar.views.fragments;

/**
 * Created by josehenrique on 20/09/17.
 */

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.jhmd.radar.R;
import br.com.jhmd.radar.model.PlaceMap;
import br.com.jhmd.radar.presenter.RadarContract;
import br.com.jhmd.radar.presenter.RadarPresenter;


public class RadarFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = RadarFragment.class.getSimpleName();

    private MapView mMapView;
    private View view;
    private GoogleMap googleMap;

    private RadarContract radarContract;
    private GoogleApiClient mGoogleApiClient;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static final int PERMISSION_REQUEST_CODE = 100;


    // A default location (Iputinga, Recife, PE) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-8.037533, -34.935195);
    private static final int DEFAULT_ZOOM = 15;
    private boolean mLocationPermissionGranted;



    public static RadarFragment newInstance() {
        return new RadarFragment();
    }

    public RadarFragment() {
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
            view = inflater.inflate(R.layout.fragment_radar, container, false);

            mMapView = (MapView) view.findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);

            // needed to get the map to display immediately
            mMapView.onResume();

            // Prompt the user for permission.
            getLocationPermission();

            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage(getActivity(), GOOGLE_API_CLIENT_ID, this)
                    .build();

        } catch (InflateException e) {
            Log.e("Exception: %s", e.getMessage());
        }

        radarContract = new RadarPresenter();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mLocationPermissionGranted){
            // get places around
            callPlaceDetectionApi();

            // set initial location
            getDeviceLocation();
        }else{

            getLocationPermission();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
        mMapView.onDestroy();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Google Places API connection failed with error code: " + connectionResult.getErrorCode());
        Toast.makeText(getActivity(), "Google Places API connection failed with error code:" + connectionResult.getErrorCode(), Toast.LENGTH_LONG).show();
    }


    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }


    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    callPlaceDetectionApi();
                }
        }
        updateLocation();
    }




    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocation() {
        if (googleMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);

                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.getUiSettings().setMapToolbarEnabled(false);

                // For dropping a marker at a point on the Map
                googleMap.addMarker(new MarkerOptions().position(mDefaultLocation).title("Usuario").snippet("PosicaoIncial").icon(radarContract.getPictureUserLogon()));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(mDefaultLocation).zoom(DEFAULT_ZOOM).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

    }

    private void callPlaceDetectionApi() throws SecurityException {

        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(@NonNull PlaceLikelihoodBuffer likelyPlaces) {

                List<PlaceMap> placeMapList = new ArrayList<>();

                for (PlaceLikelihood placeLikelihood : likelyPlaces) {

                    placeMapList.add(new PlaceMap(
                                    placeLikelihood.getPlace().getId(),
                                    placeLikelihood.getPlace().getName().toString(),
                                    placeLikelihood.getPlace().getAddress().toString(),
                                    placeLikelihood.getPlace().getPhoneNumber().toString(),
                                    placeLikelihood.getPlace().getLatLng(),
                                    radarContract.setTypePlaces(placeLikelihood.getPlace().getPlaceTypes())
                                    ));
                }

                likelyPlaces.release();
                radarContract.savePlaces(placeMapList);
                radarContract.addMarkersOnMap(googleMap);

            }
        });
    }

}