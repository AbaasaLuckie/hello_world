package com.example.helloworld;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView mBatteryLevelText;
    private ProgressBar mBatteryLevelProgress;
    private BroadcastReceiver mReceiver;
    Button start, count;
    public final static String KEY_EXTRA_CONTACT_ID = "KEY_EXTRA_CONTACT_ID";
    ExampleDBHelper dbHelper;
    android.widget.ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView1);
        mBatteryLevelText = (TextView) findViewById(R.id.battery);
        mBatteryLevelProgress = (ProgressBar) findViewById(R.id.progressBar);

        mReceiver = new BatteryBroadcastReceiver();
        start = findViewById(R.id.button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlert();
            }
        });
        count = findViewById(R.id.count);
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountDown(v);
            }
        });
        Button button=findViewById(R.id.asyncTask);
        imageView=findViewById(R.id.image);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskExample asyncTask=new AsyncTaskExample();
                asyncTask.execute("https://firebasestorage.googleapis.com/v0/b/biodataapp-61fc1.appspot.com/o/Account%2FImages%2Fcropped8245198034823675483.jpg?alt=media&token=29558a89-eedb-4d28-a650-24398d3d0794");
            }
        });
        Button addNew = (Button) findViewById(R.id.addNew);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateOrEditActivity.class);
                intent.putExtra(KEY_EXTRA_CONTACT_ID, 0);
                startActivity(intent);
            }
        });

        dbHelper = new ExampleDBHelper(this);

        final Cursor cursor = dbHelper.getAllPersons();
        String [] columns = new String[] {
                ExampleDBHelper.PERSON_COLUMN_ID,
                ExampleDBHelper.PERSON_COLUMN_NAME
        };
        int [] widgets = new int[] {
                R.id.personID,
                R.id.personName
        };

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.person_info,
                cursor, columns, widgets, 0);
        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                Cursor itemCursor = (Cursor) MainActivity.this.listView.getItemAtPosition(position);
                int personID = itemCursor.getInt(itemCursor.getColumnIndex(ExampleDBHelper.PERSON_COLUMN_ID));
                Intent intent = new Intent(getApplicationContext(), CreateOrEditActivity.class);
                intent.putExtra(KEY_EXTRA_CONTACT_ID, personID);
                startActivity(intent);
            }
        });
    }
    public void startAlert() {
        EditText text = findViewById(R.id.time);
        int i = Integer.parseInt(text.getText().toString());
        if(text.getText().toString().equals("")){

        }
        else {
            Intent intent = new Intent(this, MyBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), pendingIntent);
            Toast.makeText(this, "Alarm set in " + i + " seconds", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mReceiver);
        super.onStop();
    }

    public void save(View view) {
        Intent list = new Intent(getApplicationContext(),SaveFiles.class);
        startActivity(list);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void call(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", "+256758838398", null));

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions();
        }
        else {
            startActivity(intent);
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
    }

    private class BatteryBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

            mBatteryLevelText.setText(getString(R.string.battery_level) + " " + level);
            mBatteryLevelProgress.setProgress(level);
        }
    }
    public void sendMessage(View view) {
        EditText message = (EditText)findViewById(R.id.message);
//        Toast.makeText (this, "Sending message " + message.getText().toString(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        super.onResume();

    }
    URL ImageUrl = null;
    InputStream is = null;
    Bitmap bmImg = null;
    ImageView imageView= null;
    ProgressDialog p;
    private class AsyncTaskExample extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(MainActivity.this);
            p.setMessage("Downloading...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                ImageUrl = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                is = conn.getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bmImg = BitmapFactory.decodeStream(is, null, options);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmImg;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(imageView!=null) {
                p.hide();
                imageView.setImageBitmap(bitmap);
            }else {
                p.show();
            }
        }
    }
private AlertDialog.Builder builder;
    private AlertDialog alertDialog22;
    private void showCountDown(View view){
        builder = new AlertDialog.Builder(MainActivity.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.custom_dialog, viewGroup, false);
        final TextView ok =dialogView.findViewById(R.id.ok_txt);
        final EditText value =dialogView.findViewById(R.id.value);
        builder.setView(dialogView);
        builder.setView(dialogView);
        alertDialog22 = builder.create();

        alertDialog22.setCancelable(false);
        ok.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (value.getText().toString().equals("")){

                }else {
                    value.setEnabled(false);
                    int minutes = Integer.parseInt(value.getText().toString());
                    long millis = minutes * 60 * 1000;
                    new CountDownTimer(millis, 1000) {

                        public void onTick(long millisUntilFinished) {
                            ok.setText("seconds remaining: " + millisUntilFinished / 1000);
                            //here you can have your logic to set text to edittext
                        }

                        public void onFinish() {
                            ok.setText("done!");
                            value.setEnabled(true);
                            alertDialog22.setCancelable(true);
                            alertDialog22.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    alertDialog22.dismiss();
                                }
                            });
                        }

                    }.start();
                }

            }
        });
        alertDialog22.show();
    }
}
