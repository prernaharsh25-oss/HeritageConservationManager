/**
 * HeritageSite.java
 *
 * Model class representing a single heritage site. Implements Reportable
 * so that a report fragment can be generated directly from the object.
 */
public class HeritageSite implements Reportable {

    private String siteId;
    private String name;
    private String location;
    private String type;
    private String historicalPeriod;
    private String condition;

    public HeritageSite(String siteId, String name, String location,
                         String type, String historicalPeriod, String condition) {
        this.siteId = siteId;
        this.name = name;
        this.location = location;
        this.type = type;
        this.historicalPeriod = historicalPeriod;
        this.condition = condition;
    }

    // ---------- Getters ----------

    public String getSiteId() {
        return siteId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getHistoricalPeriod() {
        return historicalPeriod;
    }

    public String getCondition() {
        return condition;
    }

    // ---------- Setters (encapsulation with simple validation) ----------

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }

    public void setLocation(String location) {
        if (location != null && !location.trim().isEmpty()) {
            this.location = location;
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHistoricalPeriod(String historicalPeriod) {
        this.historicalPeriod = historicalPeriod;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("---- HERITAGE SITE ----\n");
        sb.append("Site ID          : ").append(siteId).append("\n");
        sb.append("Name             : ").append(name).append("\n");
        sb.append("Location         : ").append(location).append("\n");
        sb.append("Type             : ").append(type).append("\n");
        sb.append("Historical Period: ").append(historicalPeriod).append("\n");
        sb.append("Current Condition: ").append(condition).append("\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return siteId + " | " + name + " | " + location + " | " + type
                + " | " + historicalPeriod + " | " + condition;
    }
}
