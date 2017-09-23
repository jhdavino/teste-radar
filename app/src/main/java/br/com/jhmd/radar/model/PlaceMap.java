package br.com.jhmd.radar.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by josehenrique on 22/09/17.
 */

public class PlaceMap {

    private String id;
    private String name;
    private String adrress;
    private String phoneAddress;
    private LatLng latLng;
    private String typePlace;



    public PlaceMap(String id, String name, String adrress, String phoneAddress, LatLng latLng, String typePlace) {
        this.id = id;
        this.name = name;
        this.adrress = adrress;
        this.phoneAddress = phoneAddress;
        this.latLng = latLng;
        this.typePlace = typePlace;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdrress() {
        return adrress;
    }

    public void setAdrress(String adrress) {
        this.adrress = adrress;
    }

    public String getPhoneAddress() {
        return phoneAddress;
    }

    public void setPhoneAddress(String phoneAddress) {
        this.phoneAddress = phoneAddress;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String  getTypePlace() {
        return typePlace;
    }

    public void setTypePlace(String typePlace) {
        this.typePlace = typePlace;
    }
}
