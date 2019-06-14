package androva.com.rent_a_room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arian on 25-Dec-17.
 */
public class Parseadddetails {
    public static String[] location;
    public static String[] price;
    public static String[] type;
    public static String[] toiletamnt;
    public static String[] roomamnt;
    public static String[] like;
    public static String[] pict_url;
    public static String[] email;
    public static String[] lat;
    public static String[] lon;
    public static String[] phonenum;
    public static String[] bills;



    private JSONArray adddetail = null;


    List<Roomdetailsinfo> addlist;


    private String json;

    public Parseadddetails(String json) {

        this.json = json;
    }

    protected void parseJSON() {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            adddetail = jsonObject.getJSONArray("addlist");


            location = new String[adddetail.length()];
            price = new String[adddetail.length()];
            type = new String[adddetail.length()];
            like = new String[adddetail.length()];
            toiletamnt = new String[adddetail.length()];
            roomamnt = new String[adddetail.length()];
            pict_url = new String[adddetail.length()];
            email = new String[adddetail.length()];
            phonenum = new String[adddetail.length()];
            lat = new String[adddetail.length()];
            lon = new String[adddetail.length()];
            bills = new String[adddetail.length()];

            addlist = new ArrayList<Roomdetailsinfo>();


            for (int i = 0; i < adddetail.length(); i++) {
               // Roomdetailsinfo Roomdetailsinfo = new Roomdetailsinfo();

                jsonObject = adddetail.getJSONObject(i);

                location[i] = jsonObject.getString("location");
                price[i] = jsonObject.getString("price");
                type[i] = jsonObject.getString("catagory");
                toiletamnt[i] = jsonObject.getString("toiletnum");
                roomamnt[i] = jsonObject.getString("roomnum");
                like[i] = jsonObject.getString("like");
                pict_url[i] = jsonObject.getString("pict_url");
                email[i] = jsonObject.getString("email");
                phonenum[i] = jsonObject.getString("phonenum");
                lat[i] = jsonObject.getString("lat");
                lon[i] = jsonObject.getString("lon");
                bills[i] = jsonObject.getString("bills");

                Roomdetailsinfo.setLocation(location[i]);
                Roomdetailsinfo.setPrice(price[i]);
                Roomdetailsinfo.setToiletnum(toiletamnt[i]);
                Roomdetailsinfo.setRoomnum(roomamnt[i]);
                Roomdetailsinfo.setPict_url(pict_url[i]);
                Roomdetailsinfo.setEmail(email[i]);
                Roomdetailsinfo.setType(type[i]);
                Roomdetailsinfo.setLike(like[i]);

               // addlist.add(Roomdetailsinfo);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
