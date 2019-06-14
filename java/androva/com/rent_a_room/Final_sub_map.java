package androva.com.rent_a_room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Final_sub_map extends AppCompatActivity  implements  GoogleMap.InfoWindowAdapter,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,GoogleMap.OnMarkerClickListener {


    LocationRequest mLocationRequest;
    SupportMapFragment mFragment;
    Marker currLocationMarker;
    Toolbar toolbar;
    MarkerOptions markerOptions;
    LatLng latLng;
    GoogleApiClient client;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;

    private Bitmap bitmap,propicbitmap;
public EditText phn;
    //  private FusedLocationProviderClient mFusedLocationProviderClient;
    private Aoutocompleteadapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private String KEY_PRICE = "price";
    private String KEY_DESCRIPTION = "description";
    private String KEY_FLOOR = "floor";
    public static final String ROOM_URL = "http://ratulexpo.esy.es/roominsert1.php";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));
     ProgressDialog loading;
    private static final String TAG = "Final_sub_map";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    private TextView mNameView;
    private ProgressBar mProgressBar;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    FloatingActionButton fab;
    private StorageTask mUploadTask;
    private GoogleApiClient mGoogleApiClient1;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_sub_map);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapform);
        mapFragment.getMapAsync(this);
         fab = (FloatingActionButton) findViewById(R.id.fabb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(Final_sub_map.this, Roomdetailsinfo.type, Toast.LENGTH_LONG).show();
                try {
                    loading = ProgressDialog.show(Final_sub_map.this, "Uploading...", "Please wait...", false, false);
                    Toast.makeText(Final_sub_map.this, "Uploa", Toast.LENGTH_LONG).show();
                    uploadFile(Roomdetailsinfo.bitmap1,1);
                    uploadFile(Roomdetailsinfo.bitmap2,2);
                    uploadFile(Roomdetailsinfo.bitmap3,3);
                } catch (Exception e) {

                }

            }
        });
        //auto complete
       mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        mAutocompleteTextView.setThreshold(3);
        mNameView = (TextView) findViewById(R.id.name);

phn = (EditText) findViewById(R.id.editText3);

       mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);
    }
    private void setdata() {
        //Showing the progress dialog

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOM_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        Toast.makeText(Final_sub_map.this, response, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Final_sub_map.this, Roomlist.class);
                        startActivity(intent);




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        String body = "";
                        //get status code here
                        String statusCode = String.valueOf(error.networkResponse.statusCode);
                        //get response body and parse with appropriate encoding
                        if(error.networkResponse.data!=null) {
                            try {
                                body = new String(error.networkResponse.data, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        //Showing toast
                        Toast.makeText(Final_sub_map.this, body+" ", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                //   Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = Calendar.getInstance();
                String date = sdf.format(c.getTime());

                bitmap =  BitmapFactory.decodeResource(getResources(),
                        R.drawable.homeup);
                Roomdetailsinfo.location = mAutocompleteTextView.getText().toString();
                Roomdetailsinfo.lat = latLng.latitude+"";
                Roomdetailsinfo.lan = latLng.longitude+"";
                try {
                    Roomdetailsinfo.phonenumber = phn.getText().toString().trim();
                }
                catch (Exception e)
                {

                }
                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("email", User.email);
                params.put("location", Roomdetailsinfo.location);
                params.put("price", Roomdetailsinfo.price);
                params.put("description", Roomdetailsinfo.description);
                params.put("floor", Roomdetailsinfo.floor);
                params.put("catagory", Roomdetailsinfo.type);
                params.put("date", date);
                params.put("toiletnum", Roomdetailsinfo.toiletnum);
                params.put("pict_url", Roomdetailsinfo.pict_url);
                params.put("pict_url2", Roomdetailsinfo.pict_url2);
                params.put("pict_url3", Roomdetailsinfo.pict_url3);
                params.put("roomnum", Roomdetailsinfo.roomnum);
                params.put("lift", Roomdetailsinfo.lift);
                params.put("cctvsecurity", Roomdetailsinfo.cc_tv);
                params.put("broadband", Roomdetailsinfo.brodband);
                params.put("phonenumber", Roomdetailsinfo.phonenumber);
                params.put("lat", Roomdetailsinfo.lat);
                params.put("lan", Roomdetailsinfo.lan);
                params.put("ad_id", User.email+ Roomdetailsinfo.phonenumber+date+" "+System.currentTimeMillis());
                params.put("pref", Roomdetailsinfo.pref);
                params.put("month", Roomdetailsinfo.month);



                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);

    }
    @Override
    public void onBackPressed() {

            Intent intent = new Intent(Final_sub_map.this, Roomlist.class);
            startActivity(intent);

    }
    private void uploadFile(Bitmap bitmapup, final int h) {

        StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                +"ratul");
        Toast.makeText(Final_sub_map.this, "3rd fuck", Toast.LENGTH_LONG).show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmapup.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        Toast.makeText(Final_sub_map.this, "2nd fuck", Toast.LENGTH_LONG).show();
        mUploadTask = fileReference.putBytes(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        Toast.makeText(Final_sub_map.this, "Upload successful", Toast.LENGTH_LONG).show();

                        if (h==1)
                        {
                            Roomdetailsinfo.pict_url = taskSnapshot.getDownloadUrl().toString();
                        }
                      else   if (h==2)
                        {
                            Roomdetailsinfo.pict_url2 = taskSnapshot.getDownloadUrl().toString();
                        }
                       else if (h==3)
                        {
                            Roomdetailsinfo.pict_url3 = taskSnapshot.getDownloadUrl().toString();
                            setdata();
                        }



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Final_sub_map.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        mProgressBar.setProgress((int) progress);
                    }
                });

    }
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            mMap.setMyLocationEnabled(true);
            buildGoogleApiClient();

            mGoogleApiClient.connect();
        } catch (Exception e) {

        }


        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerDragListener(this);
        mMap.setOnInfoWindowClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://androva.com.rent_a_room/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://androva.com.rent_a_room/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    //For current location point
    protected synchronized void buildGoogleApiClient() {
        //  Toast.makeText(this, "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    protected synchronized void buildGoogleApiClient2() {
        //  Toast.makeText(this, "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient1 = new GoogleApiClient.Builder(Final_sub_map.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
    }
    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            double latitude = latLng.latitude;
            double longitude = latLng.longitude;
            markerOptions = new MarkerOptions();
            markerOptions.position(latLng);

            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            }
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(23.798337, 90.423308), new LatLng(23.791312, 90.424680))
                    .width(5)
                    .color(Color.RED));
            line.setVisible(true);
            String address = addresses.get(0).getAddressLine(0);
            markerOptions.title(address);
            markerOptions.draggable(true);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currLocationMarker = mMap.addMarker(markerOptions);
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(60000); //5 seconds
        mLocationRequest.setFastestInterval(60000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter


        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        try {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng).zoom(14).build();
            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        } catch (Exception e) {
            latLng = new LatLng(51.5, -.1277);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng).zoom(14).build();
            markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.draggable(true);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currLocationMarker = mMap.addMarker(markerOptions);
            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }


        try {
            currLocationMarker.setSnippet("Send reqst");
            currLocationMarker.showInfoWindow();
        } catch (Exception e) {
            Toast.makeText(this, "location error", Toast.LENGTH_SHORT).show();
        }

        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(TAG, "Google Places API connected.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "onConnectionSuspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }

    public void onSearch(String location) {
        //  EditText search= (EditText) findViewById(R.id.editText);
        // String location=search.getText().toString();
        List<Address> addressList = null;
        if (location != null || location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                Toast.makeText(this, "searcherror", Toast.LENGTH_SHORT).show();
                e.printStackTrace();

            }
        }
        Address address = addressList.get(0);
        LatLng latlang1 = new LatLng(address.getLatitude(), address.getLongitude());
        currLocationMarker.setPosition(latlang1);
