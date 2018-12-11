package javagpio.galileo;

public enum Analog implements Pin {
    A0(48),
    A1(50),
    A2(52),
    A3(54),
    A4(56),
    A5(58);

    public static final double FACTOR_5V = 5000d / 4096d;

    public final int gpio;

    private Analog(final int GPIO) {
        gpio = GPIO;
    }
}
