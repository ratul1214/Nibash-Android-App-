package androva.com.rent_a_room;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by arian on 25-Dec-17.
 */
public class Roomdetailsinfo {
   public static String price= "null";
   public static String description= "null";
   public static String date= "null";
   public static String ad_id= "null";
   public static String location= "null";
   public static String type= "null";
   public static String roomnum= "null";
   public static String toiletnum= "null";
   public static String floor= "null";
   public static String bills= "null";
   public static String email= "null";
   public static String phonenumber= "null";
   public static String lat= "null";
   public static String dislikes= "null";
   public static String like= "null";
   public static String pict_url= "null";
   public static String pict_url2= "null";
   public static String pict_url3= "null";
   public static String roompict1= "null",roompict2="null",roompict3 = "null";
   public static String lan= "null";
   public static String pref= "null";
   public static String month= "null";
   public static Bitmap bitmap1,bitmap2,bitmap3,propicbitmap, resized,verificationbitmap;
   public static String water= "false",brodband= "false",wify= "false" , lift= "false" , gerator= "false" , gass= "false" ,
           cc_tv= "false" , secuirity_guird= "false" , drawing= "false" , dining= "false" ;
   public static LatLng latLng = new LatLng(51.5,-.1277);

   public static void setLocation(String location) {
      Roomdetailsinfo.location = location;
   }

   public static void setPrice(String price) {
      Roomdetailsinfo.price = price;
   }

   public static void setToiletnum(String toiletnum) {
      Roomdetailsinfo.toiletnum = toiletnum;
   }

   public static void setRoomnum(String roomnum) {
      Roomdetailsinfo.roomnum = roomnum;
   }

   public static void setPict_url(String pict_url) {
      Roomdetailsinfo.pict_url = pict_url;
   }

   public static void setEmail(String email) {
      Roomdetailsinfo.email = email;
   }

   public static void setType(String type) {
      Roomdetailsinfo.type = type;
   }

   public static void setLike(String like) {
      Roomdetailsinfo.like = like;
   }

   public static String getLocation() {
      return location;
   }

   public static String getPrice() {
      return price;
   }
}
