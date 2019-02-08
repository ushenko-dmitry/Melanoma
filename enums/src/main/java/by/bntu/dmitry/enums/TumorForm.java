package by.bntu.dmitry.enums;

/**
 *
 * @author dmitry
 */
public enum TumorForm {

    FLAT(0, "Flat"),
    TUBEROUS(1, "Tuberous"),
    NODULAR(2, "Nodular"),
    HEMISPHERICAL(3, "Hemispherical"),
    MUSHROOM_SHAPED(4, "Mushroom shaped");

    private int id;
    private String name;

    private TumorForm(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    
    public String getName(){
        return name;
    }

    public static TumorForm setTumorForm(int id){
        switch(id){
            case 0:
                return FLAT;
            case 1:
                return TUBEROUS;
            case 2:
                return NODULAR;
            case 3:
                return HEMISPHERICAL;
            case 4:
                return MUSHROOM_SHAPED;
        }        
        return null;
    }
        
}
