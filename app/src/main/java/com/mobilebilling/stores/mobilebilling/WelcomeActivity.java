package com.mobilebilling.stores.mobilebilling;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class WelcomeActivity extends Activity {
    private Button find_store_button;
    private TextView store_name_tv;
    private Handler main_thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        main_thread = new Handler();
        store_name_tv = (TextView)findViewById(R.id.store_name);
        store_name_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to next page
                Intent scanAct = new Intent();
                scanAct.setClass(WelcomeActivity.this, ScanItemsActivity.class);
                startActivity(scanAct);
            }
        });
        find_store_button = (Button)findViewById(R.id.find_store_button);
        find_store_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog ringProgressDialog =
                        ProgressDialog.show(WelcomeActivity.this, "Please wait ...", "Finding the nearest store ...", true);
                ringProgressDialog.setCancelable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                            //Do the time intensive process here

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ringProgressDialog.dismiss();
                        main_thread.post(new Runnable() {
                            @Override
                            public void run() {
                                find_store_button.setVisibility(View.INVISIBLE);
                                store_name_tv.setText("D Mart");
                                store_name_tv.setVisibility(View.VISIBLE);
                            }
                        });

                    }
                }).start();
            }
        });
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
