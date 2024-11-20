package org.actividadut02.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Customer {
	@EqualsAndHashCode.Include
	private int customerNumber;
	private String customerName;
	private String contactLastName;
	private String contactFirstName;
	private String phone;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private int salesRepEmployeeNumber; // Puede ser null
	private double creditLimit;
}
