package org.actividadFichero.SAX;

import org.actividadFichero.CityData;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * CitiesHandler es un manejador SAX que procesa elementos de un archivo XML
 * y construye una lista de objetos CityData con la información extraída.
 * <p>
 * Este manejador reconoce los elementos <city>, <temperature>, <humidity> y <pressure>
 * y extrae los datos correspondientes para cada ciudad.
 */
public class CitiesHandler extends DefaultHandler {
	private List<CityData> cities = new ArrayList<>();
	private CityData currentCity;
	private boolean isHumidity = false;
	private boolean isPressure = false;

	/**
	 * Maneja el inicio de un elemento XML. Dependiendo del nombre del elemento (qName),
	 * realiza diferentes acciones:
	 * Si el elemento es city, añade la ciudad actual a la lista de ciudades.
	 * Si el elemento es temperature, extrae los atributos max y min y los añade a la ciudad actual.
	 * Si el elemento es humidity, establece la bandera "flag" isHumidity a true.
	 * Si el elemento es pressure, establece la bandera "flag" isPressure a true.
	 *
	 * @param uri        el URI del espacio de nombres
	 * @param localName  el nombre local del elemento (sin prefijo)
	 * @param qName      el nombre cualificado del elemento (con prefijo)
	 * @param attributes los atributos del elemento
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (qName.equals("city")) {
			String cityId = attributes.getValue("id");
			String cityName = attributes.getValue("name");
			currentCity = new CityData(cityId, cityName);
			cities.add(currentCity);
		} else if (qName.equals("temperature")) {
			String maxTemp = attributes.getValue("max");
			String minTemp = attributes.getValue("min");
			if (maxTemp != null && minTemp != null && currentCity != null) {
				currentCity.addTemperature(Double.parseDouble(maxTemp), Double.parseDouble(minTemp));
			}
		} else if (qName.equals("humidity")) {
			isHumidity = true;
		} else if (qName.equals("pressure")) {
			isPressure = true;
		}
	}

	/**
	 * Maneja el contenido de texto dentro de un elemento XML.
	 * Dependiendo de las banderas isHumidity e isPressure,
	 * añade la humedad o la presión a la ciudad actual.
	 *
	 * @param ch     el array de caracteres
	 * @param start  la posición inicial en el array
	 * @param length la longitud de los caracteres
	 * @throws SAXException si ocurre un error SAX
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String content = new String(ch, start, length).trim();

		if (isHumidity && !content.isEmpty()) {
			currentCity.addHumidity(Double.parseDouble(content));
		} else if (isPressure && !content.isEmpty()) {
			currentCity.addPressure(Double.parseDouble(content));
		}
	}

	/**
	 * Maneja el final de un elemento XML. Dependiendo del nombre del elemento (qName),
	 * restablece las banderas isHumidity e isPressure a false.
	 *
	 * @param uri       el URI del espacio de nombres
	 * @param localName el nombre local del elemento (sin prefijo)
	 * @param qName     el nombre cualificado del elemento (con prefijo)
	 */
	@Override
	public void endElement(String uri, String localName, String qName) {
		if (qName.equals("city")) {
			currentCity = null;
		} else if (qName.equals("humidity")) {
			isHumidity = false;
		} else if (qName.equals("pressure")) {
			isPressure = false;
		}
	}

	/**
	 * Devuelve la lista de objetos CityData que han sido procesados por el manejador SAX.
	 *
	 * @return una lista de objetos CityData
	 */
	public List<CityData> getCities() {
		return cities;
	}
}
