package com.example.p08_locatingaplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Side, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap ;

                if (map != null){
                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                LatLng downtownCore =  new LatLng( 1.295416599631411, 103.86085283827651);
                LatLng serangoon = new LatLng(1.3510765477411812, 103.87012575316815);
                LatLng eastCoast = new LatLng(1.3089197404107595, 103.905462077644);
                LatLng singapore = new LatLng(1.3521, 103.8198);


                map.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore,11));

                Marker central = map.addMarker(new MarkerOptions()
                        .position(downtownCore)
                        .title("HQ-Central")
                        .snippet("Downtown Core")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

                );

                Marker north = map.addMarker(new MarkerOptions()
                        .position(serangoon)
                        .title("North-HQ")
                        .snippet("332 Serangoon Ave 3, Block 332, Singapore 550332")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));

                Marker east = map.addMarker(new MarkerOptions()
                        .position(eastCoast)
                        .title("East-HQ")
                        .snippet("Tembeling Rd")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                            Toast.makeText(MainActivity.this,marker.getTitle(),Toast.LENGTH_SHORT).show();


                        return false;
                    }
                });


                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        // your code here
                        if (position == 0){
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore,11));
                        }
                        else if (position == 1) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(serangoon,15));
                        }
                        else if (position == 2) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(downtownCore,15));
                        }
                        else if (position == 3) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(eastCoast,15));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });

                UiSettings ui = map.getUiSettings();

                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);


                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED){
                    map.setMyLocationEnabled(true);
                }
                else{
                    Log.e("Gmap-Permission","Gps access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0 );
                }



            }
        });
    }
}