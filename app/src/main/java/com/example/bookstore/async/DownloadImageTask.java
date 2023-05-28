package com.example.bookstore.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.function.Consumer;

public class DownloadImageTask extends AsyncTask<URL, Void, Bitmap> {

    private final Consumer<Bitmap> resultConsumer;

    public DownloadImageTask(Consumer<Bitmap> resultConsumer) {
        this.resultConsumer = resultConsumer;
    }

    protected Bitmap doInBackground(URL... urls) {
        URL imageURL = urls[0];
        Bitmap bImage = null;
        InputStream in = null;
        try {
            in = imageURL.openStream();
            bImage= BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error Message", e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return bImage;
    }

    protected void onPostExecute(Bitmap result) {
        resultConsumer.accept(result);
    }
}
