package com.example.bookstore.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.example.bookstore.async.DownloadImageTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageUtil {

    public static void setImage(ImageView v, String uriString) {
        URL url = null;
        try {
            url = new URL(uriString);
        } catch (MalformedURLException e) {
            Log.i("ImageUtil", "Requested uriString is not an URL");
        }

        if (url != null) {
            new DownloadImageTask((image) -> {
                v.setImageBitmap(image);
            }).execute(url);
        } else {
            Uri uri = Uri.parse(uriString);
            if (uri != null) {
                v.setImageURI(uri);
            }
        }
    }

    public static String getBase64EncodedThumbnail(String imageUrl, ContentResolver contentResolver) {
        Uri uri = Uri.parse(imageUrl);
        try (InputStream is = contentResolver.openInputStream(uri);
             ByteArrayOutputStream compressedImageStream = new ByteArrayOutputStream()) {
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            Bitmap thumbnail = Bitmap.createScaledBitmap(bitmap, 86, 128, false);
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, compressedImageStream);
            byte[] bytes = compressedImageStream.toByteArray();

            String encodedData =Base64.encodeToString(bytes, Base64.DEFAULT);
            return encodedData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Bitmap getImageFromBase64(String imageData) {
        byte[] bytes = Base64.decode(imageData, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
        return bitmap;
    }
}
