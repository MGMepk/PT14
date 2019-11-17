package com.dam.eva.pt14;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

import static java.net.Proxy.Type.HTTP;
import static java.util.Arrays.asList;

public class ScrollingActivity extends AppCompatActivity {


    private ListView listView ;
    ArrayList<String> llista;
    ArrayAdapter<String> arrayAdapter;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 10;
    private Spinner spinner;

    private Bitmap bitmap ;
    private static final int REQUEST_IMAGE_PICK = 40;
    private static final int REQUEST_NEW_LINE = 20;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(), NovaActivity.class);
                startActivityForResult(intent,REQUEST_NEW_LINE);

            }
        });
        /* TODO: 16/11/19 new intent
        exemple de listener al floating action button (botó que flota, i permet una acció principal
        per exemple, crear un nou contacte al llistat de l agenda de contactes, o nova trucada a la
        activity del telèfon
*/
        setTitle("PT14 - Scrolling ListView");


        //buida
        //ArrayList<String> llista=  new ArrayList<String>();

        listView=(ListView) findViewById(R.id.listView1);
        llista=new ArrayList<String>(asList("Open Gmail","Alarm","Timer","Show alarms",
                "Navegador Propi","App PT13","Take picture","Pick pictures","Query mapa Edt",
                "smsto:"));
//        private static String[] NAMES=new String[] {"Tom","Jerry","Mary","Louise"};

        llista.add("Altres-Instalats"); //10
        llista.add("Calendari");//11
        llista.add("Insertar contacte");//12
        llista.add("mailto:");
        llista.add("SearchWeb");
        llista.add("Wifi");


