/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import io.github.oscarmaestre.chip8.CPU;
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
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void pruebaNibble1() {
        CPU cpu=new CPU();
        short nibble1=(short) 0b1010000000000000;
        int nibbleResultado=cpu.getNibble(nibble1, 1);
        Assert.assertEquals(nibbleResultado, 10);        
    }
    
    @Test
    public void pruebaNibble2() {
        CPU cpu=new CPU();
        short nibble1=(short) 0b0000111100000000;
        int nibbleResultado=cpu.getNibble(nibble1, 2);
        Assert.assertEquals(15, nibbleResultado);        
    }
    
    
    @Test
    public void pruebaNibble3() {
        CPU cpu=new CPU();
        short nibble1=(short) 0b0000000001000000;
        int nibbleResultado=cpu.getNibble(nibble1, 3);
        Assert.assertEquals(4, nibbleResultado);        
    }
    
    
    @Test
    public void pruebaNibble4() {
        CPU cpu=new CPU();
        short nibble1=(short) 0b1111111111110101;
        int nibbleResultado=cpu.getNibble(nibble1, 4);
        Assert.assertEquals(5, nibbleResultado);        
    }
    
    
    
    
    
}

