package com.example.rory.lightningsalefyp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

/**
 * Created by Rory on 02/12/2014.
 */
public class BackGroundBoundService extends Service {

    public BackGroundBoundService() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return new BackGroundBinder();
    }

    public class BackGroundBinder extends Binder {

        private ThreadGroup myThreadGroup = new ThreadGroup("Bolt");

        //Binder methods go in here, these are specific
        //to our implementation. Any public methods will
        //make up the interface to the bound service

        public void getMeABolt(final String boltTag, final Handler h) {

            new Thread (myThreadGroup, new Runnable () {

                @Override
                public void run() {

                    //Do background stuff here.
                    String s = HttpHandler.HttpGetExec(MainActivity.baseURI + MainActivity.testNFC + boltTag);

                    try {
                        //Simulate slow network service
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    Message m = new Message();
                    m.obj = s;
                    h.sendMessage(m);

                }//end run()

            }).start();

        }//end getMeABolt()

        public void updateBolt(final int boltId, final Handler h) {

            new Thread (myThreadGroup, new Runnable () {

                @Override
                public void run() {

                    //Do background stuff here.
                    String s = HttpHandler.HttpUpdateExec(MainActivity.baseURI
                            + "id/" + boltId);

                    s = "Updated";
                    Message m = new Message();
                    m.obj = s;
                    h.sendMessage(m);

                }//end run()

            }).start();

        }//end deleteBolt()

    }//end BackGroundBinder class
}
