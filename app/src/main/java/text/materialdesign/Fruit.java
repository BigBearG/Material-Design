package text.materialdesign;

/**
 * Created by liuwei on 7/12/17.
 */

public class Fruit {
    private String name;
    private int imaged;

    public Fruit(String name, int imaged) {
        this.name = name;
        this.imaged = imaged;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImaged() {
        return imaged;
    }

    public void setImaged(int imaged) {
        this.imaged = imaged;
    }
}
