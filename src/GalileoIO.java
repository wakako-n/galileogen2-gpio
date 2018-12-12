package javagpio.galileogen2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static javagpio.galileo.Pin.Direction;
import static javagpio.galileo.Pin.Value;


public class GalileoIO {
    private Map<Pin, Direction> pinMap;

    public GalileoIO() {
        init();
    }

    private void init() {
        pinMap = new HashMap<>(19);
        
        // Add a JVM shutdown hook to unexport all open pins
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override public void run() {
                // The following code will be executed when the JVM shuts down
                unExportPins();
            }
        });
    }

    /**
     * Initializes the given Pin if needed with the given direction (Direction.in or Direction.out)
     * @param PIN
     * @param DIRECTION
     */
    private void initPin(final Pin PIN, final Direction DIRECTION) {
        if (pinMap.keySet().contains(PIN) && pinMap.get(PIN) == DIRECTION) {
            // PIN is already initalized with the right DIRECTION
            return;
        } else if (pinMap.keySet().contains(PIN)) {
            pinMap.put(PIN, DIRECTION);
            // Initialize PIN with given DIRECTION
            if (PIN instanceof Digital) {
                // Initialize DIRECTION only for Digital PIN
                try (FileOutputStream out = new FileOutputStream("/sys/class/gpio/gpio" + ((Digital) PIN).gpio + "/direction")) {
                    PrintWriter pw = new PrintWriter(out);
                    pw.print(DIRECTION.cmd);
                    pw.close();
                } catch (IOException exception) {
                    System.out.println(exception);
                }
            } else if (PIN instanceof Analog) {

                // Initialize DIRECTION only for Analog PIN
                try (FileOutputStream out = new FileOutputStream("/sys/class/gpio/gpio" + ((Analog) PIN).gpio + "/direction")) {
                    PrintWriter pw = new PrintWriter(out);
                    pw.print(DIRECTION.cmd);
                    pw.close();
                } catch (IOException exception) {
                    System.out.println(exception);
                }
            } else {
                // Initialize DIRECTION only for Galileo PIN
                try (FileOutputStream out = new FileOutputStream("/sys/class/gpio/gpio" + ((GalileoPin) PIN).gpio + "/direction")) {
                    PrintWriter pw = new PrintWriter(out);
                    pw.print(DIRECTION.cmd);
                    pw.close();
                } catch (IOException exception) {
                    System.out.println(exception);
                }
            }
        } else {                        
            pinMap.put(PIN, DIRECTION);
            
            // Initialize PIN with given DIRECTION
            if (PIN instanceof Digital) {
                // Exporting GPIO port to Sysfs
                try (FileOutputStream out = new FileOutputStream("/sys/class/gpio/export")) {
                    PrintWriter pw = new PrintWriter(out);
                    pw.print(((Digital) PIN).gpio);
                    pw.close();
                } catch (IOException exception) {
                    System.out.println(exception);
                }

                // Setting GPIO Port Direction
                try (FileOutputStream out = new FileOutputStream("/sys/class/gpio/gpio" + ((Digital) PIN).gpio + "/direction")) {
                    PrintWriter pw = new PrintWriter(out);
                    pw.print(DIRECTION.cmd);
                    pw.close();
                } catch (IOException exception) {
                    System.out.println(exception);
                }
		
            } else if (PIN instanceof Analog) {	
                // Exporting GPIO port to Sysfx
                try (FileOutputStream out = new FileOutputStream("/sys/class/gpio/export")) {
                    PrintWriter pw = new PrintWriter(out);
                    pw.print(((Analog) PIN).gpio);
                    pw.close();
                } catch (IOException exception) {
                    System.out.println(exception);
                }

                // Setting GPIO Port Direction
                try (FileOutputStream out = new FileOutputStream("/sys/class/gpio/gpio" + ((Analog) PIN).gpio + "/direction")) {
                    PrintWriter pw = new PrintWriter(out);
                    pw.print(DIRECTION.cmd);
                    pw.close();
                } catch (IOException exception) {
                    System.out.println(exception);
                }
            } else {
                // Exporting GPIO port to Sysfx
                try (FileOutputStream out = new FileOutputStream("/sys/class/gpio/export")) {
                    PrintWriter pw = new PrintWriter(out);
                    pw.print(((GalileoPin) PIN).gpio);
                    pw.close();
                } catch (IOException exception) {
                    System.out.println(exception);
                }

                // Setting GPIO Port Direction
                try (FileOutputStream out = new FileOutputStream("/sys/class/gpio/gpio" + ((GalileoPin) PIN).gpio + "/direction")) {
                    PrintWriter pw = new PrintWriter(out);
                    pw.print(DIRECTION.cmd);
                    pw.close();
                } catch (IOException exception) {
                    System.out.println(exception);
                }                                
            }
        }
    }

    /**
     * Set the given GalileoPin to the given value (Value.LOW or Value.HIGH)
     * This function should be used carefully cause it could damage your board
     * if you use the wrong parameters.
     * @param PIN
     * @param VALUE
     */
    private void setGalileoPin(final GalileoPin PIN, final Value VALUE) {
        initPin(PIN, Direction.OUT);
        try (FileOutputStream out = new FileOutputStream("/sys/class/gpio/gpio" + PIN.gpio + "/value")) {
            PrintWriter pw = new PrintWriter(out);
            pw.print(Value.HIGH == VALUE ? 1 : 0);
            pw.close();
        } catch (IOException exception) {
            System.out.println(exception);
        }    
    }

    /**
     * Returns the current value of the given Pin
     * @param PIN
     * @return the current value of the given Pin
     */
    public Value getDigital(final Digital PIN) {                
        initPin(PIN, Direction.IN);
        Value value = Value.LOW;        
        try (FileInputStream in = new FileInputStream("/sys/class/gpio/gpio" + PIN.gpio + "/value")) {
            value = (((in.read())%2)) == 1 ? Value.HIGH : Value.LOW;
        } catch (IOException exception) {
            System.out.println(exception);
        }        
        return value;
    }

    /**
     * Sets the given Pin to the given Value (Value.LOW or Value.HIGH)
     * @param PIN
     * @param VALUE
     */
    public void setDigital(final Digital PIN, final Value VALUE) {
        initPin(PIN, Direction.OUT);
        try (FileOutputStream out = new FileOutputStream("/sys/class/gpio/gpio" + PIN.gpio + "/value")) {            
            switch(PIN) {
                case RX:
                    setGalileoPin(GalileoPin.GPIO_32, Value.LOW);
                    break;
                case TX:
                    setGalileoPin(GalileoPin.GPIO_28, Value.LOW);
                    break;
                case D0:
                    setGalileoPin(GalileoPin.GPIO_32, Value.LOW);
                    break;
                case D1:
                    setGalileoPin(GalileoPin.GPIO_28, Value.LOW);
                    break;
                case D2:
                    break;
                case D3:
                    break;
                case D4:
                    setGalileoPin(GalileoPin.GPIO_36, Value.LOW);		    
                    break;
                case D5:
                    setGalileoPin(GalileoPin.GPIO_18, Value.LOW);		    
                    break;
                case D6:
                    setGalileoPin(GalileoPin.GPIO_20, Value.LOW);		    
                    break;
                case D7:
                    break;
                case D8:
                    break;
                case D9:
                    setGalileoPin(GalileoPin.GPIO_22, Value.LOW);
                    break;
                case D10:
                    setGalileoPin(GalileoPin.GPIO_26, Value.LOW);
                    break;
                case D11:
                    setGalileoPin(GalileoPin.GPIO_24, Value.LOW);
                    break;
                case D12:
                    setGalileoPin(GalileoPin.GPIO_42, Value.LOW);
                    break;
                case D13:
                    setGalileoPin(GalileoPin.GPIO_30, Value.LOW);
                    break;
            }
            PrintWriter pw = new PrintWriter(out);
            pw.print(Value.HIGH == VALUE ? 1 : 0);
            pw.close();
        } catch (IOException exception) {
            System.out.println(exception);
        }
    }

    /**
     * Returns the voltage in [mV] related to 5 V supply voltage
     * @param PIN
     * @return voltage in [mV]
     */
    public double getAnalog(final Analog PIN) {        
        initPin(PIN, Direction.IN);
	int value     = -1;
        StringBuilder sysfsPath = new StringBuilder("/sys/bus/iio/devices/iio:device0/");
        switch(PIN) {
            case A0:
                sysfsPath.append("in_voltage0_raw");
                break;
            case A1:
                sysfsPath.append("in_voltage1_raw");
                break;
            case A2:
                sysfsPath.append("in_voltage2_raw");
                break;
            case A3:
                sysfsPath.append("in_voltage3_raw");
                break;
            case A4:
                sysfsPath.append("in_voltage4_raw");
                break;
            case A5:
                sysfsPath.append("in_voltage5_raw");
                break;
        }        
        try (BufferedReader br = new BufferedReader(new FileReader(new File(sysfsPath.toString())))) {
            String line;
            while ((line = br.readLine()) != null) {
                value = Integer.parseInt(line);                
            }
        } catch (IOException exception) {
            System.out.println(exception);
        }
        
        return value > -1 ? value * Analog.FACTOR_5V : 0d; 
    }
    public void setAnalog(final Analog PIN, final double VALUE) {
        initPin(PIN, Direction.OUT);
        double value = clamp(0d, 5d, VALUE);
        // TODO: Implement functionality
    }
    
    private double clamp(final double MIN, final double MAX, final double VALUE) {
        if (VALUE < MIN) return MIN;
        if (VALUE > MAX) return MAX;
        return VALUE;
    }
      
    private void unExportPin(final Pin PIN) {
        try (FileOutputStream out = new FileOutputStream("/sys/class/gpio/unexport")) {
            PrintWriter pw = new PrintWriter(out);
            if (PIN instanceof Digital) {
                pw.print(((Digital) PIN).gpio);
            } else if(PIN instanceof Analog) {
                pw.print(((Analog) PIN).gpio);
            } else {
                pw.print(((GalileoPin) PIN).gpio);
            }
            pw.close();
        } catch (IOException exception) {
            System.out.println(exception);
        }    
    }
    private void unExportPins() {
        for (Pin pin : pinMap.keySet()) {                        
            // UnExport all open pins
            try (FileOutputStream out = new FileOutputStream("/sys/class/gpio/unexport")) {
                PrintWriter pw = new PrintWriter(out);
                if (pin instanceof Digital) {
                    pw.print(((Digital) pin).gpio);
                } else if (pin instanceof Analog) {
                    pw.print(((Analog) pin).gpio);
                } else {
                    pw.print(((GalileoPin) pin).gpio);
                }
                pw.close();
            } catch (IOException exception) {
                System.out.println(exception);
            }
        }
    }
}
