package at.wifi.swdev.foodoraapp.service;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileService {

    /**
     * Resolves file name from content provider
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getFileName(Context context, Uri uri) {
        String fileName = null;

        if ("content".equals(uri.getScheme())) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index != -1) {
                        fileName = cursor.getString(index);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        // Fallback
        if (fileName == null) {
            fileName = uri.getLastPathSegment();
        }

        return fileName;
    }

    /**
     * Resolves MIME type from content Uri.
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getMimeType(Context context, Uri uri) {
        return context.getContentResolver().getType(uri);
    }

    /**
     * Copies file from Uri to app's cache directory
     *
     * @param context
     * @param uri
     * @param pickedFile
     */
    public static void copyFileToAppCache(Context context, Uri uri, File pickedFile) {
        try {
            pickedFile.createNewFile();

            // Wir wollen den Content der ausgewählten Datei lesen bzw. kopieren
            // -> try with resources
            try (InputStream inputStream = context.getContentResolver().openInputStream(uri)) {
                FileOutputStream outputStream = new FileOutputStream(pickedFile);

                byte[] buffer = new byte[1024];
                int read;
                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
            }

        } catch (IOException e) {
            Log.e("", "Error creating temporary file");
            throw new RuntimeException(e);
        }

    }
}
