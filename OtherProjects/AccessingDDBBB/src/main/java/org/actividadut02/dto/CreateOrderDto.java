package org.actividadut02.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

/* Clase de Detalles de Pedidos
*
*  Uso: Este POJO se crea para utilizarla en el metodo createOrder
*
* Por ejemplo:
* - Contiene los datos minimos para crear un nuevo pedido (
* el CustomerNumber --> Numero de Cliente
* , RequiredDate --> Fecha de Entrega
* , Comments --> Comentarios del nuevo pedido
* y OrderDetails -->  Lista de detalles del pedido sobre el producto
)

*
* */
@Data
public class CreateOrderDto{

    @EqualsAndHashCode.Include
    public int orderNumber;

//    @EqualsAndHashCode.Include
    public int customerNumber;

    public LocalDate requiredDate;

    public String comments;

    public List<CreateOrderDetailDto> orderDetails;





}
