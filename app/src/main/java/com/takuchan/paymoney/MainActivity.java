package com.takuchan.paymoney;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    EditText people,price;
    Button add,finalbutton;
    TextView onepay,payof,paypay,extra;
    ListView listView;
    ArrayAdapter<String> adapter;

    int totalprice[] = new int[1000];
    int i = 1;
    int ok = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        people = (EditText)findViewById(R.id.editText); price = (EditText)findViewById(R.id.editText2);
        add = (Button)findViewById(R.id.button2); finalbutton = (Button)findViewById(R.id.button4);
        onepay = (TextView)findViewById(R.id.textView7); payof = (TextView)findViewById(R.id.textView9); paypay = (TextView)findViewById(R.id.textView11); extra = (TextView)findViewById(R.id.textView);
        listView = (ListView)findViewById(R.id.listVIew);
        adapter  = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(price.getText().toString().length() != 0){
                    adapter.add(price.getText().toString());
                    totalprice[i] = Integer.parseInt(price.getText().toString());
                    i = i + 1;
                    ok = 1;
                    price.setText(null);
                }else{
                    Toast.makeText(MainActivity.this, "料金を入力してね", Toast.LENGTH_SHORT).show();

                }
            }
        });
        finalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(people.getText().length() == 0){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("確認")
                            .setMessage("支払い人数を入力してください")
                            .setPositiveButton("OK", null)
                            .show();
                } else if(ok == 0){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("確認")
                            .setMessage("商品単価及び商品の値段を合計した数字を入力してください。入力終了後「ADD」ボタンを押してください。")
                            .setPositiveButton("OK", null)
                            .show();
                }else if(!(people.getText().length() == 0 && ok == 0)){
                    int peopleint = Integer.parseInt(people.getText().toString());
                    Toast.makeText(MainActivity.this, "計算が終わりました", Toast.LENGTH_SHORT).show();
                    int num = 0;
                    for (int i = 0;i<totalprice.length; i ++){
                        num += totalprice[i];
                    }
                    onepay.setText(num / peopleint + "¥");
                    extra.setText("あまりは"+num%peopleint+"¥");
                }
            }
        });

    }


}
