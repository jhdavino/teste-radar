package br.com.jhmd.radar.model;

import android.graphics.Bitmap;

/**
 * Created by josehenrique on 21/09/17.
 */

public class User {

    private final String id;
    private final String name;
    private final String urlPictureProfile;
    private Bitmap pictureProfile;

    public User(String id, String name, String urlPictureProfile) {

        this.id = id;
        this.name = name;
        this.urlPictureProfile = urlPictureProfile;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getUrlPictureProfile() {
        return urlPictureProfile;
    }

    public Bitmap getPictureProfile() {
        return pictureProfile;
    }

    public void setPictureProfile(Bitmap pictureProfile) {
        this.pictureProfile = pictureProfile;
    }
}
