package androva.com.rent_a_room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arian on 13-Dec-17.
 */
public class ParseAddshow {
    public static String[] floor_number;
    public static String[] price;
    public static String[] descriptions;
    public static String[] location;
    public static String[] catagory;
    public static String[] date;
    public static String[] like;
    public static String[] dislikes;
    public static String[] toiletnum;
    public static String[] roomnum;
    public static String[] lift;
    public static String[] pict_url;
    public static String[] pict_url2;
    public static String[] pict_url3;
    public static String[] broadband;
    public static String[] cctvsecurity;
    public static String[] phonenumber;
    public static String[] lat;
    public static String[] lan;
    public static String[] email;
    public static String[] ad_id;
    public static String[] pref;
    public static String[] month;




    private JSONArray adddetail = null;


    List<Adddetail> addlist;


    private String json;

    public ParseAddshow(String json) {

        this.json = json;
    }

    protected void parseJSON() {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            adddetail = jsonObject.getJSONArray("addlist");

            floor_number = new String[adddetail.length()];
            price = new String[adddetail.length()];
            descriptions = new String[adddetail.length()];
            location = new String[adddetail.length()];
            catagory = new String[adddetail.length()];
            date = new String[adddetail.length()];
            like = new String[adddetail.length()];
            dislikes = new String[adddetail.length()];
            toiletnum = new String[adddetail.length()];
            roomnum= new String[adddetail.length()];
            lift= new String[adddetail.length()];
            pict_url= new String[adddetail.length()];
            pict_url2= new String[adddetail.length()];
            pict_url3= new String[adddetail.length()];
            broadband= new String[adddetail.length()];
            cctvsecurity= new String[adddetail.length()];
            phonenumber= new String[adddetail.length()];
            lat= new String[adddetail.length()];
            lan= new String[adddetail.length()];
            email= new String[adddetail.length()];
            ad_id= new String[adddetail.length()];
            pref= new String[adddetail.length()];
            month= new String[adddetail.length()];

            location = new String[adddetail.length()];
            price = new String[adddetail.length()];
            catagory = new String[adddetail.length()];
            like = new String[adddetail.length()];
            toiletnum = new String[adddetail.length()];
            roomnum = new String[adddetail.length()];
            pict_url = new String[adddetail.length()];
            email = new String[adddetail.length()];


            addlist = new ArrayList<Adddetail>();
            for (int i = 0; i < adddetail.length(); i++) {
                Adddetail movie_object = new Adddetail();

                jsonObject = adddetail.getJSONObject(i);
                floor_number[i] = jsonObject.getString("floor_number");
                price[i] = jsonObject.getString("price");
                descriptions[i] = jsonObject.getString("descriptions");
                location[i] = jsonObject.getString("location");
                catagory[i] = jsonObject.getString("catagory");
                date[i] = jsonObject.getString("date");
                like[i] = jsonObject.getString("likes");
                dislikes[i] = jsonObject.getString("dislikes");
                toiletnum[i] = jsonObject.getString("toiletnum");
                roomnum[i] = jsonObject.getString("roomnum");
                lift[i] = jsonObject.getString("lift");
                pict_url[i] = jsonObject.getString("pict_url");
                pict_url2[i] = jsonObject.getString("pict_url2");
                pict_url3[i] = jsonObject.getString("pict_url3");
                broadband[i] = jsonObject.getString("broadband");
                cctvsecurity[i] = jsonObject.getString("cctvsecurity");
                phonenumber[i] = jsonObject.getString("phone_number");
                lat[i] = jsonObject.getString("lat");
                lan[i] = jsonObject.getString("lan");
                email[i] = jsonObject.getString("email");
                ad_id[i] = jsonObject.getString("ad_id");
                pref[i] = jsonObject.getString("pref");
                month[i] = jsonObject.getString("month");


                movie_object.setFloor_number(floor_number[i]);
                movie_object.setPrice(price[i]);
                movie_object.setDescriptions(descriptions[i]);
                movie_object.setLocation(location[i]);
                movie_object.setCatagory(catagory[i]);
                movie_object.setDate(date[i]);
                movie_object.setLike(like[i]);
                movie_object.setDislikes(dislikes[i]);
                movie_object.setToiletnum(toiletnum[i]);
                movie_object.setRoomnum(roomnum[i]);
                movie_object.setLift(lift[i]);
                movie_object.setPict_url(pict_url[i]);
                movie_object.setPict_url2(pict_url2[i]);
                movie_object.setPict_url3(pict_url3[i]);
                movie_object.setBroadband(broadband[i]);
                movie_object.setCctvsecurity(cctvsecurity[i]);
                movie_object.setPhonenumber(phonenumber[i]);
                movie_object.setLat(lat[i]);
                movie_object.setLan(lan[i]);
                movie_object.setEmail(email[i]);
                movie_object.setAd_id(ad_id[i]);
                movie_object.setPref(pref[i]);
                movie_object.setMonth(month[i]);

                movie_object.setLocation(location[i]);
                movie_object.setPrice(price[i]);
                movie_object.setToiletnum(toiletnum[i]);
                movie_object.setRoomum(roomnum[i]);
                movie_object.setPict_url(pict_url[i]);
                movie_object.setEmail(email[i]);
                movie_object.setCatagory(catagory[i]);
                movie_object.setLike(like[i]);

                addlist.add(movie_object);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    List<Adddetail> getAdd() {
        //function to return the final populated list
        return addlist;
    }

}
