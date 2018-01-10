package com.deshpande.camerademo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;


public class OCR extends AppCompatActivity {

    private ImageView imageView_OCR;
    private Uri pic;
    private ImageView img; // guy

    private Button mBtGoBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
        imageView_OCR = (ImageView) findViewById(R.id.imageview_ocr);

        if(getIntent().hasExtra("byteArray")) {
            img = (ImageView)findViewById(R.id.imageDisplay); // guy
            Bitmap b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"), 0, getIntent()
                            .getByteArrayExtra("byteArray").length);
            img.setImageBitmap(b);

        }


        //Bitmap bit = null;
//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            if(extras == null) {
//                bit = null;
//            } else {
//                bit = extras.getString("key1");
//            }
//        } else {
//            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
//        }
          //bit = (Bitmap) getIntent().getParcelableExtra("pic");
          //imageView_OCR.setImageBitmap(bit);
//        LinearLayout lay = (LinearLayout)findViewById(R.id.myLinearlay_ocr);
//        txt = new TextView(this);
//        txt.setText(newString);
//        lay.addView(txt);
//        setContentView(lay);
        mBtGoBack=(Button) findViewById(R.id.bt_go_back);
        mBtGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }
}
