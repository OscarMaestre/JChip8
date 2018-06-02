package io.github.oscarmaestre.chip8;

import java.awt.Color;
import java.awt.Graphics;
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
        
        for (int fila=0; fila < this.FILAS; fila++){
            for (int columna = 0 ; columna < this.COLUMNAS; columna++){
                if (this.memoria[fila][columna]){
                    contextoGrafico.fillRect(fila*escala, columna*escala, escala, escala);
                }
            }
        }
    }

    @Override
    public void borrar() {
        for (int fila=0; fila < this.FILAS; fila++){
            for (int columna = 0 ; columna < this.COLUMNAS; columna++){
                this.memoria[fila][columna] = false;
                
            }
        }
    }

}
