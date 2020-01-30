# Applitools Hackathon Project:

## Pre-requisite:
<a href="https://maven.apache.org/install.html">Maven</a>, <a href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">JDK</a> installed

Install all dependencies from pom.xml:
```
cd ~
git clone https://github.com/eajazali87/applitools-hackathon.git
cd applitools-hackathon
mvn install -Dmaven.test.skip=true
```

## Run Tests as a maven command
Run **Traditional** Test Suite

  * V1:
  ```mvn clean test -Dtest_suite_xml=TraditionalTestSuiteV1.xml```

  * V2:
  ```mvn clean test -Dtest_suite_xml=TraditionalTestSuiteV2.xml```

Run **VISUAL AI** Test Suite

  * V1:
  ```mvn clean test -Dtest_suite_xml=AIVisualTestSuiteV1.xml```

  * V2:
  ```mvn clean test -Dtest_suite_xml=AIVisualTestSuiteV2.xml```

## Reports:
Go to ```applitools-hackathon/target/surefire-reports/index.html``` and open the file in a browser

## Tools used:
Selenium 3 | Browser: Chrome 73+ | Maven | TestNg


