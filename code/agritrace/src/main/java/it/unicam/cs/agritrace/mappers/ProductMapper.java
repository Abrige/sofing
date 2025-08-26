package it.unicam.cs.agritrace.mappers;

import it.unicam.cs.agritrace.dtos.common.ProductDTO;
import it.unicam.cs.agritrace.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.name", target = "category")
    ProductDTO toDto(Product product);

    List<ProductDTO> toDtoList(List<Product> products);
}
