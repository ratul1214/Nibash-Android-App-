package androva.com.rent_a_room;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Adddetailprof extends AppCompatActivity
        implements GoogleMap.InfoWindowAdapter,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener, NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMarkerClickListener {
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    SupportMapFragment mFragment;
    Marker currLocationMarker;
    Toolbar toolbar;
    MarkerOptions markerOptions;
    public LatLng latLng = new LatLng(51.5, -.1277);
    private GoogleMap mMap;
    ImageButton ib1, ib2, ib3;
    Button smsb,verify;
    TextView navheadname, navheademail, priceamnt, roomamnt, toiletamnt, location, flooramnt, type;
    CheckBox water, brodband, wify, lift, genarator, gass, cc_tv, secuirity_guird, drawing, dining;
    public static final String ROOM_URL = "http://ratulexpo.esy.es/roomdelet.php";
    List<Roomdetailsinfo> mdataset2;
    private String KEY_EMAIL = "email";
    private String KEY_CATAGORY = "catagory";
    LayoutInflater ly;
    private String KEY_PRICE = "price";
    GoogleApiClient client;
    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddetailprof);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//layout initialize
        int i = 0;
        latLng = new LatLng(Double.parseDouble(Roomdetailsinfo.lat), Double.parseDouble(Roomdetailsinfo.lan));
        Roomdetailsinfo.latLng = new LatLng(Double.parseDouble(Roomdetailsinfo.lat), Double.parseDouble(Roomdetailsinfo.lan));

        priceamnt = (TextView) findViewById(R.id.priceamount);
        roomamnt = (TextView) findViewById(R.id.bedroomamount);
        toiletamnt = (TextView) findViewById(R.id.toiletamnt);
        flooramnt = (TextView) findViewById(R.id.flooramnt);
        type = (TextView) findViewById(R.id.type);
        location = (TextView) findViewById(R.id.locationdetail);
        water = (CheckBox) findViewById(R.id.water);
        cc_tv = (CheckBox) findViewById(R.id.cctv);
        dining = (CheckBox) findViewById(R.id.dining);
        drawing = (CheckBox) findViewById(R.id.drawing);
        brodband = (CheckBox) findViewById(R.id.broadband);
        secuirity_guird = (CheckBox) findViewById(R.id.csecuirityguird);
        lift = (CheckBox) findViewById(R.id.lift);
        wify = (CheckBox) findViewById(R.id.wifi);
        genarator = (CheckBox) findViewById(R.id.generator);
        gass = (CheckBox) findViewById(R.id.gas);
        ib1 = (ImageButton) findViewById(R.id.roompict1);
        ib2 = (ImageButton) findViewById(R.id.roompict2);
        ib3 = (ImageButton) findViewById(R.id.roompict3);
        smsb = (Button) findViewById(R.id.callbut);
verify  = (Button) findViewById(R.id.veryfy);
        rl = (RelativeLayout) findViewById(R.id.relatv);

        location.setText(Roomdetailsinfo.getLocation().toString());
        priceamnt.setText(Roomdetailsinfo.getPrice().toString());
        toiletamnt.setText(Roomdetailsinfo.toiletnum.toString());
        type.setText(Roomdetailsinfo.type.toString());
        roomamnt.setText(Roomdetailsinfo.roomnum.toString());
        flooramnt.setText(Roomdetailsinfo.floor.toString());
        water.setChecked(Boolean.parseBoolean(Roomdetailsinfo.water));
        gass.setChecked(Boolean.parseBoolean(Roomdetailsinfo.gass));
        lift.setChecked(Boolean.parseBoolean(Roomdetailsinfo.lift));
        dining.setChecked(Boolean.parseBoolean(Roomdetailsinfo.dining));
        drawing.setChecked(Boolean.parseBoolean(Roomdetailsinfo.drawing));
        water.setChecked(Boolean.parseBoolean(Roomdetailsinfo.lift));
        genarator.setChecked(Boolean.parseBoolean(Roomdetailsinfo.gerator));
        secuirity_guird.setChecked(Boolean.parseBoolean(Roomdetailsinfo.secuirity_guird));
        brodband.setChecked(Boolean.parseBoolean(Roomdetailsinfo.brodband));
        Glide.with(ib1.getContext()).load(Roomdetailsinfo.pict_url).into(ib1);
        Glide.with(ib2.getContext()).load(Roomdetailsinfo.pict_url2).into(ib2);
        Glide.with(ib3.getContext()).load(Roomdetailsinfo.pict_url3).into(ib3);
        CustomScrollView myScrollView = (CustomScrollView) findViewById(R.id.idScrollView);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Adddetailprof.this, Pictshow.class);
                startActivity(intent);


            }
        });
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Adddetailprof.this, Pictshow.class);
                startActivity(intent);


            }
        });
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Adddetailprof.this, Pictshow.class);
                startActivity(intent);


            }
        });
        smsb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


deletdata();

            }
        });
verify.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Adddetailprof.this, Verification.class);
        startActivity(intent);
    }
});
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (!User.name.contains("null")&& !User.email.contains("null")) {
            NavigationView navigationV = (NavigationView) findViewById(R.id.nav_view);
            View headerView = navigationV.getHeaderView(0);
            TextView navUsername = (TextView) headerView.findViewById(R.id.drawername);
            navUsername.setText(User.name);
            TextView navUseremail = (TextView) headerView.findViewById(R.id.draweremail);
            navUseremail.setText(User.email);
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.roomdetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }


    private void deletdata() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOM_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Disimissing the progress dialog
                        loading.dismiss();

                        Toast.makeText(Adddetailprof.this, response.toString(), Toast.LENGTH_LONG).show();




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        // Toast.makeText(Roomdetail.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                //   Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);



                Intent intent = getIntent();
                String email = intent.getStringExtra("email");



                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("ad_id", Roomdetailsinfo.ad_id);




                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);

    }
    protected synchronized void buildGoogleApiClient() {
        //  Toast.makeText(this, "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_help) {
            Intent intent = new Intent(Adddetailprof.this, Help.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            if (User.email.equals("null")) {
                Intent intent = new Intent(Adddetailprof.this, Signup.class);
                startActivity(intent);
                return true;
            }
            else {
                Intent intent = new Intent(Adddetailprof.this, Profile.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_rateus) {

        }  else if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(Adddetailprof.this, About.class);
            startActivity(intent);
        } else if (id == R.id.nav_help) {

        }
        else if (id == R.id.logout) {
            try {
                Profile.dropptable();
            } catch (Exception e) {
                e.printStackTrace();
            }
            User.pass = "null";
            User.name = "null";
            User.email = "null";



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }







        latLng=Roomdetailsinfo.latLng;
        latLng = new LatLng(Double.parseDouble(Roomdetailsinfo.lat), Double.parseDouble(Roomdetailsinfo.lan));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(16).build();
        markerOptions=new MarkerOptions();
        markerOptions.position(latLng);

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        currLocationMarker = mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));




    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        latLng =new LatLng(Double.parseDouble(Roomdetailsinfo.lat), Double.parseDouble(Roomdetailsinfo.lan));
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


            buildGoogleApiClient();
            mGoogleApiClient.connect();
        }
        catch (Exception e)
        {

        }


        // Set a listener for marker click.


    }
}
