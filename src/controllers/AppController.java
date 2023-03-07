/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import views.InterfaceControllerView;


/**
 *
 * @author Ezequiel Andujar Montes
 */
public class AppController {

    private static AppController INSTANCE = null;

    // Private constructor suppresses 
    private AppController() {
    }

    // creador sincronizado para protegerse de posibles problemas  multi-hilo
    // otra prueba para evitar instanciación múltiple 
    @SuppressWarnings("DoubleCheckedLocking")
    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (AppController.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new AppController();
                }
            }
        }
    }

    public static AppController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
    
    public void run(String[] args){
        
        InterfaceControllerView iCtrl = new InterfaceControllerView();
        InterfaceControllerView.run(args);
    }
}
