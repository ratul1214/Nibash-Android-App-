package androva.com.rent_a_room;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class Signup extends AppCompatActivity {
EditText email, name, pass,emailsignin,passsignin,forgotemail;
    String emails ,names, passs,emailssignins, passssignins,forgotemails;
    static SQLiteDatabase sdb;
    boolean forgotvisible=true;
    Button signin,signup, forgotbut;
    private String KEY_NAME = "name";
    private String KEY_EMAIL = "email";
    private String KEY_PASS = "password";
    private String UPLOAD_URL ="http://paperliver.000webhostapp.com/messregistration.php";
    private String SIGNIN_URL ="http://paperliver.000webhostapp.com/roomlogin.php";
    private String FORGOT_URL ="http://paperliver.000webhostapp.com/forgotpass.php";
    private static final String TAG = Signup.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    TextView forgottv;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;



    private LinearLayout llProfileLayout,forgotlay;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail;
    public static final String DATABASE_NAME = "userdata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Profile.sdb = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        email = (EditText) findViewById(R.id.email);
        name = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.pass);
        emailsignin = (EditText) findViewById(R.id.emailsignin);
        name = (EditText) findViewById(R.id.name);
        passsignin = (EditText) findViewById(R.id.passsignin);
        forgotemail = (EditText) findViewById(R.id.emailforgtpass);
        signup = (Button) findViewById(R.id.button);
        signin = (Button) findViewById(R.id.buttonsignin);
        forgotbut = (Button) findViewById(R.id.buttonforgt);
        forgotlay = (LinearLayout) findViewById(R.id.forgotlayot);
        forgottv = (TextView) findViewById(R.id.forgottvbut);




        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( email.getText().toString().trim().equals("") || pass.getText().toString().trim().equals("") || name.getText().toString().trim().equals(""))
                {
                    if( email.getText().toString().trim().equals("")) {
                        email.setError("First name is required!");
                        email.setHint("please enter email");
                    }
                    if( name.getText().toString().trim().equals("")) {
                        name.setError(" Username is required!");
                        name.setHint("please enter username");
                    }
                        if( pass.getText().toString().trim().equals("")) {
                            pass.setError("Password is required!");
                            pass.setHint("please enter password");
                        }
                } else {
                    if (isValidEmail(email.getText().toString())) {
                        uploadImage();

                    } else {
                        email.setError("Invalid Email");
                        email.setHint("please enter email");
                    }
                }


            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidEmail(emailsignin.getText().toString())) {
                signinf();
                }
                else
                {
                    emailsignin.setError("Invalid Email");
                    emailsignin.setHint("please enter email");

                }

            }
        });
        forgotbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  uploadImage();


            }
        });






    }
    public  void appear(View v)
        {
            if (forgotvisible)
            {
                forgotlay.setVisibility(View.VISIBLE);


// Start the animation

            }
            else
            {

                                forgotlay.setVisibility(View.GONE);

            }
        }
    private void uploadImage() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        if(s.contains("Registration Successfully Done"))
                        {
                            Profile.createtable();
                            Profile.insert(names,emails,passs);
                            Profile.getdata();
                            Intent intent = new Intent(Signup.this, Postact.class);
                            startActivity(intent);
                        }
                        //Showing toast message of the response
                        Toast.makeText(Signup.this, s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(Signup.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                //   Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);

                emails = email.getText().toString().trim();
                names = name.getText().toString().trim();
                passs = pass.getText().toString().trim();

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters

                params.put(KEY_NAME, names);
                params.put(KEY_EMAIL, emails);
                params.put(KEY_PASS, passs);



                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);

    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    private void forgotpass() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, FORGOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();


                        Toast.makeText(Signup.this, s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(Signup.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                //   Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);

                emails = forgotemail.getText().toString().trim();


                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters


                params.put(KEY_EMAIL, forgotemail.getText().toString());




                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);

    }


    private void signinf() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SIGNIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();

                        {
                            JSONArray useraccnt = null;
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                               useraccnt= jsonObject.getJSONArray("signin");
                               jsonObject = useraccnt.getJSONObject(0);
                               User.name = jsonObject.getString("name");
                                User.email = jsonObject.getString("email");
                                User.pass = jsonObject.getString("password");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (!User.email.equals("null")) {
                                Intent intent = new Intent(Signup.this, Roomlist.class);
                                startActivity(intent);

                            }
                            else {
                               // Intent intent = new Intent(Signup.this, Postact.class);
                               // startActivity(intent);
                            }

                        }
                        //Showing toast message of the response
                        Toast.makeText(Signup.this, s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(Signup.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                //   Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);

                emails = emailsignin.getText().toString().trim();

                passs = passsignin.getText().toString().trim();

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters


                params.put(KEY_EMAIL, emails);
                params.put(KEY_PASS, passs);



                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);

    }



}
