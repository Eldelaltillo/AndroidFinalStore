package com.example.proyectofinal;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private Location currentLocation;
    private FusedLocationProviderClient fusedClient;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_maps);

        fusedClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        fusedClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        currentLocation = location;
                        onLocationReceived();
                    }
                });
    }

    private void onLocationReceived() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        LatLng mapFake = new LatLng(4.64732885178249,-74.1219614245784);

        // Obtener la imagen personalizada del marcador y ajustar su tamaño
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_ubicacion);
        int widthInPixels = 200; // Ancho deseado en píxeles
        int heightInPixels = 200; // Alto deseado en píxeles
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, widthInPixels, heightInPixels, false);

        // Convertir el Bitmap escalado en un BitmapDescriptor
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(scaledBitmap);

        MarkerOptions fakeMarkerOptions = new MarkerOptions()
                .position(mapFake)
                .title("Marcar la ubicación")
                .icon(icon);

        gMap.addMarker(fakeMarkerOptions);

        if (currentLocation != null) {
            LatLng currentLoc = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

            // Obtener la imagen personalizada del marcador y ajustar su tamaño
            Bitmap originalBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.logo_ubicacion2);
            int widthInPixels2 = 200; // Ancho deseado en píxeles
            int heightInPixels2 = 200; // Alto deseado en píxeles
            Bitmap scaledBitmap2 = Bitmap.createScaledBitmap(originalBitmap2, widthInPixels2, heightInPixels2, false);

            // Convertir el Bitmap escalado en un BitmapDescriptor
            BitmapDescriptor icon2 = BitmapDescriptorFactory.fromBitmap(scaledBitmap2);

            MarkerOptions currentMarkerOptions = new MarkerOptions()
                    .position(currentLoc)
                    .title("Ubicación actual")
                    .icon(icon2);

            gMap.addMarker(currentMarkerOptions);

            // Crear un LatLngBounds que incluya ambos marcadores
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(currentLoc);
            builder.include(mapFake);
            LatLngBounds bounds = builder.build();

            // Configurar el límite de la cámara para que incluya ambos marcadores con un relleno personalizado
            int padding = 300; // Espacio adicional en píxeles alrededor de los límites de los marcadores
            CameraUpdateFactory.newLatLngBounds(bounds, padding);

            // Mover la cámara con el límite configurado
            gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
        } else {
            // Manejar la situación cuando la ubicación actual es nula
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation();
            }
        }
    }
}
