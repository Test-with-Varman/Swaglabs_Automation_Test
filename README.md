
# seautomation

This repository contains a Selenium-based test automation project using Maven and TestNG.

Overview
- Project: seautomation
- Language: Java
- Build: Maven
- Test runner: TestNG (testng.xml)

Quickstart
1. Prerequisites: Java 11+, Maven installed, and ChromeDriver available on PATH (or managed by your environment).
2. From project root run:

```powershell
mvn clean test
```

Project structure (high-level)
- `src/main/java` - application and test support code (browser setup, utilities)
- `src/main/resources` - configuration and test data (properties, json) — resources are loaded from the classpath at runtime
- `src/test/java` - TestNG test classes
- `screenshots/` - test screenshots generated at runtime
- `testng.xml` / `testng-parallel.xml` - TestNG suites

What I changed
- Moved property and JSON resource access to classpath loading so files placed in `src/main/resources` are used at runtime.
  - `src/main/resources/commdata.properties` now contains the base `url` used by `readproperties`.
  - `src/main/resources/data.json` contains example checkout test data used by `CheckoutDataReader`.
- Updated `readproperties.java` and `json/CheckoutDataReader.java` to load resources via the classloader.

Notes & suggested cleanup
- You may still have duplicates of `commdata.properties` or `data.json` under `src/main/java/properties` or `src/main/java/json`. These are left in place for backward compatibility but can be removed to avoid confusion — removing them is optional.
- Excel file `src/main/java/ExcelUtils/swag.xlsx` remains in the project and is read via a file path in the code; you can move it to `src/test/resources` or adapt the code to classpath-loading if preferred.

Contribution & License
- See `CONTRIBUTING.md` for contribution guidelines.
- This repository includes a permissive MIT license in `LICENSE`.

Troubleshooting
- If tests fail to start because ChromeDriver is not found, ensure the driver executable is on your PATH or managed by a WebDriver manager.
- If resource files are not found at runtime, ensure you run Maven from the project root so resources are included on the classpath, e.g. `mvn -q test`.

Maintainers: update README with CI instructions, profiles or additional run examples as needed.
