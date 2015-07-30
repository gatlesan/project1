package com.mobilebilling.stores.mobilebilling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 7/29/2015.
 */
public class ScanItemsActivity extends Activity{
    Button btn_scan;
    private static final int scanner_act_code = 241;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_items_layout);
        btn_scan = (Button)findViewById(R.id.scan_barcode_button);
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent();
                intent.setClass(ScanItemsActivity.this, ScannerActivity.class);
                startActivityForResult(intent, scanner_act_code);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(scanner_act_code == requestCode){
            if(resultCode == RESULT_OK){
                String item_name = data.getStringExtra("myData1");
                int value = data.getIntExtra("myData2",30);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
