package comdouglas_batista.github.starlight;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Set;


public class listadispositivos extends ListActivity {
    private BluetoothAdapter MeuBluetooth2 = null;

    static String Dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> ArrayBT = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        MeuBluetooth2 = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> dispositivosPareados = MeuBluetooth2.getBondedDevices();

        if(dispositivosPareados.size() > 0) {
            for (BluetoothDevice dispositivo : dispositivosPareados) {
                String nomeBT = dispositivo.getName();
                String macBT = dispositivo.getAddress();
                ArrayBT.add(nomeBT + "\n" + macBT);
            }
        }
        setListAdapter(ArrayBT);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String informacaoGeral = ((TextView) v).getText().toString();
        String enderecoMac = informacaoGeral.substring(informacaoGeral.length() - 17);

        Intent retornaMac = new Intent();
        retornaMac.putExtra(Dados, enderecoMac);
        setResult(RESULT_OK, retornaMac);
        finish();
    }
}
