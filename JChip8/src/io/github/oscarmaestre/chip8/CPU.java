package io.github.oscarmaestre.chip8;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CPU {
    private final int TAM_BUFFER_CARGA_FICHEROS=16384;
    Memoria memoria;
    Pantalla pantalla;
    
    public CPU(Pantalla _pantalla){
        memoria = new Memoria();
        pantalla= _pantalla;
    }
    public void cargarArchivo(String ruta, int posInicio) throws FileNotFoundException, IOException{
        byte[] buffer = new byte[this.TAM_BUFFER_CARGA_FICHEROS];
        FileInputStream flujo=new FileInputStream(ruta);
        int bytesLeidos=flujo.read(buffer);
        byte[] bytesArchivo=Arrays.copyOf(buffer, bytesLeidos);   
        int pos=posInicio;
    }
    public void dibujarAlgo(){
        System.out.println("Dibujando");
        for (int i=0; i<32; i++){
            this.pantalla.activarPixel(i, i);
            this.pantalla.actualizar();
        }
    }
    public void cargarArchivo(String ruta) throws FileNotFoundException, IOException{
        cargarArchivo ( ruta, 0x0200 );
    }
    
    public byte getNibble (short word, int numNibble){
        final int N1=0b1111000000000000;
        final int N2=0b0000111100000000;
        final int N3=0b0000000011110000;
        final int N4=0b0000000000001111;
        int resultado=0;
        
        if (numNibble == 1){
            int nibble = word & N1;
            nibble = nibble >> 12;
            return (byte) nibble;
        }
        
        if (numNibble == 2){
            int nibble = word & N2;
            nibble = nibble >> 8;
            return (byte) nibble;
        }
        
        if (numNibble == 3){
            int nibble = word & N3;
            nibble = nibble >> 4;
            return (byte) nibble;
        }
        
        if (numNibble == 4){
            int nibble = word & N4;
            return (byte) nibble;
        }
        return 0;
    }
    
    public int getNNN(short instruccion){
        int nibble2=getNibble(instruccion, 2);
        int nibble3=getNibble(instruccion, 3);
        int nibble4=getNibble(instruccion, 3);
        int resultado = (nibble2 << 8 ) + (nibble3 << 4) + nibble4;
        return resultado;
    }
    
    public void ejecutar(int posInicioPrograma){
        int posInstruccionSiguiente=posInicioPrograma;
        while (posInstruccionSiguiente < 0xfff){
            short instruccion = memoria.getInstruccion(posInstruccionSiguiente);
            int nibble1=this.getNibble(instruccion, 1);
            if (nibble1==0){
                
            }
            posInstruccionSiguiente += 2;
        }
    }
}
