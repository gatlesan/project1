package com.mobilebilling.stores.mobilebilling;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.ws.rs.core.MediaType;
//import com.sun.jersey.api.client.Client;


/**
 * Created by Administrator on 7/29/2015.
 */
public class ScannerActivity extends Activity {

    static final String REST_URI = "http://localhost:8080/NewProj1";
    static final String INCH_TO_FEET = "/ProductService/getPrice/";
    private Handler main_thread;
    private ProgressDialog ringProgressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent, 0);
        setContentView(R.layout.scanner_layout);
        main_thread = new Handler();
        initiateScanner();
        processBarcodeScan();
    }

    private void initiateScanner(){
        {
            ringProgressDialog =
                    ProgressDialog.show(ScannerActivity.this, "Please wait ...", "Scanner is in progress ...", true);
            ringProgressDialog.setCancelable(false);
            /*new Thread(new Runnable() {
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
            }).start();*/
        }
    }
    private void processBarcodeScan(){
        new Thread(new Runnable() {
            int x = 11223344;

            String barcode = new String(x+1+"");
            Product p = null;
            @Override
            public void run() {
                //do web query
                p = getProductDetails(barcode);



                main_thread.post(new Runnable() {
                    @Override
                    public void run() {
                        ringProgressDialog.dismiss();
                        Intent data = new Intent();
                        Toast.makeText(ScannerActivity.this, p.getName()+", "+p.getPrice(), Toast.LENGTH_LONG).show();
                        data.putExtra("myData1", p.getName());
                        data.putExtra("myData2", p.getPrice());
                        setResult(RESULT_OK, data);
                        finish();

                    }
                });
            }
        }).start();;
    }

    protected Product getProductDetails(String prodCode){
        Product ob = null;
        try {
            ClientConfig config = new DefaultClientConfig();
            Client client = Client.create(config);
            WebResource service = client.resource(REST_URI);

            WebResource addService = service.path("rest").path(INCH_TO_FEET + prodCode);
            String prodDetails = getResponse(addService);
            ob = new ObjectMapper().readValue(prodDetails, Product.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ob;

    }
    private static String getResponse(WebResource service) {
        return service.accept(MediaType.APPLICATION_JSON).type("application/json").get(String.class );


    }
}
