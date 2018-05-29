package io.github.oscarmaestre.chip8;

public class Temporizador {
    private byte valor;
    public void decrementar() throws TimeoutException{
        valor = (byte) (valor - 1);
        
        if (valor==0) throw new TimeoutException();
    }
    public void set(byte v){
        valor=v;
    }
}
