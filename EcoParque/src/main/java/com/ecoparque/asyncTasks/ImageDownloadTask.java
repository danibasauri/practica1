package com.ecoparque.asyncTasks;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.ecoparque.R;
import com.ecoparque.objects.FileCache;
import com.ecoparque.objects.LazyAdapter;
import com.ecoparque.objects.MemoryCache;
import com.ecoparque.objects.NetInfo;
import com.ecoparque.objects.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {

    FileCache fileCache;
    MemoryCache memoryCache = new MemoryCache();
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    private int mPosition;
    private LazyAdapter.ViewHolder mHolder;
    final int stub_id = R.drawable.ic_launcher;
    private String mUrl;
    private Activity mActivity;


    public ImageDownloadTask(String url, Activity activity, int position, LazyAdapter.ViewHolder holder) {
        fileCache = new FileCache(activity.getApplicationContext());
        mActivity = activity;
        mPosition = position;
        mHolder = holder;
        mUrl = url;
        imageViews.put(mHolder.imageView, mUrl);

    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            imageViews.put(mHolder.imageView, mUrl);
            return getBitmap(mUrl);
        } catch (Exception e) {
            e.toString();
            Log.e("ImageDownloadTask", e.getMessage());
        }
        return null;
    }

    private Bitmap getBitmap(String url) {

        Bitmap bitmap = memoryCache.get(mUrl);
        if (bitmap != null) {
            mHolder.imageView.setImageBitmap(bitmap);
            return bitmap;
        }
        File f = fileCache.getFile(url);
        //from SD cache
        bitmap = Utils.decodeFile(f);
        if (bitmap != null) {
            mHolder.imageView.setImageBitmap(bitmap);
            return bitmap;
        }
        mHolder.imageView.setImageResource(stub_id);
        NetInfo netInfo = new NetInfo(mActivity);
        if (netInfo.isConnected()) {
            try {


                bitmap = null;
                URL imageUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                conn.setInstanceFollowRedirects(true);
                InputStream is = conn.getInputStream();
                OutputStream os = new FileOutputStream(f);
                Utils.CopyStream(is, os);
                os.close();
                bitmap = Utils.decodeFile(f);
                memoryCache.put(url, bitmap);
                return bitmap;

            } catch (Throwable ex) {
                ex.printStackTrace();
                if (ex instanceof OutOfMemoryError)
                    memoryCache.clear();
                return null;
            }
        } else
            return null;
    }


    boolean imageViewReused(String url, ImageView imgView) {
        String tag = imageViews.get(imgView);
        if (tag == null || !tag.equals(url))
            return true;
        return false;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }
        if (imageViewReused(mUrl, mHolder.imageView))
            return;
        if (bitmap != null) {
            if (mHolder.position == mPosition) {
                mHolder.imageView.setImageBitmap(bitmap);
            } else {
                mHolder.imageView.setImageResource(stub_id);
            }

        }


    }

}








