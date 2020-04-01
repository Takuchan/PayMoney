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
    TextView onepay,totalnum,extra;
    ListView listView;
    ArrayAdapter<String> adapter;
    Spinner spinner;

    String[] dropItem = {
            "均等に割る",
            "10¥まで",
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
        onepay = (TextView)findViewById(R.id.textView7); totalnum = (TextView)findViewById(R.id.textView8); extra = (TextView)findViewById(R.id.textView);
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
                            .setMessage("例えば、3人で¥2000の商品を割り勘するとします。\n2000÷3=666・・66...\nあまりが出てしまいました。\n\n" +
                                    "この場合に「¥10まで」を選ぶと\n1の位は四捨五入され、一人あたりの支払価格は¥670となります。\nこれにより１円玉を出し合う必要がなくなります。\n上の例はおつりが帰ってくる計算です。ですがおつりが帰らず、逆に多く支払う" +
                                    "場合もあります。例えば１の位が3で四捨五入して０になる場合です。この場合では一人だけが多く支払うことになるので、平等を保つために友達間や仲間で順番に交代して支払っていくことが大切です。金銭のやり取りなのでしっかりと話し合いで約束を決めましょう。")
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
        totalnum.setText("計:"+ num  + "¥");
        if(itemname.equals("この機能はなんですか")){
            new AlertDialog.Builder(this)
                    .setTitle("確認")
                    .setMessage("OKボタンを押して、何円で切り上げるかを選択してください。")
                    .setView(pulldownimage)
                    .setPositiveButton("OK",null)
                    .show();
        }else if(itemname.equals("¥10まで")){
            int  answer = num/peopleint / 10 * 10;
            if((num/peopleint % 10) > 5) {
                answer = answer + 10;
            }
            onepay.setText("¥" + answer);
            extra.setVisibility(View.VISIBLE);
            int otsuri = num - answer * peopleint;
            if (otsuri < 0){
                extra.setText("おつり¥"+otsuri );
            }else{
                extra.setText("一人だけ+¥" + otsuri+"を払ってください");
            }

        }else if(itemname.equals("均等に割る")){
            onepay.setText("¥"+num / peopleint );
            extra.setVisibility(View.VISIBLE);
            extra.setText("あまりは¥"+num%peopleint);
        }
    }


}
