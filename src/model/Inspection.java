
public class Inspection implements Reportable {

    private String inspectionId;
    private String siteId;
    private String inspectorName;
    private String date; 
    private String condition;
    private String issuesFound;
    private String remarks;

    public Inspection(String inspectionId, String siteId, String inspectorName,
                       String date, String condition, String issuesFound, String remarks) {
        this.inspectionId = inspectionId;
        this.siteId = siteId;
        this.inspectorName = inspectorName;
        this.date = date;
        this.condition = condition;
        this.issuesFound = issuesFound;
        this.remarks = remarks;
    }

    // getters

    public String getInspectionId() {
        return inspectionId;
    }

    public String getSiteId() {
        return siteId;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public String getDate() {
        return date;
    }

    public String getCondition() {
        return condition;
    }

    public String getIssuesFound() {
        return issuesFound;
    }

    public String getRemarks() {
        return remarks;
    }

    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("  - Inspection ID : ").append(inspectionId).append("\n");
        sb.append("    Date          : ").append(date).append("\n");
        sb.append("    Inspector     : ").append(inspectorName).append("\n");
        sb.append("    Condition     : ").append(condition).append("\n");
        sb.append("    Issues Found  : ").append(issuesFound).append("\n");
        sb.append("    Remarks       : ").append(remarks).append("\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return inspectionId + " | " + siteId + " | " + inspectorName + " | "
                + date + " | " + condition;
    }
}
