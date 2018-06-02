package io.github.oscarmaestre.chip8;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class CPU {
    private final int TAM_BUFFER_CARGA_FICHEROS=16384;
    private final int TAM_BANCO_REGISTROS=16;
    private final int REGISTRO_VF=15;
    private final int TAM_PILA=16;
    Memoria memoria;
    Pantalla pantalla;
    Teclado teclado;
    byte [] registros;
    int[] pila;
    int registroI;
    int registroPC;
    int registroSP;
    Temporizador delay, sound;
    Random generadorAzar;
    public CPU(Pantalla _pantalla){
        memoria = new Memoria();
        pantalla= _pantalla;
        teclado = new Teclado();
        registros= new byte[TAM_BANCO_REGISTROS];
        pila = new int[TAM_PILA];
        this.registroSP=0;
        generadorAzar=new Random();
        delay = new Temporizador();
        sound = new Temporizador();
    }
    
    public void apilar(int direccion){
        this.registroSP++;
        pila[this.registroSP] = direccion;
        
    }
    
    public int desapilar(){
        int direccion=pila[this.registroSP];
        this.registroSP--;
        return direccion;
    }
    public void cargarArchivo(String ruta, int posInicio) throws FileNotFoundException, IOException{
        byte[] buffer = new byte[this.TAM_BUFFER_CARGA_FICHEROS];
        FileInputStream flujo=new FileInputStream(ruta);
        int bytesLeidos=flujo.read(buffer);
        byte[] bytesArchivo=Arrays.copyOf(buffer, bytesLeidos);   
        int pos=posInicio;
        for (int i=0; i<bytesArchivo.length; i++){
            this.memoria.write(pos, bytesArchivo[i]);
            pos++;
        }
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
        this.registroPC=0x0200;
    }
    
    public byte getNibble (int word, int numNibble){
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
        System.out.println("Error:se pidio un nibble que no existe!");
        return 0;
    }
    
    public int  getNNN(int instruccion){
        int nibble2=getNibble(instruccion, 2);
        int nibble3=getNibble(instruccion, 3);
        int nibble4=getNibble(instruccion, 4);
        int resultado = (int) ((nibble2 << 8 ) + (nibble3 << 4) + nibble4);
        return resultado;
    }
    
    public byte getKK(int instruccion){
        byte nibble3=getNibble(instruccion, 3);
        byte nibble4=getNibble(instruccion, 4);
        byte resultado =  (byte) ((nibble3 << 4) + nibble4);
        return resultado;
    }
    
    public void ejecutar(int posInicioPrograma){
        int posInstruccionSiguiente=posInicioPrograma;
        this.registroPC = posInicioPrograma;
        while (true){
            this.ejecutarInstruccion();
        }
    }
    
    public byte getUltimoByte(int instruccion){
        return this.getKK(instruccion);
    }
    public void ejecutarInstruccion(){
        int instruccion=memoria.getInstruccion(this.registroPC);
        String instruccionHEX=String.format("0x%04X", instruccion);
        System.out.print("En el PC:"+this.registroPC);
        System.out.println(" estaba la instruccion "+instruccionHEX);
        this.registroPC+=2;
        
        //Instruccion CLR
        if ( instruccion == 0x00e0 ){
            this.CLR();
        } //Fin instruccion CLR
        
        //Instruccion RTS Return from subroutine
        if ( instruccion == 0x00ee ){
            this.RTS();
        } //Fin instruccion RTS Return from subroutine
        
        
        int nibble1=this.getNibble(instruccion, 1);
        if (nibble1 == 1){
            int NNN = this.getNNN(instruccion);
            this.JMP(NNN);
        } //Instruccion JMP
        
        if (nibble1 == 2) {
            int NNN = this.getNNN(instruccion);
            this.CALL(NNN);
        } //Instruccion CALL
        
        if ( nibble1 == 3 ){
            this.SE(instruccion);
        } //Instruccion SE
        
        if ( nibble1 == 4 ){
            this.SNE(instruccion);
        } //Instruccion SNE
        
        if ( nibble1 == 5) {
            this.SE2(instruccion);
        }
        if ( nibble1 == 6 ){
            this.LDBYTE(instruccion);
        }
        
        if ( nibble1 == 7 ){
            this.ADD(instruccion);
        }
        
        /* El nibble 4 puede ser necesario */
        int nibble4 = this.getNibble(instruccion, 4);
        
        if ( nibble1 == 8) {
            if ( nibble4 == 0){
                this.LDVXVY(instruccion);
            }
            if ( nibble4 == 1){
                this.ORVXVY(instruccion);
            }
            if ( nibble4 == 2){
                this.ANDVXVY(instruccion);
            }
            if ( nibble4 == 3){
                this.XORVXVY(instruccion);
            }
            if ( nibble4 == 4){
                this.ANDVXVY(instruccion);
            }
            if ( nibble4 == 5){
                this.ANDVXVY(instruccion);
            }
            if ( nibble4 == 6){
                this.SHRVXVY(instruccion);
            }
            if ( nibble4 == 7){
                this.SUBNVXVY(instruccion);
            }
            if ( nibble4 == 0xe){
                this.SHLVXVY(instruccion);
            }
        }
        
        if ( nibble1 == 9 ){
            this.SNEVXVY(instruccion);
        }
        
        if ( nibble1 == 0xa ){
            this.LDINNN(instruccion);
        }
        
        if ( nibble1 == 0xb ){
            this.JMPNNN(instruccion);
        }
        
        if ( nibble1 == 0xc ){
            this.RAND(instruccion);
        }
        
        if ( nibble1 == 0xd ){
            this.DXYN(instruccion);
        }
        
        
        if ( nibble1 == 0xe ){
            byte ultimoByte = this.getUltimoByte(instruccion);
            if (ultimoByte == 0x9e){
                this.SKP(instruccion);
            }
            if (ultimoByte == 0xa1){
                this.SKNP(instruccion);
            }
        }
        
        if (nibble1 == 0xf){
            byte ultimoByte = this.getUltimoByte(instruccion);
            if (ultimoByte == 0x07){
                this.LDTIMER(instruccion);
            }
            if (ultimoByte == 0x0A){
                this.WAITKEY(instruccion);
            }
            if (ultimoByte == 0x15){
                this.SETDELAY(instruccion);
            }
            if (ultimoByte == 0x18){
                this.SETSOUND(instruccion);
            }
            if (ultimoByte == 0x1e){
                this.ADDI(instruccion);
            }
            if (ultimoByte == 0x29){
                this.LOADCHAR(instruccion);
            }
            if (ultimoByte == 0x33){
                this.BCD(instruccion);
            }
            if (ultimoByte == 0x55){
                this.STOREV0(instruccion);
            }
            if (ultimoByte == 0x65){
                this.STOREI(instruccion);
            }
            
        }
        
    
        
        
    } //Fin ejecutar instruccion
    
    /***************************************************************
     * INSTRUCCIONES
     ***************************************************************/
    
    
    public void CLR(){
        this.pantalla.borrar();
    } //Fin instruccion CLR
    
    public void JMP(int NNN){
        this.registroPC = NNN;
    } //Fin instruccion JMP

    public void RTS() {
        this.registroPC = this.desapilar();
    } //Fin instruccion RTS

    public void CALL(int NNN) {
        this.apilar(this.registroPC);
        this.registroPC = NNN;
    }

    public void SE(int instruccion) {
        byte numRegistro=this.getNibble(instruccion, 2);
        byte KK = this.getKK(instruccion);
        
        byte valorRegistro = this.registros[numRegistro];
        if (valorRegistro == KK){
            this.registroPC = (int) (this.registroPC + 2);
        }
    }

    public void SNE(int instruccion) {
        byte numRegistro=this.getNibble(instruccion, 2);
        byte KK = this.getKK(instruccion);
        
        byte valorRegistro = this.registros[numRegistro];
        if (valorRegistro != KK){
            this.registroPC = (int) (this.registroPC + 2);
        }
    }
    
    public void SE2(int instruccion){
        byte numRegistroX = this.getNibble(instruccion, 2);
        byte numRegistroY = this.getNibble(instruccion, 3);
        byte valorRegistroX=this.registros[numRegistroX];
        byte valorRegistroY=this.registros[numRegistroY];
        if (valorRegistroX == valorRegistroY){
            this.registroPC = (int) (this.registroPC + 2);
        }
    }

    public void LDBYTE(int instruccion) {
        byte numRegistro=this.getNibble(instruccion, 2);
        byte KK = this.getKK(instruccion);
        this.registros[numRegistro] = KK;
    }

    public void ADD(int instruccion) {
        byte numRegistro=this.getNibble(instruccion, 2);
        byte KK = this.getKK(instruccion);
        this.registros[numRegistro]+= KK;
    }

    public void LDVXVY(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        byte numRegistroY = this.getNibble(instruccion, 3);
        this.registros[numRegistroX] = this.registros[numRegistroY];
    }

    public void ORVXVY(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        byte numRegistroY = this.getNibble(instruccion, 3);
        byte valorX = this.registros[numRegistroX];
        byte valorY = this.registros[numRegistroY];
        byte resultado = (byte) (valorX | valorY);
        this.registros[numRegistroX] = resultado;
    }

    public void ANDVXVY(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        byte numRegistroY = this.getNibble(instruccion, 3);
        byte valorX = this.registros[numRegistroX];
        byte valorY = this.registros[numRegistroY];
        byte resultado = (byte) (valorX & valorY);
        this.registros[numRegistroX] = resultado;
    }
    
    public void XORVXVY(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        byte numRegistroY = this.getNibble(instruccion, 3);
        byte valorX = this.registros[numRegistroX];
        byte valorY = this.registros[numRegistroY];
        byte resultado = (byte) (valorX ^ valorY);
        this.registros[numRegistroX] = resultado;
    }
    
    public void ADDVXVY(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        byte numRegistroY = this.getNibble(instruccion, 3);
        byte valorX = this.registros[numRegistroX];
        byte valorY = this.registros[numRegistroY];
        if ( valorX + valorY > 255 ){
            this.registros[REGISTRO_VF]=1;
        } else {
            this.registros[REGISTRO_VF]=0;
        }
        byte resultado = (byte) (valorX + valorY);
        this.registros[numRegistroX] = resultado;
    }
    
    public void SUBVXVY(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        byte numRegistroY = this.getNibble(instruccion, 3);
        byte valorX = this.registros[numRegistroX];
        byte valorY = this.registros[numRegistroY];
        if ( valorX > valorY   ){
            this.registros[REGISTRO_VF]=1;
        } else {
            this.registros[REGISTRO_VF]=0;
        }
        byte resultado = (byte) (valorX - valorY);
        this.registros[numRegistroX] = resultado;
    }

    public void SHRVXVY(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        byte valorX = this.registros[numRegistroX];
        
        if (valorX % 2 == 0){
            this.registros[REGISTRO_VF]=0;
        } else {
            this.registros[REGISTRO_VF]=1;
        }
        byte resultado = (byte) (valorX >> 1);
        this.registros[numRegistroX] = resultado;
    }

    public  void SUBNVXVY(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        byte numRegistroY = this.getNibble(instruccion, 3);
        byte valorX = this.registros[numRegistroX];
        byte valorY = this.registros[numRegistroY];
        if ( valorY > valorX   ){
            this.registros[REGISTRO_VF]=1;
        } else {
            this.registros[REGISTRO_VF]=0;
        }
        byte resultado = (byte) (valorY - valorX);
        this.registros[numRegistroX] = resultado;
    }

    public void SHLVXVY(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        byte valorX = this.registros[numRegistroX];
        if (valorX > 127 ){
            this.registros[REGISTRO_VF]=1;
        } else {
            this.registros[REGISTRO_VF]=0;
        }
        byte resultado = (byte) (valorX * 2);
        this.registros[numRegistroX] = resultado;
    }

    public void SNEVXVY(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        byte numRegistroY = this.getNibble(instruccion, 3);
        byte valorX = this.registros[numRegistroX];
        byte valorY = this.registros[numRegistroY];
        if (valorX != valorY){
            this.registroPC = (int) (this.registroPC + 2);
        }
    }

    public void LDINNN(int instruccion) {
        int NNN = this.getNNN(instruccion);
        this.registroI = NNN;
    }

    public void JMPNNN(int instruccion) {
        int NNN = this.getNNN(instruccion);
        byte valorV0 = this.registros[0];
        int nuevaDireccion = (int) (NNN + valorV0);
        this.registroPC = nuevaDireccion;
    }

    public void RAND(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        byte KK = this.getKK(instruccion);
        byte azar = (byte) this.generadorAzar.nextInt(255);
        byte resultado = (byte) (azar & KK);
        this.registros[numRegistroX] = resultado;
    }

    public void DXYN(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        byte numRegistroY = this.getNibble(instruccion, 3);
        byte n            = this.getNibble(instruccion, 4);
        
        byte valorX = this.registros[numRegistroX];
        byte valorY = this.registros[numRegistroY];
        
        byte[] bytesSprite = new byte[n];
        for (int nByte = 0; nByte < n ; nByte++){
            bytesSprite[ nByte ] = this.memoria.read(this.registroI);
        }
        this.pantalla.dibujarSprite(bytesSprite, valorX, valorY);
    }

    public void SKP(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        byte valorX = this.registros[numRegistroX];
        if (!teclado.teclaPulsada) return ;
        byte valorTecla = teclado.getValorTecla();
        if (valorTecla == valorX){
            this.registroPC += 2;
        }
    }
    public void SKNP(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        byte valorX = this.registros[numRegistroX];
        if (!teclado.teclaPulsada) return ;
        byte valorTecla = teclado.getValorTecla();
        if (valorTecla == valorX){
            this.registroPC += 2;
        }
    }

    public void LDTIMER(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        this.registros[numRegistroX] = this.delay.get();
    }

    public void WAITKEY(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        while (!teclado.teclaPulsada());
        byte tecla=teclado.getValorTecla();
        this.registros[numRegistroX] = tecla;
        
    }

    public void STOREI(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        int direccion = this.registroI;
        for (int i=0; i<=numRegistroX; i++){
            byte valorMemoria=this.memoria.read(direccion);
            direccion++;
        }
    }

    public void STOREV0(int instruccion) {
        byte numRegistroX = this.getNibble(instruccion, 2);
        int direccion = this.registroI;
        for (int i=0; i<=numRegistroX; i++){
            byte valorRegistro=this.registros[i];
            this.memoria.write(direccion, valorRegistro);
            direccion++;
        }
    }

    public void LOADCHAR(int instruccion) {
        byte valorX = this.getNibble(instruccion, 2);
        this.registroI = (int) (valorX * 5);
    }

    public void BCD(int instruccion) {
        byte valorX = this.getNibble(instruccion, 2);
        byte cantidad = this.registros[valorX];
        byte centenas = (byte) (cantidad / 100);
        cantidad = (byte) (cantidad - centenas);
        byte decenas = (byte) (cantidad / 10);
        byte unidades = (byte) (cantidad % 10);
        
        this.memoria.write(this.registroI   , centenas);
        this.memoria.write(this.registroI+1 , decenas);
        this.memoria.write(this.registroI+2 , unidades);
        
    }

    public void SETDELAY(int instruccion) {
        byte valorX = this.getNibble(instruccion, 2);
        delay.set(valorX);
    }

    public void SETSOUND(int instruccion) {
        byte valorX = this.getNibble(instruccion, 2);
        sound.set(valorX);
    }

    public void ADDI(int instruccion) {
        byte valorX = this.getNibble(instruccion, 2);
        int nuevoI=(int) (this.registroI + valorX);
        this.registroI = nuevoI;
    }
    
}

