package com.civica.tiendaOnline.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.civica.tiendaOnline.dto.ProductDTO;
import com.civica.tiendaOnline.model.Category;
import com.civica.tiendaOnline.model.Product;
import com.civica.tiendaOnline.repository.CategoryRepository;
import com.civica.tiendaOnline.repository.OrderDetailRepository;
import com.civica.tiendaOnline.repository.ProductRepository;
import com.civica.tiendaOnline.service.ProductService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<ProductDTO> getAllProductsDTOs() {
        return productRepository.findAll()
                .stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO findProductDTOById(Long id) {
        Product productFound = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));
        return toProductDTO(productFound);
    }

    @Override
    public ProductDTO findProductDTOByName(String name) {
        Product productFound = productRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Product with name " + name + " not found"));
        return toProductDTO(productFound);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {

        validateProductDTO(productDTO);
        createCategoryIfNoExists(productDTO.getCategoryName());

        Category categoryProduct = categoryRepository.findByName(productDTO.getCategoryName())
                .orElseThrow(
                        () -> new EntityNotFoundException("Category '" + productDTO.getCategoryName() + "' not found"));

        Product product = new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getStock(),
                categoryProduct,
                productDTO.getUpdateDate());

        productRepository.save(product);
        return toProductDTO(product);
    }

    @Override
    public ProductDTO updateProductDTO(Long id, ProductDTO productDTO) {

        validateProductDTOForUpdate(id, productDTO);

        Product updatedProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));

        createCategoryIfNoExists(productDTO.getCategoryName());

        updatedProduct.setName(productDTO.getName());
        updatedProduct.setDescription(productDTO.getDescription());
        updatedProduct.setPrice(productDTO.getPrice());
        updatedProduct.setStock(productDTO.getStock());
        updatedProduct.setUpdateDate(productDTO.getUpdateDate());

        Category newCategory = categoryRepository.findByName(productDTO.getCategoryName())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        updatedProduct.setCategory(newCategory);

        productRepository.save(updatedProduct);

        return toProductDTO(updatedProduct);
    }

    @Override
    public void deleteProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));

        if (orderDetailRepository.existsByProductId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This product is associated to an orderDetail");
        }

        productRepository.deleteById(id);
    }

    // ---------------------------- EXTRA METHODS ---------------------------- //

    private void validateProductDTO(ProductDTO productDTO) {

        if (productDTO == null) {
            throw new IllegalArgumentException("ProductDTO must not be null");
        }

        String productDTOName = productDTO.getName();

        if (productDTOName == null || productDTOName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name must not be null or empty");
        }

        Optional<Product> existingProduct = productRepository.findByName(productDTOName);
        if (existingProduct.isPresent()) {
            throw new IllegalArgumentException("Product with name '" + productDTOName + "' already exists");
        }
    }

    private void createCategoryIfNoExists(String categoryName) {
        if (!categoryRepository.findByName(categoryName).isPresent()) {
            Category newCategory = new Category();
            newCategory.setName(categoryName);
            categoryRepository.save(newCategory);
        }
    }

    private void validateProductDTOForUpdate(Long id, ProductDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException("ProductDTO must not be null");
        }

        String productDTOName = productDTO.getName();

        if (productDTOName == null || productDTOName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name must not be null or empty");
        }

        Optional<Product> productWithSameName = productRepository.findByName(productDTOName);
        if (productWithSameName.isPresent() && !productWithSameName.get().getId().equals(id)) {
            throw new IllegalArgumentException("Product with name '" + productDTOName + "' already exists");
        }
    }

    public ProductDTO toProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getUpdateDate(),
                product.getCategory().getName());
    }

    public Product toProduct(ProductDTO productDTO) {
        Category productCategory = categoryRepository.findByName(productDTO.getCategoryName())
                .orElseThrow(
                        () -> new EntityNotFoundException("Category '" + productDTO.getCategoryName() + "' not found"));

        return new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getStock(),
                productCategory,
                productDTO.getUpdateDate());
    }
    
}
