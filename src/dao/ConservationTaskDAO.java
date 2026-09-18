import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ConservationTaskDAO.java
 *
 * JDBC operations for the conservation_tasks table: create, view all,
 * view by site, and update task status.
 */
public class ConservationTaskDAO {

    /**
     * Inserts a new conservation task record.
     */
    public void addTask(ConservationTask task) throws SQLException {
        String sql = "INSERT INTO conservation_tasks "
                + "(task_id, site_id, issue, action, priority, status, assigned_to) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, task.getTaskId());
            ps.setString(2, task.getSiteId());
            ps.setString(3, task.getIssue());
            ps.setString(4, task.getAction());
            ps.setString(5, task.getPriority());
            ps.setString(6, task.getStatus());
            ps.setString(7, task.getAssignedTo());
            ps.executeUpdate();
        }
    }

    /**
     * Returns all conservation tasks, ordered by task_id.
     */
    public List<ConservationTask> getAllTasks() throws SQLException {
        List<ConservationTask> tasks = new ArrayList<>();
        String sql = "SELECT task_id, site_id, issue, action, priority, status, assigned_to "
                + "FROM conservation_tasks ORDER BY task_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tasks.add(mapRow(rs));
            }
        }
        return tasks;
    }

    /**
     * Returns all conservation tasks for a specific site.
     */
    public List<ConservationTask> getTasksBySite(String siteId) throws SQLException {
        List<ConservationTask> tasks = new ArrayList<>();
        String sql = "SELECT task_id, site_id, issue, action, priority, status, assigned_to "
                + "FROM conservation_tasks WHERE site_id = ? ORDER BY task_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, siteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tasks.add(mapRow(rs));
                }
            }
        }
        return tasks;
    }

    /**
     * Updates the status of an existing task.
     *
     * @return true if a row was updated, false if the task ID did not exist
     */
    public boolean updateStatus(String taskId, String newStatus) throws SQLException {
        String sql = "UPDATE conservation_tasks SET status = ? WHERE task_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setString(2, taskId);
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    /**
     * Checks whether a given task ID is already in use.
     */
    public boolean exists(String taskId) throws SQLException {
        String sql = "SELECT task_id FROM conservation_tasks WHERE task_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, taskId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private ConservationTask mapRow(ResultSet rs) throws SQLException {
        return new ConservationTask(
                rs.getString("task_id"),
                rs.getString("site_id"),
                rs.getString("issue"),
                rs.getString("action"),
                rs.getString("priority"),
                rs.getString("status"),
                rs.getString("assigned_to")
        );
    }
}
