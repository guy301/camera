package com.deshpande.camerademo;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button takePictureButton;
    private ImageView imageView;
    private Uri file;
    String datapath = "";
    Intent GalIntent, CamIntent, CropIntent;

    // hi sap
    private Button mBtLaunchActivity;

    int i = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePictureButton = (Button) findViewById(R.id.button_image);
        imageView = (ImageView) findViewById(R.id.imageview);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            takePictureButton.setEnabled(false);
            int i = 0;
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }


        mBtLaunchActivity = (Button) findViewById(R.id.bt_sapir);

        mBtLaunchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchActivity();
            }
        });
    }


    private void launchActivity() {

        Intent intent = new Intent(this, OCR.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureButton.setEnabled(true);
            }
        }
    }

    public void gallery(View view)
    {
        GalIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(GalIntent, "select image from Gallery"), 2);
    }

    private void launchActivity(View view) {

        Intent intent = new Intent(this, OCR.class);
        startActivity(intent);
    }


    public void takePicture(View view) {
        Intent CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        CamIntent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(CamIntent, 100);
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == 100) {//from camera
                if (resultCode == RESULT_OK) {
                    CropImage();
                    //imageView.setImageURI(file);
                }
            }
            else if (requestCode == 2)//from gallery
            {
                if (data != null){
                    file = data.getData();
                    CropImage();
                }
            }
            else if (requestCode == 1)//finished cropping
            {
                if(data != null){
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = bundle.getParcelable("data");
                    //imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    imageView.setImageBitmap(bitmap);
                    File f = new File(file.getPath());///////////

                    if (f.exists()) f.delete();/////////////


                }
        }
}

    private void CropImage() {
        try{
            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(file, "image/*");
            CropIntent.putExtra("crop", true);
            CropIntent.putExtra("outputX", 180);
            CropIntent.putExtra("outputY", 180);
            CropIntent.putExtra("aspectX", 3);
            CropIntent.putExtra("aspectY", 4);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);
            file = Uri.fromFile(getOutputMediaFile());
            datapath = file.getPath();
            CropIntent.putExtra(MediaStore.EXTRA_OUTPUT, file);

            startActivityForResult(CropIntent, 1);
        }
        catch (ActivityNotFoundException ex)
        {

        }
    }


}
