package org.actividadFichero;

/**
 * Clase de variables que llamaremos para calcular las temperaturas máximas y mínimas, así como otros promedios.
 */
public class CityData {

	private String id;
	private String name;
	private double humiditySum = 0;
	private double pressureSum = 0;
	private int humidityCount = 0;
	private int pressureCount = 0;

	private Double minTempOverall = null;
	private Double maxTempOverall = null;

	public CityData(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public void addTemperature(double maxTemp, double minTemp) {
		if (minTempOverall == null || minTemp < minTempOverall) {
			minTempOverall = minTemp;
		}
		if (maxTempOverall == null || maxTemp > maxTempOverall) {
			maxTempOverall = maxTemp;
		}
	}

	public void addHumidity(double humidity) {
		this.humiditySum += humidity;
		this.humidityCount++;
	}

	public void addPressure(double pressure) {
		this.pressureSum += pressure;
		this.pressureCount++;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getAvgTemp() {
		if (minTempOverall != null && maxTempOverall != null) {
			return (minTempOverall + maxTempOverall) / 2;
		} else {
			return 0.0;
		}
	}

	public double getAvgHumidity() {
		if (humidityCount > 0) {
			return humiditySum / humidityCount;
		} else {
			return 0.0;
		}
	}

	public double getAvgPressure() {
		if (pressureCount > 0) {
			return pressureSum / pressureCount;
		} else {
			return 0.0;
		}
	}

	public double getMinTemp() {
		if (minTempOverall != null) {
			return minTempOverall;
		} else {
			return 0.0;
		}
	}

	public double getMaxTemp() {
		if (maxTempOverall != null) {
			return maxTempOverall;
		} else {
			return 0.0;
		}
	}
}
