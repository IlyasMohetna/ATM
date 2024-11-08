package com.atm.Utils;

import javafx.scene.media.AudioClip;

/**
 * SoundService est une classe utilitaire qui gère l'initialisation et la lecture des clips audio.
 * Plus précisément, elle est utilisée pour jouer un son de clic de bouton dans l'application ATM.
 */
public class SoundService {
    private static AudioClip buttonSound;

    /**
     * Initialise le son du bouton en chargeant le fichier audio.
     * Cette méthode doit être appelée lors du démarrage de l'application pour s'assurer que le son est prêt à être joué.
     */
    public static void initializeSound() {
        try {
            buttonSound = new AudioClip(SoundService.class.getResource("/audio/atm_button_sound.mp3").toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Joue le son du bouton s'il a été correctement initialisé.
     * Cette méthode doit être appelée chaque fois qu'un son de clic de bouton est nécessaire.
     */
    public static void playButtonSound() {
        if (buttonSound != null) {
            buttonSound.play();
        }
    }
}
