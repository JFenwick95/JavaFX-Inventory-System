package Parts;

/**
 * class Outsourced.java
 */

/**
 *The Outsourced class which extends the abstract Part class
 * @author Jeff Fenwick
 */
public class Outsourced extends Part{

    private String companyName;

    /**
     * Creates an instance of an Outsourced part
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Adds a parameter of companyName to Outsourced parts
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     *
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }
}
