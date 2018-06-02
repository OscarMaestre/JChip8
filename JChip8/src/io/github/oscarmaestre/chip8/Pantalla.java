package io.github.oscarmaestre.chip8;

import java.util.BitSet;

public abstract class Pantalla {
    final int   MAX_X     =   65;
    final int   MAX_Y      =   33;
    int         escala    =   8;
    boolean[][] memoria = new boolean[MAX_X][MAX_Y];
    
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
        System.out.println(bits.toString());
        System.out.println("Longitud en bits:"+bits.length());
        
        for (int pos = 0 ; pos < bits.length(); pos++){
            if ( (pos + 1) % 8 ==0){
                x=x0;
                y=y+1;
                System.out.println();
            }
            
            if (bits.get(pos)){
                activarPixel(x, y);
                System.out.print("*");
            } else {
                desactivarPixel(x, y);
                System.out.print(" ");
            }
            x=x+1;
            if (x>MAX_X){
                x=0;
            }
        }
        actualizar();
    }
    
    public void dibujarSprite(String[] sprite, int x0, int y0){
        
        int y=y0;
        int x=x0;
        System.out.println(sprite);
        
        for (String linea : sprite){
            x=x0;
            for (char bit : linea.toCharArray() ){
                if (bit == '1'){
                    activarPixel(x, y);
                    System.out.print("*");
                } else {
                    desactivarPixel(x, y);
                    System.out.print(" ");
                }
                x++;
                if (x>MAX_X){
                    x=0;
                }
            }
            System.out.println();
            y++;
        }
        actualizar();
    }
    abstract public void actualizar();
}
