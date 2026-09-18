import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * InspectionDAO.java
 *
 * JDBC operations for the inspections table: recording a new inspection
 * and retrieving inspection history for a given site.
 */
public class InspectionDAO {

    /**
     * Inserts a new inspection record.
     */
    public void addInspection(Inspection inspection) throws SQLException {
        String sql = "INSERT INTO inspections "
                + "(inspection_id, site_id, inspector_name, inspection_date, inspection_condition, issues_found, remarks) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, inspection.getInspectionId());
            ps.setString(2, inspection.getSiteId());
            ps.setString(3, inspection.getInspectorName());
            ps.setDate(4, Date.valueOf(inspection.getDate()));
            ps.setString(5, inspection.getCondition());
            ps.setString(6, inspection.getIssuesFound());
            ps.setString(7, inspection.getRemarks());
            ps.executeUpdate();
        }
    }

    /**
     * Returns the full inspection history for a given site, most recent first.
     */
    public List<Inspection> getInspectionsBySite(String siteId) throws SQLException {
        List<Inspection> inspections = new ArrayList<>();
        String sql = "SELECT inspection_id, site_id, inspector_name, inspection_date, "
                + "inspection_condition, issues_found, remarks FROM inspections "
                + "WHERE site_id = ? ORDER BY inspection_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, siteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    inspections.add(mapRow(rs));
                }
            }
        }
        return inspections;
    }

    /**
     * Checks whether a given inspection ID is already in use.
     */
    public boolean exists(String inspectionId) throws SQLException {
        String sql = "SELECT inspection_id FROM inspections WHERE inspection_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, inspectionId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Inspection mapRow(ResultSet rs) throws SQLException {
        return new Inspection(
                rs.getString("inspection_id"),
                rs.getString("site_id"),
                rs.getString("inspector_name"),
                rs.getDate("inspection_date").toString(),
                rs.getString("inspection_condition"),
                rs.getString("issues_found"),
                rs.getString("remarks")
        );
    }
}
