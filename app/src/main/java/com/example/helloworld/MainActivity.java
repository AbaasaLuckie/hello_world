package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void sendMessage(View view) {
        EditText message = (EditText)findViewById(R.id.message);
        Toast.makeText (this, "Sending message " + message.getText().toString(), Toast.LENGTH_SHORT).show();
        Intent msg = new Intent(getApplicationContext(),DisplayMessageActivity.class);
        msg.putExtra("MESSAGE",message.getText().toString());
        startActivity(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.box:
                Intent box = new Intent(getApplicationContext(),box.class);
                startActivity(box);
                break;
            case R.id.clothes:
                Intent clothes = new Intent(getApplicationContext(),clothes.class);
                startActivity(clothes);
                break;
            case R.id.list:
                Intent list = new Intent(getApplicationContext(),ListView.class);
                startActivity(list);
                break;
            case R.id.rec:
                Intent rec = new Intent(getApplicationContext(),RecyclerView.class);
                startActivity(rec);
                break;

        }
        return true;
    }
}
