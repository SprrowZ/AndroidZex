package com.rye.catcher.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created at 2018/9/29.
 *
 * @author Zzg
 */
public class ImageUtils {
    public byte[] getBytesByBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bitmap.getByteCount());
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }
}
