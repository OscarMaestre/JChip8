package io.github.oscarmaestre.chip8;

public class Temporizador {
    private byte valor;
    public void decrementar(){
        valor = (byte) (valor - 1);
    }
    public void set(byte v){
        valor=v;
    }
}
