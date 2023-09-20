package com.example.RookieShop.service;

import com.example.RookieShop.dto.ProductDTO;
import com.example.RookieShop.exception.NotFoundEntityException;
import com.example.RookieShop.model.*;
import com.example.RookieShop.repository.CategoryRepository;
import com.example.RookieShop.repository.OrderDetailRepository;
import com.example.RookieShop.repository.ProductImageRepository;
import com.example.RookieShop.repository.ProductRepository;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.rules.ExpectedException;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ProductImageRepository imageRepository;
    @Mock
    private OrderDetailRepository orderDetailRepository;
    @InjectMocks
    private ProductService productService;
    private List<Product> list;
    private Product product1;
    private Product product2;
    private Category category;
    private ProductDTO productDTO;
    private OrderDetail orderDetail;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeEach
    public void setUp() {
        this.list = new ArrayList<>();
        this.category = new Category(1, "Category1", "Description1");
        this.product1 = new Product(1, "product_name", this.category,
                0.0, "description", 200, Float.parseFloat("4.0"),
                5, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        this.product2 = new Product(2, "product_name", this.category,
                0.0, "desceeee", 200, Float.parseFloat("4.0"),
                5, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        this.orderDetail= new OrderDetail(1,null,product1,1,111.111,0);
        this.list.add(product1);
        this.list.add(product2);
        this.productDTO = new ProductDTO("product_name", "description");
    }

    @AfterEach
    public void tearDown() {
        product1 = null;
        product2 = null;
        list = null;
    }

    @Test
    public void getAll_ReturnListProduct_IfFound() {
        Mockito.when(productRepository.findAll()).thenReturn(list);
        List<Product> result = productService.getAll();
        assertEquals(result.size(), 2);
    }

    @Test
    public void getByID_ReturnProduct_IfFound() {
        Mockito.when(productRepository.findById(product1.getProduct_id())).thenReturn(Optional.ofNullable(product1));
        Product product = productService.getByID(product1.getProduct_id());
        assertEquals(product, product1);
    }

    @Test
    public void getByID_ReturnException_IfNotFound() {
        BDDMockito.given(productRepository.findById(product1.getProduct_id())).willReturn(Optional.ofNullable(null));

        try {
            productService.getByID(product1.getProduct_id());
        } catch (NotFoundEntityException e) {
            exception.expect(NotFoundEntityException.class);
        }
    }

    @Test
    public void save_Product_IfSuccess() {
        when(productRepository.save(any(Product.class))).thenReturn(product1);
        when(categoryRepository.findById(product1.getProduct_categories().getProduct_categories_id())).thenReturn(Optional.ofNullable(category));
        productService.save(product1, product1.getProduct_categories().getProduct_categories_id());
        verify(productRepository, times(1)).save(any());
    }

    @Test
    public void deleteById_Product_IfSuccess() {
        when(productRepository.findById(product1.getProduct_id())).thenReturn(Optional.ofNullable(product1));
        when(imageRepository.findById(anyInt())).thenReturn(Optional.ofNullable(any(ProductImage.class)));
        productService.deleteById(product1.getProduct_id());
        verify(productRepository).delete(product1);
    }
    @Test
    public void deleteById_Product_IfNotSuccess() {
        BDDMockito.given(productRepository.findById(product1.getProduct_id())).willReturn(Optional.ofNullable(null));

        try {
            productService.deleteById(product1.getProduct_id());
        } catch (NotFoundEntityException e) {
            exception.expect(NotFoundEntityException.class);
        }
    }
    @Test
    public void updateProduct_IfSuccess()
    {
        when(productRepository.findById(product1.getProduct_id())).thenReturn(Optional.ofNullable(product1));
        Product product = product1;
        product.setProduct_name(product2.getProduct_name());
        product.setDescription(product2.getDescription());
        productService.UpdateProduct(product2,product1.getProduct_id());
        assertEquals(product,product1);
    }
    @Test
    public void rateProduct_IfSuccess()
    {
        float rate=5;
        when(productRepository.findById(product1.getProduct_id())).thenReturn(Optional.ofNullable(product1));
        when(orderDetailRepository.findById(anyInt())).thenReturn(Optional.ofNullable(orderDetail));
        Product product = product1;
        float rateTemp = product.getRate() * product.getNumber_rate();
        product.setNumber_rate(product.getNumber_rate() + 1);
        product.setRate((rateTemp + rate) / product.getNumber_rate());
        productService.rate(1,1,rate);
        assertEquals(product,product1);
        assertEquals(5,orderDetail.getRate());
    }
    @Test
    public void searchProduct_IfSuccess()
    {
        when(productRepository.findListProductByKey("product")).thenReturn(Optional.ofNullable(list));
        List<Product> result = productService.search("product");
        assertEquals(result.size(), 2);
    }

}
