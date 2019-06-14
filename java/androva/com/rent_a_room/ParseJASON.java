package androva.com.rent_a_room;

/**
 * Created by arian on 05-Jul-17.
 */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 9/22/2015.
 */
public class ParseJASON {

    public static String[] names;
    public static String[] urls;
    private JSONArray movies = null;


    List<movie> Movies;


    private String json;

    public ParseJASON(String json) {

        this.json = json;
    }

    protected void parseJSON() {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json);
            movies = jsonObject.getJSONArray("student_account");


            names = new String[movies.length()];
            urls = new String[movies.length()];
            Movies = new ArrayList<movie>();


            for (int i = 0; i < movies.length(); i++) {
                movie movie_object = new movie();

                jsonObject = movies.getJSONObject(i);

                names[i] = jsonObject.getString("name");
                urls[i] = jsonObject.getString("pict_url");

                movie_object.setName(names[i]);
                movie_object.setUrl(urls[i]);
                Movies.add(movie_object);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    List<movie> getMovies() {
        //function to return the final populated list
        return Movies;
    }
}