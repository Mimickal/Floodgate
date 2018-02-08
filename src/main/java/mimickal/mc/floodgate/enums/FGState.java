package mimickal.mc.floodgate.enums;

public enum FGState {
    ON, OFF, REDSTONE;

    public FGState next() {
        return values()[(ordinal() + 1) % values().length];
    }
}