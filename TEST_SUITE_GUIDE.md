# Swaglabs Test Suite - Execution Guide

## 📋 Overview
This project now includes two TestNG XML suite files for batch and parallel test execution.

---

## 🚀 Suite Files

### 1. **testng.xml** - Sequential Execution
- **Execution Mode**: Sequential (tests run one after another)
- **Thread Count**: 1
- **Best For**: Debugging, CI/CD pipelines with limited resources, stable execution
- **Execution Time**: Longest (all tests run in sequence)
- **Risk Level**: Lowest

**Included Tests:**
- LoginTest
- ProductpageTest
- CartpageTest
- FInalCheckoutpageTest
- infopageTest
- EndtoEndTest

---

### 2. **testng-parallel.xml** - Parallel Execution (3 Threads)
- **Execution Mode**: Parallel by Classes
- **Thread Count**: 3 (3 test classes run simultaneously)
- **Best For**: Faster execution, production testing
- **Execution Time**: ~33% of sequential time
- **Risk Level**: Medium (requires thread-safe driver management)

**Parallel Execution Strategy:**
- 3 test classes run simultaneously
- Each class gets its own thread
- Independent BeforeMethod/AfterMethod setup for each thread

---

## 💻 How to Run Tests

### **Option 1: Sequential Execution (Recommended for Development)**

#### Using Eclipse IDE:
1. Right-click on `testng.xml`
2. Select → "Run As" → "TestNG Suite"
3. Tests will execute sequentially

#### Using Command Line:
```powershell
cd D:\Desktop\project\seautomation\

# Sequential execution (default profile)
mvn test

# OR explicitly specify sequential profile
mvn test -Psequential
```

---

### **Option 2: Parallel Execution (Recommended for CI/CD)**

#### Using Eclipse IDE:
1. Right-click on `testng-parallel.xml`
2. Select → "Run As" → "TestNG Suite"
3. Tests will execute in parallel with 3 threads

#### Using Command Line:
```powershell
cd D:\Desktop\project\seautomation\

# Parallel execution with 3 threads
mvn test -Pparallel
```

---

## 📊 Execution Comparison

| Aspect | Sequential | Parallel (3 threads) |
|--------|-----------|---------------------|
| Execution Time | ~45 min (example) | ~15 min (example) |
| Thread Safety | Not required | **Required** ✅ |
| Driver Management | Single shared | Each thread gets own driver |
| Best Use Case | Development | Production/CI-CD |
| Debugging | Easy | Difficult |
| Resource Usage | Low | High |
| Stability | Very Stable | Good (with proper setup) |

---

## 🔧 Configuration Details

### Sequential Suite (testng.xml)
```xml
<!-- No parallel attribute = Sequential execution -->
<suite name="Swaglabs Sequential Test Suite" verbose="2">
    <!-- Tests grouped logically but run one after another -->
</suite>
```

### Parallel Suite (testng-parallel.xml)
```xml
<!-- parallel="classes" + thread-count="3" = Run 3 classes in parallel -->
<suite name="Swaglabs Parallel Test Suite" verbose="2" 
       parallel="classes" thread-count="3">
    <!-- 3 test classes run simultaneously -->
</suite>
```

---

## 🛠️ Maven Profiles

### Profile 1: Sequential (Default)
```bash
mvn test
# OR
mvn test -Psequential
```
- Uses: `testng.xml`
- Execution: One test after another

### Profile 2: Parallel
```bash
mvn test -Pparallel
```
- Uses: `testng-parallel.xml`
- Execution: 3 test classes simultaneously
- Threads: 3

---

## ⚠️ Important Prerequisites for Parallel Execution

Before running parallel tests, ensure:

1. ✅ **Thread-Safe Driver Management**
   - Each test should have its own WebDriver instance
   - Your `BeforeMethod` properly initializes driver
   - Your `AfterMethod` properly cleans up driver

2. ✅ **Independent Test Data**
   - Tests don't share state
   - No shared test data between classes
   - Each test is isolated

3. ✅ **System Resources**
   - At least 3 browser instances can run simultaneously
   - Enough RAM (minimum 4GB, recommended 8GB)
   - CPU can handle 3 parallel processes

4. ✅ **Application Stability**
   - Web application can handle multiple concurrent users
   - No conflicts between simultaneous requests

---

## 📝 Test Reports

After execution, reports are generated in:

```
D:\Desktop\project\seautomation\test-output\
```

You'll find:
- **index.html** - Main test report
- **testng-failed.xml** - Failed tests (if any)
- **testng-results.xml** - Detailed results
- **emailable-report.html** - Email-friendly report

---

## 🚨 Troubleshooting Parallel Execution

### Problem: Tests fail randomly in parallel mode
**Solution**: Ensure driver is thread-safe and properly initialized in `@BeforeMethod`

### Problem: Excessive memory usage
**Solution**: Reduce thread-count from 3 to 2 in `testng-parallel.xml`

### Problem: Tests timeout
**Solution**: Increase timeout values or reduce thread count

### Problem: Port conflicts
**Solution**: Ensure each driver instance uses separate port/profile

---

## 📌 Quick Commands Reference

```powershell
# Run sequential tests
mvn test

# Run parallel tests (3 threads)
mvn test -Pparallel

# Run specific suite
mvn test -Dsurefire.suiteXmlFiles=testng.xml

# Skip tests
mvn clean install -DskipTests

# Run with detailed output
mvn test -X

# Run and generate HTML report
mvn test -Pparallel
# Then open: test-output/index.html
```

---

## 🎯 Recommended Usage

### For Development:
```powershell
mvn test  # Sequential execution
```

### For CI/CD Pipeline:
```powershell
mvn test -Pparallel  # Parallel execution - 3x faster!
```

### For Quick Verification:
```powershell
mvn test -Pparallel  # Fast feedback
```

### For Debugging:
```powershell
mvn test  # Sequential with detailed output
```

---

## 📚 Additional Notes

- Both suites include all 6 test classes
- Parallel mode runs 3 test classes simultaneously
- Execution time is approximately 3x faster with parallel execution
- Reports are generated in `test-output/` directory
- Failed tests are logged in `testng-failed.xml`

---

## 🤝 Support

If you encounter issues:
1. Check test-output/index.html for error details
2. Review testng-results.xml for test status
3. Ensure prerequisites are met
4. Try sequential mode for debugging

---

**Created**: June 30, 2026  
**Test Framework**: TestNG 7.12.0  
**Selenium Version**: 4.41.0
