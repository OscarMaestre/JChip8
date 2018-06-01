package io.github.oscarmaestre.chip8;

public class Temporizador {
    private byte valor;
    public void decrementar() {
        if (valor==0) return ;
        valor = (byte) (valor - 1);
    }
    public byte get(){
        return valor;
    }
    public void set(byte v){
        valor=v;
    }
}
