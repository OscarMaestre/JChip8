package io.github.oscarmaestre.chip8;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorTemporizadores implements Runnable{
    Temporizador delay, sound;

    public GestorTemporizadores(Temporizador delay, Temporizador sound) {
        this.delay = delay;
        this.sound = sound;
    }
    
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(16);
                this.delay.decrementar();
                this.sound.decrementar();
            } catch (InterruptedException ex) {
                Logger.getLogger(GestorTemporizadores.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
