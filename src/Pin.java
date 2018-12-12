package javagpio.galileogen2;

public interface Pin {
    public static enum Direction {
        IN("in"),
        OUT("out");
        
        public String cmd;
        
        private Direction(final String CMD) {
            cmd = CMD;
        }
    }
    public static enum Value {
        LOW,
        HIGH;
    }
    public static enum Drive {
        PULL_UP("pullup"),
        PULL_DOWN("pulldown");
        
        public String cmd;
        
        private Drive(final String CMD) {
            cmd = CMD;
        }
    }
}
