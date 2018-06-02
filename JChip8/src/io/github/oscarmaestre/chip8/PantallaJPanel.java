package io.github.oscarmaestre.chip8;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import javax.swing.JPanel;

public class PantallaJPanel extends Pantalla{
    
    JPanel  panel;
    Graphics contextoGrafico;
    public void setPanel(JPanel panel){
        this.panel=panel;
        this.panel.setBackground(Color.BLACK);
        contextoGrafico=this.panel.getGraphics();
        contextoGrafico.setColor(Color.WHITE);
    }
    public void setContextoGrafico(Graphics g){
        this.contextoGrafico = g;
        contextoGrafico.setColor(Color.RED);
    }
    @Override
    public void actualizar() {
        
        for (int fila=0; fila < this.MAX_Y; fila++){
            for (int columna = 0 ; columna < this.MAX_X; columna++){
                if (this.memoria[columna][fila]){
                    contextoGrafico.fillRect(columna*escala,fila*escala,  escala, escala);
                }
            }
        }
    }

    @Override
    public void borrar() {
        for (int x=0; x<this.MAX_X; x++){
            for (int y=0; y<this.MAX_Y; y++){
                this.memoria[x][y]=false;
            }
        }
    }

}
