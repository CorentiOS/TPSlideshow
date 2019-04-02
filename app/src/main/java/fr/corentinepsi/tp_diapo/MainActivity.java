package fr.corentinepsi.tp_diapo;

        import android.content.Intent;
        import android.content.pm.ActivityInfo;
        import android.os.CountDownTimer;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Diapo diapo = new Diapo();
    int[] photoList = {
            R.drawable.un,
            R.drawable.deux,
            R.drawable.trois,
            R.drawable.quatre,
            R.drawable.cinq,
            R.drawable.six,
    };
    ImageView ui_imgDiapo;
    TextView ui_infoText;
    TextView ui_countdownText;
    CountDownTimer _countDownTimer;
    String __millis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED); //gerer orientation
        ui_infoText = findViewById(R.id.infoText); //timer text
        ui_infoText.setText(getString(R.string.timer)); //Timer text
        ui_imgDiapo = findViewById(R.id.imageView); //ImageView
        ui_countdownText = findViewById(R.id.countdownText); //Décompte

        Intent i = getIntent(); //si intent de l'option
        if (i != null){
            __millis = i.getStringExtra("millis");
        }

        if (__millis == null) { //début : 3sec
            __millis = "4000"; //4000 = 3sec
        }
        myCountDown(Integer.parseInt(__millis)); //fonction timer avec nb sec

        if(savedInstanceState != null) //Restauration de l'image actuelle après rotation
        {
            ui_imgDiapo.setImageResource(photoList[savedInstanceState.getInt("photoListPos")]);
            diapo.setRang(savedInstanceState.getInt("photoListPos"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) //permet de save rang photo avant rotation
    {
        super.onSaveInstanceState(outState);
        outState.putInt("photoListPos",diapo.getRang());
    }

    public void myCountDown(int millis) { //Fonction décompte, argument nb sec 1 sec = 2000 millis
        _countDownTimer = new CountDownTimer(millis, 1000) {
            public void onTick(long millisUntilFinished) {
                ui_countdownText.setText(String.valueOf(millisUntilFinished / 1000)); //txt en direct
            }

            public void onFinish() {
                diapo.addRang(1); //rang +1
                ui_imgDiapo.setImageResource(photoList[diapo.getRang()]); //image +1
                this.start(); //restart timer

            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //override pour afficher les btn headbar
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.home,menu); //home.xml
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //action btn settings
        int i = item.getItemId();
        if (i==R.id.settingsBtn) { //btn settings
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class); //intent
            intent.putExtra("millis", __millis); //envoi des millis
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out); //fade
        }

        if (i==R.id.restartBtn) { //btn restart
            Toast.makeText(MainActivity.this, "Le diaporama a été réinitialisé", Toast.LENGTH_SHORT).show(); //toast
            diapo.setRang(0); //rang = 0
            __millis = "4000"; //millis = 4000
            ui_imgDiapo.setImageResource(photoList[diapo.getRang()]); //img diapo = 0
            _countDownTimer.cancel(); //delete countdown
            myCountDown(Integer.parseInt(__millis)); //restart countdown 3sec
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickNext (View button) { //btn Next
        diapo.addRang(1); //rang +1
        ui_imgDiapo.setImageResource(photoList[diapo.getRang()]); //image+1
        _countDownTimer.cancel(); //supprime timer
        myCountDown(Integer.parseInt(__millis)); //nouveau timer
    }

    public void clickPrevious (View button) { //btn Précédent
        diapo.subsRang(1); //rang -1
        ui_imgDiapo.setImageResource(photoList[diapo.getRang()]); //image -1
        _countDownTimer.cancel(); //supprime timer
        myCountDown(Integer.parseInt(__millis)); //nouveau timer
    }
}
