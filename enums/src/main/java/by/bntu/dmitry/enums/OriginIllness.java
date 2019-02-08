package by.bntu.dmitry.enums;

/**
 *
 * @author dmitry
 */
public enum OriginIllness {

    INNATE(1, "Innate"),
    OBTAINED(2, "Obtained"),
    DO_NOT_KNOW(0, "Don't know");

    private int id;
    private String name;

    private OriginIllness(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static OriginIllness setOriginIllness(int id) {
        switch (id) {
            case 1:
                return INNATE;
            case 2:
                return OBTAINED;
            case 0:
                return DO_NOT_KNOW;
            default:
                return null;
        }
    }

}
