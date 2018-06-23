package myh.simpleaccounting.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import myh.simpleaccounting.Model.Category;
import myh.simpleaccounting.R;


import java.util.ArrayList;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewHolder> {

    private String TAG = "CategoryRecyclerViewAdapter";
    private ArrayList<Category> mDataModelArrayList ;
    private Context mContext;

    private OnItemClickListener onItemClickListener = null;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public CategoryRecyclerViewAdapter(ArrayList<Category> dataModelArrayList, Context applicationContext){
        this.mDataModelArrayList = dataModelArrayList;
        this.mContext = applicationContext;
    }


    @Override
    public CategoryRecyclerViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_recyclerview_layout, null);
        CategoryRecyclerViewHolder viewHolder = new CategoryRecyclerViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryRecyclerViewHolder holder, final int position) {

        holder.title.setText(mDataModelArrayList.get(position).getCategorytitle());
        holder.image.setImageResource(mDataModelArrayList.get(position).getCategoryimage());

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyDataSetChanged();
                    onItemClickListener.onItemClick(view, position);

                }
            });
        }


    }

    @Override
    public int getItemCount() {return mDataModelArrayList.size();}


}


class CategoryRecyclerViewHolder extends  RecyclerView.ViewHolder{

    RelativeLayout background;
    ImageView image;
    TextView title;

    public CategoryRecyclerViewHolder(View itemView){
        super(itemView);

        this.image = (ImageView)itemView.findViewById(R.id.rvcategoryview);
        this.title = (TextView)itemView.findViewById(R.id.rvtextview);
        this.background = (RelativeLayout)itemView.findViewById(R.id.rvlayoutbackground);

    }
}
