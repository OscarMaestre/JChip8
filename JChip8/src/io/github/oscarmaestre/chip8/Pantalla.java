package io.github.oscarmaestre.chip8;

import java.util.BitSet;

public abstract class Pantalla {
    final int   COLUMNAS     =   65;
    final int   FILAS      =   65;
    int         escala    =   8;
    boolean[][] memoria = new boolean[FILAS][COLUMNAS];
    
    public abstract void borrar();
    public void setEscala ( int _escala ){
        this.escala = _escala;
    }
    public void activarPixel (int x, int y){
        memoria[x][y]=true;
    }
    public void desactivarPixel(int x, int y){
        memoria[x][y]=false;
    }
    public void dibujarSprite(byte[] sprite, int x0, int y0){
        BitSet bits = BitSet.valueOf(sprite);
        int y=y0;
        int x=x0;
        for (int pos = 0 ; pos < bits.length(); pos++){
            if ( (pos + 1) % 8 ==0){
                x=x0;
                y=y+1;
            }
            if (bits.get(pos)){
                activarPixel(x, y);
            } else {
                desactivarPixel(x, y);
            }
            x=x+1;
            if (x>COLUMNAS){
                x=0;
            }
        }
        actualizar();
    }
    abstract public void actualizar();
}
