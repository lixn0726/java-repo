package indl.lixn.lx7xl.jvm;

/**
 * @author listen
 **/
public class House {

    public static void main(String[] args) {
        // Even the variable jonsHouse is only a reference
        House jonsHouse = new House();
        // Just a copy of reference
        House firstAddressCopy = jonsHouse;
        // Just a copy of reference
        House secondAddressCopy = jonsHouse;

        firstAddressCopy.paintDoor(Color.RED);

        Color color = secondAddressCopy.getDoorColor();
        // Now color will be red
        System.out.println(color.name());
    }

    private Color doorColor;

    public void paintDoor(Color color) {
        this.doorColor = color;
    }

    public Color getDoorColor() {
        return this.doorColor;
    }

    private static enum Color {
        RED,
        ;
    }

}
