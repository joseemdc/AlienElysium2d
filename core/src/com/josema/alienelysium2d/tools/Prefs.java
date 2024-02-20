package com.josema.alienelysium2d.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Prefs {
    private Preferences pref ;
    private boolean hasMusic;
    private boolean hasSoundEffects;
    private boolean hasHaptic;
    private String lang;
    private int completedLevel;

    public Prefs(){
        pref = Gdx.app.getPreferences("My Prefs");
        hasMusic = pref.getBoolean("hasSound",true);
        hasHaptic = pref.getBoolean("hasHaptic",true);
        lang= pref.getString("lang","");
        completedLevel=pref.getInteger("level",0);
    }
    public void setMusic(boolean hasMusic){
        this.hasMusic =hasMusic;
        pref.putBoolean("hasMusic",hasMusic);
        pref.flush();
    }

    public boolean hasSoundEffects(){
        return hasSoundEffects;
    }
    public void setSoundEffects(boolean hasSoundEffects){
        this.hasSoundEffects =hasSoundEffects;
        pref.putBoolean("hasSoundEffects",hasSoundEffects);
        pref.flush();
    }

    public boolean hasMusic(){
        return hasMusic;
    }
    public void setHaptic(boolean hasHaptic){
        this.hasHaptic=hasHaptic;
        pref.putBoolean("hasHaptic",hasHaptic);
        pref.flush();
    }
    public boolean hasHaptic(){
        return hasHaptic;
    }
    public void setLang(String lang){
        this.lang=lang;
        pref.putString("lang",lang);
        pref.flush();
    }
    public String getLang(){
        return lang;
    }

    //should be called once when we need to increase my level
    public void increaseLevel(){
        completedLevel++;
        pref.putInteger("level",completedLevel);
        pref.flush();
    }

    public int getLevel(){
        return completedLevel;
    }
}
