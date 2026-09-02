Contributing Guidelines
=======================

Thanks for your interest in contributing. A few small rules to keep the project consistent:

- Fork the repository and create feature branches for changes.
- Keep the code style and package structure consistent with existing files.
- For Java code, add small, focused commits and include brief test steps.
- If adding resources, prefer `src/main/resources` for runtime data and `src/test/resources` for test-only fixtures.
- Open a pull request describing the change, rationale and any impact to test runs.

If you are updating dependencies, run `mvn -q -DskipTests=false test` locally to ensure tests still run.
