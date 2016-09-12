package com.example.bhanu.news;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class DetailActivity extends Activity {

    public static String DEATIL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        String url="";
        WebView webView;
        public DetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent intent=getActivity().getIntent();
            webView= (WebView) rootView.findViewById(R.id.web_view);
            //textView= (TextView) rootView.findViewById(R.id.text3);
            if(intent!=null)
            {
                url=intent.getStringExtra(DEATIL);
                //Cursor cursor=getActivity().getContentResolver().query(uri,null,null,null,null);
                //cursor.moveToFirst();
//                if(cursor.getCount()>0)
//                {
//                    String url=cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.URL));
//                }
//
                webView.loadUrl(url);
                webView.setWebViewClient(new WebViewClient());
                //webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                webView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
                //cursor.close();
//                textView.setText(url);

            }
            return rootView;
        }
    }
}
