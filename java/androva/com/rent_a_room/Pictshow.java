package androva.com.rent_a_room;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoViewAttacher;

public class Pictshow extends AppCompatActivity {
ImageView iv;
    int pictnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictshow);
        iv = (ImageView) findViewById(R.id.pictshowact);


        if (pictnum == 0)
        {
            Glide.with(iv.getContext()).load(Roomdetailsinfo.pict_url).into(iv);
            PhotoViewAttacher pva = new PhotoViewAttacher(iv);
            pva.update();
        }

    }
}
