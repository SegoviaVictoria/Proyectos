package com.civica.tiendaOnline.service;

import java.util.List;

import com.civica.tiendaOnline.dto.ProductDTO;

public interface ProductService {

    List<ProductDTO> getAllProductsDTOs();

    ProductDTO findProductDTOById(Long id);

    ProductDTO findProductDTOByName(String name);

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProductDTO(Long id, ProductDTO productDTO);

    void deleteProductById(Long id);
}
