package androva.com.rent_a_room;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
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
import com.google.android.gms.maps.model.LatLng;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Roomlist extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
    String catagory="All",location="All",prefer = "All";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Spinner typespinner,searchdata,prefspin;
    Button searchb;

    List<movie> mDataset;
    List<Adddetail> mdataset2;
    public static final String JSON_URL = "http://paperliver.com/student_account.php";
    public static final String ROOM_URL = "http://paperliver.000webhostapp.com/roomfulldetails1.php";
    public static final String ROOM_URL1 = "http://paperliver.000webhostapp.com/filterroom.php";
    private String KEY_LOCATION = "location";
    private String KEY_CATAGORY = "catagory";
    private String KEY_PRICE = "price";
    TextView headtv,headtv2,draername,draweremail;
    public static final String DATABASE_NAME = "userdata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomlist);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Profile.sdb = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        try {
            Profile.getdata();
        }
        catch (Exception e)
        {

        }

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/PrivaOnePro.ttf");

searchb = (Button) findViewById(R.id.filter);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //spiner code
draername = (TextView) findViewById(R.id.drawername);
        draweremail = (TextView) findViewById(R.id.draweremail);


        if (!User.name.contains("null")&& !User.email.contains("null")) {
            NavigationView navigationV = (NavigationView) findViewById(R.id.nav_view);
            View headerView = navigationV.getHeaderView(0);
            TextView navUsername = (TextView) headerView.findViewById(R.id.drawername);
            navUsername.setText(User.name);
            TextView navUseremail = (TextView) headerView.findViewById(R.id.draweremail);
            navUseremail.setText(User.email);
        }

        typespinner = (Spinner) findViewById(R.id.typespinner);
        searchdata = (Spinner) findViewById(R.id.searchedittext);
        prefspin = (Spinner) findViewById(R.id.prefsearchpinner);
        searchdata.setOnItemSelectedListener(this);
        typespinner.setOnItemSelectedListener(this);
        prefspin.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.city_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.pref_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        typespinner.setAdapter(adapter);
        searchdata.setAdapter(adapter2);
        prefspin.setAdapter(adapter3);


        searchb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchfun(ROOM_URL1);
            }
        });
//recycleview code
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Toast.makeText(getApplicationContext(), "Single Click on position        :"+position,
                        Toast.LENGTH_SHORT).show();


                Roomdetailsinfo.floor = mdataset2.get(position).getFloor_number();;
                Roomdetailsinfo.price = mdataset2.get(position).getPrice();;
                Roomdetailsinfo.description = mdataset2.get(position).getDescriptions();;
                Roomdetailsinfo.location = mdataset2.get(position).getLocation();;
                Roomdetailsinfo.type = mdataset2.get(position).getCatagory();;
                Roomdetailsinfo.date = mdataset2.get(position).getDate();;
                Roomdetailsinfo.like = mdataset2.get(position).getLike();;
                Roomdetailsinfo.dislikes = mdataset2.get(position).getDislikes();;
                Roomdetailsinfo.toiletnum = mdataset2.get(position).getToiletnum();;
                Roomdetailsinfo.roomnum = mdataset2.get(position).getRoomnum();;
                Roomdetailsinfo.lift = mdataset2.get(position).getLift();;
                Roomdetailsinfo.pict_url = mdataset2.get(position).getPict_url();;
                Roomdetailsinfo.pict_url2 = mdataset2.get(position).getPict_url2();;
                Roomdetailsinfo.pict_url3 = mdataset2.get(position).getPict_url3();;
                Roomdetailsinfo.brodband = mdataset2.get(position).getBroadband();;
                Roomdetailsinfo.cc_tv = mdataset2.get(position).getCctvsecurity();;
                Roomdetailsinfo.phonenumber = mdataset2.get(position).getPhonenumber();;
                Roomdetailsinfo.lat = mdataset2.get(position).getLat();;
                Roomdetailsinfo.lan = mdataset2.get(position).getLan();;
                Roomdetailsinfo.email = mdataset2.get(position).getEmail();;
                Roomdetailsinfo.ad_id = mdataset2.get(position).getAd_id();;
                Roomdetailsinfo.latLng =   new LatLng( Double.parseDouble(Roomdetailsinfo.lat),  Double.parseDouble(Roomdetailsinfo.lan)) ;
                Roomdetailsinfo.pref = mdataset2.get(position).getPref();;
                Roomdetailsinfo.month = mdataset2.get(position).getMonth();;
                Roomdetailsinfo.description = mdataset2.get(position).getDescriptions();


               Intent intent = new Intent(Roomlist.this, Roomdetail.class);
                intent.putExtra("email",mdataset2.get(position).getEmail());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        searchfun(ROOM_URL);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Toast.makeText(getApplicationContext(), "press on spinner position "+pos,
                Toast.LENGTH_LONG).show();
        catagory = typespinner.getSelectedItem().toString().trim();
        location = searchdata.getSelectedItem().toString().trim();
        prefer = prefspin.getSelectedItem().toString().trim();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void searchfun(String RR) {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Disimissing the progress dialog
                        loading.dismiss();

                        ParseAddshow pj = new ParseAddshow(response);
                        pj.parseJSON();
                        mdataset2 = pj.getAdd();
                        mAdapter = new MyRecyclerViewAdapter(mdataset2) {
                        };

                        mRecyclerView.setAdapter(mAdapter);
                        Toast.makeText(Roomlist.this, "false,,,,", Toast.LENGTH_LONG).show();
                        //ViewCompat.setNestedScrollingEnabled(mRecyclerView, false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                      //  Toast.makeText(Roomlist.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                //   Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);



                //Getting Image Name




                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_LOCATION, location);
                params.put(KEY_CATAGORY, catagory);



                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);

    }

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

    //recycler  touch....................

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent= new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.roomlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addpost) {
            if (User.email.equals("null")) {
                Intent intent = new Intent(Roomlist.this, Signup.class);
                startActivity(intent);
                return true;
            }
            else {
                Intent intent = new Intent(Roomlist.this, Postact.class);
                startActivity(intent);
            }
        }
        else if (id == R.id.profileicon) {
            if (User.email.equals("null")) {
                Intent intent = new Intent(Roomlist.this, Signup.class);
                startActivity(intent);
                return true;
            }
            else {
                Intent intent = new Intent(Roomlist.this, Profile.class);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_help) {
            Intent intent = new Intent(Roomlist.this, Help.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            if (User.email.equals("null")) {
                Intent intent = new Intent(Roomlist.this, Signup.class);
                startActivity(intent);
                return true;
            }
            else {
                Intent intent = new Intent(Roomlist.this, Profile.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_rateus) {

        }  else if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(Roomlist.this, About.class);
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
}
