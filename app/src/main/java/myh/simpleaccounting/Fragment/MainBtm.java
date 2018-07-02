package myh.simpleaccounting.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import myh.simpleaccounting.Activity.AddInExpActivity;
import myh.simpleaccounting.Adapter.RecordListViewAdapter;
import myh.simpleaccounting.Database.DBHelper;
import myh.simpleaccounting.Model.Record;
import myh.simpleaccounting.R;
import myh.simpleaccounting.Util.DateUtil;

import java.util.ArrayList;

public class MainBtm extends Fragment implements AdapterView.OnItemLongClickListener{

    private String TAG = "CategoryActicityListView";
    private View rootview;
    private ListView lvRecode;
    private ArrayList<Record> mRecodeList;
    private RecordListViewAdapter adapter;
    private DBHelper dbHelper;
    private boolean update;


    public MainBtm() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        update = true;
        refreshView(update);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_main_btm, container, false);
        initview();
        // Inflate the layout for this fragment
        return rootview;
    }



    public void initview(){

        lvRecode = (ListView)rootview.findViewById(R.id.mainlistview);
        lvRecode.setOnItemLongClickListener(this);
        lvRecode.setEmptyView(rootview.findViewById(R.id.no_data_main_layout));
        dbHelper = new DBHelper(getActivity());
        mRecodeList = new ArrayList<>();

        mRecodeList = dbHelper.getRecord(DateUtil.getDate());


        adapter = new RecordListViewAdapter(getActivity().getApplicationContext(), mRecodeList);
        lvRecode.setAdapter(adapter);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        displayDialog(id);
        return false;
    }

    public void displayDialog(long id){


        final String targetid = Long.toString(id);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String[] options = {getResources().getString(R.string.dialog_edit), getResources().getString(R.string.dialog_delete)};

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                switch (which){
                    case 0://edit
                        Record edittarget = dbHelper.getRecordbyid(targetid);
                        int edid = edittarget.getId();
                        Intent intent = new Intent(getActivity(), AddInExpActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt( "editTag", edid);
                        intent.putExtras(bundle);
                        getActivity().startActivityForResult(intent,2);

                        break;
                    case 1://remove
                        dbHelper.deleteRecode(targetid);
                        update = true;
                        refreshView(update);
                        break;
                }
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.dialog_cancel), null);
        builder.create().show();
    }

    public void refreshView(boolean refresh){
        if (refresh == true) {
            adapter.updateView(dbHelper.getRecord(DateUtil.getDate()));
            update = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        clearView();
    }

    public void clearView(){
        mRecodeList.clear();
        adapter.notifyDataSetChanged();
    }
}