package org.actividadut02.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Clase enum para manejar las lineas de productos
 * Usamos está clase para asegurar los datos en la BD,
 * esto para evitar errores.
 *
 *
 * @author Alejandra y David
 *
 * */


@Getter
@AllArgsConstructor
public enum ProductLine {

    CLASSIC_CARS("Classic Cars"),
    MOTORCYCLES("Motorcycles"),
    PLANES("Planes"),
    SHIPS("Ships"),
    TRAINS("Trains"),
    TRUCKS_AND_BUSES("Trucks and Buses"),
    VINTAGE_CARS("Vintage Cars");



    private final String displayName;

    // Método para obtener el enum desde una cadena
    public static ProductLine fromString(String text) {
        for (ProductLine pl : ProductLine.values()) {
            if (pl.displayName.equalsIgnoreCase(text)) {
                return pl;
            }
        }  throw new IllegalArgumentException("No se pudo encontrar el enum: " + text);
    }


}
