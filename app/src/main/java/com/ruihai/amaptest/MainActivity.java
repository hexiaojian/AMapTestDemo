package com.ruihai.amaptest;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amp.apis.lib.ClusterClickListener;
import com.amp.apis.lib.ClusterItem;
import com.amp.apis.lib.ClusterOverlay;
import com.amp.apis.lib.ClusterRender;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AMap.OnMarkerClickListener,AMap.OnInfoWindowClickListener,AMap.InfoWindowAdapter,ClusterRender,AMap.OnMapLoadedListener,ClusterClickListener {
    private MapView mapView;
    private AMap aMap;
    public AMapLocationClient mLocationClient = null;

    public AMapLocationClientOption mLocationOption = null;
    private double lat;
    private double lon;
    private int clusterRadius = 10;
    public String data;
    public String data1;
    public static final String BASE_URL = "http://yuntuapi.amap.com/datasearch/";
    public List<NearPersonInfo> infoList  = null;
    public List<NearPersonInfo> newInfoList = null;
    public NearPersonInfo nearPersonInfo;
    public Button bt;
    public final static String FILE_BASE_URL = "http://file.xingka.cc/";
    public final static String STYLE_THUMBNAIL_96 = "thumbnail96";
    public Uri imageUrl;
    private NearPersonAdapter mNearPersonAdapter;

    public AMapLocationListener mLocationListener = new AMapLocationListener(){

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);//定位时间
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    amapLocation.getAoiName();//获取当前定位点的AOI信息
                    lat = amapLocation.getLatitude();
                    lon = amapLocation.getLongitude();
                    Log.v("pcw","lat : "+lat+" lon : "+lon);
                    // 设置当前地图显示为当前位置
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 19));
                    //List<MarkerOptions> list  = new ArrayList<MarkerOptions>();
                    //MarkerOptions markerOptions = new MarkerOptions();
                    //markerOptions.position(new LatLng(lat, lon));
                    //markerOptions.title("当前位置");
                    //markerOptions.snippet("备注信息");
                    //markerOptions.visible(true);
                    //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
                    //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat+0.01, lon+0.01), 19));
                    //MarkerOptions markerOptions1 = new MarkerOptions();
                    //markerOptions1.position(new LatLng(lat+0.01, lon+0.01));
                    //markerOptions1.title("当前位置");
                    //markerOptions1.snippet("备注信息");
                    //markerOptions1.visible(true);
                    //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat+0.001, lon+0.001), 19));
                    //MarkerOptions markerOptions2 = new MarkerOptions();
                    //markerOptions2.position(new LatLng(lat+0.001, lon+0.001));
                    //markerOptions2.title("当前位置");
                    //markerOptions2.snippet("备注信息");
                    //markerOptions2.visible(true);
                    //markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
                    //markerOptions1.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
                    //View view = View.inflate(getApplicationContext(),R.layout.location, null);
                    //ImageView iv_info = (ImageView) view.findViewById(R.id.iv_info);
                    //Bitmap bitmap = ((BitmapDrawable)iv_info.getDrawable()).getBitmap();
                    //bitmap = Bitmap.createBitmap(bitmap, 0 ,0, bitmap.getWidth()/100,
                            //bitmap.getHeight()/100);
                    //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                    //BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.touxiang));
                    //markerOptions.icon(bitmapDescriptor);
