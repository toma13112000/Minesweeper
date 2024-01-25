package sweeper;

public enum Box {
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,

    OPENED,
    CLOSED,
    FLAGED,
    BOMBED,
    NOBOMB;

    public Object image;

    Box nextNumberBox()
    {
        return Box.values()[this.ordinal()+1];
    }

    int getNumber()
    {
        int o = ordinal();
        if (o >= Box.NUM1.ordinal())
            return o;
        return -1;
    }
}