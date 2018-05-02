
//https://code.tutsplus.com/tutorials/android-sdk-create-a-barcode-reader--mobile-17162
package team3.kau.ie201.barcodescanner2;

import android.annotation.TargetApi;
import android.graphics.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.widget.ToggleButton;


import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.integration.android.IntentIntegrator;



public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button button;
    boolean passed1, passed2 = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        button = (Button)findViewById(R.id.button);//
        button.setOnClickListener(this);//


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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

    public  void onClick(View v){

        // if button clicked
        if(v.getId()== R.id.button){
            //start barcode scan cam 1,2
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan(0);

        }
    }
	//----------------------------------------------------------------------------------------------------------------------------
	//Starts after each scan
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //below this point is our work
        //TODO: Split into methods
        try {
            //get scan result
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

            // if not empty
            if (scanningResult != null) {
                String result = scanningResult.getContents();
                // if key is correct
                if (result.equalsIgnoreCase("team3")
                    || result.equalsIgnoreCase("member1")
                    || result.equalsIgnoreCase("member2")
                    || result.equalsIgnoreCase("member3")
                    || result.equalsIgnoreCase("member4")) {
                    // if it is key 1
                    if (result.equalsIgnoreCase("team3")) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Key 1 Accepted", Toast.LENGTH_SHORT);
                        toast.show();
                        passed1 = true;

                        //take shot of barcode 2
                        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                        scanIntegrator.initiateScan(0);

                    }

                    else if (result.equalsIgnoreCase("member1")
                            || result.equalsIgnoreCase("member1")
                            || result.equalsIgnoreCase("member2")
                            || result.equalsIgnoreCase("member3")
                            || result.equalsIgnoreCase("member4")
                            && passed1) {

                        passed1 = false;
                        Toast toast = Toast.makeText(getApplicationContext(), "Opening lock", Toast.LENGTH_SHORT);
                        toast.show();

                        //flash LED
                        flashLightOn();

                        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                        scanIntegrator.initiateScan(0);


                        //Scan again if cont. is checked
                        //make flash on!
                        //open door
                        //FlashLightUtilForL flash = new FlashLightUtilForL(this);
                        //flash.turnOnFlashLight();
                    }

                    else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Key not accepted", Toast.LENGTH_LONG);
                        toast.show();
                        passed1 = false;
                        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                        scanIntegrator.initiateScan(0);


                    }

                }

                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Key not accepted", Toast.LENGTH_SHORT);
                    toast.show();
                    passed1 = false;
                    IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                    scanIntegrator.initiateScan(0);

                }
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();


            }
        }
        catch (Exception e){
            Toast tost = Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_SHORT);
            tost.show();

        }
    }

    private void flashLightOn() {
        try {

            //Camera settings from https://developer.android.com/reference/android/hardware/Camera.html
            android.hardware.Camera.Parameters para;
            android.hardware.Camera cam = android.hardware.Camera.open(0);
            para = cam.getParameters();
            para.setFlashMode(para.FLASH_MODE_TORCH);
            cam.setParameters(para);

            //Flash on
            cam.startPreview();

            //Delay
            int dum=0;
            for (int i =0;i<999999;i++){
                i++;
                dum = i + dum;
                dum++;
                for (int ii = 0;ii<99999999;i++){
                    ii++;
                    dum = ii + i + dum;
                    for (int iii = 0;ii<99999999;i++){
                        ii++;
                        dum = iii + i + dum;
                    }
                }
            }
            //Flash off
            cam.stopPreview();
            cam.release();

        }
        catch (Exception e)
        {

        }
    }
}
