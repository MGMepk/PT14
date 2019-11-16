package com.dam.eva.pt14;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
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
import androidx.core.content.ContextCompat;

import android.provider.AlarmClock;
import android.provider.CalendarContract;
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

                Snackbar.make(view, "Afegit nou Intent", Snackbar.LENGTH_LONG)
                        .setAction("Desfer", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //llista.remove();
                                arrayAdapter.notifyDataSetChanged();
                               // startActivity(new Intent(getApplicationContext(),NovaActivity.class));
                            }
                        }).show();

            }
        });
        /* TODO: 16/11/19 new intent
        exemple de listener al floating action button (botó que flota, i permet una acció principal
        per exemple, crear un nou contacte al llistat de l agenda de contactes, o nova trucada a la
        activity del telèfon
*/
        setTitle("PT14");


        //buida
        //ArrayList<String> llista=  new ArrayList<String>();

        listView=(ListView) findViewById(R.id.listView1);
        llista=new ArrayList<String>(asList("Open Gmail","Alarm","Timer","Show alarms",
                "Navegador Propi","App PT13","Take picture","Pick pictures","Show contacts",
                "ActionDIAL vs ActionCall"));
//        private static String[] NAMES=new String[] {"Tom","Jerry","Mary","Louise"};

        llista.add("Altres-Instalats"); //11
        llista.add("Calendari");


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
                                .putExtra(AlarmClock.EXTRA_HOUR, "18")
                                .putExtra(AlarmClock.EXTRA_MINUTES, "30");
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);

                        }
                        break;
                    case 2:

                        intent = new Intent(AlarmClock.ACTION_SET_TIMER)

                                .putExtra(AlarmClock.EXTRA_MESSAGE,"message2")
                                .putExtra(AlarmClock.EXTRA_LENGTH, "120")
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
                        }

                        break;

                    case 7:
                        intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(intent,REQUEST_IMAGE_PICK);

                        break;
                    case 8:
                        intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("content://contacts/people/"));
                        startActivity(intent);

                        break;
                    case 9: //action dial vs call
                        intent = new Intent(Intent.ACTION_DIAL,
                                Uri.parse("tel:(+34)6666666"));
                        startActivity(intent);

                        break;
                    case 10:
                        //Intent intent = new Intent(ReserveIntents.ACTION_RESERVE_TAXI_RESERVATION;
                        //solo wearos

                        // String igUrl="https://www.t.me/34687361674";

                        intent=new Intent(Intent.ACTION_VIEW);
                      //  intent.setType("text/plain");

                        //url="http://www.escoladeltreball.org";
                        //intent=new Intent("com.android.chrome",Uri.parse(url));


                        //intent.setPackage("cat.ereza.properbusbcn");
                        //intent=new Intent("com.dam.eva.pt11");

                       // intent.setPackage("org.telegram.messenger");

                        //intent.setPackage("com.google.android.gms");

                        //intent.setPackage("com.google.android.youtube");
                        intent.setPackage("com.google.android.calculator");
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



                }

            }
        });
    }

    private void pickImage(View v){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);

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


                ImageView profilePicture=(ImageView) findViewById(R.id.userPicture);
                profilePicture.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d( "test: ",e.getMessage()+e.getCause());
            }
        else if (requestCode==REQUEST_NEW_LINE && resultCode==Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {

                String res = extras.getString("nomAct");

                // String res= data.getExtras().getString("nom");

                llista.add(res);
                arrayAdapter.notifyDataSetChanged();

                CoordinatorLayout layout =   findViewById(R.id.linearLayout2);

                Snackbar.make(layout.getRootView(), "Afegit nou Intent", Snackbar.LENGTH_LONG)
                        .setAction("Desfer", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //llista.remove();
                                arrayAdapter.notifyDataSetChanged();
                                // startActivity(new Intent(getApplicationContext(),NovaActivity.class));
                            }
                        }).show();

            }

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
