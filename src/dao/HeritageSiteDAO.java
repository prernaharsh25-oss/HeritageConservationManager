import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HeritageSiteDAO {
    public void addSite(HeritageSite site) throws SQLException {
        String sql = "INSERT INTO heritage_sites "
                + "(site_id, name, location, type, historical_period, current_condition) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, site.getSiteId());
            ps.setString(2, site.getName());
            ps.setString(3, site.getLocation());
            ps.setString(4, site.getType());
            ps.setString(5, site.getHistoricalPeriod());
            ps.setString(6, site.getCondition());
            ps.executeUpdate();
        }
    }

    public List<HeritageSite> getAllSites() throws SQLException {
        List<HeritageSite> sites = new ArrayList<>();
        String sql = "SELECT site_id, name, location, type, historical_period, current_condition "
                + "FROM heritage_sites ORDER BY site_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                sites.add(mapRow(rs));
            }
        }
        return sites;
    }

    public HeritageSite findById(String siteId) throws SQLException {
        String sql = "SELECT site_id, name, location, type, historical_period, current_condition "
                + "FROM heritage_sites WHERE site_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, siteId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public boolean exists(String siteId) throws SQLException {
        return findById(siteId) != null;
    }

    private HeritageSite mapRow(ResultSet rs) throws SQLException {
        return new HeritageSite(
                rs.getString("site_id"),
                rs.getString("name"),
                rs.getString("location"),
                rs.getString("type"),
                rs.getString("historical_period"),
                rs.getString("current_condition")
        );
    }
}