//trets de:
        //https://developer.android.com/guide/components/intents-common

        arrayAdapter=new ArrayAdapter<String >(this,android.R.layout.simple_list_item_1,llista);
        listView.setAdapter (arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                String url;
                switch (position) {
                    case 0:

                        intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("*/*");
                        String[] addresses={"ebarbeito@correu.escoladeltreball.org","ebarbeit@xtec.cat"};
                        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "app pt14");
                        intent.putExtra(Intent.EXTRA_REFERRER,"eva");
                        //intent.putExtra(Intent.EXTRA_STREAM, attachment);

                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }

                        break;
                    case 1:
                        intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                                .putExtra(AlarmClock.EXTRA_MESSAGE, "message")
                                .putExtra(AlarmClock.EXTRA_HOUR, "1800")
                                .putExtra(AlarmClock.EXTRA_MINUTES, "3000");
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);

                        }
                        break;
                    case 2:

                        intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                                .putExtra(AlarmClock.EXTRA_MESSAGE,"message2")
                                .putExtra(AlarmClock.EXTRA_LENGTH, "120000")
                                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            try {
                                startActivity(intent);

                            } catch (Exception e){
                                Log.d("test", "onItemClick: "+e.getCause()+e.getMessage());

                            }

                        }
                        else
                            Toast.makeText(ScrollingActivity.this, "No pot obrir Timer", Toast.LENGTH_LONG).show();
                        break;
                    case 3:

                        intent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            try {
                                startActivity(intent);

                            } catch (Exception e){
                                Log.d("test", "onItemClick: "+e.getCause()+e.getMessage());

                            }
                        }
                        else
                            Toast.makeText(ScrollingActivity.this, "No pot obrir Timer", Toast.LENGTH_LONG).show();
                        break;

                    case 4:
                        url="http://www.escoladeltreball.org";
                        intent=new Intent("com.example.ausias.pt13.NavegadorPropi",Uri.parse(url));
                        startActivity(intent);
                        break;

                    case 5:
                        intent=new Intent("com.example.ausias.pt13.Proves");
                        startActivity(intent);
                        break;

                    case 6:

                        int permCheck= ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.CAMERA);
                        if (permCheck== PackageManager.PERMISSION_GRANTED) {
                            intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    "PT14 no té permissos per obrir la càmera", Toast.LENGTH_SHORT).show();

                            if (ActivityCompat.shouldShowRequestPermissionRationale(ScrollingActivity.this
                                    , Manifest.permission.CAMERA)) {

                                Toast.makeText(getApplicationContext(), "Per seguretat, està deshabilitada. ",
                                        Toast.LENGTH_LONG).show();
                                //menu dialeg

                                ActivityCompat.requestPermissions(ScrollingActivity.this
                                        ,  new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

                                // show an explanation to the user
                                // asincrona:no bloquejar el thread esperant la seva resposta
                                // Bona pràctica, try again to request the permission.
                            } else {
                                // request the permission.
                                // CALLBACK_NUMBER is a integer constants
                                //Toast.makeText(this, "demana permis ", Toast.LENGTH_SHORT).show();
                                ActivityCompat.requestPermissions(ScrollingActivity.this
                                        ,  new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                                // The callback method gets the result of the request.
                            }

                        }

                        break;

                    case 7:
                        intent = new Intent(getApplicationContext(),ShowPictureAct.class);
                        startActivity(intent);

                        break;
                    case 8:
                        intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("geo:0,0?q=escola del treball"));

                        startActivity(intent);
                        break;


                    case 9: //action dial vs call; call necesita call_phone de permisos
                        //intent = new Intent(Intent.ACTION_DIAL,
                        //        Uri.parse("tel:(+34)6666666"));
                        //startActivity(intent);
                        intent = new Intent(Intent.ACTION_SEND);
                        intent.setData(Uri.parse("smsto:"));
                        intent.putExtra("sms_body", "missatge");
                        // intent.putExtra(Intent.EXTRA_STREAM, attachment);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }

                        break;
                    case 10:
                        //Intent intent = new Intent(ReserveIntents.ACTION_RESERVE_TAXI_RESERVATION;
                        //solo wearos

                         url="https://www.t.me/34687361674";
                        //intent=new Intent(Intent.ACTION_SEND);

                        //  intent.setType("text/plain");

                        //url="http://www.escoladeltreball.org";
                        intent=new Intent("org.telegram.messenger",Uri.parse(url));


                        //intent.setPackage("cat.ereza.properbusbcn");
                        //intent=new Intent("com.dam.eva.pt11");

                       // intent.setPackage("org.telegram.messenger");
                        //intent.setPackage("com.google.android.gms");
                        //intent.setPackage("com.google.android.youtube");
                       // intent.setPackage("com.google.android.calculator");
                        //intent.setPackage("com.google.android.tts");
                        //intent.setPackage("com.spotify.music");
                        //intent.setPackage("com.termux");
                       // intent.setPackage("org.mozilla.firefox");
                       // intent.setPackage("es.bcn.bicing");
                        //intent.setPackage("com.dam.eva.pt22xat");

                        //Log.d("test", "Invoking chrome");

                        //startActivity(intent);

                        if (intent.resolveActivity(getPackageManager()) == null) {
                            Log.d("test", "Couldn't find it:alternatives showing");
                            intent = new Intent(Intent.ACTION_VIEW);
                        }

                        startActivity(intent);
                        break;

                    case 11:
                        intent = new Intent(Intent.ACTION_INSERT)
                                .setData(CalendarContract.Events.CONTENT_URI)
                                .putExtra(CalendarContract.Events.TITLE, "party")
                                .putExtra(CalendarContract.Events.EVENT_LOCATION, "bcn")
                                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 1000)
                                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, "2000000");
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                        break;
                    case 12:
                        intent = new Intent(Intent.ACTION_INSERT);
                        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, "evie");
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, "eva.bcn08@gmail.com");
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                        break;
                    case 13:
                        intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:"));
                        String[] addresses1={"ebarbeito@correu.escoladeltreball.org","ebarbeit@xtec.cat"};
                        intent.putExtra(Intent.EXTRA_EMAIL, addresses1);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "app pt14");

                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }

                        break;
                    case 14:
                        intent = new Intent(Intent.ACTION_WEB_SEARCH);
                        intent.putExtra(SearchManager.QUERY, "com guanyar la loteria");
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                        break;

                    case 15:
                        intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                        break;

                }

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK)


            try (InputStream stream = getContentResolver().openInputStream(data.getData());)
            {
                // recyle unused bitmaps
                if (bitmap != null) {
                    bitmap.recycle();
                }

                bitmap = BitmapFactory.decodeStream(stream);
                //obrir nou intent aquí...
                Log.d("test", "onClick: passa aaaa??");


                //ImageView profilePicture=(ImageView) findViewById(R.id.userPicture);
                //profilePicture.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d( "test: ",e.getMessage()+e.getCause());
            }
        else if (requestCode==REQUEST_NEW_LINE && resultCode==Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {

                String res = extras.getString("nomAct");

                llista.add(res);
                arrayAdapter.notifyDataSetChanged();

                //igual que vam fer a la PT12
                CoordinatorLayout layout =   findViewById(R.id.linearLayout2);
//layout.addView();

                Snackbar.make(layout.getRootView(), "Afegit nou Intent", Snackbar.LENGTH_LONG)
                        .setAction("Desfer", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("test", "onClick: passa a snackbar??");
                                llista.remove(llista.size()-1);
                                Log.d("test", "onClick: passa llista size"+ llista.size());

                                arrayAdapter.notifyDataSetChanged();
                               // Toast.makeText(ScrollingActivity.this, "Desfeta", Toast.LENGTH_SHORT).show();
                            }
                        }).show();

            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, do your work....
                    Log.d("onRequestPerm", "uff");
                    try {
                        startActivity(new Intent("android.media.action.IMAGE_CAPTURE"));
                        // Intent intent1=new Intent(Intent.ACTION_PICK,"MediaStore.Images.Media.EXTERNAL_CONTENT_URI");


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("onReq: ", e.getMessage());
                    }

                } else {
                    // permission denied
                    // Disable the functionality that depends on this permission.

                }
                return;
            }

            // other 'case' statements for other permssions
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
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
}
