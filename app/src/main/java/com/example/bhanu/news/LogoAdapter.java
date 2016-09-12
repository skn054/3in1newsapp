package com.example.bhanu.news;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bhanu kiran on 24/08/2016.
 */
public class LogoAdapter extends ArrayAdapter<Logo> {
    Context mContext;
    public LogoAdapter(Context context,List<Logo> list)
    {
        super(context,0,list);
        mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.list_item_view,parent,false);
            ViewHolder holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
       ViewHolder holder= (ViewHolder) convertView.getTag();
        Logo logo=getItem(position);
        holder.imageView.setImageResource(logo.id);
        holder.textView.setText(logo.name);
        return convertView;


    }
    public static class ViewHolder
    {
        ImageView imageView;
        TextView textView;
        public ViewHolder(View view)
        {
             imageView= (ImageView) view.findViewById(R.id.logo);
            textView= (TextView) view.findViewById(R.id.logo_text);
        }

    }


}
