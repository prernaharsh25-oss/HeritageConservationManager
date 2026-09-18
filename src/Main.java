import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final HeritageSiteDAO siteDAO = new HeritageSiteDAO();
    private static final InspectionDAO inspectionDAO = new InspectionDAO();
    private static final ConservationTaskDAO taskDAO = new ConservationTaskDAO();
    private static final ReportGenerator reportGenerator = new ReportGenerator();
    private static final Map<String, HeritageSite> siteCache = new HashMap<>();

    public static void main(String[] args) {
        refreshSiteCache();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readMenuChoice(1, 5);
            switch (choice) {
                case 1:
                    heritageSitesMenu();
                    break;
                case 2:
                    inspectionsMenu();
                    break;
                case 3:
                    tasksMenu();
                    break;
                case 4:
                    generateReport();
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting Heritage Conservation Manager. Goodbye!");
                    break;
                default:
                    // readMenuChoice already restricts the range; nothing to do here.
                    break;
            }
        }
        scanner.close();
    }

    // ==========================================================
    // MAIN MENU
    // ==========================================================

    private static void printMainMenu() {
        System.out.println("\n=========================================");
        System.out.println(" HERITAGE CONSERVATION MANAGER");
        System.out.println("=========================================");
        System.out.println("1. Heritage Sites");
        System.out.println("2. Inspections");
        System.out.println("3. Conservation Tasks");
        System.out.println("4. Generate Report");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    // ==========================================================
    // HERITAGE SITES
    // ==========================================================

    private static void heritageSitesMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Heritage Sites ---");
            System.out.println("1. Add Site");
            System.out.println("2. View All Sites");
            System.out.println("3. Search Site");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");
            int choice = readMenuChoice(1, 4);
            switch (choice) {
                case 1:
                    addSite();
                    break;
                case 2:
                    viewAllSites();
                    break;
                case 3:
                    searchSite();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    break;
            }
        }
    }

    private static void addSite() {
        try {
            System.out.print("Site ID: ");
            String siteId = readNonEmpty();
            if (siteDAO.exists(siteId)) {
                throw new InvalidDataException("A site with ID '" + siteId + "' already exists.");
            }

            System.out.print("Name: ");
            String name = readNonEmpty();
            System.out.print("Location: ");
            String location = readNonEmpty();
            System.out.print("Type: ");
            String type = readNonEmpty();
            System.out.print("Historical Period: ");
            String period = readLine();
            System.out.print("Current Condition: ");
            String condition = readNonEmpty();

            HeritageSite site = new HeritageSite(siteId, name, location, type, period, condition);
            siteDAO.addSite(site);
            siteCache.put(siteId, site);

            System.out.println("Site added successfully.");
        } catch (InvalidDataException e) {
            System.out.println("Invalid data: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database error while adding site: " + e.getMessage());
        }
    }

    private static void viewAllSites() {
        try {
            List<HeritageSite> sites = siteDAO.getAllSites();
            if (sites.isEmpty()) {
                System.out.println("No heritage sites recorded yet.");
                return;
            }
            System.out.println("\nSite ID | Name | Location | Type | Period | Condition");
            for (HeritageSite site : sites) {
                System.out.println(site);
            }
        } catch (SQLException e) {
            System.out.println("Database error while fetching sites: " + e.getMessage());
        }
    }

    private static void searchSite() {
        try {
            System.out.print("Enter Site ID to search: ");
            String siteId = readNonEmpty();
            HeritageSite site = findSiteOrThrow(siteId);
            System.out.println(site.generateReport());
        } catch (SiteNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database error while searching for site: " + e.getMessage());
        }
    }

    // ==========================================================
    // INSPECTIONS
    // ==========================================================

    private static void inspectionsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Inspections ---");
            System.out.println("1. Record Inspection");
            System.out.println("2. View Site Inspection History");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");
            int choice = readMenuChoice(1, 3);
            switch (choice) {
                case 1:
                    recordInspection();
                    break;
                case 2:
                    viewInspectionHistory();
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    break;
            }
        }
    }

    private static void recordInspection() {
        try {
            System.out.print("Site ID: ");
            String siteId = readNonEmpty();
            findSiteOrThrow(siteId); // ensures the site exists

            System.out.print("Inspection ID: ");
            String inspectionId = readNonEmpty();
            if (inspectionDAO.exists(inspectionId)) {
                throw new InvalidDataException("An inspection with ID '" + inspectionId + "' already exists.");
            }

            System.out.print("Inspector Name: ");
            String inspectorName = readNonEmpty();

            System.out.print("Date (yyyy-MM-dd): ");
            String date = readValidDate();

            System.out.print("Condition: ");
            String condition = readNonEmpty();
            System.out.print("Issues Found: ");
            String issuesFound = readLine();
            System.out.print("Remarks: ");
            String remarks = readLine();

            Inspection inspection = new Inspection(
                    inspectionId, siteId, inspectorName, date, condition, issuesFound, remarks);
            inspectionDAO.addInspection(inspection);

            System.out.println("Inspection recorded successfully.");
        } catch (SiteNotFoundException | InvalidDataException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database error while recording inspection: " + e.getMessage());
        }
    }

    private static void viewInspectionHistory() {
        try {
            System.out.print("Site ID: ");
            String siteId = readNonEmpty();
            findSiteOrThrow(siteId);

            List<Inspection> inspections = inspectionDAO.getInspectionsBySite(siteId);
            if (inspections.isEmpty()) {
                System.out.println("No inspections recorded for this site yet.");
                return;
            }
            for (Inspection inspection : inspections) {
                System.out.println(inspection.generateReport());
            }
        } catch (SiteNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database error while fetching inspection history: " + e.getMessage());
        }
    }

    // ==========================================================
    // CONSERVATION TASKS
    // ==========================================================

    private static void tasksMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Conservation Tasks ---");
            System.out.println("1. Create Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Update Task Status");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");
            int choice = readMenuChoice(1, 4);
            switch (choice) {
                case 1:
                    createTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    updateTaskStatus();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    break;
            }
        }
    }

    private static void createTask() {
        try {
            System.out.print("Site ID: ");
            String siteId = readNonEmpty();
            findSiteOrThrow(siteId);

            System.out.print("Task ID: ");
            String taskId = readNonEmpty();
            if (taskDAO.exists(taskId)) {
                throw new InvalidDataException("A task with ID '" + taskId + "' already exists.");
            }

            System.out.print("Issue: ");
            String issue = readNonEmpty();
            System.out.print("Action: ");
            String action = readNonEmpty();

            System.out.print("Priority (LOW, MEDIUM, HIGH): ");
            String priority = readValidPriority();

            System.out.print("Assigned To: ");
            String assignedTo = readLine();

            
            ConservationTask task = new ConservationTask(
                    taskId, siteId, issue, action, priority, "PENDING", assignedTo);
            taskDAO.addTask(task);

            System.out.println("Task created successfully with status PENDING.");
        } catch (SiteNotFoundException | InvalidDataException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database error while creating task: " + e.getMessage());
        }
    }

    private static void viewTasks() {
        try {
            System.out.print("View (1) all tasks or (2) tasks for one site? ");
            int scope = readMenuChoice(1, 2);
            List<ConservationTask> tasks;
            if (scope == 1) {
                tasks = taskDAO.getAllTasks();
            } else {
                System.out.print("Site ID: ");
                String siteId = readNonEmpty();
                findSiteOrThrow(siteId);
                tasks = taskDAO.getTasksBySite(siteId);
            }

            if (tasks.isEmpty()) {
                System.out.println("No conservation tasks found.");
                return;
            }
            System.out.println("\nTask ID | Site ID | Issue | Action | Priority | Status | Assigned To");
            for (ConservationTask task : tasks) {
                System.out.println(task);
            }
        } catch (SiteNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database error while fetching tasks: " + e.getMessage());
        }
    }

    private static void updateTaskStatus() {
        try {
            System.out.print("Task ID: ");
            String taskId = readNonEmpty();

            System.out.print("New Status (PENDING, IN PROGRESS, COMPLETED): ");
            String status = readValidStatus();

            boolean updated = taskDAO.updateStatus(taskId, status);
            if (updated) {
                System.out.println("Task status updated successfully.");
            } else {
                System.out.println("No task found with ID '" + taskId + "'.");
            }
        } catch (SQLException e) {
            System.out.println("Database error while updating task status: " + e.getMessage());
        }
    }

    // ==========================================================
    // REPORT GENERATION
    // ==========================================================

    private static void generateReport() {
        try {
            System.out.print("Enter Site ID to generate report for: ");
            String siteId = readNonEmpty();
            HeritageSite site = findSiteOrThrow(siteId);

            List<Inspection> inspections = inspectionDAO.getInspectionsBySite(siteId);
            List<ConservationTask> tasks = taskDAO.getTasksBySite(siteId);

            String path = reportGenerator.generateSiteReport(site, inspections, tasks);
            System.out.println("Report generated: " + path);
        } catch (SiteNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database error while gathering report data: " + e.getMessage());
        } catch (java.io.IOException e) {
            System.out.println("File error while writing report: " + e.getMessage());
        }
    }

    // ==========================================================
    // HELPERS
    // ==========================================================

    
    private static HeritageSite findSiteOrThrow(String siteId) throws SQLException, SiteNotFoundException {
        HeritageSite site = siteDAO.findById(siteId);
        if (site == null) {
            throw new SiteNotFoundException("No heritage site found with ID '" + siteId + "'.");
        }
        siteCache.put(siteId, site);
        return site;
    }

    private static void refreshSiteCache() {
        try {
            siteCache.clear();
            for (HeritageSite site : siteDAO.getAllSites()) {
                siteCache.put(site.getSiteId(), site);
            }
        } catch (SQLException e) {
            System.out.println("Warning: could not load sites from database at startup: " + e.getMessage());
        }
    }

    private static String readLine() {
        return scanner.nextLine().trim();
    }

    private static String readNonEmpty() {
        String value = readLine();
        while (value.isEmpty()) {
            System.out.print("This field cannot be empty. Please enter a value: ");
            value = readLine();
        }
        return value;
    }

    private static String readValidDate() {
        while (true) {
            String value = readLine();
            try {
                LocalDate.parse(value);
                return value;
            } catch (DateTimeParseException e) {
                System.out.print("Invalid date format. Please use yyyy-MM-dd: ");
            }
        }
    }

    private static String readValidPriority() {
        while (true) {
            String value = readLine().toUpperCase();
            if (ConservationTask.isValidPriority(value)) {
                return value;
            }
            System.out.print("Invalid priority. Enter LOW, MEDIUM, or HIGH: ");
        }
    }

    private static String readValidStatus() {
        while (true) {
            String value = readLine().toUpperCase();
            if (ConservationTask.isValidStatus(value)) {
                return value;
            }
            System.out.print("Invalid status. Enter PENDING, IN PROGRESS, or COMPLETED: ");
        }
    }

    /**
     * Reads a menu choice within [min, max]. Re-prompts on non-numeric
     * or out-of-range input so bad input never crashes the program.
     */
    private static int readMenuChoice(int min, int max) {
        while (true) {
            String input = readLine();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
