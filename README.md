# miroTest

Miro Assignment:

Pre-requisite:
1.	Apache Maven 3.6
2.	Java 8 or above
3.	IDE- eclipse, intellieJ(or any other)

Framework:
1.	Framework has been developed using Page Object modal and page factory design pattern.
2.	Framework creates multi-thread driver instances to support parallel test execution.
3.	Framework support multi browser execution (chrome, Firefox, edge).
4.	Framework uses different listeners to keep more control over actions.
5.	Framework uses ‘Extent’ reports as a reporting tool.
6.	Framework support auto ‘re-triggered’ option for failed scripts.
7.	Framework used dataprovider to pass different set of inputs.
8.	Framework uses external input file(excel) to read input from outside source code.

Execution Instructions:
•	Inside repository configxmlmainTest.xml is placed.
•	Set the browser name and run it as testNG suite.
•	Retry count to retrigger failed scripts has been put in ‘com.utilityConstants’ file.

Test scenarios covered:
1.	Happy path- create a new user
2.	Negative case- if entered details like email is not valid input
3.	Negative case- if entered data like email is already existing

Reporting:
•	Reports can be found as an ‘html’ file inside ‘extentreports’ folder.
 

 

 

Future Enhancement:
1.	Zip previously executed reports in a separate folder.
2.	Record the failed/passed testcases on requirement basis.
3.	Integration with defect logging tools such as – Jira.
4.	Provide external feature to trigger the specific testcases.
