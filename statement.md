# Heritage Conservation Manager — Project Statement

## 1. Problem Statement

Heritage monuments are valuable cultural and historical assets that require regular inspection and proper conservation planning. However, inspection information, identified issues, and conservation activities can become difficult to organize when they are maintained manually or across separate records.

The Heritage Conservation Manager is a Java-based application designed to provide a structured way to manage heritage sites, record their inspection history, and track conservation tasks. The system stores the information in a MySQL database using JDBC, allowing the records to be maintained and retrieved systematically.

---

## 2. Scope of the Project

The project focuses on the basic management of heritage conservation records.

The scope includes:

- Managing heritage site information.
- Recording inspections conducted for heritage sites.
- Maintaining inspection history for individual sites.
- Creating and managing conservation tasks.
- Updating the status of conservation tasks.
- Generating text-based reports for selected heritage sites.
- Storing and retrieving application data using MySQL and JDBC.

The project is implemented as a console-based Java application and focuses on record management rather than automated damage detection, prediction, or environmental monitoring.

---

## 3. Target Users

The application is intended for users involved in basic heritage site record management, such as:

- Heritage site inspectors.
- Conservation staff.
- Heritage management personnel.
- Students or academic users studying heritage conservation management systems.

---

## 4. High-Level Features

### Heritage Site Management
- Add a new heritage site.
- View all registered heritage sites.
- Search for a heritage site using its ID.

### Inspection Management
- Record an inspection for a heritage site.
- View the inspection history of a selected site.
- Store inspection date, condition, issues found, and remarks.

### Conservation Task Management
- Create conservation tasks for identified issues.
- View existing conservation tasks.
- Assign priorities to tasks.
- Update task status as pending, in progress, or completed.

### Report Generation
- Generate a text report for a selected heritage site.
- Include site information, inspection history, and conservation tasks in the report.
- Save generated reports as .txt files.

### Database Connectivity
- Use JDBC to connect the Java application with MySQL.
- Store heritage sites, inspections, and conservation tasks in relational database tables.