package javagpio.galileo;

public enum Digital implements Pin {
    RX(11),
    TX(12),
    D0(11),
    D1(12),
    D2(61),
    D3(62),
    D4(6),
    D5(0),
    D6(1),
    D7(38),
    D8(40),
    D9(4),
    D10(10),
    D11(5),
    D12(15),
    D13(7);
    
    public final int gpio;
    
    private Digital(final int GPIO) {
        gpio = GPIO;    
    }
}