latLng= latlang1;

        mMap.animateCamera(CameraUpdateFactory
                .newLatLng(latlang1));
        currLocationMarker.setSnippet("Send reqst");
        currLocationMarker.showInfoWindow();

        double latitude = latLng.latitude;
        double longitude = latLng.longitude;

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());
        Toast.makeText(this, "drag end", Toast.LENGTH_SHORT).show();

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address1 = addresses.get(0).getAddressLine(0);
        markerOptions.title(address1 + "/nSend reqst");

        currLocationMarker.setTitle(address1);

        currLocationMarker.setSnippet("Send reqst");
        currLocationMarker.showInfoWindow();
    }

    @Override
    public void onLocationChanged(Location location) {

        //place marker at current position
        //mGoogleMap.clear();
      /*  if (currLocationMarker != null) {
            currLocationMarker.remove();
        }*/
        // latLng = new LatLng(location.getLatitude(), location.getLongitude());
       /* MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);

        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        currLocationMarker = mMap.addMarker(markerOptions);

        Toast.makeText(this,"Location Changed", Toast.LENGTH_SHORT).show();*/

        //zoom to current position:
       /* CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(14).build();

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));*/

        //If you only need one location, unregister the listener
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
/*        Intent intent=new Intent(MapsActivity.this,Reqst.class);
        startActivity(intent);*/
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Toast.makeText(this, "drag end", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        latLng = marker.getPosition();
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());
        Toast.makeText(this, "drag end", Toast.LENGTH_SHORT).show();

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0);
        markerOptions.title(address + "/nSend reqst");

        currLocationMarker.setTitle(address);

        currLocationMarker.setSnippet("Send reqst");
        currLocationMarker.showInfoWindow();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        // Intent intent=new Intent(MapsActivity.this,Reqst.class);
        //  startActivity(intent);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }


    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }


    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();
            Roomdetailsinfo.latLng = place.getLatLng();
            Roomdetailsinfo.lat = Roomdetailsinfo.latLng.latitude+"";
            Roomdetailsinfo.lan = Roomdetailsinfo.latLng.longitude+"";
            mNameView.setText(Html.fromHtml(place.getAddress() + ""));
onSearch(place.getAddress()+"");

        }
    };


}
