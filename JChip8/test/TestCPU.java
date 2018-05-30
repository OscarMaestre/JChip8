/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import io.github.oscarmaestre.chip8.CPU;
import io.github.oscarmaestre.chip8.Interfaz;
import io.github.oscarmaestre.chip8.PantallaJPanel;
import java.io.IOException;
import javax.swing.JFrame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author usuario
 */
public class TestCPU {
    
    public TestCPU() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Test
    public void pruebaVolcado() throws IOException {
        JFrame frame=new JFrame();
        String fich="/home/usuario/Descargas/Roger.ch8";
        PantallaJPanel p=new PantallaJPanel();
        p.setContextoGrafico(frame.getGraphics());
        
        CPU cpu=new CPU(p);
        cpu.cargarArchivo(fich);
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frame.setVisible(true);
            }
        });
    }
    
}
