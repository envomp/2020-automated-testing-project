## icd0004 course project 2020

### Project authors
- Oskar Pihlak
- Magnus Muru
- Enrico Vompa

### Used tech stack
 - Java
 - Maven

## Running the application

if windows:
```shell script
todo
```
else
```shell script
todo
```

## Functional requirements

### Main details and current weather
- [X] City name can be provided as a string input
- [X] The output is a weather report with main details and current weather data
- [X] Main details part has city, coordinates and temperatureUnit properties
- [X] Coordinates are in the format lat, log. Example: "59.44,24.75"
- [X] Current weather part has date, temperature, humidity and pressure properties
- [X] Output is a JSON object
- [X] At least 3 unit tests exists
- [ ] Mock integration test exists for OWM for the main details data
- [ ] OWM integration is covered by integration tests for the main details data

### Forecast 
- [X] City name can be provided as a string input
- [X] The output is a weather report with main details and current weather data AND forecast report
- [X] Forecast report part has date, temperature, humidity and pressure for each day
- [X] Forecast calculates average of temperature, humidity and pressure
- [X] Forecast is in ascending order (2020-10-01, 2020-10-02, 2020-10-03)
- [X] At least 3 unit tests exists
- [ ] Mock integration test exists for OWM for the forecast data
- [ ] OWM integration is covered by integration tests for the forecast data

### Read city name from file and produce a JSON file for given city
- [ ] Only specific file format is allowed (you choose which: txt, csv, json, plain, docx)
- [ ] Display error message if an unsupported file is provided
- [ ] Display error message when file is missing
- [ ] Write 3 integration tests to test integrations between the weather report application and file reader and writer

### Read multiple city names from file and produce a JSON output file for each city
- [ ] Can read multiple cities from file
- [ ] Can create weather report for given cities into separate JSON files
- [ ] Log INFO message when existing weather report file for city is being overwritten
- [ ] When an error occurs for invalid city name(s) then weather reports are created only for valid city names 
- [ ] When an error occurs for invalid city name(s) then application logs ERROR message for that city.

### Continuous Integration
- [X] CI pipeline is ran when changes are pushed to master
- [X] CI pipeline fails if any test fails
- [X] CI pipeline passes when all tests have passed 
- [X] CI pipeline produces a log (why did it fail?)
