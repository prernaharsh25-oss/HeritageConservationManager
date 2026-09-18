/**
 * Reportable.java
 *
 * Common interface implemented by any domain object that can produce
 * a human-readable text report about itself. HeritageSite and Inspection
 * both implement this interface, which lets Main / ReportGenerator work
 * with either type through a single Reportable reference
 * (interface-based polymorphism).
 */
public interface Reportable {

    /**
     * Builds and returns a plain-text report fragment describing this object.
     *
     * @return a human-readable, multi-line String report
     */
    String generateReport();
}