//                    for (int i = 0; i < 5; i++){
//                        if(i ==0){
//                            markerOptions.position(new LatLng(lat+i, lon+i));
//                        }
//
//                        aMap.addMarker(markerOptions);
//                    }list
                    //list.add(markerOptions);
                    //list.add(markerOptions1);
                    //list.add(markerOptions2);
                    //aMap.clear();
                    //for(int i = 0; i < list.size(); i++){
                       // aMap.addMarker(list.get(i));
                   // }
                    Log.e("TAG", "有消息1" );
                    /*MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(lat, lon));
                    markerOptions.title("当前位置");
                    markerOptions.snippet("备注信息");
                    markerOptions.visible(true);
                    View selfView = View.inflate(getApplicationContext(),R.layout.selfimage,null);
                    ImageView iv_self = (ImageView) selfView.findViewById(R.id.iv_self);
                    Bitmap bitmapSelf = ((BitmapDrawable)iv_self.getDrawable()).getBitmap();
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmapSelf));
                    aMap.addMarker(markerOptions);*/

                    //aMap.addMarker(markerOptions);

                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        bt = (Button) findViewById(R.id.bt_click);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,NearPersonListActivity.class);
                startActivity(intent);
            }
        });

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        infoList = new ArrayList<NearPersonInfo>();
        newInfoList = new ArrayList<NearPersonInfo>();

        //getdata();
        getdata();
        init();

        //aMap.setOnMarkerClickListener(this);
        aMap.setInfoWindowAdapter(this);
    }



    private void getdata(){
        Log.e("TAG", "有消息getdata" );
       nearPersonInfo = new NearPersonInfo();

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
                //Log.e("获取字符串列表", string);
                List<NearPersonInfo> list = response.body().getNearPersonInfos();
                infoList.clear();
                infoList.addAll(list);
//                mNearPersonAdapter.notifyDataSetChanged();

                for(int i = 0 ;i< infoList.size();i++){
                    String image_header = infoList.get(i).getImg();
                    //Log.e("2222222222", "2222222222222222" );
                    //Log.e("2222222222", image_header );
                    String new_image_header = getThumbnail96Url(image_header);
                    imageUrl = Uri.parse(new_image_header);
                }
                Log.e("获取集合长度", String.valueOf(infoList.size()));

                for (NearPersonInfo info:infoList ) {
                   // Log.e("获取经纬度", info.get_location());

                }
                aMap.reloadMap();


            }

            @Override
            public void onFailure(Call<NearPerson> call, Throwable t) {
                Log.e("获取status", "获取失败");

            }
        });
    }

    private void init() {

        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnMapLoadedListener(this);

        }
        setUpMap();

    }

    private void setUpMap() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(50000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }
    public static String getThumbnail96Url(String imgName) {
        return String.format("%s%s_%s", FILE_BASE_URL, imgName, STYLE_THUMBNAIL_96);
    }



    private class NearPersonAdapter extends BaseAdapter{

        List<NearPersonInfo> nearPersonInfoList;

        public NearPersonAdapter(List<NearPersonInfo> nearPersonInfoList) {
            this.nearPersonInfoList = nearPersonInfoList;
        }

        @Override
        public int getCount() {
            return nearPersonInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = View.inflate(getApplicationContext(),R.layout.location, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            //tv_name.setText(infoList.get(position).getNick());
            for(int i = 0; i<5; i++){
                NearPersonInfo nearPersonInfos = nearPersonInfoList.get(i);
                //Log.e("333333333333333", nearPersonInfos.getNick() );
                Log.e("setText", "setText------" + i);
                //tv_name.setText(nearPersonInfos.getNick());
                tv_name.setText(String .valueOf(i));

            }
            SimpleDraweeView sdv_image = (SimpleDraweeView) view.findViewById(R.id.sdv_image);
            GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources())
                    .setRoundingParams(RoundingParams.asCircle())
                    .setFadeDuration(100)
                    .build();
            sdv_image.setHierarchy(hierarchy);
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(imageUrl)
                    .build();
            sdv_image.setController(controller);

            return view;
        }
    }


    @Override
    public Drawable getDrawAble(int clusterNum) {

        Log.e("getDrawAble", "有消息getDrawAble");
        int radius = dp2px(getApplicationContext(), clusterRadius);
        TextView tv_name;
        Log.e("TAG", "有消息getDrawAble" );
        // 设置当前地图显示为当前位置
        //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 19));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(lat, lon));
        markerOptions.title("当前位置");
        markerOptions.snippet("备注信息");
        markerOptions.visible(true);
        View selfView = View.inflate(getApplicationContext(),R.layout.selfimage,null);
        ImageView iv_self = (ImageView) selfView.findViewById(R.id.iv_self);
        Bitmap bitmapSelf = ((BitmapDrawable)iv_self.getDrawable()).getBitmap();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmapSelf));
        aMap.addMarker(markerOptions);

        View view = View.inflate(getApplicationContext(),R.layout.location, null);

        //ListView lv_adapter = (ListView) view.findViewById(R.id.lv_plist);
        //mNearPersonAdapter = new NearPersonAdapter(infoList);
        //lv_adapter.setAdapter(mNearPersonAdapter);
