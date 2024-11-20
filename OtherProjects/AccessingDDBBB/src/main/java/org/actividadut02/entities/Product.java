package org.actividadut02.entities;

import lombok.*;
import org.actividadut02.enums.ProductLine;


@Data
public class Product {

    @EqualsAndHashCode.Include
    private String productCode;


    private String productName;

    private ProductLine productLine; // Lo usamos para sacar las opciones del enum de ProductLine

    private String productScale;

    private String productVendor;

    private String productDescription;

    private int quantityInStock;

    private double buyPrice;

    private double MSRP;



}
