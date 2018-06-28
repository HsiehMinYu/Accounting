package myh.simpleaccounting.Activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import myh.simpleaccounting.Adapter.CategoryRecyclerViewAdapter;
import myh.simpleaccounting.Database.DBHelper;
import myh.simpleaccounting.Model.Category;
import myh.simpleaccounting.Model.Record;
import myh.simpleaccounting.R;
import myh.simpleaccounting.Util.DateUtil;
import myh.simpleaccounting.Util.Utils;


import java.util.ArrayList;
import java.util.Calendar;


public class AddInExpActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "AddInExpActivity";
    private TextView tvdate, tvcategory;
    private EditText etmoney, etnote;
    private Button btnsave;
    private RecyclerView rvcategory;
    private ImageView tbinexp;
    private DBHelper db;
    private Record record;
    private String date,category,note;
    private int money,editid,rvcategorytag=0;
    private CategoryRecyclerViewAdapter adapter;
    private ArrayList<Category> list;
    private Boolean edittag = false;
    private int mYear, mMonth, mDay;
    final Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_in_exp);

        tvdate = (TextView)findViewById(R.id.addpagedatetext);
        tvdate.setOnClickListener(this);
        tvcategory = (TextView)findViewById(R.id.addpagecategorytext);
        etmoney = (EditText)findViewById(R.id.addpagemoneytext);
        etnote = (EditText)findViewById(R.id.addpagenotetext);
        btnsave = (Button)findViewById(R.id.addpagesavebtn);
        btnsave.setOnClickListener(this);
        tbinexp = (ImageView) findViewById(R.id.addpagetoggleiv);
        tbinexp.setOnClickListener(this);

        db = new DBHelper(this);
        tvdate.setText(DateUtil.getDate());

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        rvcategory = (RecyclerView)findViewById(R.id.addpagerecycleview);
        rvcategorytag=1;
        initrv(rvcategorytag);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            editid = bundle.getInt("editTag");
            Log.v(TAG,"id="+editid);
            edittag = true;
            editrefresh();
        }



    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.addpagedatetext:
                new DatePickerDialog(AddInExpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = DateUtil.setDateFormat(year,month,day);

                        tvdate.setText(format);
                    }
                }, mYear,mMonth, mDay).show();
                break;

            case R.id.addpagesavebtn:
                //用if()來判斷是哪一個狀態:新增還是修改

                try {
                    if(TextUtils.isEmpty(tvcategory.getText()) == false &&
                            TextUtils.isEmpty(etmoney.getText()) == false) {

                        if (edittag == true) {
                            Log.d(TAG, "成功判斷修改" + etmoney.getText().toString());
                            update();
                        } else {
                            add();
                        }
                        finish();
                    }else{Utils.showToast("金額與類別不能為空",this);}
                }
                catch(Exception e){e.printStackTrace();}
                break;

            case R.id.addpagetoggleiv:
                if(rvcategorytag==1){
                    initrv(rvcategorytag);
                    rvcategorytag=2;
                }else{
                    initrv(rvcategorytag);
                    rvcategorytag=1;
                }
                break;

        }

    }

    public void add(){

        money = Integer.parseInt(etmoney.getText().toString());
        date = tvdate.getText().toString();
        category = tvcategory.getText().toString();
        note = etnote.getText().toString();

        boolean insertData = db.insertRecord(money, category, note, date);

        /*if (insertData) {
            Utils.showToast("Data Successfully Inserted!",this);
        } else {
            Utils.showToast("Something went wrong",this);
        }*/
    }

    public void editrefresh(){


        record = db.getRecordbyid(Integer.toString(editid));
        tvdate.setText(record.getTimestamp());
        //etmoney.setText(record.getMoney());
        tvcategory.setText(record.getCategory());
        etnote.setText(record.getNote());
        //Utils.showToast("editrefresh",this);


    }

    public void update(){
        money = Integer.parseInt(etmoney.getText().toString());
        date = tvdate.getText().toString();
        category = tvcategory.getText().toString();
        note = etnote.getText().toString();

        record.setId(editid);
        record.setMoney(money);
        record.setCategory(category);
        record.setNote(note);
        record.setTimestamp(date);
        db.updateRecode(record);
    }

    public void initrv(int tag){

        Category cate = new Category();

        if(tag==1){list=cate.getexpcomeset();
        }
        else{list = cate.getincomeset();}
        adapter = new CategoryRecyclerViewAdapter(list, getApplicationContext());
        rvcategory.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);

        adapter.setOnItemClickListener(new CategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                tvcategory.setText(list.get(position).getCategorytitle());
                //adapter.notifyDataSetChanged();
            }
        });
        rvcategorytag=2;
        rvcategory.setLayoutManager(gridLayoutManager);
        adapter.notifyDataSetChanged();
    }
}