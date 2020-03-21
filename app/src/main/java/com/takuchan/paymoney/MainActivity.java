package com.takuchan.paymoney;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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
    Spinner spinner;

    String[] dropItem = {
            "均等に割る",
            "10¥まで",
            "100¥まで",
            "1,000¥まで",
            "10,000¥まで",
            "この機能はなんですか"
    };
    String itemname;
    ImageView pulldownimage;

    int totalprice[] = new int[1000];
    int i = 1;
    int ok = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pulldownimage = new ImageView(this);
        pulldownimage.setImageResource(R.drawable.pulldown);

        people = (EditText)findViewById(R.id.editText); price = (EditText)findViewById(R.id.editText2);
        add = (Button)findViewById(R.id.button2); finalbutton = (Button)findViewById(R.id.button4);
        onepay = (TextView)findViewById(R.id.textView7); payof = (TextView)findViewById(R.id.textView9); paypay = (TextView)findViewById(R.id.textView11); extra = (TextView)findViewById(R.id.textView);
        listView = (ListView)findViewById(R.id.listVIew);
        adapter  = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        //スピナーのドロップ
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter spinneradapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,dropItem);
        spinner.setAdapter(spinneradapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemname = (String) parent.getAdapter().getItem(position);
                if(itemname.equals("この機能はなんですか")){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("この機能はなんですか")
                            .setMessage("例えば、3人で2000¥の商品を割り勘するとします。\n2000÷3\n普通に計算すると666¥あまり6666...と続きます\n\n" +
                                    "この場合に「10¥まで」を選ぶと\n一人以外は、一人あたりの支払価格は660¥となり1の位は四捨五入されます。\n四捨五入した1の位の6¥は、友達間で順番に交代してに支払っていくことや、幹事支払ってもらうなど、一人で全員分の四捨五入された価格を仕払ます。そして小銭を出し合う必要がなくなります。")
                            .setPositiveButton("OK", null)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                    ItemSelectDetail();
                }
            }
        });

    }
    void ItemSelectDetail(){
        int peopleint = Integer.parseInt(people.getText().toString());
        int num = 0;
        for (int i = 0;i<totalprice.length; i ++){
            num += totalprice[i];
        }
        if(itemname.equals("この機能はなんですか")){
            new AlertDialog.Builder(this)
                    .setTitle("確認")
                    .setMessage("OKボタンを押して、何円で切り上げるかを選択してください。")
                    .setView(pulldownimage)
                    .setPositiveButton("OK",null)
                    .show();
        }else if(itemname.equals("10¥まで")){
            int  answer = num/peopleint / 10 * 10;
            onepay.setText(answer + "¥");
            extra.setVisibility(View.VISIBLE);
            extra.setText(num/peopleint % 10 + "¥");


        }else if(itemname.equals("100¥まで")){

        }else if(itemname.equals("1,000¥まで")){

        }else if(itemname.equals("10,000まで")){

        }else if(itemname.equals("均等に割る")){
            onepay.setText(num / peopleint + "¥");
            extra.setVisibility(View.VISIBLE);
            extra.setText("あまりは"+num%peopleint+"¥");
            Toast.makeText(this, "計算が終わりました", Toast.LENGTH_SHORT).show();
        }
    }


}
