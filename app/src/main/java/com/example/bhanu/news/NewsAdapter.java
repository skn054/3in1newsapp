package com.example.bhanu.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by bhanu kiran on 23/08/2016.
 */
public class NewsAdapter  extends BaseAdapter {
    List<News> mlist;
    Context mContext;
    private final LayoutInflater mInflater;

    public NewsAdapter(Context context,List<News> list)
    {
        mContext=context;
        mlist=list;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view=mInflater.inflate(R.layout.list_item,viewGroup,false);
            ViewHolder holder=new ViewHolder(view);
            view.setTag(holder);

        }
        ViewHolder holder= (ViewHolder) view.getTag();
        TextView textView=holder.desc;
        News news= (News) getItem(i);
        String desc=news.getDesc();
        if(desc.length()>0)
        textView.setText(desc);
        else
        textView.setText(news.getTitle());
        Picasso.with(mContext).load(news.getUrlToImage()).into(holder.url);
        String value=news.getDate();
        if(value.length()>11)
        {
            value=value.substring(0,10);
            holder.date.setText("Date: "+value);
        }
        return view;
    }

    public void setData(List<News> newses) {
        clear();
        for(News news:newses)
        {
            mlist.add(news);
            notifyDataSetChanged();
        }
    }

    private void clear() {

        mlist.clear();
        notifyDataSetChanged();
    }
    public static class ViewHolder
    {
        public final TextView desc;
        public final ImageView url;
        public final TextView date;
        public ViewHolder(View view)
        {
            desc= (TextView) view.findViewById(R.id.text_view_desc);
            url= (ImageView) view.findViewById(R.id.image_view);
            date= (TextView) view.findViewById(R.id.date);
        }


    }

}
