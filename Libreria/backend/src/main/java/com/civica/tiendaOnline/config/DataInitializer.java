package com.civica.tiendaOnline.config;

import com.civica.tiendaOnline.model.*;
import com.civica.tiendaOnline.repository.*;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    private final int NUM_CATEGORIES = 3;
    private final int NUM_PRODUCTS = 20;
    private final int NUM_CUSTOMERS = 10;
    private final int NUM_ORDERS = 15;
    private final int NUM_ORDER_DETAILS = 30;

    @Autowired
    public DataInitializer(CategoryRepository categoryRepository, ProductRepository productRepository,
            CustomerRepository customerRepository, OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public void run(String... args) {
        Faker faker = new Faker();
        Random random = new Random();

        if (categoryRepository.count() == 0) {
            List<Category> categories = new ArrayList<>();
            for (int i = 0; i < NUM_CATEGORIES; i++) {
                Category category = new Category();
                category.setName(faker.book().genre());
                categories.add(categoryRepository.save(category));
            }
        }

        if (productRepository.count() == 0) {
            List<Category> categories = categoryRepository.findAll();
            BigDecimal price = BigDecimal.valueOf(5 + random.nextDouble() * 95).setScale(2, RoundingMode.HALF_UP);
            for (int i = 0; i < NUM_PRODUCTS; i++) {
                Product product = new Product();
                product.setName(faker.book().title());
                product.setDescription(faker.lorem().sentence(20));
                product.setPrice(price);
                product.setStock(random.nextInt(50) + 1);
                product.setUpdateDate(LocalDateTime.now());
                product.setCategory(categories.get(random.nextInt(categories.size())));
                productRepository.save(product);
            }
        }

        if (customerRepository.count() == 0) {
            for (int i = 0; i < NUM_CUSTOMERS; i++) {
                Customer customer = new Customer();
                customer.setFirstName(faker.name().firstName());
                customer.setLastName(faker.name().lastName());
                customer.setEmail(faker.internet().emailAddress());
                customer.setPhone(faker.phoneNumber().cellPhone());
                customer.setRegistrationDate(LocalDateTime.now().minusDays(random.nextInt(365)));
                customer.setActive(random.nextBoolean());
                customerRepository.save(customer);
            }
        }

        if (orderRepository.count() == 0) {
            List<Customer> customers = customerRepository.findAll();
            List<Product> products = productRepository.findAll();

            for (int i = 0; i < NUM_ORDERS; i++) {
                Order order = new Order();
                order.setOrderDate(LocalDateTime.now().minusDays(random.nextInt(30)));
                order.setShipped(random.nextBoolean());
                order.setCustomer(customers.get(random.nextInt(customers.size())));

                int detailsCount = 1 + random.nextInt(3);
                BigDecimal total = BigDecimal.ZERO;

                for (int j = 0; j < detailsCount; j++) {
                    Product product = products.get(random.nextInt(products.size()));
                    int quantity = 1 + random.nextInt(3);
                    BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(quantity)).setScale(2,
                            RoundingMode.HALF_UP);

                    OrderDetail detail = new OrderDetail();
                    detail.setProduct(product);
                    detail.setQuantity(quantity);
                    detail.setPrice(price);
                    order.addOrderDetail(detail);

                    total = total.add(price);
                }

                order.setTotal(total.setScale(2, RoundingMode.HALF_UP));
                orderRepository.save(order);
            }
        }
    }
}
