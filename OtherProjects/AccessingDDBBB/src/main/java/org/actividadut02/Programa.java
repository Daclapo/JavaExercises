package org.actividadut02;

import org.actividadut02.dataaccess.CustomerDataAccessImpl;
import org.actividadut02.dataaccess.OrderDataAccessImpl;
import org.actividadut02.dataaccess.ProductDataAccessImpl;
import org.actividadut02.dto.CreateOrderDetailDto;
import org.actividadut02.dto.CreateOrderDto;
import org.actividadut02.entities.Order;
import org.actividadut02.entities.Product;
import org.actividadut02.enums.ProductLine;
import org.actividadut02.services.*;
import org.actividadut02.entities.Customer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * La clase principal, Programa.
 * funciona:
 * 1º Preguntar al usuario sobre que tabla/POJO quiere usar.
 * 2º Preguntar que operación realizar (crear, modificar, borrar, buscar, actualizar).
 * 3º Ejecutar la operación.
 */
public class Programa {

	private static final Scanner sc = new Scanner(System.in);

	// Importar las dependencias
	private static final ProductService productService;

    static {
        productService = new ProductServiceImpl(new ProductDataAccessImpl());
    }

    private static final OrderService orderService;

    static {
        orderService = new OrderServiceImpl(new OrderDataAccessImpl());
    }

	private static final CustomerService customerService;

	static {
		customerService = new CustomerServiceImpl(new CustomerDataAccessImpl());
	}


    public static void main(String[] args) throws SQLException {

//	1.	Preguntamos al usuario desde la consola que tabla/POJO quiere usar.

		boolean salir = false;

		while (!salir){
			System.out.println("Sobre que tabla quieres probar?");
			System.out.println("1. Products");
			System.out.println("2. Order");
			System.out.println("3. Customer");
			System.out.println("4. Salir");
			int entitieSelected = sc.nextInt();
			sc.nextLine(); // para limpiar el buffer

			switch (entitieSelected) {
				case 1:
					gestionarProduct();
					break;
				case 2:
					gestionarOrder();
					break;
				case 3:
					gestionarCustomer();
					break;
				case 4:
					salir = true;
					System.out.println("Saliendo...");
					break;
				default:
					System.out.println("Por favor, introduce un valor entre 1 y 4");
					break;
			}
		}



	}


	// 2. Preguntamos al usuario que operación realizar (crear, modificar, borrar, buscar, actualizar) sobre POJO seleccionado.
	private static void gestionarProduct() throws SQLException {
		System.out.println("\nQue operacion quieres realizar con Productos?");
		System.out.println("1. Contar numero de productos");
		System.out.println("2. Verificar que exista producto");
		System.out.println("3. Buscar producto por codigo");
		System.out.println("4. Listar productos");
		System.out.println("5. Guardar/Actualizar producto");
		System.out.println("6. Eliminar producto");
		int operationSelected = sc.nextInt();
		sc.nextLine(); // para limpiar el buffer

		switch (operationSelected) {
			case 1:
				countP();
				break;
			case 2:
				 existsByIdP();
				break;
			case 3:
				 findByIdP();
				break;
			case 4:
				 findAllP();
				break;
			case 5:
				saveP();
				break;
			case 6:
				deleteByIdP();
				break;
		}
	}



	private static void countP() {
		Long productCount = productService.count();
		System.out.println("Número total de productos en la base de datos: " + productCount + "\n\n");
	}


