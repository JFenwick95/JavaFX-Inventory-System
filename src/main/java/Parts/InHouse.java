package Parts;

/**
 * class InHouse.java
 */

/**
 *The InHouse class which extends the abstract Part class.
 * @author Jeff Fenwick
 */
public class InHouse extends Part{

    private int machineId;

    /**
     * Creates an instance of an InHouse part
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Adds a parameter of machineId to InHouse parts
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     *
     * @return the machine ID
     */
    public int getMachineId() {
        return machineId;
    }
}
