package com.example.utskalkulator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {
    Handler mHandler;
    RadioGroup rgpehitungan;
    Button btnreser,btnsubmit;
    RecyclerView recyclerView;
    TextView hasil;
    private EditText no1,no2;

    HistoryDBHelper mydb;
    ArrayList<String>iddb,nomor1,nomor2,sum,simbol;
    customadapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgpehitungan =findViewById(R.id.rdhitung);
        no1 =findViewById(R.id.num1);
        no2 =findViewById(R.id.num2);
        hasil = findViewById(R.id.hasil);
        btnreser = findViewById(R.id.reset);
        btnsubmit = findViewById(R.id.hitung);
        recyclerView = findViewById(R.id.recyclerview);

        mydb =new HistoryDBHelper(MainActivity.this);

        iddb = new ArrayList<>();
        nomor1 = new ArrayList<>();
        simbol = new ArrayList<>();
        nomor2= new ArrayList<>();
        sum = new ArrayList<>();


        myadapter = new customadapter(MainActivity.this,iddb,nomor1,simbol,nomor2,sum);
        recyclerView.setAdapter(myadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryDBHelper dbHelper =new HistoryDBHelper(MainActivity.this);

                int checkedId =rgpehitungan.getCheckedRadioButtonId();
                double num1;
                double num2;
                if(checkedId ==-1||no1.getText().toString().isEmpty()||no2.getText().toString().isEmpty())
                {
                    pesan.pesan(getApplicationContext(),"tolong diisi dengan benar!!");
                }
                else
                {
                    num1 =Double.parseDouble(no1.getText().toString());
                    num2 =Double.parseDouble(no2.getText().toString());
                    FindRadioButton(num1,num2,checkedId,dbHelper);
                    rgpehitungan.clearCheck();
                    no1.getText().clear();
                    no2.getText().clear();
                }
            }
        });
        
        btnreser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgpehitungan.clearCheck();
                no1.getText().clear();
                no2.getText().clear();

                HistoryDBHelper mydb = new HistoryDBHelper(MainActivity.this);
                mydb.deletealldata();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);

            }
        });

        displaydataarray();
        this.mHandler = new Handler();

        this.mHandler.postDelayed(m_Runnable,20000);


    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Anda yaikin ingin keluar ?");
        builder.setCancelable(true);
        builder.setNegativeButton("tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                mydb.deletealldata();
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            Toast.makeText(MainActivity.this,"in runnable",Toast.LENGTH_SHORT).show();
            onPause();
            MainActivity.this.mHandler.postDelayed(m_Runnable, 20000);
        }

    };//runnable


    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(m_Runnable);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);

    }

    void displaydataarray()
    {
        Cursor cursor = mydb.readalldata();
        if(cursor.getCount()==0)
        {
            Toast.makeText(this,"no data",Toast.LENGTH_SHORT).show();
        }
        else
        {
            while(cursor.moveToNext())
            {
                iddb.add(cursor.getString(0));
                nomor1.add(cursor.getString(1));
                simbol.add(cursor.getString(2));
                nomor2.add(cursor.getString(3));
                sum.add(cursor.getString(4));
            }
        }
    }


    private void FindRadioButton(double num1, double num2, int checkedId, HistoryDBHelper dbHelper) {

        double count;
        String simbol;

        switch (checkedId)
        {
            case R.id.tambah:
                simbol = "+";
                count =num1 + num2;
                hasil.setText(Double.toString(count));
                dbHelper.addHistory(num1,simbol,num2,count);
                break;
            case R.id.kurang:
                simbol = "-";
                count =num1 - num2;
                hasil.setText(Double.toString(count));
                dbHelper.addHistory(num1,simbol,num2,count);
                break;
            case R.id.kali:
                simbol = "x";
                count =num1 * num2;
                hasil.setText(Double.toString(count));
                dbHelper.addHistory(num1,simbol,num2,count);
                break;
            case R.id.bagi:
                simbol = "/";
                count =num1 / num2;
                hasil.setText(Double.toString(count));
                dbHelper.addHistory(num1,simbol,num2,count);
                break;

        }



    }


}