	private static void existsByIdP(){
		System.out.println("¿Cuál es el 'productCode' del producto que desea buscar?");
		String id = sc.nextLine();

//		Comprobamos si el ID está vacío
		if (id.isEmpty()) {
			System.out.println("No existe un producto con ese codigo\n'");
			return;
		}

// Verificamos que id exista usando existsById

		if (productService.existsById(id)){
			System.out.println("El " + id + " existe");
			System.out.println("¿Deseas ver el detalle del pedido? (s/n)");

//			Manejamos la respuesta del usuario
			if(sc.nextLine().equalsIgnoreCase("s")) {
				List<Product> product = productService.findAll();
				if(product != null) {

					System.out.println("Product Details:");
					System.out.printf("""
										ProductCode: %s
										ProductName: %s
										ProductLine: %s
										ProductScale: %s
										ProductVendor: %s
										ProductDescription: %s
										QuantityInStock: %s
										BuyPrice: %s
										MSRP: %s
										""" ,
							product.get(1).getProductCode(), product.get(2).getProductName(), product.get(3).getProductLine().getDisplayName(),
							product.get(4).getProductScale(), product.get(5).getProductVendor(), product.get(6).getProductDescription(),
							product.get(7).getQuantityInStock(), product.get(8).getBuyPrice(), product.get(9).getMSRP());
					System.out.println(" \n");
				}
			}
		}else {
			System.out.println("El " + id + " no existe");
		}
	}


	private static void findByIdP() throws SQLException {
		System.out.println("¿Cual es el 'productCode' del producto de desea buscar?");
		String id = sc.nextLine();
		Optional<Product> product = productService.findById(id);

		if(product.isPresent()) {
			System.out.printf("""
					ProductCode: %s
					ProductName: %s
					ProductLine: %s
					ProductScale: %s
					ProductVendor: %s
					ProductDescription: %s
					QuantityInStock: %s
					BuyPrice: %s
					MSRP: %s

					""",
					product.get().getProductCode(), product.get().getProductName(), product.get().getProductLine().getDisplayName(),
					product.get().getProductScale(), product.get().getProductVendor(), product.get().getProductDescription(),
					product.get().getQuantityInStock(), product.get().getBuyPrice(), product.get().getMSRP());
		}else
			System.out.printf("No hay ningun producto con el 'productCode' %s\n\n\n", id);
	}


	private static void findAllP(){
		List<Product> products = productService.findAll();

		if(products.isEmpty()){
			System.out.println(" Elija algún producto\n\n");
		}else {
			String formatoFila = "| %-12s | %-44s | %-17s | %-7s | %-25s | %-9d | %-10.2f |%n";

			System.out.format("+--------------+----------------------------------------------+-------------------+---------+---------------------------+------------+-----------+\n");
			System.out.format("| Product Code | Product Name                     	 	      | Product Line      |  Vendor | Desc                      | Quantity   | Buy Price |\n");
			System.out.format("+--------------+----------------------------------------------+-------------------+---------+---------------------------+------------+-----------+\n");

			for (Product product : products) {
				System.out.format(
						formatoFila,
						product.getProductCode(),
						product.getProductName(),
						product.getProductLine().getDisplayName(),
						product.getProductDescription(),
						product.getProductVendor(),
						product.getQuantityInStock(),
						product.getBuyPrice(),
						product.getMSRP()
				);
			}
			System.out.format("+--------------+----------------------------------------------+-------------------+---------+---------------------------+------------+-----------|\n");
		}
	}


