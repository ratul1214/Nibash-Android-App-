package androva.com.rent_a_room;

import android.*;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.google.android.gms.common.api.Status;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Postact extends AppCompatActivity
        implements View.OnClickListener,  NavigationView.OnNavigationItemSelectedListener{
    PlaceAutocompleteFragment autocompleteFragment;
int criti=0;
    LocationRequest mLocationRequest;
    SupportMapFragment mFragment;
    Marker currLocationMarker;
    Toolbar toolbar;
    MarkerOptions markerOptions;
    LatLng latLng;
    private int PICK_IMAGE_REQUEST = 1;
   public static Bitmap bitm,propicbitmap, resized;
ImageButton roompict1, roompict2, roompict3,roompict;
Spinner typespin,monthspin;
    EditText price, bedroom, toilet, floor, utilitybills,phonenumber,email,description;
    CheckBox water,brodband,wify , lift,genarator,gass,cc_tv,secuirity_guird , drawing, dining,male,female,family,jobholder;
    GoogleApiClient client;
    public static final String ROOM_URL = "http://paperliver.com/roomdetails.php";
    List<Roomdetailsinfo> mdataset2;
    private String KEY_EMAIL = "email";
    private String KEY_TYPE = "catagory";
    private String KEY_PRICE = "price";
    private String KEY_PHONENUM = "phonenum";
    private String KEY_ROOMNUM = "roomnum";
    private String KEY_TOILETNUM = "toiletnum";
    private String KEY_FLOOR = "floor";
    private String KEY_BILLS = "bills";
    private String KEY_LOCATION = "location";
    private static final String TAG = "Postact";
    Bitmap bitmap;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
 //  private FusedLocationProviderClient mFusedLocationProviderClient;
    private Aoutocompleteadapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));

    private Uri mImageUri;



    //widgets
    private AutoCompleteTextView mSearchText;
    private ImageView mGps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        typespin = (Spinner) findViewById(R.id.typespinnerform);

        monthspin = (Spinner) findViewById(R.id.monthspinnerform);
        roompict1 = (ImageButton) findViewById(R.id.roompict1);
        roompict2 = (ImageButton) findViewById(R.id.roompict2);
        roompict3 = (ImageButton) findViewById(R.id.roompict3);
        roompict1.setOnClickListener(this);
        roompict2.setOnClickListener(this);
        roompict3.setOnClickListener(this);
        price = (EditText) findViewById(R.id.priceform);
        bedroom = (EditText) findViewById(R.id.bedroomform);
        toilet = (EditText) findViewById(R.id.toiletform);
        floor = (EditText) findViewById(R.id.floorform);
        utilitybills = (EditText) findViewById(R.id.billsform);
        description = (EditText) findViewById(R.id.descriptionform);
        water = (CheckBox) findViewById(R.id.waterform);
        cc_tv = (CheckBox) findViewById(R.id.cctvform);
        dining = (CheckBox) findViewById(R.id.diningform);
        drawing = (CheckBox) findViewById(R.id.drawingform);
        brodband = (CheckBox) findViewById(R.id.broadbandform);
        male = (CheckBox) findViewById(R.id.male);
        female = (CheckBox) findViewById(R.id.female);
        family = (CheckBox) findViewById(R.id.family);
        jobholder = (CheckBox) findViewById(R.id.jobholder);
        secuirity_guird = (CheckBox) findViewById(R.id.csecuirityguirdform);
        lift = (CheckBox) findViewById(R.id.liftform);
        wify = (CheckBox) findViewById(R.id.wifiform);
        genarator = (CheckBox) findViewById(R.id.generatorform);
        gass = (CheckBox) findViewById(R.id.gasform);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typespin.setAdapter(adapter);

        ArrayAdapter<CharSequence> monthadapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        monthspin.setAdapter(monthadapter);

        if (!User.name.contains("null")&& !User.email.contains("null")) {
            NavigationView navigationV = (NavigationView) findViewById(R.id.nav_view);
            View headerView = navigationV.getHeaderView(0);
            TextView navUsername = (TextView) headerView.findViewById(R.id.drawername);
            navUsername.setText(User.name);
            TextView navUseremail = (TextView) headerView.findViewById(R.id.draweremail);
            navUseremail.setText(User.email);
        }

typespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Roomdetailsinfo.type = typespin.getSelectedItem().toString().trim();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});

        monthspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // Roomdetailsinfo.type = typespin.getSelectedItem().toString().trim();
                Roomdetailsinfo.month = monthspin.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           try {
                    Roomdetailsinfo.bills = utilitybills.getText().toString().trim();
                }
                catch (Exception e)
                {

                }
                try {
                    Roomdetailsinfo.description = description.getText().toString().trim();
                }
                catch (Exception e)
                {

                }
                try {
                    Roomdetailsinfo.email = email.getText().toString().trim();
                }
                catch (Exception e)
                {

                }

                try {
                    Roomdetailsinfo.toiletnum = toilet.getText().toString().trim();
                }
                catch (Exception e)
                {

                }
                try {
                    Roomdetailsinfo.roomnum = bedroom.getText().toString().trim();
                }
                catch (Exception e)
                {

                }
                try {
                    Roomdetailsinfo.price = price.getText().toString().trim();
                }
                catch (Exception e)
                {

                }
                try {
                    Roomdetailsinfo.phonenumber = phonenumber.getText().toString().trim();
                }
                catch (Exception e)
                {

                }
                try {
                    Roomdetailsinfo.floor = floor.getText().toString().trim();
                }
                catch (Exception e)
                {

                }


               try {





                   Roomdetailsinfo.date = "";
                   Roomdetailsinfo.ad_id = Roomdetailsinfo.email + Roomdetailsinfo.date;
                   try {
                       Roomdetailsinfo.roompict1 = getStringImage(Roomdetailsinfo.bitmap1);
                       Roomdetailsinfo.roompict2 = getStringImage(Roomdetailsinfo.bitmap2);
                       Roomdetailsinfo.roompict3 = getStringImage(Roomdetailsinfo.bitmap3);

                   } catch (Exception e) {
                       e.printStackTrace();
                   }
                   if (water.isChecked()) {
                       Roomdetailsinfo.water = "true";
                   }
                   if (gass.isChecked()) {
                       Roomdetailsinfo.gass = "true";
                   }
                   if (brodband.isChecked()) {
                       Roomdetailsinfo.brodband = "true";
                   }
                   if (drawing.isChecked()) {
                       Roomdetailsinfo.drawing = "true";
                   }
                   if (dining.isChecked()) {
                       Roomdetailsinfo.dining = "true";
                   }
                   if (wify.isChecked()) {
                       Roomdetailsinfo.wify = "true";
                   }
                   if (lift.isChecked()) {
                       Roomdetailsinfo.water = "true";
                   }
                   if (cc_tv.isChecked()) {
                       Roomdetailsinfo.cc_tv = "true";
                   }
                   if (genarator.isChecked()) {
                       Roomdetailsinfo.gerator = "true";
                   }
                   if (male.isChecked()) {
                       if(Roomdetailsinfo.pref.equals("null"))
                       {
                           Roomdetailsinfo.pref = "male,";
                       }
                       else {
                           Roomdetailsinfo.pref = Roomdetailsinfo.pref + "male,";
                       }
                   }
                   if (female.isChecked()) {
                       if (Roomdetailsinfo.pref.equals("null")) {
                           Roomdetailsinfo.pref = "female";
                       } else {
                           Roomdetailsinfo.pref = Roomdetailsinfo.pref + ",female";
                       }
                   }
                   if (family.isChecked()) {
                       if(Roomdetailsinfo.pref.equals("null"))
                       {
                           Roomdetailsinfo.pref = "family";
                       }
                       else {

                           Roomdetailsinfo.pref = Roomdetailsinfo.pref + ",family";
                       }
                   }
                   if (jobholder.isChecked()) {
                       if (Roomdetailsinfo.pref.equals("null")) {
                           Roomdetailsinfo.pref = "jobholder";
                       } else {

                           Roomdetailsinfo.pref = Roomdetailsinfo.pref + ",jobholder";
                       }
                   }


               }
               catch (Exception e)
               {

               }

               /* Snackbar.make(view, "Replace with your own action::"+ date, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = Calendar.getInstance();
                String date = sdf.format(c.getTime());
                         */

                Intent intent = new Intent(Postact.this, Final_sub_map.class);
                startActivity(intent);


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(roompict1);
        }
    }*/

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Postact.this, Roomlist.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.postact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_help) {
            Intent intent = new Intent(Postact.this, Help.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            if (User.email.equals("null")) {
                Intent intent = new Intent(Postact.this, Signup.class);
                startActivity(intent);
                return true;
            }
            else {
                Intent intent = new Intent(Postact.this, Profile.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_rateus) {

        }  else if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(Postact.this, About.class);
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



    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                bitmap = Bitmap.createScaledBitmap(bitmap, 300, 400, true);

                roompict.setImageBitmap(bitmap);
                if (roompict == findViewById(R.id.roompict1)) {
                    Roomdetailsinfo.bitmap1 = bitmap;
                }
               else if (roompict == findViewById(R.id.roompict2)) {
                    Roomdetailsinfo.bitmap2 = bitmap;
                }
               else if (roompict == findViewById(R.id.roompict3)) {
                    Roomdetailsinfo.bitmap3 = bitmap;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onClick(View view) {
        if (view == findViewById(R.id.roompict1)) {


            bitmap = Roomdetailsinfo.bitmap1;
            roompict = roompict1;

            showFileChooser();




        }
        else   if (view == findViewById(R.id.roompict2)) {

            roompict = roompict2;
            bitmap = Roomdetailsinfo.bitmap2;

            showFileChooser();


                }



        else if (view == findViewById(R.id.roompict3)) {

            roompict = roompict3;
            bitmap = Roomdetailsinfo.bitmap3;

            showFileChooser();


                }


            }

}
