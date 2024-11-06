package com.atm.Utils;

import javafx.scene.media.AudioClip;

public class SoundService {
    private static AudioClip buttonSound;

    public static void initializeSound() {
        try {
            buttonSound = new AudioClip(SoundService.class.getResource("/audio/atm_button_sound.mp3").toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playButtonSound() {
        if (buttonSound != null) {
            buttonSound.play();
        }
    }
}