	private static Product saveP() throws SQLException {
		Product pr = new Product();

		System.out.println("Introduzca el ID del producto actual o insertar uno nuevo: " );
		String code = sc.nextLine();
		pr.setProductCode(code);

		System.out.println("Introduzca el nombre del producto nuevo:");
		String name = sc.nextLine();
		pr.setProductName(name);

//	Mostramos al usuario la lista de marcas del Producto

//		String productLineInput = null;
		boolean isValid = false;

		while (!isValid) {
			System.out.println("Introduce una línea de producto: ");
			for (ProductLine productLine : ProductLine.values()) {
				System.out.println(productLine.getDisplayName());  // Mostrar las opciones al usuario
			}

			String productLineInput = sc.nextLine();  // Leer entrada del usuario

			try {
				// Intentar convertir la entrada en un valor de ProductLine
				ProductLine selectedProductLine = ProductLine.fromString(productLineInput);
				pr.setProductLine(selectedProductLine);  // Si es válido, lo asignamos
				isValid = true;  // Salimos del bucle
			} catch (IllegalArgumentException e) {
				// En caso de error (entrada no válida), mostramos un mensaje y volvemos a pedir la entrada
				System.out.println("Error: No se pudo encontrar la línea del producto. Inténtelo de nuevo.");
			}
		}


		System.out.println("Introduce una escala. Ej: '1:10 scale':");
		String scaleProduct = sc.nextLine();
		pr.setProductScale(scaleProduct);

		System.out.println("Introduce un vendedor. Ej: 'Classic Metal Creations':");
		String vendedorProduct = sc.nextLine();
		pr.setProductVendor(vendedorProduct);

		System.out.println("Introduce una descripción:");
		String descriptionProduct = sc.nextLine();
		pr.setProductDescription(descriptionProduct);


		System.out.println("Introduce una cantidad. Ej: 10:");
		int quantityProduct = Integer.parseInt(sc.nextLine());
		pr.setQuantityInStock(quantityProduct);

		System.out.println("Introduce un precio. Ej: 69.20:");
		double priceProduct = Double.parseDouble(sc.nextLine());
		pr.setBuyPrice(priceProduct);

		System.out.println("Introduce un precio MSRP. Ej: 69.20:");
		double priceMSRP = Double.parseDouble(sc.nextLine());
		pr.setMSRP(priceMSRP);

		try	{
			Product savedProduct = productService.save(pr); // Guardamos el producto
			System.out.println("Producto guardado con exito:\n " + "---> " + savedProduct.getProductCode() + " <---");
			System.out.println("Nombre: " + savedProduct.getProductName());
			System.out.println("Linea: " + savedProduct.getProductLine());
			System.out.println("Escala: " + savedProduct.getProductScale());
			System.out.println("Vendedor: " + savedProduct.getProductVendor());
			System.out.println("Descripción: " + savedProduct.getProductDescription());
			System.out.println("Precio: " + savedProduct.getBuyPrice());
			System.out.println("MSRP: " + savedProduct.getMSRP());
			System.out.println(" ");
		}catch (IllegalArgumentException e) {
			System.out.println("Error de validación: " + e.getMessage());
		}catch (RuntimeException e) {
			System.out.println( e.getMessage());
		}

		return pr;
	}


	private static void deleteByIdP() {

		System.out.println("¿Cual es el 'productCode' del producto de deseas eliminar?:");
		String id = sc.nextLine();
		if(productService.existsById(id)){
			System.out.println("Producto existente");
			System.out.println("Estas seguro que deseas eliminarlo (s/n)");

//			Manejamos la respuesta del usuario
			String answer = sc.nextLine();

			if("s".equals(answer)) {
				productService.deleteById(id);
			}else {
				System.out.println("No se ha eliminado el producto");
			}
		}else{
			System.out.println("No existe el producto");
		}


	}



//  --------------------------------------------------------------------------------------------------------------------

//	------------------------------------------------------------------------------


	private static void gestionarOrder() throws SQLException {
		System.out.println("\nQue operación quieres realizar con Orders?");
		System.out.println("1. Contar el numero de pedidos");
		System.out.println("2. Verificar que exista el pedido");
		System.out.println("3. Buscar pedido por codigo");
		System.out.println("4. Listar pedidos");
		System.out.println("5. Crear un nuevo pedido");
		System.out.println("6. Eliminar un pedido");
		int operationSelected = sc.nextInt();
		sc.nextLine();

		switch (operationSelected){
			case 1:
				countPedido();
				break;
			case 2:
				existsByIdPedido();
				break;
			case 3:
				findByIdPedido();
				break;
			case 4:
				findAllPedido();
				break;
			case 5:
				createOrder();
				break;
			case 6:
				deleteByIdPedido();
				break;
			default:
				System.out.println("Operacion no valida");

		}
	}


	private static void countPedido() {
		Long orderCount = orderService.count();
		System.out.println("Nº Total de pedidos: " + orderCount + "\n");


	}

