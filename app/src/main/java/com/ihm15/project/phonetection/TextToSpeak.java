package com.ihm15.project.phonetection;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.Locale;

/**
 * Created by Manon on 01/11/2015.
 */
public class TextToSpeak implements TextToSpeech.OnInitListener {

    private static TextToSpeech textToSpeech;
    private final static String TAG = "tts";

    /**
     * Constructeur
     * @param c, context
     */
    public TextToSpeak(Context c, TextToSpeech.OnUtteranceCompletedListener upl) {
        textToSpeech = new TextToSpeech(c,this);
        textToSpeech.setOnUtteranceCompletedListener(upl);
    }


    /**
     * Permet de dire une insctruction non enregistrée
     * @param instruction
     */
    public void speakInstruction(String instruction) {
        textToSpeech.speak(instruction, TextToSpeech.QUEUE_ADD, null);

    }

    public void stop(){
        textToSpeech.stop();
    }

    /**
     * Destroy
     */
    public void destroy() {
        textToSpeech.shutdown();
    }

    /**
     * Si la synthèse vocale est en cours, renvoie true, false sinon
     * @return boolean
     */
    public boolean isSpeak() {
        return textToSpeech.isSpeaking();
    }

    @Override
    public void onInit(int status) {
        //Vérifie la disponibilité de la synthèse vocale
        if (status == TextToSpeech.SUCCESS) {
            //Choi de la langue
            //int result = textToSpeech.setLanguage(Locale.FRANCE);
            int result = textToSpeech.setLanguage(Locale.ENGLISH);
            //Vérifie si la langue est supporté par le terminal
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //Renovoie une erreur
                Log.e(TAG, "Langage is not available");
            } else {

            }
        } else {
            //La synthèse vocale n'est pas disponible
            Log.e(TAG, "La synthèse vocale n'est pas disponible");
        }
    }
}
