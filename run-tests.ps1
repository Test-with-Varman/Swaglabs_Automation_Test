# Swaglabs Test Execution Script for PowerShell
# Usage: .\run-tests.ps1

function Show-Menu {
    Clear-Host
    Write-Host ""
    Write-Host "===============================================" -ForegroundColor Cyan
    Write-Host "  SWAGLABS TEST SUITE EXECUTION MENU" -ForegroundColor Cyan
    Write-Host "===============================================" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Select Test Execution Mode:" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "1. Sequential Execution (Development)" -ForegroundColor Green
    Write-Host "   - Tests run one after another" -ForegroundColor DarkGreen
    Write-Host "   - Best for: Debugging, Development" -ForegroundColor DarkGreen
    Write-Host "   - Time: ~45-60 minutes" -ForegroundColor DarkGreen
    Write-Host ""
    Write-Host "2. Parallel Execution (Production)" -ForegroundColor Green
    Write-Host "   - Tests run with 3 threads" -ForegroundColor DarkGreen
    Write-Host "   - Best for: CI/CD, Fast feedback" -ForegroundColor DarkGreen
    Write-Host "   - Time: ~15-20 minutes (3x faster!)" -ForegroundColor DarkGreen
    Write-Host ""
    Write-Host "3. Display Test Report" -ForegroundColor Green
    Write-Host "   - Open latest test results" -ForegroundColor DarkGreen
    Write-Host ""
    Write-Host "4. Clean & Run Sequential" -ForegroundColor Green
    Write-Host "   - Clean previous results, then run sequential" -ForegroundColor DarkGreen
    Write-Host ""
    Write-Host "5. Clean & Run Parallel" -ForegroundColor Green
    Write-Host "   - Clean previous results, then run parallel" -ForegroundColor DarkGreen
    Write-Host ""
    Write-Host "6. Show Detailed Info" -ForegroundColor Green
    Write-Host "   - Display test suite information" -ForegroundColor DarkGreen
    Write-Host ""
    Write-Host "7. Exit" -ForegroundColor Red
    Write-Host ""
}

function Show-Info {
    Clear-Host
    Write-Host ""
    Write-Host "===============================================" -ForegroundColor Cyan
    Write-Host "  TEST SUITE INFORMATION" -ForegroundColor Cyan
    Write-Host "===============================================" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Test Classes Included:" -ForegroundColor Yellow
    Write-Host "  1. LoginTest" -ForegroundColor White
    Write-Host "  2. ProductpageTest" -ForegroundColor White
    Write-Host "  3. CartpageTest" -ForegroundColor White
    Write-Host "  4. FInalCheckoutpageTest" -ForegroundColor White
    Write-Host "  5. infopageTest" -ForegroundColor White
    Write-Host "  6. EndtoEndTest" -ForegroundColor White
    Write-Host ""
    Write-Host "Sequential Suite (testng.xml):" -ForegroundColor Yellow
    Write-Host "  - Runs all tests sequentially" -ForegroundColor White
    Write-Host "  - Single thread execution" -ForegroundColor White
    Write-Host "  - Lowest resource usage" -ForegroundColor White
    Write-Host ""
    Write-Host "Parallel Suite (testng-parallel.xml):" -ForegroundColor Yellow
    Write-Host "  - Runs tests with 3 parallel threads" -ForegroundColor White
    Write-Host "  - Each test class runs independently" -ForegroundColor White
    Write-Host "  - ~3x faster execution" -ForegroundColor White
    Write-Host ""
    Write-Host "Test Output Directory:" -ForegroundColor Yellow
    Write-Host "  $PSScriptRoot\test-output\" -ForegroundColor White
    Write-Host ""
    Write-Host "Key Files:" -ForegroundColor Yellow
    Write-Host "  - index.html (Main test report)" -ForegroundColor White
    Write-Host "  - testng-results.xml (Detailed results)" -ForegroundColor White
    Write-Host "  - testng-failed.xml (Failed tests)" -ForegroundColor White
    Write-Host ""
    Write-Host "Press Enter to continue..." -ForegroundColor Cyan
    Read-Host
}

function Run-SequentialTests {
    Clear-Host
    Write-Host ""
    Write-Host "Running Sequential Test Suite..." -ForegroundColor Cyan
    Write-Host "Tests will run one after another" -ForegroundColor Yellow
    Write-Host ""
    & mvn test
    Write-Host ""
    Write-Host "Sequential tests completed!" -ForegroundColor Green
    Write-Host "Check test-output\index.html for results" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Press Enter to continue..." -ForegroundColor Cyan
    Read-Host
}

function Run-ParallelTests {
    Clear-Host
    Write-Host ""
    Write-Host "Running Parallel Test Suite (3 threads)..." -ForegroundColor Cyan
    Write-Host "Tests will run simultaneously with 3 threads" -ForegroundColor Yellow
    Write-Host ""
    & mvn test -Pparallel
    Write-Host ""
    Write-Host "Parallel tests completed!" -ForegroundColor Green
    Write-Host "Check test-output\index.html for results" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Press Enter to continue..." -ForegroundColor Cyan
    Read-Host
}

function Open-Report {
    Clear-Host
    if (Test-Path "test-output\index.html") {
        Write-Host ""
        Write-Host "Opening test report in default browser..." -ForegroundColor Cyan
        Write-Host ""
        & start "test-output\index.html"
        Write-Host "Report opened!" -ForegroundColor Green
        Start-Sleep -Seconds 2
    } else {
        Write-Host ""
        Write-Host "ERROR: Test report not found!" -ForegroundColor Red
        Write-Host "Run tests first to generate report" -ForegroundColor Yellow
        Write-Host ""
        Write-Host "Press Enter to continue..." -ForegroundColor Cyan
        Read-Host
    }
}

function Clean-And-RunSequential {
    Clear-Host
    Write-Host ""
    Write-Host "Cleaning previous results..." -ForegroundColor Cyan
    & mvn clean
    Write-Host ""
    Write-Host "Running Sequential Test Suite..." -ForegroundColor Cyan
    Write-Host ""
    & mvn test
    Write-Host ""
    Write-Host "Sequential tests completed!" -ForegroundColor Green
    Write-Host "Check test-output\index.html for results" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Press Enter to continue..." -ForegroundColor Cyan
    Read-Host
}

function Clean-And-RunParallel {
    Clear-Host
    Write-Host ""
    Write-Host "Cleaning previous results..." -ForegroundColor Cyan
    & mvn clean
    Write-Host ""
    Write-Host "Running Parallel Test Suite (3 threads)..." -ForegroundColor Cyan
    Write-Host ""
    & mvn test -Pparallel
    Write-Host ""
    Write-Host "Parallel tests completed!" -ForegroundColor Green
    Write-Host "Check test-output\index.html for results" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Press Enter to continue..." -ForegroundColor Cyan
    Read-Host
}

# Main loop
do {
    Show-Menu
    $choice = Read-Host "Enter your choice (1-7)"
    
    switch ($choice) {
        "1" { Run-SequentialTests }
        "2" { Run-ParallelTests }
        "3" { Open-Report }
        "4" { Clean-And-RunSequential }
        "5" { Clean-And-RunParallel }
        "6" { Show-Info }
        "7" { break }
        default {
            Write-Host ""
            Write-Host "Invalid choice. Please try again." -ForegroundColor Red
            Write-Host "Press Enter to continue..." -ForegroundColor Cyan
            Read-Host
        }
    }
} while ($true)

Write-Host ""
Write-Host "Thank you for using Swaglabs Test Suite!" -ForegroundColor Green
