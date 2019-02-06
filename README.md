# Rabobank-customer-statement
RaboBank Assignment for back end software development

## To Start

  * Clone this repository
  * Run `mvn clean package`
  * Run `java -jar target/CustomerStatementProcessor-0.0.1-SNAPSHOT.jar` or using maven command
    mvn spring-boot:run on command prompt where the pom.xml is located
  * The log file for the application will be generated under "logs/rabobank.log" directory where the project is executed.

## Implementation details

 * The implementation is a spring boot microservice.
 * The validation is implemented as annotation-driven bean validation.
 * To generate getters, builders and constructors of bean model project lombok is used.
 * Based on the input criteria given in the requirement spec custom parsers for parsing
   xml and csv files are used.
   
## Description of the source classes

 * Parsing of the files are done by the parser classes in the util package - 
   "CsvFileParser" and "CsvLineParser" where each record is parsed and transformed to a bean.
   Similarly for xml files "XmlFileParser" is used to parse the xml record and "XmlStatementParser"
   is used to transform the record to "CustomerStatement" object.
 * An xsd file is defined based on the criteria to auto-generate classes initially where the parsed
   data is loaded after which it will be transformed to a bean.
 * "ReportWriter" utility is used for writing the final output.
 * "StatementValidator" utility is used to validate the parsed records and return "StatementError" bean.   
 * "CustomerStatement" class is used to validate for violation of the rule - startBalance + mutation = endBalance.
 
## Unit Test coverage

 * Respective unit test coverage of the classes are implemented
 
## Code Quality Analysis
 
  Code quality reports are generated using the following command

  ````
   mvn clean package site
  ````
  Reports can be found at  `target\site\project-reports.html`
  
## Running the application

   * To test the application a REST client like POSTMAN is required.
   
   * For uploading a single file choose POST and the following url http://localhost:8080/uploadfile
   * For choosing the payload choose form-data option in POSTMAN - give the key as 'file' and 
     click the 'choose files' button to select a single file and sent.
   * To select multiple files give url as http://localhost:8080/uploadMultipleFiles the key as 'files'   
   * Sample screen shots are attached for refernce.
   * The output response is as follows which shows the violations
     {
	 
        "statementErrorList": [
		
            {
			
                "referenceNumber": "112806",
				
                "statementDescription": "Clothes for Richard de Vries",
				
                "errorMessage": "referenceNumber already processed"
				
            },
			
            {
                "referenceNumber": "112806",
				
                "statementDescription": "Tickets from Richard Bakker",
				
                "errorMessage": "referenceNumber already processed"
				
            }
        ],
        "statementsProcessed": 10,
		
        "violationsFound": 2,
		
        "fileName": "records.csv"
		
    }
      
  
  
## Requirements and Constraints
   
   * Compiled and Tested with JDK 1.8
   
  