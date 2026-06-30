# 🚀 Quick Reference - Test Suite Execution

## 📂 Files Created

| File | Purpose |
|------|---------|
| `testng.xml` | Sequential test suite (tests run one after another) |
| `testng-parallel.xml` | Parallel test suite (3 threads simultaneously) |
| `pom.xml` (updated) | Added Maven Surefire plugin configuration |
| `TEST_SUITE_GUIDE.md` | Comprehensive guide |
| `run-tests.bat` | Windows CMD batch script |
| `run-tests.ps1` | Windows PowerShell script |

---

## ⚡ Quick Commands

### Sequential Execution
```powershell
# Using Maven directly
mvn test

# Using batch script
.\run-tests.bat
# Select option: 1

# Using PowerShell script
.\run-tests.ps1
# Select option: 1
```

### Parallel Execution (3 threads)
```powershell
# Using Maven directly
mvn test -Pparallel

# Using batch script
.\run-tests.bat
# Select option: 2

# Using PowerShell script
.\run-tests.ps1
# Select option: 2
```

### Clean & Run Sequential
```powershell
mvn clean test
```

### Clean & Run Parallel
```powershell
mvn clean test -Pparallel
```

---

## 📊 Execution Comparison

```
SEQUENTIAL (testng.xml)
├─ LoginTest
├─ ProductpageTest
├─ CartpageTest
├─ FInalCheckoutpageTest
├─ infopageTest
└─ EndtoEndTest
   Time: ~45-60 minutes | Threads: 1 | Best for: Development

PARALLEL (testng-parallel.xml) - 3 Threads
├─ Thread 1: LoginTest + ProductpageTest
├─ Thread 2: CartpageTest + FInalCheckoutpageTest
└─ Thread 3: infopageTest + EndtoEndTest
   Time: ~15-20 minutes | Threads: 3 | Best for: CI/CD
```

---

## 🎯 When to Use What?

| Use Case | Command | Mode |
|----------|---------|------|
| **Local Development** | `mvn test` | Sequential |
| **Debugging Tests** | `mvn test` | Sequential |
| **CI/CD Pipeline** | `mvn test -Pparallel` | Parallel |
| **Quick Feedback** | `mvn test -Pparallel` | Parallel |
| **Production Testing** | `mvn test -Pparallel` | Parallel |
| **Verify Fix** | `mvn clean test` | Sequential |

---

## 📊 Test Classes Included (6 Total)

1. ✅ **LoginTest** - Login functionality
2. ✅ **ProductpageTest** - Product page features
3. ✅ **CartpageTest** - Cart operations
4. ✅ **FInalCheckoutpageTest** - Final checkout
5. ✅ **infopageTest** - Information page
6. ✅ **EndtoEndTest** - Complete purchase flow (10 scenarios)

---

## 📋 Test Reports

**Location:** `D:\Desktop\project\seautomation\test-output\`

**Key Files:**
- `index.html` - Main test report (open in browser)
- `testng-results.xml` - Detailed XML results
- `testng-failed.xml` - Failed tests (if any)
- `emailable-report.html` - Email-friendly report

**View Report:**
```powershell
# Windows
start test-output\index.html

# Or run script to auto-open
.\run-tests.ps1
# Select option: 3
```

---

## 🛠️ Configuration Details

### Sequential Suite (testng.xml)
```xml
<suite name="Swaglabs Sequential Test Suite" verbose="2">
    <!-- 6 test cases run one after another -->
</suite>
```

### Parallel Suite (testng-parallel.xml)
```xml
<suite name="Swaglabs Parallel Test Suite" verbose="2" 
       parallel="classes" thread-count="3">
    <!-- 6 test cases run with 3 threads simultaneously -->
</suite>
```

### Maven Profiles (pom.xml)
```xml
<!-- Profile 1: Sequential (default) -->
<profile>
    <id>sequential</id>
    <activation><activeByDefault>true</activeByDefault></activation>
    <!-- Uses: testng.xml -->
</profile>

<!-- Profile 2: Parallel -->
<profile>
    <id>parallel</id>
    <!-- Uses: testng-parallel.xml -->
</profile>
```

---

## 🔧 Execution Methods

### Method 1: Direct Maven Commands
```powershell
cd D:\Desktop\project\seautomation\
mvn test              # Sequential
mvn test -Pparallel   # Parallel
```

### Method 2: Windows Batch Script
```powershell
D:\Desktop\project\seautomation\run-tests.bat
# Interactive menu appears
```

### Method 3: PowerShell Script
```powershell
D:\Desktop\project\seautomation\run-tests.ps1
# Interactive menu appears with colored output
```

### Method 4: Eclipse IDE
- Right-click `testng.xml` → Run As → TestNG Suite (Sequential)
- Right-click `testng-parallel.xml` → Run As → TestNG Suite (Parallel)

---

## ✅ Success Indicators

**Sequential Mode:**
```
[INFO] BUILD SUCCESS ✓
[INFO] Tests run: 100, Failures: 0, Errors: 0, Skipped: 0
Duration: ~45-60 minutes
```

**Parallel Mode:**
```
[INFO] BUILD SUCCESS ✓
[INFO] Tests run: 100, Failures: 0, Errors: 0, Skipped: 0
Duration: ~15-20 minutes (3x faster!)
```

---

## ⚠️ Important Notes

1. **Parallel Mode Requirements:**
   - Each test must have independent driver instance
   - `@BeforeMethod` must initialize fresh driver
   - `@AfterMethod` must cleanup driver properly
   - Minimum 4GB RAM recommended

2. **Thread Safety:**
   - Don't share WebDriver instances between threads
   - Use ThreadLocal if needed for driver storage
   - Ensure no shared test data

3. **Resource Usage:**
   - Sequential: Low CPU, Low RAM (~1GB)
   - Parallel (3 threads): High CPU, Medium RAM (~3-4GB)

---

## 🆘 Troubleshooting

**Problem:** Tests fail in parallel but pass sequentially
- **Solution:** Check for shared test data or driver management issues

**Problem:** "Port already in use"
- **Solution:** Ensure browsers are closing properly in tearDown()

**Problem:** Test timeout
- **Solution:** Reduce thread count or increase timeout values

**Problem:** Report not generated
- **Solution:** Check test-output directory permissions

---

## 📞 Quick Contacts

- **Test Framework:** TestNG 7.12.0
- **Browser Automation:** Selenium 4.41.0
- **Build Tool:** Apache Maven 3.x
- **Report Generation:** TestNG built-in HTML reporter

---

## 🎓 Examples

### Run Sequential and Generate Report
```powershell
mvn clean test
# Opens: test-output/index.html
```

### Run Parallel for CI/CD
```powershell
mvn clean test -Pparallel
# Completes 3x faster
# Reports in: test-output/
```

### Run Only Failed Tests
```powershell
mvn test -Pparallel -DsuiteXmlFile=test-output/testng-failed.xml
```

---

**Setup Date:** June 30, 2026  
**Version:** 1.0  
**Maintained by:** Test Automation Team
