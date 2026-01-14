package com.gabfmm.gerenciador_de_senhas.service;

public class MainMenuService {

    // -- Attributes --

    private int secondsRemaining;

    // -- Methods --

    public MainMenuService() {
        // 20 minutes
        secondsRemaining = 20 * 60;
    }

    public boolean updateRemainingTime(){
        secondsRemaining--;

        return secondsRemaining != 0;
    }

    /*
        Returns in the following format:

        mm:ss

        mm is minute
        ss is second
    */
    public String getRemainingTime(){

        String minutes = Integer.toString(secondsRemaining / 60);
        String seconds = Integer.toString(secondsRemaining % 60);

        if(minutes.length() == 1)
            minutes = "0" + minutes;

        if(seconds.length() == 1)
            seconds = "0" + seconds;

        return minutes + ":" + seconds;
    }
}
