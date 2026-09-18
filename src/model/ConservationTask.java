/**
 * ConservationTask.java
 *
 * Model class representing a conservation task raised for an issue
 * found at a heritage site.
 *
 * Allowed status values : PENDING, IN PROGRESS, COMPLETED
 * Allowed priority values: LOW, MEDIUM, HIGH
 */
public class ConservationTask {

    public static final String[] VALID_STATUSES = {"PENDING", "IN PROGRESS", "COMPLETED"};
    public static final String[] VALID_PRIORITIES = {"LOW", "MEDIUM", "HIGH"};

    private String taskId;
    private String siteId;
    private String issue;
    private String action;
    private String priority;
    private String status;
    private String assignedTo;

    public ConservationTask(String taskId, String siteId, String issue, String action,
                             String priority, String status, String assignedTo) {
        this.taskId = taskId;
        this.siteId = siteId;
        this.issue = issue;
        this.action = action;
        this.priority = priority;
        this.status = status;
        this.assignedTo = assignedTo;
    }

    // ---------- Getters ----------

    public String getTaskId() {
        return taskId;
    }

    public String getSiteId() {
        return siteId;
    }

    public String getIssue() {
        return issue;
    }

    public String getAction() {
        return action;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    // ---------- Setters ----------

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Utility used by validation code to check a status value.
     */
    public static boolean isValidStatus(String value) {
        for (String s : VALID_STATUSES) {
            if (s.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Utility used by validation code to check a priority value.
     */
    public static boolean isValidPriority(String value) {
        for (String p : VALID_PRIORITIES) {
            if (p.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return taskId + " | " + siteId + " | " + issue + " | " + action + " | "
                + priority + " | " + status + " | " + assignedTo;
    }
}