//        nearPersonAdapter.notifyDataSetChanged();

        //TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        //TextView tv_near = (TextView) view.findViewById(R.id.tv_near);

        tv_name = (TextView) view.findViewById(R.id.tv_name);

        for(int i = 0; i<infoList.size(); i++){
            NearPersonInfo nearPersonInfos = infoList.get(i);
            newInfoList.add(nearPersonInfos);
            tv_name.invalidate();
            tv_name.setText(nearPersonInfos.getNick());
            Log.e("nearPersonInfos", nearPersonInfos.getNick() );
        }


        for(int i = 0 ;i< infoList.size();i++){

            Log.e("11111111111111111", infoList.get(i).getNick() );


            //tv_name = new TextView(getApplicationContext());
            //tv_name.setText(infoList.get(i).getNick());
            //tv_near.setText("10"+i);
        }
        //tv_name.setText("林林");
        //view.measure(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        //int width = view.getMeasuredWidth();
        //int hight = view.getMeasuredHeight();

        //Log.e("宽高度", String.valueOf(width)+"," + String.valueOf(hight));
        //Bitmap bitmap = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_4444);
        //Bitmap bitmap1 = ((BitmapDrawable)view.getDrawable()).getBitmap();
        //view.draw(new Canvas(bitmap));


        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        //Bitmap bitmap = getViewBitmap(view);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);

       /* if (clusterNum == 1) {
            Log.e("TAG", "1" );
            return getApplication().getResources().getDrawable(
                    R.drawable.icon_openmap_mark);
        } else if (clusterNum < 5) {
            Log.e("TAG", "<5" );
            BitmapDrawable drawable = new BitmapDrawable(drawCircle(radius,
                    Color.argb(159, 21, 15, 6)));


            return drawable;
        } else if (clusterNum < 10) {
            Log.e("TAG", "<10" );
            BitmapDrawable drawable = new BitmapDrawable(drawCircle(radius,
                    Color.argb(199, 217, 114, 0)));
            return drawable;
        } else {
            Log.e("TAG", ">10" );
            BitmapDrawable drawable = new BitmapDrawable(drawCircle(radius,
                    Color.argb(235, 215, 66, 2)));
            return drawable;

        }*/

        //return null;
        return drawable;
    }

    @Override
    public void onMapLoaded() {
        TextView tv_name;
        Log.e("TAG", "有消息onMapLoaded" );
        ClusterOverlay clusterOverlay = new ClusterOverlay(aMap,
                dp2px(getApplicationContext(),clusterRadius),
                getApplicationContext());
        clusterOverlay.setClusterRenderer(this);
        clusterOverlay.setOnClusterClickListener(this);

        Log.e("经度", "hahhahahahahaha" );
        for (int i = 0; i <infoList.size(); i++) {
            Log.e("TAG", "heiheiheiheiehei" );

            String lat = infoList.get(i).get_location();
            String [] newLat = lat.split(",");
            String  jindu = newLat[0];
            String weidu = newLat[1];
            Log.e("经度", jindu );
            Log.e("维度", weidu );
            Double dJindu =Double.valueOf(jindu);
            Double dWeidu =Double.valueOf(weidu);
            RegionItem regionItem = new RegionItem(new LatLng(dWeidu,dJindu));
            clusterOverlay.addClusterItem(regionItem);

        }

    }

    private Bitmap drawCircle(int radius, int color) {
        Bitmap bitmap = Bitmap.createBitmap(radius * 2, radius * 2,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        RectF rectF = new RectF(0, 0, radius * 2, radius * 2);
        paint.setColor(color);
        canvas.drawArc(rectF, 0, 360, true, paint);
        return bitmap;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stopLocation();//停止定位
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端。
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow = View.inflate(getApplicationContext(),R.layout.custon_info,null);
        render(marker, infoWindow);
        return infoWindow;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getApplicationContext(),"haha",Toast.LENGTH_LONG);
        Log.i("TAG","111");
        return true;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View infoContent = View.inflate(getApplicationContext(),R.layout.custon_info,null);
        render(marker, infoContent);
        return infoContent;
    }
    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(getApplicationContext(),"haha",Toast.LENGTH_LONG);
        Log.i("TAG","111");

    }

    public void render(Marker marker, View view) {
        MarkerOptions markerOptions = new MarkerOptions();
        Log.i("TAG","111");
        TextView tv_info = (TextView) view.findViewById(R.id.tv_info);
        tv_info.setText("hahahaha");
        Button bt_info = (Button) view.findViewById(R.id.bt_info);
        bt_info.setText("heiheihei");
        //markerOptions.title("当前位置");
        //markerOptions.position(new LatLng(lat, lon));
        //aMap.addMarker(markerOptions);
    }


    @Override
    public void onClick(Marker marker, List<ClusterItem> list) {
        Toast.makeText(getApplicationContext(),"haha",Toast.LENGTH_LONG);
        Log.i("TAG","111");

    }
}
