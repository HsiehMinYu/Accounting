package myh.simpleaccounting.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import myh.simpleaccounting.R;
import myh.simpleaccounting.Util.DateUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String TAG = "MainActivity";
    private ImageButton maininbtn,maincabtn;
    private TextView datetv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        maininbtn = (ImageButton)findViewById(R.id.mainincomebtn);
        maininbtn.setOnClickListener(this);
        maincabtn = (ImageButton)findViewById(R.id.maincategorybtn);
        maincabtn.setOnClickListener(this);
        datetv = (TextView)findViewById(R.id.maindateview);
        datetv.setText(DateUtil.getDate());
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();



    }
    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.mainincomebtn:
                getAddPage();
                break;
            case R.id.maincategorybtn:
                getCatePage();
                break;
        }
    }

    public void getAddPage(){

        Intent intent = new Intent(MainActivity.this, AddInExpActivity.class);
        startActivity(intent);
    }

    public void getCatePage() {
        Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
        startActivity(intent);
    }



}