	private static boolean existsByIdPedido()  {
		System.out.println("¿Escribe  el orderNumber?");
		int id = Integer.parseInt(sc.nextLine());

//		Buscamos en la lista si existe un pedido con el orderNumber indicado
		List<Order> orders = orderService.findAll();
		Order foundOrder = null;

		for(Order order : orders) {
			if(order.getOrderNumber() == id){
				foundOrder = order;
				break;
			}
		}

//		Verificamos si se encontró el pedido
		if(foundOrder == null) {
			System.out.println("El pedido no existe");
			return false;

		}else{
			System.out.println("El " + id + " existe");
			System.out.println("¿Deseas ver el detalle del pedido? (s/n)");
			String answer = sc.nextLine().trim().toLowerCase();

			if("s".equals(answer)) {


				System.out.println("Order Details:");
				System.out.printf("""
					OrderNumber: %s
					OrderDate: %s
					RequiredDate: %s
					ShippedDate: %s
					Status: %s
					Comments: %s
					CustomerNumber: %s

					""", foundOrder.getOrderNumber(), foundOrder.getOrderDate(), foundOrder.getRequiredDate(),
						foundOrder.getShippedDate(), foundOrder.getStatus(), foundOrder.getComments(),
						foundOrder.getCustomerNumber());
			}else{
				System.out.println("No se ha podido ver el pedido");
			}
		}

		return true;
	}


	private static void findByIdPedido() throws SQLException {
		System.out.println("¿Cual es el orderNumber que desea buscar?");
		int id = Integer.parseInt(sc.nextLine());
		Optional<Order> order = orderService.findById(id);

		if (order.isPresent()) {
			System.out.println("Order Details:");
			System.out.printf("""
					OrderNumber: %s
					OrderDate: %s
					RequiredDate: %s
					ShippedDate: %s
					Status: %s
					Comments: %s
					CustomerNumber: %s
					""",
					order.get().getOrderNumber(), order.get().getOrderDate(), order.get().getRequiredDate(),
					order.get().getShippedDate(), order.get().getStatus(), order.get().getComments(),
					order.get().getCustomerNumber());

		}else {
			System.out.println("El pedido no existe");
		}

	}




	private static void findAllPedido() {
		List<Order> orders = orderService.findAll();

		if(orders.isEmpty()) {
			System.out.println("Eliga algún pedido");
		} else {
			String formatoRow = "| %-12s | %-12s | %-12s | %-29s | %-12s | %n";


			System.out.format("+--------------+---------------+--------------+-------------------------------+------------------------|");
			System.out.format("| Order Number | OrderDate	  | Status       | Comments                      | Customer Number         ");
			System.out.format("+--------------+---------------+--------------+-------------------------------+------------------------|");

			for (Order order : orders) {
				System.out.format(formatoRow,
						order.getOrderNumber(),
						order.getOrderDate(),
						order.getStatus(),
						order.getComments(),
						order.getCustomerNumber());
			}
		}
		System.out.format("+--------------+--------------+--------------+-------------+--------------+%n");
	}

	private static void createOrder() {
		CreateOrderDto createOrderDto = new CreateOrderDto();

		// Solicitar el número de cliente
		System.out.println("Introduce el número de cliente: ");
		createOrderDto.customerNumber = Integer.parseInt(sc.nextLine());

		// Solicitar y parsear la fecha requerida
		System.out.println("Ingrese la fecha requerida (YYYY-MM-DD): ");
		try {
			String fechaInput = sc.nextLine();
			createOrderDto.requiredDate = LocalDate.parse(fechaInput);
		} catch (DateTimeParseException e) {
			createOrderDto.requiredDate = LocalDate.now();
		}

		// Solicitar comentarios
		System.out.println("Introduce los comentarios: ");
		createOrderDto.comments = sc.nextLine();

		// Agregar productos al pedido
		List<CreateOrderDetailDto> orderDetails = new ArrayList<>();
		boolean addMoreProducts;

		do {
			CreateOrderDetailDto createOrderDetailDto = new CreateOrderDetailDto();

			System.out.println("Introduce el 'productCode' del producto: ");
			createOrderDetailDto.setProductCode(sc.nextLine());

			System.out.println("Introduce la cantidad: ");
			createOrderDetailDto.setQuantityOrdered(Integer.parseInt(sc.nextLine()));

			orderDetails.add(createOrderDetailDto);

			System.out.println("¿Deseas agregar otro producto? (s/n)");
			addMoreProducts = sc.nextLine().equalsIgnoreCase("s");

		} while (addMoreProducts);

		createOrderDto.orderDetails = orderDetails;

		// Crear el pedido mediante OrderService
		try {
			orderService.createOrder(createOrderDto);
			System.out.println("El pedido se ha creado correctamente.");
		} catch (SQLException e) {
			System.err.println("Error al crear el pedido: " + e.getMessage());
		}
	}


