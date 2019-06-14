package androva.com.rent_a_room;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.MobileAds;
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

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import uk.co.senab.photoview.PhotoViewAttacher;

public class Roomdetail extends AppCompatActivity
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
    Button smsb, callb, emailb;
    TextView navheadname, navheademail, priceamnt, roomamnt, toiletamnt, location, flooramnt, type,pref,month,descriptiontv,water, brodband, wify, lift, genarator, gass, cc_tv, secuirity_guird, drawing, dining;
    TextView headtv,headtv2,draername,draweremail;
    public static final String ROOM_URL = "http://paperliver.000webhostapp.com/roomdetails1.php";
    List<Roomdetailsinfo> mdataset2;
    private String KEY_EMAIL = "email";
    private String KEY_CATAGORY = "catagory";
    LayoutInflater ly;
    private String KEY_PRICE = "price";
    GoogleApiClient client;
    RelativeLayout rl;
  final   InterstitialAd mInterstitialAd = new InterstitialAd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomdetail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//layout initialize
        int i = 0;

        latLng = new LatLng(Double.parseDouble(Roomdetailsinfo.lat), Double.parseDouble(Roomdetailsinfo.lan));
        Roomdetailsinfo.latLng = new LatLng(Double.parseDouble(Roomdetailsinfo.lat), Double.parseDouble(Roomdetailsinfo.lan));

        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");


        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        priceamnt = (TextView) findViewById(R.id.priceamount);
        roomamnt = (TextView) findViewById(R.id.bedroomamount);
        toiletamnt = (TextView) findViewById(R.id.toiletamnt);
        flooramnt = (TextView) findViewById(R.id.flooramnt);
        type = (TextView) findViewById(R.id.type);
        location = (TextView) findViewById(R.id.locationdetail);
        pref = (TextView) findViewById(R.id.prefshow);
        month = (TextView) findViewById(R.id.monthshow);
        descriptiontv = (TextView) findViewById(R.id.descriptiontv);

        water = (TextView) findViewById(R.id.water);
        cc_tv = (TextView) findViewById(R.id.cctv);
        dining = (TextView) findViewById(R.id.dining);
        drawing = (TextView) findViewById(R.id.drawing);
        brodband = (TextView) findViewById(R.id.broadband);
        secuirity_guird = (TextView) findViewById(R.id.securityguird);
        lift =(TextView) findViewById(R.id.lift);
        wify = (TextView) findViewById(R.id.wifi);
        genarator =(TextView) findViewById(R.id.generator);
        gass = (TextView) findViewById(R.id.gas);
        ib1 = (ImageButton) findViewById(R.id.roompict1);
        ib2 = (ImageButton) findViewById(R.id.roompict2);
        ib3 = (ImageButton) findViewById(R.id.roompict3);
        smsb = (Button) findViewById(R.id.smsbut);
        callb = (Button) findViewById(R.id.callbut);
        emailb = (Button) findViewById(R.id.emailbut);
        rl = (RelativeLayout) findViewById(R.id.relatv);

        location.setText(Roomdetailsinfo.getLocation().toString());
        priceamnt.setText(Roomdetailsinfo.getPrice().toString());
        toiletamnt.setText(Roomdetailsinfo.toiletnum.toString());
        type.setText(Roomdetailsinfo.type.toString());
        roomamnt.setText(Roomdetailsinfo.roomnum.toString());
        flooramnt.setText(Roomdetailsinfo.floor.toString());
        pref.setText(Roomdetailsinfo.pref.toString());
        month.setText(Roomdetailsinfo.month.toString());
        descriptiontv.setText(Roomdetailsinfo.description.toString());
        if(Boolean.parseBoolean(Roomdetailsinfo.water))
        {
            water.setVisibility(View.VISIBLE);
        }
        if(Boolean.parseBoolean(Roomdetailsinfo.gass))
        {
            gass.setVisibility(View.VISIBLE);
        };
        if(Boolean.parseBoolean(Roomdetailsinfo.lift))
        {
            lift.setVisibility(View.VISIBLE);
        };
        if(Boolean.parseBoolean(Roomdetailsinfo.dining))
        {
            dining.setVisibility(View.VISIBLE);
        };
        if(Boolean.parseBoolean(Roomdetailsinfo.drawing))
        {
            drawing.setVisibility(View.VISIBLE);
        };
        if(Boolean.parseBoolean(Roomdetailsinfo.lift))
        {
            lift.setVisibility(View.VISIBLE);
        };
        if(Boolean.parseBoolean(Roomdetailsinfo.gerator))
        {
            genarator.setVisibility(View.VISIBLE);
        };
        if(Boolean.parseBoolean(Roomdetailsinfo.secuirity_guird))
        {
            secuirity_guird.setVisibility(View.VISIBLE);
        };
        if(Boolean.parseBoolean(Roomdetailsinfo.brodband))
        {
            brodband.setVisibility(View.VISIBLE);
        };
        Glide.with(ib1.getContext()).load(Roomdetailsinfo.pict_url).into(ib1);
        Glide.with(ib2.getContext()).load(Roomdetailsinfo.pict_url2).into(ib2);
        Glide.with(ib3.getContext()).load(Roomdetailsinfo.pict_url3).into(ib3);
        CustomScrollView myScrollView = (CustomScrollView) findViewById(R.id.idScrollView);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Roomdetail.this, Pictshow.class);
                intent.putExtra("picnum",1);
                startActivity(intent);


            }
        });
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Roomdetail.this, Pictshow.class);
                intent.putExtra("picnum",2);
                startActivity(intent);


            }
        });
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Roomdetail.this, Pictshow.class);
                intent.putExtra("picnum",3);
                startActivity(intent);


            }
        });
        smsb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(Roomdetail.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(Roomdetail.this);
                }
                builder.setTitle(Roomdetailsinfo.phonenumber)
                        .setMessage("Are you sure you want call this number?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                                smsIntent.setType("vnd.android-dir/mms-sms");
                                smsIntent.putExtra("address", Roomdetailsinfo.phonenumber);

                                startActivity(smsIntent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();




            }
        });
        callb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(Roomdetail.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(Roomdetail.this);
                }
                builder.setTitle(Roomdetailsinfo.phonenumber)
                        .setMessage("Are you sure you want call this number?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_CALL);

                                intent.setData(Uri.parse("tel:" + Roomdetailsinfo.phonenumber));

                                if (ActivityCompat.checkSelfPermission(Roomdetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();



            }
        });
        emailb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mailto = User.email +
                        "?cc=" +Roomdetailsinfo.email ;

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));

                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    //TODO: Handle case where no email app is available
                }

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
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                Intent intent = new Intent(Roomdetail.this, Roomlist.class);
                startActivity(intent);

            }

        });
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
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
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


    private void getdata() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOM_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        Parseadddetails pj = new Parseadddetails(response);
                        pj.parseJSON();





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
                params.put(KEY_EMAIL, email);




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
            Intent intent = new Intent(Roomdetail.this, Help.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            if (User.email.equals("null")) {
                Intent intent = new Intent(Roomdetail.this, Signup.class);
                startActivity(intent);
                return true;
            }
            else {
                Intent intent = new Intent(Roomdetail.this, Profile.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_rateus) {

        }  else if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(Roomdetail.this, About.class);
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
