package io.github.oscarmaestre.chip8;

public class Memoria {
    private byte[] memoria=new byte[4096];
    public Memoria(){
        this.cargarROMEstandar();
    }
    public byte read (int pos){
        return memoria[pos];
    }
    public void write(int pos, byte dato){
        memoria[pos]=dato;
    }
    
    public byte[] getROM(){
        /*Algunos programas puedes esperar estos sprites
        en la ROM, que "pintan" los hexadecimales del 0 al F
        Ver
            http://devernay.free.fr/hacks/chip8/C8TECH10.HTM#2.4
        */
        int[] romIntegers={
            0xf0,0x90,0x90,0x90,0xf0, //0
            0x20,0x60,0x20,0x20,0x70, //1
            0xf0,0x10,0xf0,0x80,0xf0, //2
            0xf0,0x10,0xf0,0x10,0xf0, //3
            0x90,0x90,0xf0,0x10,0x10, //4
            0xf0,0x80,0xf0,0x10,0xf0, //5
            0xF0,0x80,0xF0,0x90,0xF0, //6
            0xF0,0x10,0x20,0x40,0x40, //7
            0xF0,0x90,0xF0,0x90,0xF0, //8
            0xF0,0x90,0xF0,0x10,0xF0, //9
            0xF0,0x90,0xF0,0x90,0x90, //A,
            0xE0,0x90,0xE0,0x90,0xE0, //B
            0xF0,0x80,0x80,0x80,0xF0, //C
            0xE0,0x90,0x90,0x90,0xE0, //D
            0xF0,0x80,0xF0,0x80,0xF0, //E
            0xF0,0x80,0xF0,0x80,0x80, //F
        };
        byte[] rom=new byte[romIntegers.length];
        for (int i=0; i<romIntegers.length; i++){
            int enteroEnCaracter=romIntegers[i];
            rom[i]=(byte)enteroEnCaracter;
        }
        return rom;
    }
    public void cargarROM(byte[] rom){
        for (int i=0; i<rom.length; i++){
            this.write(i, rom[i]);
        }
    }
    public void cargarROMEstandar(){
        byte[] rom=this.getROM();
        this.cargarROM(rom);
    }
    public int getInstruccion (int pos){
        int byte1 =(int) memoria[pos];
        int byte2= (int) memoria[pos+1];
        byte1 = (byte1 << 8) & 0xff00;
        byte2 = byte2 & 0x00ff;
        int instruccion = byte1 | byte2;
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
            String byteFormateado=String.format("%02X", b);
            resultado+=byteFormateado + " ";
        }
        return resultado;
    }
}
