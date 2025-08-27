package it.unicam.cs.agritrace.mappers;

import it.unicam.cs.agritrace.dtos.common.CompanyDTO;
import it.unicam.cs.agritrace.dtos.common.OrderDTO;
import it.unicam.cs.agritrace.dtos.common.OrderItemDTO;
import it.unicam.cs.agritrace.dtos.common.UserDTO;
import it.unicam.cs.agritrace.model.Company;
import it.unicam.cs.agritrace.model.Order;
import it.unicam.cs.agritrace.model.OrderItem;
import it.unicam.cs.agritrace.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    // Mappa direttamente l’Order in OrderDTO
    @Mapping(source = "buyer", target = "buyer")
    @Mapping(source = "seller", target = "seller")
    @Mapping(source = "status.name", target = "status")
    OrderDTO toDto(Order order);

    // Mapping delle liste
    List<OrderDTO> toDtoList(List<Order> orders);

    // Sotto-mapper: OrderItem → OrderItemDTO
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "unitPrice", target = "unitPrice")
    @Mapping(source = "quantity", target = "quantity")
    OrderItemDTO toDto(OrderItem orderItem);

    Set<OrderItemDTO> toDtoOrderItems(Set<OrderItem> orderItems);

    // Sotto-mapper: User → UserDTO
    @Mapping(source = "username", target = "username")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    UserDTO toDto(User user);

    // Sotto-mapper: Company → CompanyDTO
    @Mapping(source = "name", target = "name")
    @Mapping(source = "fiscalCode", target = "fiscalCode")
    CompanyDTO toDto(Company company);
}
