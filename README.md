# Applitools Hackathon Project

### Pre-requisite:
<a href="https://maven.apache.org/install.html">Maven</a>, <a href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">JDK</a> installed

Install all dependencies from pom.xml:
```
cd  applitools-hackathon
mvn install -Dmaven.test.skip=true to install
```

### Run Tests as a maven command
Run **Traditional** Test Suite

  * V1:
  ```mvn clean test -DsuiteXmlFile=TraditionalTestSuiteV1.xml```

  * V2:
  ```mvn clean test -DsuiteXmlFile=TraditionalTestSuiteV2.xml```

Run **VISUAL AI** Test Suite

  * V1:
  ```mvn clean test -DsuiteXmlFile=AIVisualTestSuiteV1.xml```

  * V2:
  ```mvn clean test -DsuiteXmlFile=AIVisualTestSuiteV2.xml```

### Reports:
Go to ```applitoolshackathon/target/surefire-reports/Surefire\ suite/Surefire\ test.html``` and open the file in a browser

### Tools used:
Selenium 3 | Browser: Chrome 73+ | Maven | TestNg
