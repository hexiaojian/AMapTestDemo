package com.ruihai.amaptest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by apple on 16/10/11.
 */

public class NearPersonListActivity extends Activity {

    private ListView lv_plist;
    public List<NearPersonInfo> infoList  = null;
    public static final String BASE_URL = "http://yuntuapi.amap.com/datasearch/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearpersonlist);
        infoList = new ArrayList<NearPersonInfo>();
        lv_plist = (ListView) findViewById(R.id.lv_plist);
        getdata();
        NearPersonAdapter nearPersonAdapter = new NearPersonAdapter();
        infoList.clear();
        lv_plist.setAdapter(nearPersonAdapter);
       /* if(infoList.size() != 0) {
            lv_plist.setAdapter(nearPersonAdapter);
        }else{
            nearPersonAdapter.notifyDataSetChanged();
        }*/


    }

    private void getdata(){
        Log.e("TAG", "有消息getdata" );
        String key = "f7f7b6bddf1e4790c51eebb48dc7f6dc";
        String tableid = "57de19d9305a2a208ad6ab8a";
        String center = "117.284049,31.826138";
        String radius = "50000";
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiServers restApi = retrofit.create(ApiServers.class);
        Call<NearPerson> callBack = restApi.sendpost(key, tableid, center, radius);
        callBack.enqueue(new Callback<NearPerson>() {
            @Override
            public void onResponse(Call<NearPerson> call, Response<NearPerson> response) {
                String string = response.body().toString();
                Log.e("获取字符串列表", string);
                List<NearPersonInfo>  list = response.body().getNearPersonInfos();
                infoList.clear();
                infoList.addAll(list);
                Log.e("获取集合长度", String.valueOf(infoList.size()));



            }

            @Override
            public void onFailure(Call<NearPerson> call, Throwable t) {
                Log.e("获取status", "获取失败");

            }
        });
    }

    private class NearPersonAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(),R.layout.person_list_item,null);
            ImageView iv_header = (ImageView) view.findViewById(R.id.iv_header);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            ImageView iv_sex = (ImageView) view.findViewById(R.id.iv_sex);
            TextView tv_distance = (TextView) view.findViewById(R.id.tv_distance);
            TextView tv_signature = (TextView) view.findViewById(R.id.tv_signature);
            //tv_name.setText(infoList.get(position).getNick());

            return view;
        }
    }
}
