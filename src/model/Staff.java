/**
 * Staff.java
 *
 * Abstract parent class holding fields and behavior common to all staff
 * members involved in heritage conservation work. Concrete subtypes are
 * Inspector and Conservator.
 */
public abstract class Staff {

    private String id;
    private String name;

    public Staff(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }

    /**
     * Each staff subtype describes its own role. This is overridden
     * polymorphically by Inspector and Conservator.
     */
    public abstract String getRole();

    @Override
    public String toString() {
        return id + " | " + name + " | " + getRole();
    }
}
