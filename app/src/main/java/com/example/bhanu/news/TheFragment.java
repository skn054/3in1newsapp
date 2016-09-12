package com.example.bhanu.news;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhanu kiran on 23/08/2016.
 */
public class TheFragment extends Fragment {
    NewsAdapter1 adapter;
    public TheFragment() {
    }
    private static final String POPULAR="popular";
    private static final String TOP="top";
    private static final String LATEST="latest";
    private String mSortBy=LATEST;
    public ArrayList<News> mlist;
    public String ArrayKey="Key";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView= (ListView) rootView.findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News news= (News) adapterView.getItemAtPosition(i);
                String url=news.getUrl();
                Intent intent=new Intent(getActivity(),DetailActivity.class);
                intent.putExtra(DetailActivity.DEATIL,url);
                startActivity(intent);
            }
        });
        setHasOptionsMenu(true);
        adapter=new NewsAdapter1(getActivity(),new ArrayList<News>());
        listView.setAdapter(adapter);
        if(savedInstanceState!=null)
        {
            if(savedInstanceState.containsKey(ArrayKey))
            {
                mlist=savedInstanceState.getParcelableArrayList(ArrayKey);
                adapter.setData(mlist);

            }
        }
        else
        {
            update(mSortBy);
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mlist!=null)
        {
            outState.putParcelableArrayList(ArrayKey,mlist);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sort,menu);
        MenuItem popular=menu.findItem(R.id.popular);
        MenuItem top=menu.findItem(R.id.top);
        MenuItem latest=menu.findItem(R.id.latest);
        if(mSortBy.contentEquals(LATEST))
        {
            if(!latest.isChecked())
            {
                latest.setChecked(true);
            }
        }
        else if(mSortBy.contentEquals(TOP))
        {
            if(!top.isChecked())
            {
                top.setChecked(true);
            }

        }
        else if(mSortBy.contentEquals(POPULAR))
        {
            if(!popular.isChecked())
            {
                popular.setChecked(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.latest:
            {
                if (item.isChecked()){
                    item.setChecked(false);}
                else
                {item.setChecked(true);}
                mSortBy=LATEST;
                update(mSortBy);

                return true;
            }
            case R.id.top:
            {
                if (item.isChecked()){
                    item.setChecked(false);}
                else
                {item.setChecked(true);}
                mSortBy=TOP;
                update(mSortBy);
                return true;
            }
            case R.id.popular:
            {
                if (item.isChecked()){
                    item.setChecked(false);}
                else
                {item.setChecked(true);}
                mSortBy=POPULAR;
                update(mSortBy);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        update(mSortBy);
//
//    }

    private void update(String sortby) {
        NewsTask task=new NewsTask();
        task.execute(sortby);
    }

    public class NewsTask extends AsyncTask<String,Void,List<News>>
    {

        HttpURLConnection connection=null;
        BufferedReader reader=null;
        String Json;
        @Override
        protected List<News> doInBackground(String... params) {


            String baseUrl ="https://newsapi.org/v1/articles?";
            Uri uri=Uri.parse(baseUrl).buildUpon().appendQueryParameter("source","the-hindu").appendQueryParameter("sortBy",params[0]).appendQueryParameter("apiKey",BuildConfig.OPEN_WEATHER_MAP_API_KEY).build();
            try {
                URL url=new URL(uri.toString());
                connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream inputStream=connection.getInputStream();
                if(inputStream==null)
                {
                    return null;
                }
                reader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer=new StringBuffer();
                String line;
                while((line=reader.readLine())!=null)
                {
                    buffer.append(line);
                }
                if(buffer.length()==0)
                {
                    return null;
                }
                Json=buffer.toString();
                return make(Json);
                // Log.d("Background",Json);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        private List<News> make(String json) throws JSONException {
            JSONObject object=new JSONObject(json);
            JSONArray jsonArray=object.getJSONArray("articles");
            List<News> list=new ArrayList<>(jsonArray.length());
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject obj=jsonArray.getJSONObject(i);
                News news=new News(obj);
                list.add(news);
            }


            return list;


        }

        @Override
        protected void onPostExecute(List<News> newses) {

            if(newses!=null)
            {
                adapter.setData(newses);
                mlist=new ArrayList<News>();
                mlist.addAll(newses);
            }
        }
    }
}
