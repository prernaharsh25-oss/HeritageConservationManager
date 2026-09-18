
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
