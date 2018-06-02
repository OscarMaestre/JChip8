/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import io.github.oscarmaestre.chip8.CPU;
import io.github.oscarmaestre.chip8.PantallaJPanel;
import io.github.oscarmaestre.chip8.Teclado;
import javax.swing.JFrame;
import junit.framework.Assert;
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
public class PruebaNibble {
    CPU cpu;
    public PruebaNibble() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        JFrame frame=new JFrame();
        frame.show();
        String fich="/home/usuario/Descargas/Roger.ch8";
        PantallaJPanel p=new PantallaJPanel();
        p.setContextoGrafico(frame.getGraphics());
        Teclado t=new Teclado();
        cpu=new CPU(p, t);
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void pruebaNibble1() {
        
        short nibble1=(short) 0b1010000000000000;
        int nibbleResultado=cpu.getNibble(nibble1, 1);
        Assert.assertEquals(nibbleResultado, 10);        
    }
    
    @Test
    public void pruebaNibble2() {
        short nibble1=(short) 0b0000111100000000;
        int nibbleResultado=cpu.getNibble(nibble1, 2);
        Assert.assertEquals(15, nibbleResultado);        
    }
    
    
    @Test
    public void pruebaNibble3() {
        short nibble1=(short) 0b0000000001000000;
        int nibbleResultado=cpu.getNibble(nibble1, 3);
        Assert.assertEquals(4, nibbleResultado);        
    }
    
    
    @Test
    public void pruebaNibble4() {
        short nibble1=(short) 0b1111111111110101;
        int nibbleResultado=cpu.getNibble(nibble1, 4);
        Assert.assertEquals(5, nibbleResultado);        
    }
    
    @Test 
    public void getUltimoByte(){
        int instruccion = 0b1010101011111111;
        int resultado= cpu.getUltimoByte(instruccion) ;
        Assert.assertEquals(255, resultado);        
    }
    
    
    
    
    
}

