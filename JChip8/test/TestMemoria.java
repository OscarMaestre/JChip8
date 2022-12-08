/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import io.github.oscarmaestre.chip8.Memoria;
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
public class TestMemoria {
    
    
     @Test
     public void testRomCaracterA() {
         Memoria memoria=new Memoria();
         byte[] rom = memoria.getROM();
         //Hacemos pruebas básicas para ver si el caracter A
         //está correctamente cargado en memoria
         Assert.assertEquals(0xf0, rom[0]&0xff);
         Assert.assertEquals(0x90, rom[3]&0xff);
     }
     @Test
     public void testRomCaracterF() {
         Memoria memoria=new Memoria();
         byte[] rom = memoria.getROM();
         //Hacemos pruebas básicas para ver si el caracter A
         //está correctamente cargado en memoria
         Assert.assertEquals(0xf0, rom[75]&0xff);
         Assert.assertEquals(0x80, rom[78]&0xff);
     }
}
