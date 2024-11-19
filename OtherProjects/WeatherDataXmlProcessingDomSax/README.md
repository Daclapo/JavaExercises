# XML Weather Data Processor with DOM & SAX

## Overview

This Java project processes meteorological data stored in an XML file ([`weather-data-modificado.xml`](https://github.com/Daclapo/JavaExercises/blob/main/OtherProjects/WeatherDataXmlProcessingDomSax/src/main/resources/weather-data-modificado.xml)) using both DOM and SAX parsers. XML is widely used for structured data storage due to its flexibility and readability. This project demonstrates two distinct approaches to XML parsing through two independent components:

1. **DOM-Based XML Weather Data Aggregator**:
    - Reads and analyzes weather data to compute statistical averages.
    - Outputs a structured XML summary ([`city-weather-summary.xml`](https://github.com/Daclapo/JavaExercises/blob/main/OtherProjects/WeatherDataXmlProcessingDomSax/src/main/resources/city-weather-summary.xml)).

2. **SAX-Based Weather Summary Report Generator**:
    - Extracts similar statistics using a memory-efficient approach.
    - Generates a plain-text summary ([`informeSax.txt`](https://github.com/Daclapo/JavaExercises/blob/main/OtherProjects/WeatherDataXmlProcessingDomSax/src/main/resources/outSax/informeSax.txt)).

---

## DOM-Based XML Weather Data Aggregator

This component uses the DOM (Document Object Model) parser, which loads the entire XML document into memory, making it ideal for small to moderately sized files that require detailed manipulation.

### Functionality

1. **Input Handling**:
    - Prompts the user for the path of the input XML file ([`weather-data-modificado.xml`](https://github.com/Daclapo/JavaExercises/blob/main/OtherProjects/WeatherDataXmlProcessingDomSax/src/main/resources/weather-data-modificado.xml)).
    - Validates the file's existence and terminates with an error if it is missing.

2. **Data Processing**:
    - Computes the following statistical metrics for each city:
        - **Temperature**:
            - Maximum temperature recorded across all periods and stations.
            - Minimum temperature.
            - Mean temperature, rounded to two decimal places.
        - **Humidity**:
            - Average humidity across all stations and periods.
        - **Pressure**:
            - Average atmospheric pressure, rounded to two decimal places.

3. **Output Generation**:
    - Prompts the user for the desired output XML file path.
    - Produces a structured XML file such as:
      ```xml
      <city-weather-summary>
          <city id="c001" name="Madrid">
              <average-temperature>
                  <max>19.5</max>
                  <min>13.2</min>
                  <avg>17.5</avg>
              </average-temperature>
              <average-humidity>75.3</average-humidity>
              <average-pressure>1014.2</average-pressure>
          </city>
          <!-- Other cities -->
      </city-weather-summary>
      ```

4. **Error Management**:
    - Prompts the user for confirmation before overwriting an existing output file.
    - Logs and skips invalid entries (e.g., malformed temperature values).
    - Handles missing XML tags gracefully.

### DOM Advantages and Usage

- **Tree-Based Model**: Allows traversal and manipulation of XML documents as a hierarchical structure.
- **Rich Querying**: DOM supports XPath, which simplifies locating nodes. For instance:
  ```java
  XPathExpression expr = xpath.compile("//city[@name='Madrid']/average-temperature/max");
  Node maxTempNode = (Node) expr.evaluate(doc, XPathConstants.NODE);
  System.out.println("Max Temperature: " + maxTempNode.getTextContent());
  ```

## SAX-Based Weather Summary Report Generator

This component uses the SAX (Simple API for XML) parser, which processes XML documents sequentially. SAX is particularly well-suited for large XML files due to its low memory footprint.

### Functionality

1. **Input Handling**:
    - Prompts the user for the path of the input XML file ([`weather-data-modificado.xml`](https://github.com/Daclapo/JavaExercises/blob/main/OtherProjects/WeatherDataXmlProcessingDomSax/src/main/resources/weather-data-modificado.xml)).
    - Validates the file's existence and terminates gracefully if it is not found.

2. **Data Processing**:
    - Computes the same statistics as the DOM-based component:
        - Minimum, maximum, and mean temperatures.
        - Average humidity and atmospheric pressure.

3. **Output Generation**:
    - Prompts the user for the output text file path.
    - Produces a column-aligned text report for easy readability, such as:
      ```
      Madrid
      ------------------------------------------------------------------------
      T mínima     T máxima     T media     Humedad media     Presión media
         13.20        19.50       17.35             75.30           1014.20

      Another City
      ------------------------------------------------------------------------
      T mínima     T máxima     T media     Humedad media     Presión media
         10.50        22.00       16.25             60.75           1009.80
      ```

4. **Error Management**:
    - Asks the user for confirmation if the output file already exists.
    - Validates the structure of the XML during parsing.
    - Skips malformed entries and continues processing other data.
    - Alerts the user to any issues encountered, such as missing tags or invalid values.

### SAX Advantages and Usage

- **Event-Driven Parsing**: Processes XML incrementally without loading the entire file into memory.
- **Efficiency**: Ideal for large XML files or scenarios where only specific parts of the document need to be read.

---

## Comparison of DOM and SAX Implementations

| Feature              | DOM-Based Aggregator                             | SAX-Based Report Generator                        |
|----------------------|--------------------------------------------------|---------------------------------------------------|
| **Parsing Model**    | Tree-based: loads the entire document            | Event-driven: processes data sequentially         |
| **Memory Usage**     | High, as the entire document is in memory        | Low, as it parses data on-the-fly                 |
| **Performance**      | Faster for smaller files, slower for large files | Faster for large files, slower for smaller files  |
| **Complexity**       | Suitable for structured data manipulation        | Suitable for simple, sequential processing        |
| **Output Type**      | XML with hierarchical summaries                  | Plain-text report                                 |
| **Typical Use Case** | When extensive manipulation of data is required  | When processing large files efficiently           |


**Example Use Cases**:
- Use DOM when generating complex, hierarchical XML outputs or transforming existing XML structures.
- Use SAX when extracting information from large files or producing simple, sequential reports.

---

## Learning Objectives

This project demonstrates the following key skills:

1. **XML File Processing**:
    - Utilize DOM for structured data creation and SAX for efficient data parsing.

2. **Statistical Computation**:
    - Calculate and summarize complex metrics from raw data.

3. **Java Programming**:
    - Reinforce knowledge of file handling, streams, and XML parsing.

4. **Error Handling**:
    - Implement user-friendly prompts and robust file validation.

---

## Technologies Used

- **Java**: Core programming language for implementation.
- **DOM Parser**: For tree-based XML document handling.
- **SAX Parser**: For memory-efficient, event-driven XML parsing.
- **Streams API**: For text-based file generation.

---
