package comdouglas_batista.github.starlight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class iniciacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciacao);


        ImageView lam = (ImageView) findViewById(R.id.lam);
        ImageView nom = (ImageView) findViewById(R.id.nom);

        Animation anime = AnimationUtils.loadAnimation(this,R.anim.trans);

        lam.startAnimation(anime);
        nom.startAnimation(anime);

        final Intent tela = new Intent(this, MainActivity.class);

        Thread timer = new Thread() {
            public void run(){
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(tela);
                    finish();
                }
            }
        };
        timer.start();
    }
}
