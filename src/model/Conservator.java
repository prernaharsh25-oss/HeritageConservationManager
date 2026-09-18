/**
 * Conservator.java
 *
 * Staff subtype representing a person who carries out conservation
 * (restoration / repair) work on heritage sites.
 */
public class Conservator extends Staff {

    private String craftSkill;

    public Conservator(String id, String name, String craftSkill) {
        super(id, name);
        this.craftSkill = craftSkill;
    }

    public String getCraftSkill() {
        return craftSkill;
    }

    @Override
    public String getRole() {
        return "Conservator";
    }
}
