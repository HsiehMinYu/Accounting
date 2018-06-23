package myh.simpleaccounting.Activity;

import android.app.DatePickerDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import myh.simpleaccounting.Database.DBHelper;
import myh.simpleaccounting.Fragment.CategoryActicityListView;
import myh.simpleaccounting.R;
import myh.simpleaccounting.Util.DateUtil;
import myh.simpleaccounting.Util.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener{

    private String TAG = "CategoryActivity";
    private TextView tvstart, tvend;
    private int mYear, mMonth, mDay;
    private String sttemp,edtemp,starttime,endtime;
    private ImageButton searchbtn;
    private DBHelper db;
    final Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        tvstart = (TextView)findViewById(R.id.categorypagestarttimetext);
        tvstart.setOnClickListener(this);
        tvend = (TextView)findViewById(R.id.categorypageendtimetext);
        tvend.setOnClickListener(this);
        searchbtn = (ImageButton)findViewById(R.id.searchbtn);
        searchbtn.setOnClickListener(this);
        db = new DBHelper(this);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        tvstart.setText(DateUtil.getDate());
        tvend.setText(DateUtil.getDate());

        initsendtime();

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.categorypagestarttimetext:
                new DatePickerDialog(CategoryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = DateUtil.setDateFormat(year,month,day);
                        tvstart.setText(format);
                    }

                }, mYear,mMonth, mDay).show();
                break;
            case R.id.categorypageendtimetext:
                new DatePickerDialog(CategoryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = DateUtil.setDateFormat(year,month,day);

                        tvend.setText(format);
                    }
                }, mYear,mMonth, mDay).show();

                break;
            case R.id.searchbtn:
                sttemp =  tvstart.getText().toString();
                edtemp =  tvend.getText().toString();
                compareDate(sttemp, edtemp);
                break;
            default:
                break;

        }
    }


    private void compareDate(String stime,String etime){

        SimpleDateFormat currentTime= new SimpleDateFormat("yyyy-MM-dd");

        try{

            Date beginTime = currentTime.parse(stime);
            Date endTime=currentTime.parse(etime);
            if(beginTime.getTime() > endTime.getTime()) {
                tvend.setText(DateUtil.getDate());
                tvstart.setText(DateUtil.getDate());
                Utils.showToast("時間間隔錯誤", this);
            }
            else{
                initsendtime();

                updatefrag();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public Bundle getstarttime(){
        Bundle bundle = new Bundle();
        bundle.putString("starttime",starttime);
        Log.d(TAG,"s="+bundle);
        return bundle;
    }
    public Bundle getendtime(){
        Bundle bundle = new Bundle();
        bundle.putString("endtime",endtime);

        Log.d(TAG,"b="+bundle);
        return bundle;
    }
    private void initsendtime(){
        starttime = tvstart.getText().toString();
        endtime = tvend.getText().toString();
        Log.d(TAG,"startTime="+starttime+"endTime="+endtime);
    }

    private void updatefrag(){

        CategoryActicityListView fragment = new CategoryActicityListView();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.categoryfragment, fragment).commit();

    }
}
