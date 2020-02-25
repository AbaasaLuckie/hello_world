package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class box extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box);

        AssetManager assetManager = getAssets();
        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(),"test.txt");
        try{
            in = assetManager.open("test.txt");
            out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);
            readFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        PackageManager packageManager = getPackageManager();
        Intent testInput = new Intent(Intent.ACTION_VIEW);
        testInput.setType("application/txt");
        List list = packageManager.queryIntentActivities(testInput,PackageManager.MATCH_DEFAULT_ONLY);
        if(list.size() > 0 && file.isFile()){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file://"+getFilesDir()+"/test.txt"),"application/txt");
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "File does not exist", Toast.LENGTH_SHORT).show();
        }
    }

    private void readFile(InputStream in,OutputStream out) throws IOException
    {
        byte [] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}
