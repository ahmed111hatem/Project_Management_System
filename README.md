# Corporate Management System (Access Control)

A polished, modern Java Swing desktop application implementing role-based authentication and document access management. The user interface features a custom premium dark theme optimized across platforms using clean typography and responsive grid layout structures.

##  Features
- **Secure Role-Based Authentication:** Custom verification handling roles for Admin, Project Managers (PM), Team Leaders, and Employees.
- **Automated Input Validation:** Real-time text listening filters ensuring names exclude numeric characters and emails enforce valid corporate extensions (`@gmail.com`).
- **Integrated System Documentation Viewer:** Instant cross-platform desktop integration allowing users to launch project briefs or reports natively.
- **Centralized Premium Theme Architecture:** Global decoupled styling implementation using utility configurations for unified UI components, color palettes, and adaptive rendering.

##  Architecture & Tech Stack
- **Language:** Java 21 / 17
- **UI Framework:** Java Swing & AWT (Anti-aliased cross-platform configurations)
- **Design Pattern:** Decoupled Style Architecture (Centralized Design Constants)

##  Project Structure

```text
src/main/java/org/example/
│
├── LoginScreen.java        # Main UI window containing secure authorization flows
├── theme.java              # Global centralized configuration file for styles, fonts & colors
├── AdminDashboard.java     # Secure UI window for Administrative roles
├── PMDashboard.java        # Secure UI window for Project Managers
├── LeaderDashboard.java    # Secure UI window for Team Leaders
└── EmployeeDashboard.java  # Secure UI window for general staff roles
