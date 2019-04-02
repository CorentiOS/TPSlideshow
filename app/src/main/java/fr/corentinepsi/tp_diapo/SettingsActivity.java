package fr.corentinepsi.tp_diapo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class SettingsActivity extends AppCompatActivity {
    TextView ui_secondTextView;
    SeekBar ui_seekBar;
    String __millis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Options du diaporama"); //changer title

        Intent intent = getIntent(); //get intent
        __millis = intent.getStringExtra("millis"); //get millis
        int _millisToSecond = (Integer.parseInt(__millis) / 1000) - 1; //convert millis -> sec
        ui_secondTextView = findViewById(R.id.secondTextView); //nbSecondes
        ui_seekBar = findViewById(R.id.seekBar4); //seekbar
        ui_seekBar.setProgress(_millisToSecond); //rang seekbar
        ui_secondTextView.setText(String.valueOf(_millisToSecond)); //nbSecondes set text

        ui_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { //Listener sur les rangs
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ui_secondTextView.setText(String.valueOf(progress)); //update le text en direct
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void clickSave(View button) { //btn Save
        __millis = String.valueOf(ui_seekBar.getProgress() * 1000 + 1000); //convert to millis
        Intent intent = new Intent(this, MainActivity.class); //intent
        intent.putExtra("millis", __millis); //envoi des millis
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out); //fade
        Toast.makeText(SettingsActivity.this, "Les modifications ont été enregistrées", Toast.LENGTH_SHORT).show(); //toast
    }
}
