/**
 * Inspector.java
 *
 * Staff subtype representing a person who carries out site inspections.
 */
public class Inspector extends Staff {

    private String specialization;

    public Inspector(String id, String name, String specialization) {
        super(id, name);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public String getRole() {
        return "Inspector";
    }
}