	private static void deleteByIdPedido() throws SQLException {
		System.out.println("¿Cual es el 'orderNumber' del pedido de deseas eliminar?:");
		int id = Integer.parseInt(sc.nextLine());

		if (orderService.existsById(id)) {
			System.out.println("Pedido existente");
			System.out.println("Estas seguro que deseas eliminarlo (s/n)");

			// Manejamos la respuesta del usuario
			String answer = sc.nextLine();
			if ("s".equals(answer)) {
				orderService.deleteById(id);
			} else {
				System.out.println("No se ha eliminado el pedido");
			}
		} else {
			System.out.println("No existe el pedido");
		}

	}



//  --------------------------------------------------------------------------------------------------------------------



	private static void gestionarCustomer() throws SQLException {
		System.out.println("\nQue operacion quieres realizar con Customers/Clientes?");
		System.out.println("1. Contar numero de clientes");
		System.out.println("2. Verificar que exista cliente");
		System.out.println("3. Buscar cliente por 'customerNumber'");
		System.out.println("4. Listar clientes");
		System.out.println("5. Guardar/Actualizar cliente");
		System.out.println("6. Eliminar cliente");
		int operationSelected = sc.nextInt();
		sc.nextLine(); // para limpiar el buffer

		switch (operationSelected) {
			case 1:
				countC();
				break;
			case 2:
				existsByIdC();
				break;
			case 3:
				findByIdC();
				break;
			case 4:
				findAllC();
				break;
			case 5:
				saveC();
				break;
			case 6:
				deleteByIdC();
				break;
		}
	}


	private static void countC() {
		Long customerCount = customerService.count();
		System.out.println("Numero total de clientes en la base de datos: " + customerCount + "\n\n");
	}


	private static boolean existsByIdC() throws SQLException {
		System.out.println("¿Cuál es el 'customerNumber' del cliente que desea buscar?");
		int id = sc.nextInt();
		sc.nextLine();
		Optional<Customer> customer = customerService.findById(id);
		boolean ret = false;

		if (customer.isPresent()) {
			ret = true;
			System.out.printf("El cliente con el customerNumber %s si extiste.\n¿Deseas ver el detalle del cliente? (s/n)\n", id);
			String confirmacion = sc.nextLine();
			if (confirmacion.equalsIgnoreCase("s")) {
				System.out.printf("""
								CustomerNumber: %s
								CustomerName: %s
								ContactLastName: %s
								ContactFirstName: %s
								Phone: %s
								AddressLine1: %s
								AddressLine2: %s
								City: %s
								State: %s
								PostalCode: %s
								Country: %s
								SalesRepEmployeeNumber: %s
								CreditLimit: %s
								
								
								""",
						customer.get().getCustomerNumber(), customer.get().getCustomerName(), customer.get().getContactLastName(),
						customer.get().getContactFirstName(), customer.get().getPhone(), customer.get().getAddressLine1(),
						customer.get().getAddressLine2(), customer.get().getCity(), customer.get().getState(),
						customer.get().getPostalCode(), customer.get().getPostalCode(), customer.get().getCountry(),
						customer.get().getSalesRepEmployeeNumber(), customer.get().getCreditLimit());
			} else
				System.out.println("\n");
		} else
			System.out.printf("No hay ningun cliente con el 'customerNumber' %s\n\n\n", id);
		return ret;
	}


