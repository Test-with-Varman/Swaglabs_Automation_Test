@echo off
REM Swaglabs Test Execution Script for Windows
REM This script provides easy options to run tests

setlocal enabledelayedexpansion

cls
echo.
echo ===============================================
echo   SWAGLABS TEST SUITE EXECUTION MENU
echo ===============================================
echo.
echo Select Test Execution Mode:
echo.
echo 1. Sequential Execution (Development)
echo    - Tests run one after another
echo    - Best for: Debugging, Development
echo    - Time: ~45-60 minutes
echo.
echo 2. Parallel Execution (Production)
echo    - Tests run with 3 threads
echo    - Best for: CI/CD, Fast feedback
echo    - Time: ~15-20 minutes (3x faster!)
echo.
echo 3. Display Test Report
echo    - Open latest test results
echo.
echo 4. Clean & Run Sequential
echo    - Clean previous results, then run sequential
echo.
echo 5. Clean & Run Parallel
echo    - Clean previous results, then run parallel
echo.
echo 6. Exit
echo.

set /p choice="Enter your choice (1-6): "

if "%choice%"=="1" (
    cls
    echo Running Sequential Test Suite...
    echo.
    call mvn test
    echo.
    echo Sequential tests completed! Check test-output\index.html for results
    pause
)

if "%choice%"=="2" (
    cls
    echo Running Parallel Test Suite (3 threads)...
    echo.
    call mvn test -Pparallel
    echo.
    echo Parallel tests completed! Check test-output\index.html for results
    pause
)

if "%choice%"=="3" (
    cls
    echo Opening test report...
    start test-output\index.html
    echo Report opened in default browser
    pause
)

if "%choice%"=="4" (
    cls
    echo Cleaning previous results...
    call mvn clean
    echo.
    echo Running Sequential Test Suite...
    echo.
    call mvn test
    echo.
    echo Sequential tests completed! Check test-output\index.html for results
    pause
)

if "%choice%"=="5" (
    cls
    echo Cleaning previous results...
    call mvn clean
    echo.
    echo Running Parallel Test Suite (3 threads)...
    echo.
    call mvn test -Pparallel
    echo.
    echo Parallel tests completed! Check test-output\index.html for results
    pause
)

if "%choice%"=="6" (
    exit
)

echo Invalid choice. Please run the script again.
pause
