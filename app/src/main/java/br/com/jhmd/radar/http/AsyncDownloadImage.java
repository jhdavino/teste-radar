package br.com.jhmd.radar.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by josehenrique on 21/09/17.
 */


public class AsyncDownloadImage extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = AsyncDownloadImage.class.getSimpleName();

    private IAsyncDownloadImage delegate = null;

    public AsyncDownloadImage(IAsyncDownloadImage delegate) {
        this.delegate = delegate;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String urlImage = params[0];
        Bitmap dataResult = null;

        if (urlImage != null && urlImage.trim().length() > 0) {
            try {
                final URL u = new URL(urlImage);
                final URLConnection conn = u.openConnection();
                conn.connect();

                final BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                dataResult = BitmapFactory.decodeStream(bis);
                bis.close();

            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

        return dataResult;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            delegate.downloadImageFinalized(result);
        }
    }
}