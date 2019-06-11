package com.example.kiryu.udppad;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView ivStart,ivSelect, ivUp, ivDown, ivLeft, ivRight, ivBlue, ivGreen, ivRed, ivConnect;
    EditText etHostIP;

    String isStartPressed ="0", isSelectPressed ="0";
    String isUpPressed ="0", isDownPressed ="0", isLeftPressed ="0", isRightPressed ="0";
    String isBluePressed ="0", isGreenPressed ="0", isRedPressed ="0";

    static float leftRightOfSensor, upDownOfSensor;

    int code=48080;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Log.d("qaqa","start aplikacji");
        ivStart=findViewById(R.id.start);
        ivStart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    isStartPressed ="1";
                    setImage(ivStart,R.drawable.start0n);
                }else if(event.getAction()== MotionEvent.ACTION_UP){
                    isStartPressed ="0";
                    setImage(ivStart,R.drawable.start);
                }
                return true;
            }
        });
        ivSelect=findViewById(R.id.select);
        ivSelect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    isSelectPressed ="1";
                    setImage(ivSelect,R.drawable.select0n);
                }else if(event.getAction()== MotionEvent.ACTION_UP){
                    isSelectPressed ="0";
                    setImage(ivSelect,R.drawable.select);
                }
                return true;
            }
        });
        ivUp =findViewById(R.id.gora);
        ivUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){

                    isUpPressed ="1";
                    setImage(ivUp,R.drawable.gora0n);
                }else if(event.getAction()== MotionEvent.ACTION_UP){
                    isUpPressed ="0";
                    setImage(ivUp,R.drawable.gora);
                }
                return true;
            }
        });
        ivDown =findViewById(R.id.dol);
        ivDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){

                    isDownPressed ="1";
                    setImage(ivDown,R.drawable.dol0n);
                }else if(event.getAction()== MotionEvent.ACTION_UP){
                    isDownPressed ="0";
                    setImage(ivDown,R.drawable.dol);
                }
                return true;
            }
        });
        ivLeft =findViewById(R.id.lewo);
        ivLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){

                    isLeftPressed ="1";
                    setImage(ivLeft,R.drawable.lewo0n);
                }else if(event.getAction()== MotionEvent.ACTION_UP){
                    setImage(ivLeft,R.drawable.lewo);
                    isLeftPressed ="0";
                }
                return true;
            }
        });
        ivRight =findViewById(R.id.prawo);
        ivRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    setImage(ivRight,R.drawable.prawo0n);
                    isRightPressed ="1";
                }else if(event.getAction()== MotionEvent.ACTION_UP){
                    isRightPressed ="0";
                    setImage(ivRight,R.drawable.prawo);
                }
                return true;
            }
        });
        ivBlue =findViewById(R.id.blue);
        ivBlue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    setImage(ivBlue,R.drawable.blue0n);
                    isBluePressed ="1";
                }else if(event.getAction()== MotionEvent.ACTION_UP){
                    isBluePressed ="0";
                    setImage(ivBlue,R.drawable.blue);
                }
                return true;
            }
        });
        ivGreen =findViewById(R.id.green);
        ivGreen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    setImage(ivGreen,R.drawable.green0n);
                    isGreenPressed ="1";
                }else if(event.getAction()== MotionEvent.ACTION_UP){
                    isGreenPressed ="0";
                    setImage(ivGreen,R.drawable.green);

                }
                return true;
            }
        });
        ivRed =findViewById(R.id.red);
        ivRed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    setImage(ivRed,R.drawable.red0n);
                    isRedPressed ="1";
                }else if(event.getAction()== MotionEvent.ACTION_UP){
                    isRedPressed ="0";
                    setImage(ivRed,R.drawable.red);
                }
                return true;
            }
        });
        ivConnect =findViewById(R.id.connect);



        etHostIP =findViewById(R.id.hostAdress);
        sensor();
    }

    public void sensor(){
        Sensor sensor;
        SensorManager sensorManager;
        SensorEventListener sensorEventListener;

        sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                leftRightOfSensor =event.values[1];
                upDownOfSensor =event.values[2];
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_GAME);
    }


    public void connect(View view) {
        Log.d("qaqa","jestem w connect");
        ivConnect.setVisibility(View.INVISIBLE);
        Protocol protocol=new Protocol();
        protocol.execute();
    }


    private class Protocol extends AsyncTask {

        InetAddress ia;
        DatagramSocket ds;
        DatagramPacket dpSend;
        String ipHosta;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ipHosta= etHostIP.getText().toString();
        }

        @Override
        protected Object doInBackground(Object[] objects){
            try{

                ia= InetAddress.getByName(ipHosta);
                ds=new DatagramSocket(6000);
                ///////////////////////////
                Thread.sleep(100);
                    Random r = new Random((long) ((leftRightOfSensor+20) * 1000 * (upDownOfSensor+20) * 1000)+System.currentTimeMillis());
                    Log.d("qaqa","seed"+(long) ((leftRightOfSensor+20) * 1000 * (upDownOfSensor+20) * 1000));
                    code = r.nextInt(88889) + 10000;
                    Log.d("qaqa","l "+leftRightOfSensor*1000+" r "+upDownOfSensor*1000+"code: "+code);


                ///////////////////////////
                Encryption encryption = new Encryption();
                encryption.initialProcedure(ds, dpSend,ia);
                //////////////////////


                String string;
                int i=0;
                while(true) {
                    int leftRight=(int)((leftRightOfSensor +50)*10);
                    int upDown=(int)((upDownOfSensor +50)*10);

                    string =String.valueOf(leftRight)+""+String.valueOf(upDown)+""+ pressedButtons()+""+String.valueOf(code);
                    String stringToSend=encryption.encrypt(string);

                    byte[] bytes=new byte[0];

                    bytes = stringToSend.getBytes();
                    dpSend = new DatagramPacket(bytes, bytes.length, ia, 6000);

                    Thread.sleep(20);
                    ds.send(dpSend);
                i++;
                }
            }catch (Exception e){}
            return null;
        }

        public String pressedButtons(){
            return isStartPressed +""+ isSelectPressed +""+
                    isUpPressed +""+ isDownPressed +""+ isLeftPressed +""+ isRightPressed +""+
                    isBluePressed +""+ isGreenPressed +""+ isRedPressed;

        }

    }

    public void setImage(ImageView imageView, int id){
        Drawable drawable= ContextCompat.getDrawable(this,id);
        Bitmap bitmap= ((BitmapDrawable)drawable).getBitmap();
        imageView.setImageBitmap(bitmap);
    }

}
