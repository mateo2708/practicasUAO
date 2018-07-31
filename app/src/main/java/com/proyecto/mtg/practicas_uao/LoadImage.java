package com.proyecto.mtg.practicas_uao;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

class LoadImage extends AsyncTask<Object, Void, Bitmap> {

    private ImageView imv;
    private String path;

    public LoadImage(ImageView imv, String path) {
        this.imv = imv;
        this.path = path;
    }

    @Override
    protected Bitmap doInBackground(Object... params) {

        Bitmap bmp = null;
        try {
            URL url = new URL(path);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
//        if (!imv.getTag().toString().equals(path)) {
           /* The path is not same. This means that this
              image view is handled by some other async task.
              We don't do anything and return. */
  //          return;
   //     }

        if (result != null && imv != null) {
            imv.setVisibility(View.VISIBLE);
            imv.setImageBitmap(result);
        }
    }
}