package br.com.nglauber.aula09_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            boolean active = intent.getBooleanExtra("state", false);
            Toast.makeText(context, "Ação disparada:\n" +
                    intent.getAction() +"\n"+
                    active, Toast.LENGTH_SHORT).show();

        } else if ("ACAO_TESTE".equals(intent.getAction())){

            Toast.makeText(context, "Ação disparada:\n" + intent.getAction() +"\n"+
                    intent.getStringExtra("nome"), Toast.LENGTH_SHORT).show();
        }
    }
}
