package com.dam.manuelgarcia.pt14;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;
import static java.util.Arrays.asList;

public class ScrollingActivity extends AppCompatActivity {


    private ListView listView;
    ArrayList<String> llista;
    ArrayAdapter<String> arrayAdapter;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 10;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 50;

    BroadcastReceiver battery = new BatteryReceiver();


    private static final int REQUEST_NEW_LINE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), NovaActivity.class);
                startActivityForResult(intent, REQUEST_NEW_LINE);

            }
        });

        IntentFilter filter = new IntentFilter(CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        registerReceiver(battery, filter);

        /* A dalt, exemple de listener al floating action button (botó que flota, i permet una
        acció principal per exemple, se sol usar per crear un nou contacte al llistat de l agenda
        de contactes, o nova trucada a la activity del telèfon */


        setTitle("PT14 - Scrolling ListView");

        listView = findViewById(R.id.listView1);
        llista = new ArrayList<>(asList("Open Gmail i més", "Només Gmail, mailto:", "Show alarms",
                "Obrir PT12", "Obrir Navegador Propi de PT13", "Obrir App PT13",
                "Fer foto", "Escollir foto de galeria",
                "Query mapa Edt", "Buscar a la Web \"aprendre Android\""));

        llista.add("Obrir Telegram, sino hi és, apps tipus SEND/SHARE"); //10
        llista.add("Calendari");//11
        llista.add("Insertar contacte");//12
        llista.add("Wifi"); //13
        llista.add("sms to");//14
        llista.add("Calculadora");//15
        llista.add("Youtube");//16

        //tots trets de:
        //https://developer.android.com/guide/components/intents-common

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, llista);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                String url;
                switch (position) {
                    case 0:
                        //ofereix tots per enviar...
                        intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("*/*");
                        String[] addresses = {"ebarbeito@correu.escoladeltreball.org", "ebarbeit@xtec.cat"};
                        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "testing app pt14");
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }

                        break;
                    case 1:
                        //força només gmail
                        intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:"));
                        String[] addresses1 = {"ebarbeito@correu.escoladeltreball.org", "ebarbeit@xtec.cat"};
                        intent.putExtra(Intent.EXTRA_EMAIL, addresses1);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "app pt14");

                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);

                        } else
                            Toast.makeText(ScrollingActivity.this, "No pot obrir cap", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        intent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            try {
                                startActivity(intent);

                            } catch (Exception e) {
                                Log.d("test", "onItemClick: " + e.getCause() + e.getMessage());

                            }
                        } else
                            Toast.makeText(ScrollingActivity.this, "No pot obrir Show_Alarms", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        //Mostra la teva activitat 12
                        intent = new Intent("com.manuelgarcia.pt12.MainActivity");
                        if (intent.resolveActivity(getPackageManager()) == null) {
                            Log.d("test", "Couldn't find it:alternatives showing");
                            //   intent = new Intent(Intent.ACTION_VIEW);
                        } else startActivity(intent);
                        break;

                    case 4:
                        url = "http://www.escoladeltreball.org";
                        intent = new Intent("com.manuelgarcia.pt13b.NavegadorPropi", Uri.parse(url));
                        if (intent.resolveActivity(getPackageManager()) == null) {
                            Log.d("test", "Couldn't find it:alternatives showing");
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        }
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent("com.manuelgarcia.pt13b.MainActivity");
                        startActivity(intent);
                        break;
                    case 6:
                        int permCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.CAMERA);
                        if (permCheck == PackageManager.PERMISSION_GRANTED) {
                            intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            startActivity(intent);
                        } else {
                            requestCameraPermission();
                        }

                        break;
                    case 7:
                        intent = new Intent(getApplicationContext(), ShowPictureAct.class);
                        startActivity(intent);
                        break;
                    case 8:
                        intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("geo:0,0?q=escola del treball"));
                        startActivity(intent);
                        break;
                    case 9:
                        intent = new Intent(Intent.ACTION_WEB_SEARCH);
                        intent.putExtra(SearchManager.QUERY, "learn android");
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                        break;

                    case 10:
                        String miss = "què fas?";
                        intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, miss);
                        intent.setType("text/plain");
                        intent.setPackage("org.telegram.messenger");

                        // TODO: 19/11/19 obrir (intentar-ho al menys) aquests de sota a altres línies de la ListView
                        // intent.setPackage("com.google.android.youtube");
                        //intent.setPackage("com.spotify.music");
                        //intent.setPackage("com.termux");
                        // intent.setPackage("org.mozilla.firefox");

                        if (intent.resolveActivity(getPackageManager()) == null) {
                            Log.d("test", "Couldn't find it:alternatives showing");
                            intent = new Intent(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT, miss);
                            intent.setType("text/plain");
                        }
                        startActivity(intent);
                        break;

                    case 11:
                        intent = new Intent(Intent.ACTION_INSERT)
                                .setData(CalendarContract.Events.CONTENT_URI)
                                .putExtra(CalendarContract.Events.TITLE, "Examen Android")
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
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, "don diable");
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, "diable@gmail.com");
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, "666666666");
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                        break;
                    case 13:
                        //https://developer.android.com/guide/components/intents-common#Settings
                        intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                        break;
                    case 14:
                        intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra("sms_body", "missatge");
                        if (intent.resolveActivity(getPackageManager()) == null) {
                            Log.d("test", "Couldn't find it:alternatives showing");
                            intent = new Intent(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT, "missatge sms");
                            intent.setType("text/plain");
                        }
                        startActivity(intent);
                        break;
                    case 15:
                        //Mostra la teva calculadora de activitat 13
                        intent = new Intent("com.manuelgarcia.pt13b.Calculadora");
                        if (intent.resolveActivity(getPackageManager()) == null) {
                            Log.d("test", "Couldn't find it:alternatives showing");
                        } else startActivity(intent);
                        break;
                    case 16:
                        // TODO Youtube
                        String search = "rock";
                        intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, search);
                        intent.setType("text/plain");
                        intent.setPackage("com.google.android.youtube");
                        if (intent.resolveActivity(getPackageManager()) == null) {
                            Log.d("test", "Couldn't find it:alternatives showing");
                        } else startActivity(intent);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + position);
                }

            }
        });
    }

    // TODO Notificacion Alarma.


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_NEW_LINE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {

                String res = extras.getString("nomAct");

                llista.add(res);
                arrayAdapter.notifyDataSetChanged();


                //igual que PT12
                CoordinatorLayout layout = findViewById(R.id.linearLayout2);
                //layout.addView();
                Snackbar.make(layout.getRootView(), "Afegit nou Intent", Snackbar.LENGTH_LONG)
                        .setAction("Desfer", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("test", "onClick: passa a snackbar");
                                llista.remove(llista.size() - 1);
                                Log.d("test", "onClick: passa llista size" + llista.size());

                                arrayAdapter.notifyDataSetChanged();
                                // Toast.makeText(ScrollingActivity.this, "Desfeta", Toast.LENGTH_SHORT).show();
                            }
                        }).show();

            }

        }

    }


    private void requestCameraPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(ScrollingActivity.this
                , Manifest.permission.CAMERA)) {

            new AlertDialog.Builder(this)
                    .setTitle("Es necessita permís de càmera")
                    .setMessage("Per fer fotos necessitem accedir a la càmera")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ScrollingActivity.this
                                    , new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    })
                    .setNegativeButton("cancel.lar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(ScrollingActivity.this
                    , new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, do your work....
                    Log.d("onRequestPerm", "donats");
                    try {
                        startActivity(new Intent("android.media.action.IMAGE_CAPTURE"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("onReq: ", e.getMessage() + e.getCause());
                    }

                } else {
                    // permission denied
                    // Disable the functionality that depends on this permission.
                    Log.d("ajop", "que ha pasat");
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        startActivity(new Intent("android."));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("onReq: ", e.getMessage() + e.getCause());
                    }

                } else {
                    Log.d("ajop", "que ha pasat");
                }
                return;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
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
        if (id == R.id.phone_call) {
            startActivity(new Intent(this, Call.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(battery);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(battery);
    }
}
