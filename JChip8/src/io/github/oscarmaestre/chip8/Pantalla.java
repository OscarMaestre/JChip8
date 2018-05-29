package io.github.oscarmaestre.chip8;

import java.util.BitSet;

public class Pantalla {
    final int ANCHO     =   64;
    final int ALTO      =   32;
    boolean[][] memoria = new boolean[ANCHO][ALTO];
    
    public void borrar(){
        
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
        }
    }
}
