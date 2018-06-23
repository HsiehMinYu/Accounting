package myh.simpleaccounting.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import myh.simpleaccounting.Model.Record;
import myh.simpleaccounting.R;

import java.util.ArrayList;

public class RecordListViewAdapter extends BaseAdapter {

    private String TAG = "RecordListViewAdapter";
    private Context mContext;
    private ArrayList<Record> mRecordList;

    public RecordListViewAdapter(Context mContext, ArrayList<Record> mRecordList) {
        this.mContext = mContext;
        this.mRecordList = mRecordList;
    }

    @Override
    public int getCount() {
        return mRecordList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRecordList.get(position);
    }

    @Override
    public long getItemId(int position) { return mRecordList.get(position).getId();}

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        //初始化item，解決listview滾動後數據重複問題


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.main_listview_layout, null);
            Log.v("tag", "positon " + position + " convertView is null, " + "new: " + convertView);
            viewHolder = new ViewHolder();
            viewHolder.tvMoney = (TextView)convertView.findViewById(R.id.viewmoney);
            viewHolder.tvCategory = (TextView)convertView.findViewById(R.id.viewcategory);
            viewHolder.tvNote = (TextView)convertView.findViewById(R.id.viewnote);
            viewHolder.tvTimeStamp = (TextView)convertView.findViewById(R.id.viewtime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            Log.v("tag", "positon " + position + " convertView is not null, "  + convertView);
        }

        Record record = (Record) getItem(position);
        viewHolder.tvMoney.setText(String.valueOf(record.getMoney())+"$");
        viewHolder.tvCategory.setText(record.getCategory());
        viewHolder.tvNote.setText(record.getNote());
        viewHolder.tvTimeStamp.setText(record.getTimestamp());

        return convertView;

    }

    /*public void clearData() {
           mRecordList.clear();
           Log.d(TAG,"-------------------clearData-------------------------------");
           notifyDataSetChanged();
       }*/

    public void updateView(ArrayList<Record> records){
        this.mRecordList = records;
        notifyDataSetChanged();
    }



}

class ViewHolder{
    TextView tvMoney;
    TextView tvCategory;
    TextView tvNote;
    TextView tvTimeStamp;
}
