/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import io.github.oscarmaestre.chip8.CPU;
import java.io.IOException;
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
        String fich="/home/usuario/Descargas/Roger.ch8";
        CPU cpu=new CPU();
        cpu.cargarArchivo(fich);
    }
    
}
