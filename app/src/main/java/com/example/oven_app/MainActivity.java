package com.example.oven_app;

import static java.lang.Math.pow;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 6000;
    private boolean timerRunning = false;

    private CountDownTimer countDownTimer_f;
    private long timeLeftInMilliseconds_f = 6000;
    private boolean timerRunning_f = false;

    private CountDownTimer countDownTimer_heated;
    private long heated_time = 1000; // xronos mexri na kriwsei to mati

    private boolean estia_1_clicked = false;
    private boolean estia_2_clicked = false;
    private boolean estia_3_clicked = false;
    private boolean estia_4_clicked = false;

    private boolean f1 = false;
    private boolean f2 = false;
    private boolean f3 = false;
    private boolean f4 = false;
    private Integer [] thermokrasies_estiwn = {0,1,2,3,4,5,6,7,8,9};
    private Integer [] thermokrasies_fournou = {0,30,50,80,100,130,150,180,200,230,250};

    private TextView text_estia1;
    private TextView text_estia2;
    private TextView text_estia3;
    private TextView text_estia4;

    private FragmentContainerView timer_end;
    private FragmentContainerView xronometra;
    private String [] kati;
    private String wra = "00";
    private String lepta = "00";
    private String deyterolepta="00";

    private boolean changing_esties_timer;
    private boolean changing_fournou_timer;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changing_esties_timer = false;
        changing_fournou_timer = false;

        text_estia1 = (TextView) findViewById(R.id.textview_thermokrasia_1);
        text_estia2 = (TextView) findViewById(R.id.textview_thermokrasia_2);
        text_estia3 = (TextView) findViewById(R.id.textview_thermokrasia_3);
        text_estia4 = (TextView) findViewById(R.id.textview_thermokrasia_4);

        ImageView image_inside_oven = (ImageView) findViewById(R.id.in_oven_leitourgia);
        TextView text_inside_oven = (TextView) findViewById(R.id.in_oven_temp);

        ImageView border = findViewById(R.id.border_time);
        border.setTranslationZ(-10);

        ImageView vasi_estiwn = findViewById(R.id.bash_estiwn);
        vasi_estiwn.setTranslationZ(-10);

        ImageButton estia_1 = findViewById(R.id.estia_1_button);
        ImageButton estia_2 = findViewById(R.id.estia_2_button);
        ImageButton estia_3 = findViewById(R.id.estia_3_button);
        ImageButton estia_4 = findViewById(R.id.estia_4_button);

        ImageButton gr_aer = findViewById(R.id.grill_aeras);
        ImageButton aeras = findViewById(R.id.aeras);
        ImageButton pk = findViewById(R.id.panw_katw);
        ImageButton grill = findViewById(R.id.grill);

        ImageButton info_timer = (ImageButton) findViewById(R.id.button_info_timer);
        ImageButton info_estias = (ImageButton) findViewById(R.id.button_info_esties);
        ImageButton info_fournoy = (ImageButton) findViewById(R.id.button_info_fournos);
        TextView pop_up = (TextView) findViewById(R.id.pop_up_inside);
        Button pop_up_ok = (Button) findViewById(R.id.pop_up_ok);
        //pop_up.setTranslationZ(10);
        pop_up_ok.setTranslationZ(100);

        timer_end = (FragmentContainerView) findViewById(R.id.fragmentContainerView);
        timer_end.setTranslationZ(20);

        xronometra = (FragmentContainerView) findViewById(R.id.fragmentXronometro);
        xronometra.setTranslationZ(20);


        ArrayAdapter adapter = new ArrayAdapter<Integer>(this,R.layout.lista,thermokrasies_estiwn);
        ListView list_view = (ListView) findViewById(R.id.list_temp_esties);
        list_view.setFadingEdgeLength(200);
        list_view.setAdapter(adapter);

        Button timer_estias = findViewById(R.id.timer_estias);


        timer_estias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xronometra.setVisibility(View.VISIBLE);
                TextView test =xronometra.findViewById(R.id.textView2);
                test.setText("Ρυθμίστε το χρονόμετρό σας για τις εστίες");
            }
        });


        Button timer_fournos = findViewById(R.id.timer_fournoy);

        timer_fournos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xronometra.setVisibility(View.VISIBLE);
                TextView test =xronometra.findViewById(R.id.textView2);
                test.setText("Ρυθμίστε το χρονόμετρό σας για το φούρνο");
            }
        });


        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("Epilegmeno",thermokrasies_estiwn[position]+"");
                if(estia_1_clicked){
                    if(position != 0 ) text_estia1.setText(position+"");
                    else {
                        if(!text_estia1.getText().equals("")){
                            text_estia1.setText("!");
                            startTimer_forcooling(1);
                        }
                        else{
                            text_estia1.setText("");
                        }

                    }
                    estia_1.setImageDrawable(getResources().getDrawable(R.drawable.eye));
                    estia_1_clicked = false;
                }
                if(estia_2_clicked){
                    if(position != 0 ) text_estia2.setText(position+"");
                    else {
                        if(!text_estia2.getText().equals("")){
                            text_estia2.setText("!");
                            startTimer_forcooling(2);
                        }
                        else{
                            text_estia2.setText("");
                        }
                    }
                    estia_2.setImageDrawable(getResources().getDrawable(R.drawable.eye));
                    estia_2_clicked = false;
                }
                if(estia_3_clicked){
                    if(position != 0 ) text_estia3.setText(position+"");
                    else {
                        if(!text_estia3.getText().equals("")){
                            text_estia3.setText("!");
                            startTimer_forcooling(3);
                        }
                        else{
                            text_estia3.setText("");
                        }
                    }
                    estia_3.setImageDrawable(getResources().getDrawable(R.drawable.eye));
                    estia_3_clicked = false;
                }
                if(estia_4_clicked){
                    if(position != 0 ) text_estia4.setText(position+"");
                    else {
                        if(!text_estia4.getText().equals("")){
                            text_estia4.setText("!");
                            startTimer_forcooling(4);
                        }
                        else{
                            text_estia4.setText("");
                        }
                    }
                    estia_4.setImageDrawable(getResources().getDrawable(R.drawable.eye));
                    estia_4_clicked = false;
                }

            }
        });

        ArrayAdapter adapter_f = new ArrayAdapter<Integer>(this,R.layout.lista,thermokrasies_fournou);
        ListView list_view_f = (ListView) findViewById(R.id.list_temp_fournos);
        list_view_f.setFadingEdgeLength(150);
        list_view_f.setAdapter(adapter_f);

        list_view_f.setOnItemClickListener(new AdapterView.OnItemClickListener() { //-------------------------------------------------<><><><><><><><
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(f1){
                    if(position != 0 ) {
                        text_inside_oven.setText(thermokrasies_fournou[position]+"");
                        image_inside_oven.setImageDrawable(getResources().getDrawable(R.drawable.panwkatw_in_use_));
                    }
                    else {
                        text_inside_oven.setText("");
                        image_inside_oven.setImageDrawable(getResources().getDrawable(R.drawable.white_background));

                    }

                    pk.setImageDrawable(getResources().getDrawable(R.drawable.panwkatw_));
                    f1 = false;
                }
                else if(f2){
                    if(position != 0 ) {
                        text_inside_oven.setText(thermokrasies_fournou[position]+"");
                        image_inside_oven.setImageDrawable(getResources().getDrawable(R.drawable.aeras_in_use_));
                    }
                    else {
                        text_inside_oven.setText("");
                        image_inside_oven.setImageDrawable(getResources().getDrawable(R.drawable.white_background));
                    }
                    aeras.setImageDrawable(getResources().getDrawable(R.drawable.aeras_));
                    f2 = false;
                }
                else if(f3){
                    if(position != 0 ) {
                        text_inside_oven.setText(thermokrasies_fournou[position]+"");
                        image_inside_oven.setImageDrawable(getResources().getDrawable(R.drawable.aeras_grill_in_use_));
                    }
                    else {
                        text_inside_oven.setText("");
                        image_inside_oven.setImageDrawable(getResources().getDrawable(R.drawable.white_background));
                    }
                    gr_aer.setImageDrawable(getResources().getDrawable(R.drawable.aeras_grill_));
                    f3 = false;
                }
                else if(f4){
                    if(position != 0 ) {
                        text_inside_oven.setText(thermokrasies_fournou[position]+"");
                        image_inside_oven.setImageDrawable(getResources().getDrawable(R.drawable.grill_in_use_));
                    }
                    else {
                        text_inside_oven.setText("");
                        image_inside_oven.setImageDrawable(getResources().getDrawable(R.drawable.white_background));
                    }
                    grill.setImageDrawable(getResources().getDrawable(R.drawable.grill_));
                    f4 = false;
                }

            }
        });



        setTitle("                                                       ΔΙΕΠΑΦΗ ΦΟΥΡΝΟΥ");
        LocalDateTime dtime = LocalDateTime.now();
        String now_time = dtime.toLocalTime().toString();
        TextView pm_ap_update = findViewById(R.id.pm_am);
        //Log.d("pm",now_time.substring(0,2));
        if(Integer.parseInt(now_time.substring(0,2)) < 12){
            pm_ap_update.setText("πμ");
        }
        else{
            pm_ap_update.setText("μμ");
        }

        estia_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!estia_1_clicked){
                    estia_1.setImageDrawable(getResources().getDrawable(R.drawable.eye_in_use));
                    estia_1_clicked = true;
                }else{
                    estia_1.setImageDrawable(getResources().getDrawable(R.drawable.eye));
                    estia_1_clicked = false;
                }
            }
        });


        estia_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!estia_2_clicked){
                    estia_2.setImageDrawable(getResources().getDrawable(R.drawable.eye_in_use));
                    estia_2_clicked = true;
                }else{
                    estia_2.setImageDrawable(getResources().getDrawable(R.drawable.eye));
                    estia_2_clicked = false;
                }
            }
        });


        estia_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!estia_3_clicked){
                    estia_3.setImageDrawable(getResources().getDrawable(R.drawable.eye_in_use));
                    estia_3_clicked = true;
                }else{
                    estia_3.setImageDrawable(getResources().getDrawable(R.drawable.eye));
                    estia_3_clicked = false;
                }
            }
        });


        estia_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!estia_4_clicked){
                    estia_4.setImageDrawable(getResources().getDrawable(R.drawable.eye_in_use));
                    estia_4_clicked = true;
                }else{
                    estia_4.setImageDrawable(getResources().getDrawable(R.drawable.eye));
                    estia_4_clicked = false;
                }
            }
        });

        //------------------------------------------------------------------------------------------------

        aeras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!f2){
                    aeras.setImageDrawable(getResources().getDrawable(R.drawable.aeras_in_use_));
                    grill.setImageDrawable(getResources().getDrawable(R.drawable.grill_));
                    gr_aer.setImageDrawable(getResources().getDrawable(R.drawable.aeras_grill_));
                    pk.setImageDrawable(getResources().getDrawable(R.drawable.panwkatw_));

                    f2 = true;
                    f1 = false;
                    f3 = false;
                    f4 = false;
                }else{
                    aeras.setImageDrawable(getResources().getDrawable(R.drawable.aeras_));
                    f2 = false;
                }
            }
        });


        pk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!f1){
                    aeras.setImageDrawable(getResources().getDrawable(R.drawable.aeras_));
                    grill.setImageDrawable(getResources().getDrawable(R.drawable.grill_));
                    gr_aer.setImageDrawable(getResources().getDrawable(R.drawable.aeras_grill_));
                    pk.setImageDrawable(getResources().getDrawable(R.drawable.panwkatw_in_use_));

                    f1 = true;
                    f2 = false;
                    f3 = false;
                    f4 = false;
                }else{
                    pk.setImageDrawable(getResources().getDrawable(R.drawable.panwkatw_));
                    f1 = false;
                }
            }
        });


        gr_aer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!f3){
                    aeras.setImageDrawable(getResources().getDrawable(R.drawable.aeras_));
                    grill.setImageDrawable(getResources().getDrawable(R.drawable.grill_));
                    gr_aer.setImageDrawable(getResources().getDrawable(R.drawable.aeras_grill_in_use_));
                    pk.setImageDrawable(getResources().getDrawable(R.drawable.panwkatw_));

                    f3 = true;
                    f1 = false;
                    f2 = false;
                    f4 = false;

                }else{
                    gr_aer.setImageDrawable(getResources().getDrawable(R.drawable.aeras_grill_));
                    f3 = false;
                }
            }
        });


        grill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!f4){
                    aeras.setImageDrawable(getResources().getDrawable(R.drawable.aeras_));
                    grill.setImageDrawable(getResources().getDrawable(R.drawable.grill_in_use_));
                    gr_aer.setImageDrawable(getResources().getDrawable(R.drawable.aeras_grill_));
                    pk.setImageDrawable(getResources().getDrawable(R.drawable.panwkatw_));

                    f4 = true;
                    f1 = false;
                    f2 = false;
                    f3 = false;

                }else{
                    grill.setImageDrawable(getResources().getDrawable(R.drawable.grill_));
                    f4 = false;
                }
            }
        });


        // ---------------------------------------------------------------------------------
        // turn off

        ImageButton kleisimo_estiwn = (ImageButton) findViewById(R.id.turn_off_esties);
        kleisimo_estiwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estia_1.setImageDrawable(getResources().getDrawable(R.drawable.eye));
                estia_2.setImageDrawable(getResources().getDrawable(R.drawable.eye));
                estia_3.setImageDrawable(getResources().getDrawable(R.drawable.eye));
                estia_4.setImageDrawable(getResources().getDrawable(R.drawable.eye));

                estia_1_clicked = false;
                estia_2_clicked = false;
                estia_3_clicked = false;
                estia_4_clicked = false;

                if(!text_estia1.getText().equals("")){
                    startTimer_forcooling(1);
                    text_estia1.setText("!");
                }
                if(!text_estia2.getText().equals("")){
                    startTimer_forcooling(2);
                    text_estia2.setText("!");
                }
                if(!text_estia3.getText().equals("")){
                    startTimer_forcooling(3);
                    text_estia3.setText("!");
                }
                if(!text_estia4.getText().equals("")){
                    startTimer_forcooling(4);
                    text_estia4.setText("!");
                }
            }
        });

        ImageButton kleisimo_fournoy = (ImageButton) findViewById(R.id.turn_off_fournos);
        kleisimo_fournoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aeras.setImageDrawable(getResources().getDrawable(R.drawable.aeras_));
                grill.setImageDrawable(getResources().getDrawable(R.drawable.grill_));
                gr_aer.setImageDrawable(getResources().getDrawable(R.drawable.aeras_grill_));
                pk.setImageDrawable(getResources().getDrawable(R.drawable.panwkatw_));

                f1 = false;
                f2 = false;
                f3 = false;
                f4 = false;

                text_inside_oven.setText("");
                image_inside_oven.setImageDrawable(getResources().getDrawable(R.drawable.white_background));
            }
        });

        // -----------------------------------------------------------------------------------------------------
        // info button pop up messages

        pop_up_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop_up.setText("");
                pop_up_ok.setVisibility(View.GONE);
                pop_up.setVisibility(View.GONE);
            }
        });

        info_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop_up_ok.setVisibility(View.VISIBLE);
                pop_up.setVisibility(View.VISIBLE);
                pop_up.setText("Χρονόμετρα : \n \t Υπάρχουν ξεχωριστά χρονόμετρα για τις εστίες και των φούρνο που ρυθμίζονται πατώντας τα παρακάτω κουμπιά. Εστίες και φούρνος δεν κλείνουν όταν σταματά το timer.");

            }
        });

        info_estias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop_up_ok.setVisibility(View.VISIBLE);
                pop_up.setVisibility(View.VISIBLE);
                pop_up.setText("Εστίες : \n \t Η επιλογή εστίας πραγματοποιείται πατώντας πάνω στο σχήμα της εστίας στο σχήμα παρακάτω. (Μπορούν να ρυθμιστούν πολλές εστίες ταυτόχρονα)");
            }
        });

        info_fournoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop_up_ok.setVisibility(View.VISIBLE);
                pop_up.setVisibility(View.VISIBLE);
                pop_up.setText("Λειτουργίες Φούρνου : \n \t Πάνω κάτω \n \t Αέρας \n \t Αέρας γκριλ \n \t Γκριλ \n \t Ρύθμιση λειτουργίας πριν από θερμοκρασία.");
            }
        });

        Button hiddbutton = (Button) findViewById(R.id.hidden_button);
        hiddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("effe","asjdhfklasdhfkjhasdkjlh;adjhf");
                TextView v = (TextView) findViewById(R.id.hidden_info);
                String info_from_frag;
                info_from_frag = v.getText().toString();
                //TextView temporary = xronometra.findViewById(R.id.textView2);

                // flag

                String epilogh;

                if(!info_from_frag.equals("-") ){
                    epilogh = info_from_frag.split("#")[1];
                    info_from_frag = info_from_frag.split("#")[0];
                    Log.d("effe"," ok ");
                    if(epilogh.equals("e")){
                        if(timerRunning) countDownTimer.cancel();
                        Log.d("effe"," stis esties");
                        wra = info_from_frag.split(",")[0];
                        lepta = info_from_frag.split(",")[1];
                        deyterolepta = info_from_frag.split(",")[2];
                        timerRunning = true;
                        long to_add = (long)Integer.parseInt(wra)*36*(long)pow(10,8);
                        startTimer_e(to_add+Integer.parseInt(lepta)*60000+Integer.parseInt(deyterolepta)*1000);
                        changing_esties_timer = false;
                        info_from_frag = "-";
                    }
                    if(epilogh.equals("f")){
                        if(timerRunning_f) countDownTimer_f.cancel();
                        Log.d("effe"," ston fourno");
                        wra = info_from_frag.split(",")[0];
                        lepta = info_from_frag.split(",")[1];
                        deyterolepta = info_from_frag.split(",")[2];
                        timerRunning_f = true;
                        long to_add = (long)Integer.parseInt(wra)*36*(long)pow(10,8);
                        startTimer_f(to_add+Integer.parseInt(lepta)*60000+Integer.parseInt(deyterolepta)*1000);
                        changing_fournou_timer = false;
                        info_from_frag = "-";
                    }
                }
            }
        });

        // ---------------------------------
        Button stop_it = (Button) findViewById(R.id.button_stop_timer);

        stop_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });
    }

    private void startTimer_f(long time_in_mil) {
        timeLeftInMilliseconds_f = time_in_mil;
        countDownTimer_f = new CountDownTimer(timeLeftInMilliseconds_f,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds_f = l;
                updateTimer_f();
            }

            @Override
            public void onFinish() {
                //timer_end = (FragmentContainerView) findViewById(R.id.fragmentContainerView);
                //timer_end.setTranslationZ(20);
                Log.d("toastaki","telos_timer_fournoy");
                timer_end.setVisibility(View.VISIBLE);
                play(); // --------------------------<><><><><
                timerRunning_f = false;
            }
        }.start();

        timerRunning_f = true;
    }


    private void startTimer_e(long time_in_mil) {
        timeLeftInMilliseconds = time_in_mil;
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                //timer_end = (FragmentContainerView) findViewById(R.id.fragmentContainerView);
                //timer_end.setTranslationZ(20);
                timer_end.setVisibility(View.VISIBLE);
                Log.d("toastaki","telos_timer_estia");
                play(); // --------------------------<><><><><
                timerRunning = false;
            }
        }.start();

        timerRunning = true;
    }

    public void play(){
        if(player == null){
            player = MediaPlayer.create(this,R.raw.familiar_hxos_kouzinas);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    play();
                }
            });
        }
        player.start();
    }

    public void stop(){
        if(player !=null){
            player.release();
            player = null;
        }
    }

    private void startTimer_forcooling(int estia_position) {
        countDownTimer_heated = new CountDownTimer(heated_time,1000) {
            @Override
            public void onTick(long l) {
                // do nothing
            }

            @Override
            public void onFinish() {
                String temp;
                if(estia_position == 1){
                    text_estia1.setText("");
                }
                else if(estia_position == 2){
                    text_estia2.setText("");
                }
                else if(estia_position == 3){
                    text_estia3.setText("");
                }
                else{
                    text_estia4.setText("");
                }

            }
        }.start();

        timerRunning = true;
    }

    private void updateTimer_f() {
        /*
        int minutes = (int)timeLeftInMilliseconds_f/ 60000;
        int seconds = (int)timeLeftInMilliseconds_f% 60000 / 1000;
        */

        int wres = (int) ( timeLeftInMilliseconds_f/(36*pow(10,8)) );
        long minutes = timeLeftInMilliseconds_f % (long)(36*pow(10,8)) / 60000;
        if(minutes> 59) {
            minutes -= 999;
            minutes /= 1000;
        }
        long seconds = timeLeftInMilliseconds_f % (long)(36*pow(10,8)) % 60000 / 1000;


        String timeLeftText;

        if(wres<10){
            timeLeftText = "0" + wres + ":";
        }else{
            timeLeftText = "" + wres + ":";
        }

        if(minutes<10){
            timeLeftText += "0" + minutes + ":";
        }else{
            timeLeftText += "" + minutes + ":";
        }
        if(seconds<10) timeLeftText+="0";
        timeLeftText += seconds;

        TextView timer_fournou_box = findViewById(R.id.time_left_fournoy);
        timer_fournou_box.setText(timeLeftText);
    }

    private void updateTimer() {

        int wres = (int) ( timeLeftInMilliseconds/(36*pow(10,8)) );
        long minutes = timeLeftInMilliseconds % (long)(36*pow(10,8)) / 60000;
        if(minutes> 59) {
            minutes -= 999;
            minutes /= 1000;
        }
        long seconds = timeLeftInMilliseconds % (long)(36*pow(10,8)) % 60000 / 1000;
        String timeLeftText;

        Log.d("effe_timer","wres--> "+wres);
        Log.d("effe_timer","lepta--> "+minutes);
        Log.d("effe_timer","deyterolepta--> "+seconds);

        if(wres<10){
            timeLeftText = "0" + wres + ":";
        }else{
            timeLeftText = "" + wres + ":";
        }

        if(minutes<10){
             timeLeftText += "0" + minutes + ":";
        }else{
             timeLeftText += "" + minutes + ":";
        }

        if(seconds<10) timeLeftText+="0";
        timeLeftText += seconds;

        TextView timer_estias_box = findViewById(R.id.time_left_estia);
        timer_estias_box.setText(timeLeftText);


    }


}