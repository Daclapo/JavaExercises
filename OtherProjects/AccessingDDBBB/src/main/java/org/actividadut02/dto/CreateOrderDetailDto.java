package org.actividadut02.dto;

import lombok.Data;

@Data
public class CreateOrderDetailDto {

    private String productCode;   // Código del producto
    private int quantityOrdered; // Cantidad solicitada para el producto
    public int orderLineNumber;


}
