import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * ReportGenerator.java
 *
 * Builds a readable text report for a heritage site (site info +
 * inspection history + conservation tasks) and writes it to
 * reports/<siteId>_report.txt using java.nio.file.Files.
 */
public class ReportGenerator {

    private static final String REPORTS_DIR = "reports";

    /**
     * Generates and writes a full report for one site.
     *
     * @param site         the heritage site (implements Reportable)
     * @param inspections  the site's inspection history (each implements Reportable)
     * @param tasks        the site's conservation tasks
     * @return the path of the written report file
     * @throws IOException if the file cannot be written
     */
    public String generateSiteReport(HeritageSite site, List<Inspection> inspections,
                                      List<ConservationTask> tasks) throws IOException {

        StringBuilder report = new StringBuilder();
        report.append("=========================================\n");
        report.append(" HERITAGE CONSERVATION REPORT\n");
        report.append("=========================================\n\n");

        // Demonstrates interface polymorphism: both HeritageSite and
        // Inspection are used here only through their Reportable behavior.
        Reportable siteAsReportable = site;
        report.append(siteAsReportable.generateReport());

        report.append("\n---- INSPECTION HISTORY ----\n");
        if (inspections.isEmpty()) {
            report.append("  No inspections recorded.\n");
        } else {
            for (Inspection inspection : inspections) {
                Reportable inspectionAsReportable = inspection;
                report.append(inspectionAsReportable.generateReport());
                report.append("\n");
            }
        }

        report.append("---- CONSERVATION TASKS ----\n");
        if (tasks.isEmpty()) {
            report.append("  No conservation tasks recorded.\n");
        } else {
            for (ConservationTask task : tasks) {
                report.append("  - Task ID     : ").append(task.getTaskId()).append("\n");
                report.append("    Issue       : ").append(task.getIssue()).append("\n");
                report.append("    Action      : ").append(task.getAction()).append("\n");
                report.append("    Priority    : ").append(task.getPriority()).append("\n");
                report.append("    Status      : ").append(task.getStatus()).append("\n");
                report.append("    Assigned To : ").append(task.getAssignedTo()).append("\n\n");
            }
        }

        report.append("=========================================\n");
        report.append(" END OF REPORT\n");
        report.append("=========================================\n");

        return writeToFile(site.getSiteId(), report.toString());
    }

    private String writeToFile(String siteId, String content) throws IOException {
        Path dir = Paths.get(REPORTS_DIR);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }

        Path filePath = dir.resolve(siteId + "_report.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write(content);
        }
        return filePath.toString();
    }
}
