package io.github.oscarmaestre.chip8;

public class Memoria {
    private byte[] memoria=new byte[0xfff];
    public byte read (int pos){
        return memoria[pos];
    }
    public void write(int pos, byte dato){
        memoria[pos]=dato;
    }
    
    
    public short getInstruccion (int pos){
        short instruccion = (short) (( memoria[pos] << 8 ) + memoria[pos+1]);
        return instruccion;
    }
    /** 
     * Copia un bloque de bytes en memoria empezando a poner el fichero
     * en una cierta posición de memoria
     * @param buffer Bloque a copiar
     * @param posInicio Posición de memoria donde debe empezar a copiarse
     */
    public void copiarBuffer(byte[] buffer,  int posInicio){
        int pos = posInicio;
        for (byte b : buffer ){
            memoria[pos] = buffer[pos++];
        }
    }
    
    public String getVolcado(int posInicio, int posFinal){
        String resultado="";
        int pos=posInicio;
        while (pos < posFinal){
            byte b=this.read(pos++);
            System.out.println(b);
        }
        return resultado;
    }
}
