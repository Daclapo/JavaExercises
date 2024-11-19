package org.actividadFichero;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * Los métodos startElement(), endElement(), characters() se heredan de la clase AbstractHandler y
 * son típicamente propios del parser SAX.
 * SAX no construye un árbol de nodos en memoria como DOM, sino q dispara esos eventos a medida que analiza el XML linea por linea. *
 * DOM, en cambio, analiza el XML completo y lo carga en memoria como una estructura de árbol,
 * permitiendo una manipulación directa de los elementos en cualquier parte del documento.
 */
public class MainDomXML {

//  src/main/resources/weather-data-modificado.xml


//  src/main/resources/city-weather-summary.xml

    private static Document document ;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {


            System.out.println("Ingrese la ubicación del fichero XML");
            String inputFile = sc.nextLine();

            File file = new File(inputFile);

            if (!file.exists()) {

                System.out.println("Error: El fichero no existe");
                return;
            }

            System.out.println("Ingrese la ubicación del fichero XML de salida");
            String outputFile = sc.nextLine();

            File outputFileFile = new File(outputFile);

            if (outputFileFile.exists()) {

                System.out.println("Error: El fichero de salida ya existe. ¿Deseas sobrescribirlo? (s/n): ");
                String response = sc.nextLine();
                if(!response.equalsIgnoreCase("s")){
                    System.out.println("El programa ha terminado");
                }
            }else{
                outputFileFile.getParentFile().mkdirs();
                try{
                    outputFileFile.createNewFile();
                }catch (IOException e){
                    System.out.println("Error al crear el fichero de salida" + e);
                    return;
                }
            }

            System.out.println("Procesando el fichero XML...");

            Path inputPath = Path.of(inputFile);

        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputPath.toFile());


            List<CityData> cities = new ArrayList<>();


            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();


            NodeList cityNodes = (NodeList) xpath.evaluate("//city", document, XPathConstants.NODESET);

            for (int i = 0; i < cityNodes.getLength(); i++) {
                Element cityElement = (Element) cityNodes.item(i);
                String cityId = cityElement.getAttribute("id");
                String cityName = cityElement.getAttribute("name");


                CityData cityData = new CityData(cityId, cityName);

                NodeList cityStations = (NodeList) xpath.evaluate("station", cityElement, XPathConstants.NODESET);

                for (int j = 0; j < cityStations.getLength(); j++) {
                    Element stationElement = (Element) cityStations.item(j);

                    NodeList measurements = (NodeList) xpath.evaluate("measurements/period", stationElement, XPathConstants.NODESET);

                    for (int k = 0; k < measurements.getLength(); k++) {
                        Element periodElement = (Element) measurements.item(k);

                        Element tempElement = (Element) xpath.evaluate("temperature", periodElement, XPathConstants.NODE);
                        if (tempElement != null) {
                            double maxTemp = Double.parseDouble(tempElement.getAttribute("max"));
                            double minTemp = Double.parseDouble(tempElement.getAttribute("min"));
                            cityData.addTemperature(maxTemp, minTemp);
                        }

                        Node humidityNode = (Node) xpath.evaluate("humidity", periodElement, XPathConstants.NODE);
                        if (humidityNode != null) {
                            double humidity = Double.parseDouble(humidityNode.getTextContent());
                            cityData.addHumidity(humidity);
                        }

                        Node pressureNode = (Node) xpath.evaluate("pressure", periodElement, XPathConstants.NODE);
                        if (pressureNode != null) {
                            double pressure = Double.parseDouble(pressureNode.getTextContent());
                            cityData.addPressure(pressure);
                        }
                    }

                }

                cities.add(cityData);
            }

            createXML(cities, outputFileFile);


        }catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            System.err.println("Error al procesar el archivo XML: " + e);
            System.exit(1);
        }


    }


    private static void createXML(List< CityData> cities, File outputFilePath) {
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();


            Element rootElement = document.createElement("cities-weather-summary");
            document.appendChild(rootElement);

            for (CityData cityData : cities) {


                Element cityElement = document.createElement("city");
                cityElement.setAttribute("id", cityData.getId());
                cityElement.setAttribute("name", cityData.getName());
                rootElement.appendChild(cityElement);
                Element avgTempElement = document.createElement("average-temperature");


                Element avgElement = document.createElement("average-temperature");
                avgElement.setTextContent(String.format("%.2f", cityData.getAvgTemp()));
                avgElement.appendChild(avgTempElement);


                Element maxElement = document.createElement("max");
                maxElement.setTextContent(String.format("%.2f", cityData.getMaxTemp()));
                avgTempElement.appendChild(maxElement);


                Element minElement = document.createElement("min");
                minElement.setTextContent(String.format("%.2f", cityData.getMinTemp()));
                avgTempElement.appendChild(minElement);

                cityElement.appendChild(avgTempElement);


                Element humidityElement = document.createElement("average-humidity");
                humidityElement.setTextContent(String.format("%.2f", cityData.getAvgHumidity()));
                cityElement.appendChild(humidityElement);


                Element pressureElement = document.createElement("average-pressure");
                pressureElement.setTextContent(String.format("%.2f", cityData.getAvgPressure()));
                cityElement.appendChild(pressureElement);

                rootElement.appendChild(cityElement);


            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(outputFilePath);
            transformer.transform(source, result);

            System.out.println("Archivo XML generado correctamente: " + outputFilePath);

        } catch (ParserConfigurationException | TransformerException e) {
            System.err.println("Error al crear el archivo XML: " + e.getMessage());
        }
    }

}
