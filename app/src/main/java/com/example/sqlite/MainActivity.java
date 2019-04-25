package com.example.sqlite;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button Insert, Delete, View, Update;
    EditText Name, Family, Id;
    TextView txtCount;
    DatabaseManager dbm;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Insert = findViewById(R.id.btn_insert);
        Delete = findViewById(R.id.btn_delet);
        View = findViewById(R.id.btn_view);
        Update = findViewById(R.id.btn_update);
        txtCount = findViewById(R.id.txt_count);
        Name = findViewById(R.id.edt_name);
        Family = findViewById(R.id.edt_family);
        Id = findViewById(R.id.edt_id);

        dbm = new DatabaseManager(this);
        setTxtCount();

        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String mId = Id.getText().toString();
                String mName = Name.getText().toString();
                String mFamily = Family.getText().toString();
                if (TextUtils.isEmpty(mId) || TextUtils.isEmpty(mName) || TextUtils.isEmpty(mFamily)) {
                    Toast.makeText(MainActivity.this, "اطلاعات کامل را وارد کنید", Toast.LENGTH_SHORT).show();
                } else {
                    Person iperson = new Person();
                    iperson.pId = mId;
                    iperson.pName = mName;
                    iperson.pFamily = mFamily;
                    dbm.isertperson(iperson);
                    Toast.makeText(MainActivity.this, "با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                    setTxtCount();
                }

            }
        });


        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                String vID = Id.getText().toString();
                Person vPer = dbm.getperson(vID);
                Name.setText(vPer.pName);
                Family.setText(vPer.pFamily);

            }
        });


        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                String uID = Id.getText().toString();
                String uNAME = Name.getText().toString();
                String uFAMILY = Family.getText().toString();
                Person uperson = new Person();
                uperson.pId = uID;
                uperson.pName = uNAME;
                uperson.pFamily = uFAMILY;
                dbm.updateperson(uperson);


            }
        });


        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                String delID = Id.getText().toString();
                Person dperson = new Person();
                dperson.pId = delID;
                boolean del = dbm.deletperson(dperson);


                if (del == true) {
                    Toast.makeText(MainActivity.this, "حذف شد", Toast.LENGTH_SHORT).show();
                    setTxtCount();
                } else {

                    Toast.makeText(MainActivity.this, " حذف نشد دوباره امتحان کن", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void setTxtCount() {
        int sCount = dbm.personCount();
        txtCount.setText("" + sCount);
        Log.i("nnnnn", "" + sCount);
    }


}
