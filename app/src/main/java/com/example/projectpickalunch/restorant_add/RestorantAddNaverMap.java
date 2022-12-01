package com.example.projectpickalunch.restorant_add;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.projectpickalunch.restaurant_search.Search;
import com.naver.maps.geometry.LatLng;
import com.example.projectpickalunch.R;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class RestorantAddNaverMap extends AppCompatActivity implements
        OnMapReadyCallback {

    private  double lat, lon;

    AppCompatButton test2;
    EditText restorant_address_edit_text;

    List<Address> address=null;



    private  NaverMap naverMap;
    private  FusedLocationSource locationSource;
    private  static  final  int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private  static  final  String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    List<LatLng> IsLatLng = new ArrayList<>();

    //재명
    TextView txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restorant_add_naver_map);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map_fragment);
        if (mapFragment == null){
            fm.beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        //주소값 EditText에 입력
        test2 = (AppCompatButton) findViewById(R.id.test2);
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == R.id.test2) {
                    // 데이터 첨부를하고 액티비티 실행
                    String temp = txt.getText().toString();
                    Intent intent01 = new Intent(getApplicationContext(), RestorantAdd.class);
                    intent01.putExtra("key01", temp);
                    startActivity(intent01);
                }
                if (view.getId() == R.id.test2) {
                    // 종료
                    finish();
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        naverMap.setLocationSource(locationSource); //현재위치 표시
        ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE);

        Marker marker = new Marker();
        LatLng latLng = new LatLng(
                naverMap.getCameraPosition().target.latitude,
                naverMap.getCameraPosition().target.longitude
        );
        marker.setPosition(latLng);


        NaverMap.OnCameraChangeListener onCameraChangeListener = new NaverMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(int i, boolean b) {
                Marker marker = new Marker();
                LatLng latLng = new LatLng(
                        naverMap.getCameraPosition().target.latitude,
                        naverMap.getCameraPosition().target.longitude
                );
                marker.setPosition(latLng);
                marker.setMap(naverMap);
            }
        };
//        naverMap.addOnCameraChangeListener(onCameraChangeListener);


        Geocoder g = new Geocoder(this);
        naverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(@NonNull Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                txt = (TextView) findViewById(R.id.test1);

                try {
                    address = g.getFromLocation(lat,lon,10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(address != null){
                    if(address.size()==0){
                        txt.setText("주소찾기 오류");
                    }else{
                        txt.setText(address.get(0).getAddressLine(0));

                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if(locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)){
           if(!locationSource.isActivated()){
               naverMap.setLocationTrackingMode(LocationTrackingMode.None);
               return;
           }else {
               naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
           }
       }
       super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

