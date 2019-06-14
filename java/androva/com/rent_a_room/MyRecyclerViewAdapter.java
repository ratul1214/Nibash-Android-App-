package androva.com.rent_a_room;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    Context context;


    private List<Adddetail> mDataset;
    //private List<Adddetail> mdataset;
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView location,price,toiletnum,roomnum,catagory,date;
        public ImageView imageView;


        public ViewHolder(View v) {
            super(v);
            location = (TextView) v.findViewById(R.id.location);
            price = (TextView) v.findViewById(R.id.price);
            roomnum = (TextView) v.findViewById(R.id.roomnum);
            toiletnum = (TextView) v.findViewById(R.id.toiletnum);
            catagory = (TextView) v.findViewById(R.id.catagory);
            date = (TextView) v.findViewById(R.id.date);
            imageView = (ImageView) v.findViewById(R.id.roompict);

        }
    }
    public void add(int position, Adddetail item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }
    public void remove(Adddetail item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }
    public MyRecyclerViewAdapter(List<Adddetail> myDataset) {
        mDataset = myDataset;
    }
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rentlist, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.location.setText(mDataset.get(position).getLocation());
        holder.roomnum.setText(mDataset.get(position).getRoomnum());
        holder.toiletnum.setText(mDataset.get(position).getToiletnum());
        holder.catagory.setText(mDataset.get(position).getCatagory());
        holder.price.setText(mDataset.get(position).getPrice());
        holder.date.setText(mDataset.get(position).getDate());

      DatabaseReference mDatabaseRef;



        Glide.with(holder.imageView.getContext()).load(mDataset.get(position).getPict_url()).into(holder.imageView);



    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

}