	private static void findByIdC() throws SQLException {
		System.out.println("¿Cuál es el 'customerNumber' del cliente que desea buscar?");
		int id = sc.nextInt();
		sc.nextLine();
		Optional<Customer> customer = customerService.findById(id);
		if(customer.isPresent()) {
			System.out.printf("""
					CustomerNumber: %s
					CustomerName: %s
					ContactLastName: %s
					ContactFirstName: %s
					Phone: %s
					AddressLine1: %s
					AddressLine2: %s
					City: %s
					State: %s
					PostalCode: %s
					Country: %s
					SalesRepEmployeeNumber: %s
					CreditLimit: %s
					
					
					""",
					customer.get().getCustomerNumber(), customer.get().getCustomerName(), customer.get().getContactLastName(),
					customer.get().getContactFirstName(), customer.get().getPhone(), customer.get().getAddressLine1(),
					customer.get().getAddressLine2(), customer.get().getCity(), customer.get().getState(),
					customer.get().getPostalCode(), customer.get().getPostalCode(), customer.get().getCountry(),
					customer.get().getSalesRepEmployeeNumber(), customer.get().getCreditLimit());
		}else
			System.out.printf("No hay ningun customer con el 'customerNumber' %s\n\n\n", id);
	}


	private static void findAllC() {
		List<Customer> customers = customerService.findAll();

		if (customers.isEmpty()) {
			System.out.println("No hay clientes disponibles.\n\n");
		} else {
			String formatoFila = "| %-15s | %-34s | %-21s | %-18s | %-18s | %-32s | %-26s | %-17s | %-14s | %-11s | %-15s | %-10s | %-11.2f |%n";

			System.out.format("+-----------------+------------------------------------+-----------------------+--------------------+--------------------+----------------------------------+----------------------------+-------------------+----------------+-------------+-----------------+------------+-------------+\n");
			System.out.format("| Customer Number | Customer Name                      | Contact Last Name     | Contact First Name | Phone              | Address Line 1                   | Address Line 2             | City              | State          | Postal Code | Country         | Sales Rep  | Credit Limit|\n");
			System.out.format("+-----------------+------------------------------------+-----------------------+--------------------+--------------------+----------------------------------+----------------------------+-------------------+----------------+-------------+-----------------+------------+-------------+\n");

			for (Customer customer : customers) {
				System.out.format(
						formatoFila,
						customer.getCustomerNumber(),
						customer.getCustomerName(),
						customer.getContactLastName(),
						customer.getContactFirstName(),
						customer.getPhone(),
						customer.getAddressLine1(),
						customer.getAddressLine2(),
						customer.getCity(),
						customer.getState(),
						customer.getPostalCode(),
						customer.getCountry(),
						customer.getSalesRepEmployeeNumber(),
						customer.getCreditLimit()
				);
			}
			System.out.format("+-----------------+------------------------------------+-----------------------+--------------------+--------------------+----------------------------------+----------------------------+-------------------+----------------+-------------+-----------------+------------+-------------+\n");
		}
	}


