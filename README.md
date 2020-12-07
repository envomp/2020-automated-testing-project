# icd0004 course project 2020

## Project authors
- Oskar Pihlak
- Enrico Vompa

## Used tech stack
 - Java
 - Maven

# Running the application

## With docker

Prerequisites:
- docker-compose

Make sure you have `files` directory with `input.txt` inside of it present on the same level as the `docker-compose.yml` file

Or change the location in `.env` file

And finally run `docker-compose run weather_app`

## Locally

Prerequisites:
- Java

First create jar

if windows:
```shell script
mvnw.cmd package
```
else
```shell script
./mvnw package
```

Either create an input file to `/app/input.txt` and run `java -jar target/weather-1.0.0.jar`

Or choose a custom location like `./files/input.txt` and run `java -jar target/weather-1.0.0.jar --weather.report.inputPath=files/input.txt`

both variables can be set as environment variables (OPEN_WEATHER_MAP_API_KEY and WEATHER_APP_INPUT respectively) as well

If any tests fail locally when running on local machine or when jar doesn't process non ascii characters well, then make sure locale is set to utf-8 or use docker instead

# Running tests

Tests are automatically ran when building the project

Either run:
```shell script
docker-compose -f docker-compose-build.yml build weather_app
```

or use the local run guide up top


# Functional requirements

## Main details and current weather
- [X] City name can be provided as a string input
- [X] The output is a weather report with main details and current weather data
- [X] Main details part has city, coordinates and temperatureUnit properties
- [X] Coordinates are in the format lat, log. Example: "59.44,24.75"
- [X] Current weather part has date, temperature, humidity and pressure properties
- [X] Output is a JSON object
- [X] At least 3 unit tests exists
- [X] Mock integration test exists for OWM for the main details data
- [X] OWM integration is covered by integration tests for the main details data

## Forecast 
- [X] City name can be provided as a string input
- [X] The output is a weather report with main details and current weather data AND forecast report
- [X] Forecast report part has date, temperature, humidity and pressure for each day
- [X] Forecast calculates average of temperature, humidity and pressure
- [X] Forecast is in ascending order (2020-10-01, 2020-10-02, 2020-10-03)
- [X] At least 3 unit tests exists
- [X] Mock integration test exists for OWM for the forecast data
- [X] OWM integration is covered by integration tests for the forecast data

## Read city name from file and produce a JSON file for given city
- [X] Only specific file format is allowed (you choose which: **txt**, csv, json, plain, docx)
- [X] Display error message if an unsupported file is provided
- [X] Display error message when file is missing
- [X] Write 3 integration tests to test integrations between the weather report application and file reader and writer

## Read multiple city names from file and produce a JSON output file for each city
- [X] Can read multiple cities from file
- [X] Can create weather report for given cities into separate JSON files
- [X] Log WARN message when existing weather report file for city is being overwritten
- [X] When an error occurs for invalid city name(s) then weather reports are created only for valid city names 
- [X] When an error occurs for invalid city name(s) then application logs ERROR message for that city.

## Continuous Integration
- [X] CI pipeline is ran when changes are pushed to master
- [X] CI pipeline fails if any test fails
- [X] CI pipeline passes when all tests have passed 
- [X] CI pipeline produces a log (why did it fail?)
