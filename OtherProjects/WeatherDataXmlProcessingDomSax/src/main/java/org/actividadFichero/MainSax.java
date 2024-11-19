package org.actividadFichero;

import org.actividadFichero.SAX.CitiesHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;


// src/main/resources/weather-data-modificado.xml
// src/main/resources/outSax/informeSax.txt

public class MainSax {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Introduce la ruta del fichero XML de entrada: ");
		String inputFilePath = scanner.nextLine();
		File xmlFile = new File(inputFilePath);

		if (!xmlFile.exists()) {
			System.err.println("Error: el archivo XML no se encuentra en la ruta especificada: " + xmlFile.getAbsolutePath());
			return;
		}

		System.out.print("Introduce la ruta del fichero de texto de salida: ");
		String outputFilePath = scanner.nextLine();
		File outputFile = new File(outputFilePath);

		if (outputFile.exists()) {
			System.out.print("El fichero de salida ya existe. ¿Deseas sobrescribirlo? (sí/no): ");
			String overwrite = scanner.nextLine();
			if (!overwrite.equalsIgnoreCase("si") || !overwrite.equalsIgnoreCase("si")) {  // Incluyendo con y sin tilde
				System.out.println("El programa ha terminado.");
				return;
			}
		}else{
			outputFile.getParentFile().mkdirs();
			try{
				outputFile.createNewFile();
			}catch (IOException e){
				System.out.println("Error al crear el fichero de salida" + e);
				return;
			}
		}

		try {
			List<CityData> cities = saxProcessData(xmlFile);
			File newXmlFile = new File("src/main/resources/processed-data-sax.xml");
			createNewXmlFile(cities, newXmlFile);
			generateReport(cities, outputFile);
		} catch (SAXException | IOException | TransformerException e) {
			System.err.println("Error durante el procesamiento del archivo XML: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Error inesperado: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Procesa un archivo XML utilizando un parser SAX y devuelve una lista de objetos CityData.
	 *
	 * @param xmlFile el archivo XML a procesar
	 * @return una lista de objetos CityData procesados desde el archivo XML
	 * @throws ParserConfigurationException si hay un error en la configuración del parser
	 * @throws SAXException si ocurre un error durante el parsing del archivo XML
	 * @throws IOException si ocurre un error de I/O durante el procesamiento del archivo XML
	 */
	private static List<CityData> saxProcessData(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		CitiesHandler handler = new CitiesHandler();

		System.out.println("Procesando el archivo XML...");
		saxParser.parse(xmlFile, handler);
		System.out.println("Procesamiento completado.");

		return handler.getCities();
	}

	/**
	 * Crea un nuevo archivo XML con los datos de las ciudades proporcionadas.
	 *
	 * @param cities una lista de objetos CityData que contienen los datos de las ciudades
	 * @param newXmlFile el archivo XML que se creará con los datos procesados
	 * @throws ParserConfigurationException si hay un error en la configuración del parser
	 * @throws TransformerException si ocurre un error durante la transformación del documento XML
	 */
	private static void createNewXmlFile(List<CityData> cities, File newXmlFile) throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("cities");
		doc.appendChild(rootElement);

		for (CityData city : cities) {
			Element cityElement = doc.createElement("city");
			cityElement.setAttribute("name", city.getName());

			Element avgMaxTemp = doc.createElement("avgMaxTemp");
			avgMaxTemp.appendChild(doc.createTextNode(String.format("%.2f", city.getMaxTemp())));
			cityElement.appendChild(avgMaxTemp);

			Element avgMinTemp = doc.createElement("avgMinTemp");
			avgMinTemp.appendChild(doc.createTextNode(String.format("%.2f", city.getMinTemp())));
			cityElement.appendChild(avgMinTemp);

			Element avgTemp = doc.createElement("avgTemp");
			avgTemp.appendChild(doc.createTextNode(String.format("%.2f", city.getAvgTemp())));
			cityElement.appendChild(avgTemp);

			Element avgHumidity = doc.createElement("avgHumidity");
			avgHumidity.appendChild(doc.createTextNode(String.format("%.2f", city.getAvgHumidity())));
			cityElement.appendChild(avgHumidity);

			Element avgPressure = doc.createElement("avgPressure");
			avgPressure.appendChild(doc.createTextNode(String.format("%.2f", city.getAvgPressure())));
			cityElement.appendChild(avgPressure);

			rootElement.appendChild(cityElement);
		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(newXmlFile);
		transformer.transform(source, result);

		System.out.println("Archivo XML procesado creado: " + newXmlFile.getAbsolutePath());
	}


	/**
	 * Genera un informe formateado en un archivo de texto con los datos de las ciudades proporcionadas.
	 *
	 * @param cities una lista de objetos CityData que contienen los datos de las ciudades
	 * @param outputFile el archivo de texto donde se generará el informe
	 * @throws IOException si ocurre un error de I/O durante la escritura del archivo
	 */
	private static void generateReport(List<CityData> cities, File outputFile) throws IOException {
		try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
			for (CityData city : cities) {
				// Para poner el primer caracter en mayuscula de la ciudad que sea, ya que el valor esta en minuscula.
				String cityName = city.getName().substring(0, 1).toUpperCase() + city.getName().substring(1).toLowerCase();
				writer.println(cityName);
				writer.println("----------------------------------------------------------------------");
				writer.printf(" T mínima\t  T máxima\t\tT media\t\tHumedad media\tPresión media\n");
				writer.printf("\t%.2f\t\t %.2f\t\t  %.2f\t\t\t    %.2f\t\t  %.2f\n\n",
						city.getMinTemp(), city.getMaxTemp(), city.getAvgTemp(), city.getAvgHumidity(), city.getAvgPressure());
				writer.println();
			}
		}
		System.out.println("Informe generado en: " + outputFile.getAbsolutePath());
	}
}

// src/main/resources/weather-data-modificado.xml
// src/main/resources/outSax/out.txt
