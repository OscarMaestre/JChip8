package io.github.oscarmaestre.chip8;

public class Teclado {
    private final byte NO_TECLA_PULSADA = (byte) 0xff;
    boolean teclaPulsada=false;
    byte valorTecla=NO_TECLA_PULSADA;

    public boolean teclaPulsada() {
        return teclaPulsada;
    }

    public void setTeclaPulsada(boolean teclaPulsada) {
        this.teclaPulsada = teclaPulsada;
        if (!this.teclaPulsada){
            valorTecla=NO_TECLA_PULSADA;
        }
    }

    public byte getValorTecla() {
        return valorTecla;
    }

    public void setValorTecla(byte valorTecla) {
        System.out.println(valorTecla);
        this.valorTecla = valorTecla;
        this.setTeclaPulsada(true);
    }
    
}
