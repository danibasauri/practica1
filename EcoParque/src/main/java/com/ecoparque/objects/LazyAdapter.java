package com.ecoparque.objects;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecoparque.R;
import com.ecoparque.asyncTasks.ImageDownloadTask;

import java.util.ArrayList;

/**
 * Created by dcandelas on 20/11/13.
 */
public class LazyAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Item> data;
    private static LayoutInflater inflater = null;
    public ImageDownloadTask imDownlTask;

    public LazyAdapter(Activity a, ArrayList d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_listview_item, null);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.text);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            holder.position = position;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameView.setText(data.get(position).getName());
        holder.position = position;
        imDownlTask = new ImageDownloadTask(data.get(position).getUrl(), activity, position, holder);
        imDownlTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data.get(position).getUrl());
        return convertView;
    }


    public static class ViewHolder {
        TextView nameView;
        public ImageView imageView;
        public int position;
    }
}
