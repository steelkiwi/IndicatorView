package steelkiwi.com.library;

/**
 * Created by yaroslav on 4/28/17.
 */

public enum IndicatorType {
    HANG_DOWN(0), LOOK_OUT(1);

    private int type;

    IndicatorType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static IndicatorType fromId(int id) {
        for(IndicatorType indicatorType : values()) {
            if(indicatorType.getType() == id) return indicatorType;
        }
        throw new IllegalArgumentException();
    }
}
