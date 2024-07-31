package com.example.myapplication;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myapplication.databinding.ActivityMyLocationBinding;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.Arrays;

import java.util.List;

public class MyLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMyLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng barbados = new LatLng(13.120495311230808, -59.60465391887053);
         mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(barbados, 22), 5000, null);
        mMap.addMarker(new MarkerOptions().position(barbados).title("India Lift's World Cup Here").icon(getBitmapDescriptor(R.drawable.departing_asset_map)));//.icon(BitmapDescriptorFactory.fromResource(R.drawable.departing_asset_map)));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(barbados));

        LatLng bcciHeadquaters = new LatLng(18.93744860039392, 72.82530438125876);
        mMap.addMarker(new MarkerOptions().position(bcciHeadquaters).title("BCCI Headquarters").icon(getBitmapDescriptor(R.drawable.home_asset_location)));
        mMap.addCircle(new CircleOptions().radius(1000).center(barbados)
                .strokeWidth(1f)
                .fillColor(0x550000FF));
        mMap.addCircle(new CircleOptions().radius(1000).center(bcciHeadquaters)
                .strokeWidth(1f)
                .fillColor(0x550000FF));
        addPatternedPolyline(mMap, barbados, bcciHeadquaters);

    }

    private BitmapDescriptor getBitmapDescriptor(@DrawableRes int id) {
        Drawable vectorDrawable = ContextCompat.getDrawable(this, id);
        if (vectorDrawable == null) {
            return null;
        }
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);

    }

    public void addPatternedPolyline(GoogleMap mMap, LatLng barbados, LatLng bcciHeadquaters) {
        // Define the polyline pattern
        List<PatternItem> pattern = getPattern();

        // Add the polyline to the map with the specified pattern
        Polyline polyline = mMap.addPolyline(new PolylineOptions()
                .add(barbados, bcciHeadquaters)
                .width(10)
                .color(Color.BLUE)
                .pattern(pattern));  // Apply the pattern
    }

    private List<PatternItem> getPattern() {
        // Create a pattern with alternating dashes and gaps
        return Arrays.asList(
                new Dash(20), // Dash of 20 pixels
                new Gap(20)   // Gap of 20 pixels
        );
    }
}