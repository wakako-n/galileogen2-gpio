package javagpio.galileo;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Main {

    public Main() throws UnsupportedEncodingException {
        GalileoIO galileoIO = new GalileoIO();                    
        double    value   = galileoIO.getAnalog(Analog.A0);
        galieloIO.setDigital(Digital.D1, Pin.Value.HIGH);
	//        PrintStream out     = new PrintStream(System.out, true, "UTF-8");
	//        out.println(value);             
        System.exit(0);                   
    }
   
    public static void main(String[] args) throws UnsupportedEncodingException {
        new Main();
    }
}