	private static Customer saveC() {
		Customer customer = new Customer();

		try {
			System.out.println("Introduzca el customerNumber del cliente (int):");
			int id = obtenerEntero();
			sc.nextLine();
			customer.setCustomerNumber(id);

			System.out.println("Introduzca el nombre del cliente:");
			String name = obtenerString();
			customer.setCustomerName(name);

			System.out.println("Introduzca el/los apellido/s del contacto:");
			String conLastName = obtenerString();
			customer.setContactLastName(conLastName);

			System.out.println("Introduzca el nombre (sin apellidos) del contacto:");
			String conFirstName = obtenerString();
			customer.setContactFirstName(conFirstName);

			System.out.println("Introduzca el número de teléfono del cliente:");
			String phone = obtenerString();
			customer.setPhone(phone);

			System.out.println("Introduzca la primera direccion del cliente:");
			String addressLine1 = obtenerString();
			customer.setAddressLine1(addressLine1);

			System.out.println("Introduzca la segunda direccion del cliente (si no la hay déjelo vacío):");
			String addressLine2 = sc.nextLine();
			customer.setAddressLine2(addressLine2);

			System.out.println("Introduzca la ciudad en la que vive del cliente:");
			String city = obtenerString();
			customer.setCity(city);

			System.out.println("Introduzca el estado en el que reside el cliente (si no vive en un estado, déjelo vacío):");
			String state = sc.nextLine();
			customer.setState(state);

			System.out.println("Introduzca el código postal de la residencia del cliente:");
			String postalCode = obtenerString();
			customer.setPostalCode(postalCode);

			System.out.println("Introduzca el país de residencia de el cliente:");
			String country = obtenerString();
			customer.setCountry(country);

			// El salesRepEmpNum debe coincidir con alguno de la tabla employees, si no, lanzara una excepción, sea al actualizar o al insertar.
			System.out.println("Introduzca el número de ventas del representante de ventas con el que has interactuado (int):");
			Integer salesRepEmpNum = obtenerEntero();
			sc.nextLine();
			customer.setSalesRepEmployeeNumber(salesRepEmpNum);

			System.out.println("Introduzca límite de crédito que tengas (double):");
			double creditLimit = obtenerDouble();
			sc.nextLine();
			customer.setCreditLimit(creditLimit);

			try {
				Customer savedCustomer = customerService.save(customer);

				System.out.println("Cliente guardado con exito:\n " + "---> " + savedCustomer.getCustomerNumber() + " <---");
				System.out.println("Nombre: " + savedCustomer.getCustomerName());
				System.out.println("Apellido del contacto: " + savedCustomer.getContactLastName());
				System.out.println("Nombre del contacto: " + savedCustomer.getContactFirstName());
				System.out.println("Teléfono: " + savedCustomer.getPhone());
				System.out.println("Dirección 1: " + savedCustomer.getAddressLine1());
				System.out.println("Dirección 2: " + savedCustomer.getAddressLine2());
				System.out.println("Ciudad: " + savedCustomer.getCity());
				System.out.println("Estado: " + savedCustomer.getState());
				System.out.println("Código postal: " + savedCustomer.getPostalCode());
				System.out.println("País: " + savedCustomer.getCountry());
				System.out.println("Número de representante de ventas: " + savedCustomer.getSalesRepEmployeeNumber());
				System.out.println("Límite de crédito: " + savedCustomer.getCreditLimit());
				System.out.println(" ");
			} catch (IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
			} catch (SQLException e) {
				System.out.println("Error de SQL: " + e.getMessage());
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Error de validación: " + e.getMessage());
		} catch (RuntimeException e) {
			System.out.println("Error al actualizar el cliente: " + e.getMessage());
		}
		return customer;
	}

	private static String obtenerString() {
		while (true) {
			String input = sc.nextLine();
			if (!input.trim().isEmpty()) {
				return input;
			} else {
				System.out.println("Este campo no puede estar vacio, por favor, introduzca un valor");
			}
		}
	}
	private static int obtenerEntero() {
		while (true) {
			try {
				return sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Por favor, introduzca un número entero válido.");
				sc.next();
			}
		}
	}
	private static double obtenerDouble() {
		while (true) {
			try {
				return sc.nextDouble();
			} catch (InputMismatchException e) {
				System.out.println("Por favor, introduzca un número decimal válido.");
				sc.next();
			}
		}
	}


	private static void deleteByIdC() {
		System.out.println("¿Cual es el 'customerNumber' del cliente de deseas eliminar?:");
		int id = sc.nextInt();

		if(customerService.existsById(id))
			customerService.deleteById(id);
		else
			System.out.println("No se ha encontrado ninguna entrada con el customerNumber " + id);
	}
}