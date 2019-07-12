
/**
 * @author dhannajayN
 */
public class Param {

    private Object value;

    private int type;

    private String name;

    public Param() {

    }

    public Param(Object value, int type) {
        this.value = value;
        this.type = type;
    }

    public Param(Object value, int type, String name) {
        this.value = value;
        this.type = type;
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + "[" + this.type + "] " + this.value;
    }

}
