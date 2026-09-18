
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
  
    public abstract String getRole();

    @Override
    public String toString() {
        return id + " | " + name + " | " + getRole();
    }
}
