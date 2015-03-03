package com.example.rory.lightningsalefyp;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rory on 02/12/2014.
 */

//The clairey commit
public class MainActivity extends Activity {


    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";

    private TextView mTextView;
    private NfcAdapter mNfcAdapter;


    ProgressDialog pDialog;
    ListView list;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "Name";
    private static final String TAG_COMMENT = "Comment";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_STORE_NAME = "store_name";
    private static final String TAG_EXPIRY = "expiry";
    private static final String TAG_TAGS = "tags";
    private static final String TAG_LIKES = "likes";
    private int sortMeasure = 0;

    public static String testNFC = "bolt/";

    public static String baseURI =
            "http://roryoc.me/lightning_sale/gateway.php/";



    public final Handler myCallBack = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            String s = (String)msg.obj;

            if(s.equals("Updated")) {
                


            } else {


                try{
                    JSONArray jArr = new JSONArray(s);


                    for (int count = 0; count < jArr.length(); count++) {

                        JSONObject obj = jArr.getJSONObject(count);

                        String id = obj.getString(TAG_ID);
                        String Name = obj.getString(TAG_NAME);
                        String Comment = obj.getString(TAG_COMMENT);
                        String location = obj.getString(TAG_LOCATION);
                        String store_name = obj.getString(TAG_STORE_NAME);
                        String expiry = obj.getString(TAG_EXPIRY);
                        String tags = obj.getString(TAG_TAGS);
                        String likes = obj.getString(TAG_LIKES);



                        if(tags.equals("mens")){
                            tags = "Men's Apparel";
                        }
                        else if(tags.equals("woman")){
                            tags = "Women's Apparel";
                        }
                        else if(tags.equals("childrens")){
                            tags = "Children's Apparel";
                        }
                        else if(tags.equals("footwear")){
                            tags = "Footwear";
                        }
                        else if(tags.equals("health")){
                            tags = "Health & Beauty";
                        }
                        else if(tags.equals("jewellery")){
                            tags = "Jewellery & Accessories";
                        }
                        else if(tags.equals("elec")){
                            tags = "Electronics & Computers";
                        }
                        else if(tags.equals("drink")){
                            tags = "Food & Drink";
                        }
                        else if(tags.equals("Services")){
                            tags = "Services";
                        }
                        else if(tags.equals("sports")){
                            tags = "Sporting Goods & Athletic Wear";
                        }
                        else if(tags.equals("books")){
                            tags = "Books & News";
                        }
                        else if(tags.equals("toys")){
                            tags = "Toys & Hobbies";
                        }
                        else if(tags.equals("market")){
                            tags = "Supermarket";
                        }


                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentDateandTime = sdf.format(new Date());
                        Date d1 = null;
                        Date d2 = null;

                        try {
                            d1 = sdf.parse(currentDateandTime);
                            d2 = sdf.parse(expiry);

                            //in milliseconds
                            long diff = d2.getTime() - d1.getTime();


                            long diffMinutes = diff / (60 * 1000) % 60;
                            long diffHours = diff / (60 * 60 * 1000) % 24;



                            System.out.print(diffHours + " hours, ");
                            System.out.print(diffMinutes + " minutes, ");


                            String strHours = Long.toString(diffHours);
                            String strMinutes = Long.toString(diffMinutes);


                            expiry = (strHours + "h and " + strMinutes + "m");


                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        // Adding value HashMap key => value
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(TAG_ID, id);
                        map.put(TAG_NAME, Name);
                        map.put(TAG_COMMENT, Comment);
                        map.put(TAG_LOCATION, location);
                        map.put(TAG_STORE_NAME, store_name);
                        map.put(TAG_EXPIRY, expiry);
                        map.put(TAG_TAGS, tags);
                        map.put(TAG_LIKES, likes);

                        oslist.add(map);


                        list=(ListView)findViewById(R.id.list);

                        class MapComparator implements Comparator<Map<String, String>> {
                            private final String key;

                            public MapComparator(String key){
                                this.key = key;
                            }

                            public int compare(Map<String, String> first,
                                               Map<String, String> second){
                                // TODO: Null checking, both for maps and values
                                int firstValue = Integer.parseInt(first.get(key));
                                int secondValue = Integer.parseInt(second.get(key));
                                return Integer.compare(secondValue, firstValue);
                            }
                        }

                        if(sortMeasure == 1) {
                            Collections.sort(oslist, new MapComparator(TAG_LIKES));
                        }

                        class MapComparatorText implements Comparator<Map<String, String>> {
                            private final String key;

                            public MapComparatorText(String key){
                                this.key = key;
                            }

                            public int compare(Map<String, String> first,
                                               Map<String, String> second){
                                // TODO: Null checking, both for maps and values
                                String firstValue = first.get(key);
                                String secondValue = second.get(key);
                                return firstValue.compareTo(secondValue);
                            }
                        }

                        if(sortMeasure == 2) {
                            Collections.sort(oslist, new MapComparatorText(TAG_EXPIRY));
                        }
                        ListAdapter adapter = new SimpleAdapter(MainActivity.this, oslist,
                                R.layout.list_v,
                                new String[] {TAG_NAME, TAG_COMMENT, TAG_LOCATION, TAG_STORE_NAME, TAG_EXPIRY, TAG_LIKES, TAG_TAGS}, new int[] {
                               R.id.name, R.id.comment, R.id.location, R.id.store_name, R.id.expiry, R.id.likes, R.id.tagView });
                        list.setAdapter(adapter);


                        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){


                            int likePushed = 0;
                            int lastLiked;


                            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                           int pos, long id) {

                                int likeId = Integer.parseInt(oslist.get(+pos).get("id"));


                                if(likeId != lastLiked) {
                                    Toast.makeText(MainActivity.this, "You liked " + oslist.get(+pos).get("Name"), Toast.LENGTH_SHORT).show();

                                    like(likeId);
                                    lastLiked = Integer.parseInt(oslist.get(+pos).get("id"));
                                }
                                else if (likeId == lastLiked){
                                    Toast.makeText(MainActivity.this, "You've already liked this", Toast.LENGTH_SHORT).show();
                                }


                                return true;
                            }

                        });

                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                Toast.makeText(MainActivity.this, oslist.get(+position).get("Comment"), Toast.LENGTH_SHORT).show();
                            }
                        });
                        //so on
                    }
                    pDialog.dismiss();

                }catch (JSONException e) {
                    Log.e("MYAPP", "unexpected JSON exception", e);

                }

            }
        }
    };



    protected boolean isBound = false;
    protected BackGroundBoundService.BackGroundBinder myBinder;

    protected ServiceConnection conn = new BackGroundServiceConnection();

    protected class BackGroundServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            myBinder = (BackGroundBoundService.BackGroundBinder) service;
            isBound = true;
            update();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Intent boundServiceIntent = new Intent(this,BackGroundBoundService.class);
        bindService(boundServiceIntent, conn, Context.BIND_AUTO_CREATE);


    }

    public void onResume(){
        super.onResume();
        setupForegroundDispatch(this, mNfcAdapter);

    }

    @Override
    protected void onPause() {
		/*
		 * Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
		 */
        stopForegroundDispatch(this, mNfcAdapter);

        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

        // Unbind from the background service
        if (isBound) {
            unbindService(conn);
            isBound = false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oslist = new ArrayList<HashMap<String, String>>();
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mTextView = (TextView) findViewById(R.id.name);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;

        }

        if (!mNfcAdapter.isEnabled()) {
            mTextView.setText("NFC is disabled.");
        }

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
		/*
		 * This method gets called, when a new Intent gets associated with the current activity instance.
		 * Instead of creating a new activity, onNewIntent will be called. For more information have a look
		 * at the documentation.
		 *
		 * In our case this method gets called, when the user attaches a Tag to the device.
		 */
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);

            } else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }

    /**
     * @param activity The corresponding {@link Activity} requesting the foreground dispatch.
     * @param adapter The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }


    /**
     * @param activity The corresponding {@link BaseActivity} requesting to stop the foreground dispatch.
     * @param adapter The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }


    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // NDEF is not supported by this Tag.
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e(TAG, "Unsupported Encoding", e);
                    }
                }
            }

            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {
			/*
			 * See NFC forum specification for "Text Record Type Definition" at 3.2.1
			 *
			 * http://www.nfc-forum.org/specs/
			 *
			 * bit_7 defines encoding
			 * bit_6 reserved for future use, must be 0
			 * bit_5..0 length of IANA language code
			 */

            byte[] payload = record.getPayload();

            // Get the Text Encoding
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

            // Get the Language Code
            int languageCodeLength = payload[0] & 0063;

            // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            // e.g. "en"

            // Get the Text
            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                testNFC = result;
                System.out.println(testNFC);
            }
        }
    }




    /**
     * On selecting action bar icons
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_refresh:
                sortMeasure = 0;
                update();
                return true;
            case R.id.action_sort:

                return true;
            case R.id.menuSortHot:
                sortMeasure = 1;
                update();
                return true;
            case R.id.menuSortDate:
                sortMeasure = 2;
                update();
                return true;
            case android.R.id.home:
                prefScreen();
                return true;
            case R.id.menuSortMens:
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Loading Men's ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                oslist.clear();
                if (isBound) {
                    myBinder.getMeABolt("mens", myCallBack);
                }
                return true;
            case R.id.menuSortWomens:
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Loading Women's ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                oslist.clear();
                if (isBound) {
                    myBinder.getMeABolt("woman", myCallBack);
                }
                return true;
            case R.id.menuSortChild:
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Loading Children's ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                oslist.clear();
                if (isBound) {
                    myBinder.getMeABolt("childrens", myCallBack);
                }
                return true;
            case R.id.menuSortFoot:
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Loading Footwear ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                oslist.clear();
                if (isBound) {
                    myBinder.getMeABolt("footwear", myCallBack);
                }
                return true;
            case R.id.menuSortHealth:
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Loading Health & Beauty ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                oslist.clear();
                if (isBound) {
                    myBinder.getMeABolt("health", myCallBack);
                }
                return true;
            case R.id.menuSortJewel:
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Loading Jewelry & Accessories ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                oslist.clear();
                if (isBound) {
                    myBinder.getMeABolt("jewellery", myCallBack);
                }
                return true;
            case R.id.menuSortElec:
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Loading Electronics ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                oslist.clear();
                if (isBound) {
                    myBinder.getMeABolt("elec", myCallBack);
                }
                return true;
            case R.id.menuSortFood:
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Loading Food & Drink ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                oslist.clear();
                if (isBound) {
                    myBinder.getMeABolt("drink", myCallBack);
                }
                return true;
            case R.id.menuSortServices:
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Loading Services ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                oslist.clear();
                if (isBound) {
                    myBinder.getMeABolt("services", myCallBack);
                }
                return true;
            case R.id.menuSortSport:
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Loading Sporting Goods ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                oslist.clear();
                if (isBound) {
                    myBinder.getMeABolt("sports", myCallBack);
                }
                return true;
            case R.id.menuSortBooks:
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Loading Books & News ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                oslist.clear();
                if (isBound) {
                    myBinder.getMeABolt("books", myCallBack);
                }
                return true;
            case R.id.menuSortToys:
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Loading Toys & Hobbies ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                oslist.clear();
                if (isBound) {
                    myBinder.getMeABolt("toys", myCallBack);
                }
                return true;
            case R.id.menuSortMarket:
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Loading Supermarket ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                oslist.clear();
                if (isBound) {
                    myBinder.getMeABolt("supermarket", myCallBack);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void like(int idToLike){


        if (isBound) {
            myBinder.updateBolt(idToLike, myCallBack);
        }


    }

    public void prefScreen(){

        Intent nextScreen = new Intent(this, PrefMenu.class);
        this.startActivity(nextScreen);

    }

    public void update(){
        SharedPreferences settings = getSharedPreferences("Test", Context.MODE_PRIVATE);
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Getting Data ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
        for (int i = 0; i < 13; i++) {
            String currentTag;

            if(i == 0){
                currentTag = settings.getString("dataM","");
                if(currentTag.equals("")) {

                }
                else{
                    oslist.clear();
                    if (isBound) {
                        myBinder.getMeABolt(currentTag, myCallBack);
                    }

                }
            }
            else if(i == 1){
                currentTag = settings.getString("dataW","");
                if(currentTag.equals("")) {

                }
                else{
                    oslist.clear();
                    if (isBound) {
                        myBinder.getMeABolt(currentTag, myCallBack);
                    }

                }
            }
            else if(i == 2){
                currentTag = settings.getString("dataC","");
                oslist.clear();
                if(currentTag.equals("")) {

                }
                else{
                    oslist.clear();
                    if (isBound) {
                        myBinder.getMeABolt(currentTag, myCallBack);
                    }

                }
            }
            else if(i == 3){
                currentTag = settings.getString("dataFoot","");
                oslist.clear();
                if(currentTag.equals("")) {

                }
                else{
                    oslist.clear();
                    if (isBound) {
                        myBinder.getMeABolt(currentTag, myCallBack);
                    }
                }

            }
            else if(i == 4){
                currentTag = settings.getString("dataH","");
                oslist.clear();
                if(currentTag.equals("")) {

                }
                else{
                    oslist.clear();
                    if (isBound) {
                        myBinder.getMeABolt(currentTag, myCallBack);
                    }

                }
            }
            else if(i == 5){
                currentTag = settings.getString("dataJ","");
                oslist.clear();
                if(currentTag.equals("")) {

                }
                else{
                    oslist.clear();
                    if (isBound) {
                        myBinder.getMeABolt(currentTag, myCallBack);
                    }

                }
            }
            else if(i == 6){
                currentTag = settings.getString("dataE","");
                oslist.clear();
                if(currentTag.equals("")) {

                }
                else{
                    oslist.clear();
                    if (isBound) {
                        myBinder.getMeABolt(currentTag, myCallBack);
                    }

                }
            }
            else if(i == 7){
                currentTag = settings.getString("dataFood","");
                oslist.clear();
                if(currentTag.equals("")) {

                }
                else{
                    oslist.clear();
                    if (isBound) {
                        myBinder.getMeABolt(currentTag, myCallBack);
                    }

                }
            }
            else if(i == 8){
                currentTag = settings.getString("dataSer","");
                oslist.clear();
                if(currentTag.equals("")) {

                }
                else{
                    oslist.clear();
                    if (isBound) {
                        myBinder.getMeABolt(currentTag, myCallBack);
                    }

                }
            }
            else if(i == 9){
                currentTag = settings.getString("dataSpo","");
                oslist.clear();
                if(currentTag.equals("")) {

                }
                else{
                    oslist.clear();
                    if (isBound) {
                        myBinder.getMeABolt(currentTag, myCallBack);
                    }

                }
            }
            else if(i == 10){
                currentTag = settings.getString("dataB","");
                oslist.clear();
                if(currentTag.equals("")) {

                }
                else{
                    oslist.clear();
                    if (isBound) {
                        myBinder.getMeABolt(currentTag, myCallBack);
                    }

                }
            }
            else if(i == 11){
                currentTag = settings.getString("dataT","");
                oslist.clear();
                if(currentTag.equals("")) {

                }
                else{
                    oslist.clear();
                    if (isBound) {
                        myBinder.getMeABolt(currentTag, myCallBack);
                    }

                }
            }
            else if(i == 12){
                currentTag = settings.getString("dataSup","");
                oslist.clear();
                if(currentTag.equals("")) {

                }
                else{
                    oslist.clear();
                    if (isBound) {
                        myBinder.getMeABolt(currentTag, myCallBack);
                    }

                }
            }


        }






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void buttonDeleteOnClick(View v) {

    }




}