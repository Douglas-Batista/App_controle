package comdouglas_batista.github.starlight;
//bibliotecas importadas(usadas)(importadas altomaticamente)
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Creditos extends AppCompatActivity {

    //Declaração do botão
    Button Voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);
        Voltar = (Button)findViewById(R.id.Voltar);

        //Ação que o botão vai realizar
        Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //finaliza a atividade após o toque do botão
                finish();
            };
        });
    }
}


