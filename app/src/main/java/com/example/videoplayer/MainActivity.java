package com.example.videoplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<VideoModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList=new ArrayList<>();
        requestStoragePermission();
        getWidget();
        getVideo();

    }

    //Requesting permission
    private void requestStoragePermission() {
        Log.d("First", "Request Storage Permission");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 121);

    }


    public void getVideo(){
        ContentResolver contentResolver=getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String [] projection={MediaStore.MediaColumns.DATA,
                           MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                           MediaStore.Video.Media._ID,
                           MediaStore.Video.Thumbnails.DATA};

        String orderby=MediaStore.Images.Media.DATE_TAKEN;

        Cursor videoCursor=contentResolver.query(uri,projection,null,null,orderby+" DESC");
        int columnIndexdata = videoCursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        // int columnIndexFolderName = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        //int coulumnId = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
        int thum = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);
        Log.d("videoLog",""+thum);

        while (videoCursor.moveToNext()){
            String absolutePath = videoCursor.getString(columnIndexdata);
               VideoModel videoModel= new VideoModel();
               videoModel.setBoolean_selected(false);
               videoModel.setStr_path(absolutePath);
               videoModel.setStr_thum(videoCursor.getString(thum));
               Log.d("videoLog",""+videoCursor.getString(thum));
               arrayList.add(videoModel);
            }

        VideoAdapter videoAdapter=new VideoAdapter(getApplicationContext(),arrayList,MainActivity.this);
        recyclerView.setAdapter(videoAdapter);

    }

    private void getWidget() {
        recyclerView=findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 121: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
