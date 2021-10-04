# Digital Skills Technical Task - Identity E2E

Technical Task - Identity E2E

## Installation

- Use either IntelliJ or Eclipse as IDE

- import a project by cloning as below:
 ```
 'git clone https://github.com/jgireesh/Technical-Task-Identity-E2E-05OCT21.git'
  ```
once imported, use below commands to download required dependencies for the project.

```
mvn clean install
```

## Instructions to run

Run the @Test method 

under src/test/java/com/technicalTest/TechnicalTests.java
digitalSkillsAssignment()

## Assumptions made

- Chromedriver from project_directory/browserdrivers folder has macOs driver, if you are using windows download appropriate driver.

  Example: if your chrome browser belongs to version 91, so download chromedriver with 91 
  version only.

- Tested with only Chrome browser, other browsers can be implemented the same way as chromedriver.

- Output file provided has BW57BOF and input file has BW57 BOW, so assumption made to correct as typo and used output file. if that was not expected, error appears in the screen while searching for number plate and all values of actual becomes null, so that will be still fail.

- Used TestNG framework with simple annotations, however I am mostly comfortable only in BDD Cucumber frameworks.

Sorry: if any wrong assumptions made, please feel free to let me know so that will redo them.


## Contact me

If anything please email me on [Gireesh](j.gireesh@outlook.com)