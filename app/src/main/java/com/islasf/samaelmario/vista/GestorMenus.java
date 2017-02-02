package com.islasf.samaelmario.vista;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Esta clase permite gestionar el control de los menús cuando el usuario interactúa con los ítems del Menú.
 */

public class GestorMenus {

    private  Permisos permisos;
    private Activity actividad_llamante;

    /**
     * Constructor de la clase GestorMenus. Recibe por parámetro una actividad, y en él contiene la actividad de la cual ha sido llamada, y también contiene una instancia de un objeto de tipo
     * Permisos para determinar los permisos que tiene el usuario.
     * @param actividad Objeto de tipo Activity que hace referencia a la actividad de la cual es llamada la clase GestorMenus.
     */
    public GestorMenus(Activity actividad){
        this.actividad_llamante = actividad;
        permisos = new Permisos(actividad_llamante);
    }

    /**
     * Este método se encarga de inflar el menú a raíz de la actividad que llama a la clase GestorMenus
     * @param menu Objeto de la clase Menu
     */
    public void onCreateOptionsMenu(Menu menu){
        actividad_llamante.getMenuInflater().inflate(R.menu.menu, menu);
    }

    /**
     * Este métdoo se ejecuta cuando algún ítem del menú ha sido pulsado. La gestion para saberlo, se hace mediante un switch, en el cual se recoge la id del ítem, a través del método
     * 'getItemId()'. En los Case (que habrá tantos Case como ítems tenga el menú) se recoge la id de dicho item mediante la clase R.
     * Dependiendo del item del menú que sea, se realiza una operación u otra.
     * @param item Objeto de tipo MenuItem que hace referencia al ítem del Menú que ha sido pulsado
     */
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

    /**
     * Este método abre el Popup-Menu del E-mail cuando el usuario interactua con el ítem del menú que corresponde al E-mail. En éste método se instancia un objeto de tipo PopupMenu, el cual recibe dos
     * parámetros: el primero hace referencia a la actividad de la cual ha sido llamado, y el segundo la vista que hace referencia al ítem del menú que ha sido pulsado, que en este caso es el
     * E-mail.
     * @param iconoEmails Hace referencia a la vista del item del menú correspondiente al E-mail
     */
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

    /**
     * Este método abre el Popup-Menu del SMS cuando el usuario interactua con el ítem del menú que corresponde al SMS. En éste método se instancia un objeto de tipo PopupMenu, el cual recibe dos
     * parámetros: el primero hace referencia a la actividad de la cual ha sido llamado, y el segundo la vista que hace referencia al ítem del menú que ha sido pulsado, que en este caso es el
     * SMS.
     * @param iconoSms Hace referencia a la vista del item del menú correspondiente al SMS
     */
    public void abrirPopupSms(View iconoSms) {

        PopupMenu popupMenu = new PopupMenu(actividad_llamante, iconoSms);
        popupMenu.inflate(R.menu.menu_contextual_sms);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.NuevoSms) {
                    if(permisos.verificarPermisos_SMS())
                        irEnvioSmsActivity();
                    else Toast.makeText(actividad_llamante, "No tiene permisos para enviar SMS. Por favor, vaya a preferencias y seleccione habilitar el envío de SMS", Toast.LENGTH_LONG).show();
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

    /**
     * Este método instancia un Intent a través del cual se va a llamar a la clase EnvioEmailActivity
     */
    public void irEnvioEmailActivity(){

        Intent intent = new Intent(actividad_llamante, EnvioEmailActivity.class);
        actividad_llamante.startActivity(intent);
    }

    /**
     * Este método instancia un Intent a través del cual se va a llamar a la clase ListaEmailsActivity
     */
    public void irListaEmailsActivity(){

        Intent intent = new Intent(actividad_llamante, ListaEmailsActivity.class);
        actividad_llamante.startActivity(intent);
    }
    /**
     * Este método instancia un Intent a través del cual se va a llamar a la clase EnvioSMSActivity
     */
    public void irEnvioSmsActivity(){

        Intent intent = new Intent(actividad_llamante, EnvioSMSActivity.class);
        actividad_llamante.startActivity(intent);
    }
    /**
     * Este método instancia un Intent a través del cual se va a llamar a la clase ListaSmsActivity
     */
    public void irListaSmsActivity(){

        Intent intent = new Intent(actividad_llamante, ListaSmsActivity.class);
        actividad_llamante.startActivity(intent);
    }
}
