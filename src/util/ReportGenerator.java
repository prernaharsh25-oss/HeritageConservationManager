import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class ReportGenerator {

    private static final String REPORTS_DIR = "reports";


    public String generateSiteReport(HeritageSite site, List<Inspection> inspections,
                                      List<ConservationTask> tasks) throws IOException {

        StringBuilder report = new StringBuilder();
        report.append("=========================================\n");
        report.append(" HERITAGE CONSERVATION REPORT\n");
        report.append("=========================================\n\n");
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
