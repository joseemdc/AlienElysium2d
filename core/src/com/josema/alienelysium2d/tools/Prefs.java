package com.josema.alienelysium2d.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase que maneja las preferencias del juego
 */
public class Prefs {
    /**
     * Instancia de preferencias
     */
    private Preferences pref ;
    /**
     * Indica si está activada la música
     */
    private boolean hasMusic;
    /**
     * Indica si están activados los efectos de sonido
     */
    private boolean hasSoundEffects;
    /**
     * Indica si está activada la respuesta háptica
     */
    private boolean hasHaptic;
    /**
     * Indica si está activado el acelerómetro
     */
    private boolean usesAccelerometer;
    /**
     * Código del lenguaje utilizado
     */
    private String lang;
    /**
     * Indica el número de records que hay guardados
     */
    private int nrecords;
    /**
     * Colección con todos los records guardados
     */
    private ArrayList<Integer> records= new ArrayList<Integer>();

    /**
     * Crea una nueva clase para gestionar las preferencias y obtiene los valores que pueda haber guardados de otra sesión
     */
    public Prefs(){
        pref = Gdx.app.getPreferences("My Prefs");
        hasMusic = pref.getBoolean("hasMusic",true);
        hasHaptic = pref.getBoolean("hasHaptic",true);
        hasSoundEffects= pref.getBoolean("hasSoundEffects",true);
        usesAccelerometer= pref.getBoolean("usesAccelerometer",true);
        nrecords= pref.getInteger("nrecords",0);
        if(nrecords>0){
            for (int i=1;i<=nrecords;i++){
                records.add(pref.getInteger("records_"+i));
            }
        }
        lang= pref.getString("lang","");

    }

    /**
     * Devuelve un array con los records que hay guardados
     * @return Array de Integer
     */
    public ArrayList<Integer> getRecords(){
        records.clear();
        nrecords= pref.getInteger("nrecords",0);
        if(nrecords>0){
            for (int i=1;i<=nrecords;i++){
                records.add(pref.getInteger("record_"+i));
            }
        }
        return records;
    }

    /**
     * Añade un nuevo record a la colección y a las preferencias
     * @param record Integer
     */
    public void addRecord(int record){
        pref.putInteger("record_"+(nrecords+1),record);
        pref.flush();
        nrecords++;
        pref.putInteger("nrecords",nrecords);
        pref.flush();
    }

    /**
     * Devuelve si está activado el acelerómetro o no
     * @return True si está activado, False si no
     */
    public boolean usesAccelerometer(){
        return usesAccelerometer;
    }

    /**
     * Establece la propiedad usesAccelerometer para indicar si está activado o no el acelerómetro
     * @param usesAccelerometer Boolean
     */
    public void setUsesAccelerometer(boolean usesAccelerometer){
        this.usesAccelerometer=usesAccelerometer;
        pref.putBoolean("usesAccelerometer",usesAccelerometer);
        pref.flush();
    }

    /**
     * Obtiene el valor de la propiedad hasMusic
     * @return True si la música está activada, False si está desactivada
     */
    public boolean hasMusic(){
        return hasMusic;
    }

    /**
     * Establece el valor de la propiedad hasMusic y la guarda en preferencias
     * @param hasMusic
     */
    public void setMusic(boolean hasMusic){
        this.hasMusic =hasMusic;
        pref.putBoolean("hasMusic",hasMusic);
        pref.flush();
    }

    /**
     * Obtiene el valor de la propiedad hasSondEffects
     * @return True si los efectos están activados, False si están desactivados
     */
    public boolean hasSoundEffects(){
        return hasSoundEffects;
    }

    /**
     * Establece el valor de la propiedad hasSoundEffects y lo guarda en preferencias
     * @param hasSoundEffects Boolean
     */
    public void setSoundEffects(boolean hasSoundEffects){
        this.hasSoundEffects =hasSoundEffects;
        pref.putBoolean("hasSoundEffects",hasSoundEffects);
        pref.flush();
    }

    /**
     * Establece el valor de la propiedad hasHaptic y lo guarda en preferencias
     * @param hasHaptic Boolean
     */
    public void setHaptic(boolean hasHaptic){
        this.hasHaptic=hasHaptic;
        pref.putBoolean("hasHaptic",hasHaptic);
        pref.flush();
    }
    /**
     * Obtiene el valor de la propiedad hasHaptic
     * @return True si la respuesta haptica esta actuvada, False si está desactivada
     */
    public boolean hasHaptic(){
        return hasHaptic;
    }

    /**
     * Establece el lenguaje que se quiere usar
     * @param lang
     */
    public void setLang(String lang){
        this.lang=lang;
        pref.putString("lang",lang);
        pref.flush();
    }

    /**
     * Obtiene el lenguaje guardado en preferencias
     * @return String lang
     */
    public String getLang(){
        return lang;
    }




}
