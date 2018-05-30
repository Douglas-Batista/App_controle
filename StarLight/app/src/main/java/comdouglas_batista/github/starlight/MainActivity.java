package comdouglas_batista.github.starlight;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //Declaração dos botões
    Button conexao, led1, led2, led3, led4, led5,led6, dled1, dled2, dled3, dled4, dled5,dled6, Creditos;

    private static final int ativaBT = 1;//para ativar o bluetooth
    private static final int pedeconexao = 2;//usado pra pedir conexão
    ConnectedThread Threadconexao; //armazena os dados que serão enviados
    BluetoothAdapter MeuBluetooth = null; //é o ponto de entrada para toda a interação com o Bluetooth
    BluetoothDevice Meudevice = null;//usado para solicitar uma conexão com um dispositivo remoto por meio de um BluetoothSocket
    BluetoothSocket Meusocket = null;//é o ponto de conexão que permite que um aplicativo permute dados com outro dispositivo Bluetooth
    boolean conectar = false; //usado cara conectar no bluetooth
    private static String MAC = null;//armazena o MAC do dispositivo para ser utilizado na conexão

    UUID Meuuuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//um formato padrão usado para identificar informações do bluetooth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declara os botões e conectada com os ID dos botões criados no layout
        conexao = (Button)findViewById(R.id.conexao);
        led1 = (Button)findViewById(R.id.bled1);
        led2 = (Button)findViewById(R.id.bled2);
        led3 = (Button)findViewById(R.id.bled3);
        led4 = (Button)findViewById(R.id.bled4);
        led5 = (Button)findViewById(R.id.bled5);
        led6 = (Button)findViewById(R.id.bled6);
        dled1 = (Button)findViewById(R.id.bled1d);
        dled2 = (Button)findViewById(R.id.bled2d);
        dled3 = (Button)findViewById(R.id.bled3d);
        dled4 = (Button)findViewById(R.id.bled4d);
        dled5 = (Button)findViewById(R.id.bled5d);
        dled6 = (Button)findViewById(R.id.bled6d);
        Creditos = (Button)findViewById(R.id.Creditos);

        MeuBluetooth = BluetoothAdapter.getDefaultAdapter();//verifica se o dispositivo suporta a conexão com o bluetooth

        //verificar se tem bluetooth
        if(MeuBluetooth == null){
            Toast.makeText(getApplicationContext(),"Seu dispositivo não tem bluetooth",Toast.LENGTH_LONG).show();//mensagem flutuante

            //solicitar a ativação do bluetooth
        } else if(!MeuBluetooth.isEnabled()){
            Intent ativarBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativarBT, ativaBT);
        }

        conexao.setOnClickListener(new View.OnClickListener() {
            //solicita a ativação do bluetooth caso ele não esteja ativado
            @Override
            public void onClick(View v) { //definindo ações que ocorrerá ao conectar ou desconectar o bluetooth com o arduino
                if(conectar){
                    try {
                        Threadconexao.enviar("A");//desliga os leds caso desconecte do bluetooth
                        Threadconexao.enviar("B");
                        Threadconexao.enviar("C");
                        Threadconexao.enviar("D");
                        Threadconexao.enviar("E");
                        Threadconexao.enviar("F");
                        dled1.setBackgroundResource(R.drawable.pdesligar); //muda as cores dos botoes desligar
                        dled2.setBackgroundResource(R.drawable.pdesligar);
                        dled3.setBackgroundResource(R.drawable.pdesligar);
                        dled4.setBackgroundResource(R.drawable.pdesligar);
                        dled5.setBackgroundResource(R.drawable.pdesligar);
                        dled6.setBackgroundResource(R.drawable.pdesligar);
                        led1.setBackgroundResource(R.drawable.ligar);//muda as cores dos botoes ligar
                        led2.setBackgroundResource(R.drawable.ligar);
                        led3.setBackgroundResource(R.drawable.ligar);
                        led4.setBackgroundResource(R.drawable.ligar);
                        led5.setBackgroundResource(R.drawable.ligar);
                        led6.setBackgroundResource(R.drawable.ligar);

                        Meusocket.close(); //fecha a transmisão de dados
                        conectar = false; //desconecta
                        conexao.setText("Conectar");//troca o nome do botão
                        Toast.makeText(getApplicationContext(),"O bluetooth foi desconectado.",Toast.LENGTH_LONG).show();//mensagem flutuante

                    }catch (IOException erro){//caso ocorra um erro na desconexão
                        Toast.makeText(getApplicationContext(),"Ocorreu um erro.",Toast.LENGTH_LONG).show();//mensagem flutuante
                    }
                }else{
                    // ao conectar
                    Intent abreLista = new Intent(MainActivity.this, listadispositivos.class); //Abre a lista de dispoitivos
                    startActivityForResult(abreLista, pedeconexao); //conecta ao bluetooth
                }
            }
        });

        led1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//ações ao clicar no primeiro botão ligar

                if (conectar){
                    Threadconexao.enviar("a");//envia a letra "a" para ligar a led
                    led1.setBackgroundResource(R.drawable.pligar); //muda o layout do botão ligar definido no Drawable
                    dled1.setBackgroundResource(R.drawable.desligar);//muda o layout do botão desligar definido no Drawable
                }else{
                    Toast.makeText(getApplicationContext(),"O bluetooth não esta conectado.",Toast.LENGTH_LONG).show();//mensagem flutuante
                }
            }
        });

        led2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//ações ao clicar no primeiro botão ligar

                if (conectar){
                    Threadconexao.enviar("b");//envia a letra "b" para ligar a led
                    led2.setBackgroundResource(R.drawable.pligar);//muda o layout do botão ligar definido no Drawable
                    dled2.setBackgroundResource(R.drawable.desligar);//muda o layout do botão desligar definido no Drawable
                }else{
                    Toast.makeText(getApplicationContext(),"O bluetooth não esta conectado.",Toast.LENGTH_LONG).show();//mensagem flutuante
                }
            }
        });

        led3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//ações ao clicar no primeiro botão ligar

                if (conectar){
                    Threadconexao.enviar("c");//envia a letra "c" para ligar a led
                    led3.setBackgroundResource(R.drawable.pligar);//muda o layout do botão ligar definido no Drawable
                    dled3.setBackgroundResource(R.drawable.desligar);//muda o layout do botão desligar definido no Drawable
                }else{
                    Toast.makeText(getApplicationContext(),"O bluetooth não esta conectado.",Toast.LENGTH_LONG).show();//mensagem flutuante
                }
            }
        });

        led4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (conectar){
                    Threadconexao.enviar("d");//envia a letra "d" para ligar a led
                    led4.setBackgroundResource(R.drawable.pligar);//muda o layout do botão ligar definido no Drawable
                    dled4.setBackgroundResource(R.drawable.desligar);//muda o layout do botão desligar definido no Drawable
                }else{
                    Toast.makeText(getApplicationContext(),"O bluetooth não esta conectado.",Toast.LENGTH_LONG).show();//mensagem flutuante
                }
            }
        });

        led5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (conectar){
                    Threadconexao.enviar("e");//envia a letra "e" para ligar a led
                    led5.setBackgroundResource(R.drawable.pligar);//muda o layout do botão ligar definido no Drawable
                    dled5.setBackgroundResource(R.drawable.desligar);//muda o layout do botão desligar definido no Drawable
                }else{
                    Toast.makeText(getApplicationContext(),"O bluetooth não esta conectado.",Toast.LENGTH_LONG).show();//mensagem flutuante
                }
            }
        });

        led6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (conectar){
                    Threadconexao.enviar("f");//envia a letra "f" para ligar a led
                    led6.setBackgroundResource(R.drawable.pligar);//muda o layout do botão ligar definido no Drawable
                    dled6.setBackgroundResource(R.drawable.desligar);//muda o layout do botão desligar definido no Drawable
                }else{
                    Toast.makeText(getApplicationContext(),"O bluetooth não esta conectado.",Toast.LENGTH_LONG).show();//mensagem flutuante
                }
            }
        });

        dled1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (conectar){
                    Threadconexao.enviar("A");
                    led1.setBackgroundResource(R.drawable.ligar);
                    dled1.setBackgroundResource(R.drawable.pdesligar);
                }else{
                    Toast.makeText(getApplicationContext(),"O bluetooth não esta conectado.",Toast.LENGTH_LONG).show();
                }
            }
        });

        dled2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (conectar){
                    Threadconexao.enviar("B");
                    led2.setBackgroundResource(R.drawable.ligar);
                    dled2.setBackgroundResource(R.drawable.pdesligar);
                }else{
                    Toast.makeText(getApplicationContext(),"O bluetooth não esta conectado.",Toast.LENGTH_LONG).show();
                }
            }
        });

        dled3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (conectar){
                    Threadconexao.enviar("C");
                    led3.setBackgroundResource(R.drawable.ligar);
                    dled3.setBackgroundResource(R.drawable.pdesligar);
                }else{
                    Toast.makeText(getApplicationContext(),"O bluetooth não esta conectado.",Toast.LENGTH_LONG).show();
                }
            }
        });

        dled4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (conectar){
                    Threadconexao.enviar("D");//envia a letra "D" para desligar a led
                    led4.setBackgroundResource(R.drawable.ligar);//muda o layout do botão ligar definido no Drawable
                    dled4.setBackgroundResource(R.drawable.pdesligar);//muda o layout do botão desligar definido no Drawable
                }else{
                    Toast.makeText(getApplicationContext(),"O bluetooth não esta conectado.",Toast.LENGTH_LONG).show();//mensagem flutuante
                }
            }
        });

        dled5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (conectar){
                    Threadconexao.enviar("E");//envia a letra "E" para desligar a led
                    led5.setBackgroundResource(R.drawable.ligar);//muda o layout do botão ligar definido no Drawable
                    dled5.setBackgroundResource(R.drawable.pdesligar);//muda o layout do botão desligar definido no Drawable
                }else{
                    Toast.makeText(getApplicationContext(),"O bluetooth não esta conectado.",Toast.LENGTH_LONG).show();//mensagem flutuante
                }
            }
        });

        dled6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (conectar){
                    Threadconexao.enviar("F");//envia a letra "F" para desligar a led
                    led6.setBackgroundResource(R.drawable.ligar);//muda o layout do botão ligar definido no Drawable
                    dled6.setBackgroundResource(R.drawable.pdesligar);//muda o layout do botão desligar definido no Drawable
                }else{
                    Toast.makeText(getApplicationContext(),"O bluetooth não esta conectado.",Toast.LENGTH_LONG).show();//mensagem flutuante
                }
            }
        });

        Creditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Creditos.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case ativaBT:
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(getApplicationContext(),"O bluetooth foi ativado.",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"O bluetooth não foi ativado, o aplicativo foi encerrado.",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;

            case pedeconexao:
                if(resultCode == Activity.RESULT_OK){
                    MAC = data.getExtras().getString(listadispositivos.Dados);
                    Meudevice = MeuBluetooth.getRemoteDevice(MAC);

                    try {
                        Meusocket = Meudevice.createRfcommSocketToServiceRecord(Meuuuid);
                        Meusocket.connect();
                        conectar = true;
                        Threadconexao = new ConnectedThread(Meusocket);
                        Threadconexao.start();
                        conexao.setText("Desconectar");
                        Toast.makeText(getApplicationContext(),"Você foi conectado.",Toast.LENGTH_LONG).show();

                    }catch (IOException erro){
                        conectar = false;
                        Toast.makeText(getApplicationContext(),"Ocorreu um erro.",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Falha ao obter conexão.",Toast.LENGTH_LONG).show();
                }
        }
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {

            InputStream tmpIn = null; //processa o recebimento de dados pelo BluetoothSocket
            OutputStream tmpOut = null; //processa o envio de dados pelo BluetoothSocket

            // Obtém os fluxos de entrada e saída finais
            try {
                tmpIn = socket.getInputStream();  //realiza o processamento dos dados
                tmpOut = socket.getOutputStream(); //realiza o processamento dos dados
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        // armazenamento de buffer para o envio final
        public void enviar(String Enviados) {
            byte[] buffer = Enviados.getBytes();
            try {
                mmOutStream.write(buffer);
            } catch (IOException e) { }
        }
    }
}

