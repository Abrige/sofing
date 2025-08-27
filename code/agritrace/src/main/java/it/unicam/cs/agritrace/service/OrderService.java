package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.common.CompanyDTO;
import it.unicam.cs.agritrace.dtos.common.OrderDTO;
import it.unicam.cs.agritrace.dtos.common.UserDTO;
import it.unicam.cs.agritrace.model.Company;
import it.unicam.cs.agritrace.model.Order;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.OrderRepository;
import org.mapstruct.control.MappingControl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    List<OrderDTO> getAllOrders(){
        List<OrderDTO> ordersDTO = new ArrayList<>();
        for (Order order : orderRepository.findAll()) {
            User buyer = order.getBuyer();
            Company seller = order.getSeller();
            UserDTO userDTO = new UserDTO(buyer.getUsername(), buyer.getFirstName(), buyer.getLastName(),
                    buyer.getEmail() ,
                    buyer.getPhone());
            CompanyDTO companyDTO = new CompanyDTO(seller.getName(), seller.getFiscalCode());
            OrderDTO orderDTO = new OrderDTO(userDTO, companyDTO, order.getTotalAmount(),
                    order.getStatus().getName(), order.getOrderedAt(), order.getDeliveryDate(), );

            ordersDTO.add()
        }
    }
}
