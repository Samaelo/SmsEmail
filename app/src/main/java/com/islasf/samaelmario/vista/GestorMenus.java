package com.islasf.samaelmario.vista;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Mario on 30/01/2017.
 */

public class GestorMenus {
    private Activity actividad_llamante;
    public GestorMenus(Activity actividad){
        this.actividad_llamante = actividad;
    }


    public void onCreateOptionsMenu(Menu menu){
        actividad_llamante.getMenuInflater().inflate(R.menu.menu, menu);
    }

    public void onOptionsItemSelected( MenuItem item){
        int id = item.getItemId();

        switch(id){
            case R.id.item_Mostrar_Emails:
                abrirPopupEmail(actividad_llamante.findViewById(R.id.item_Mostrar_Emails));
                break;
            case R.id.item_Mostrar_SMS:
                abrirPopupSms(actividad_llamante.findViewById(R.id.item_Mostrar_SMS));
                break;
            case R.id.itemPreferencias:
                actividad_llamante.startActivity(new Intent(actividad_llamante,PreferenciasActivity.class));
                break;
            case R.id.itemVerPerfil:
                actividad_llamante.startActivity(new Intent(actividad_llamante,PerfilActivity.class));
                break;
        }
    }

    // -- POP UP
    public void abrirPopupEmail(View iconoEmails) {

        PopupMenu popupMenu = new PopupMenu(actividad_llamante, iconoEmails);
        popupMenu.inflate(R.menu.menu_contextual_email);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.NuevoMail) {
                    irEnvioEmailActivity();
                }
                else if (item.getItemId() == R.id.ListarMails) {

                    try {
                        irListaEmailsActivity();
                    }catch(Exception e){}
                }
                return true;
            }
        });

        MenuPopupHelper menuHelper = new MenuPopupHelper(actividad_llamante, (MenuBuilder) popupMenu.getMenu(), iconoEmails);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }

    public void abrirPopupSms(View iconoSms) {

        PopupMenu popupMenu = new PopupMenu(actividad_llamante, iconoSms);
        popupMenu.inflate(R.menu.menu_contextual_sms);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.NuevoSms) {
                    irEnvioSmsActivity();
                }
                else if (item.getItemId() == R.id.ListarSmses) {
                    irListaSmsActivity();
                }
                return true;
            }
        });

        MenuPopupHelper menuHelper = new MenuPopupHelper(actividad_llamante, (MenuBuilder) popupMenu.getMenu(), iconoSms);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }

    public void irEnvioEmailActivity(){

        Intent intent = new Intent(actividad_llamante, EnvioEmailActivity.class);
        actividad_llamante.startActivity(intent);
    }

    public void irListaEmailsActivity(){

        Intent intent = new Intent(actividad_llamante, ListaEmailsActivity.class);
        actividad_llamante.startActivity(intent);
    }

    public void irEnvioSmsActivity(){

        Intent intent = new Intent(actividad_llamante, EnvioSMSActivity.class);
        actividad_llamante.startActivity(intent);
    }

    public void irListaSmsActivity(){

        Intent intent = new Intent(actividad_llamante, ListaSmsActivity.class);
        actividad_llamante.startActivity(intent);
    }
}
