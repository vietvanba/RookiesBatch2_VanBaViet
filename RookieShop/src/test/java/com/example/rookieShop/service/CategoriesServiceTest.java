package com.example.RookieShop.service;
import com.example.RookieShop.exception.CanNotDeleteEntityException;
import com.example.RookieShop.exception.CanNotUpdateEntityException;
import com.example.RookieShop.model.Category;
import com.example.RookieShop.repository.CategoryRepository;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
public class CategoriesServiceTest {
    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    private List<Category> list;
    private Category category1;
    private Category category2;

    @BeforeEach
    public void setUp() {
        this.list = new ArrayList<>();
        this.category1 = new Category(1, "Category1", "Description1");
        this.category2 = new Category(2, "Category2", "Description2");
        this.list.add(category1);
        this.list.add(category2);
    }

    @AfterEach
    public void tearDown() {
        category1 = null;
        category2 = null;
        list = null;
    }

    @Test
    public void getAll_ReturnListCategory_IfFound() {
        when(repository.findAll()).thenReturn(list);
        List<Category> result = service.getAll();
        assertEquals(result.size(), 2);
    }

    @Test
    public void getByID_ReturnCategory_IfFound() {
        when(repository.findById(category1.getProduct_categories_id())).thenReturn(Optional.ofNullable(category1));
        Category result = service.getByID(category1.getProduct_categories_id());
        assertEquals(result, category1);
    }
    @Test
    public void saveCategory_ReturnCategory_IfSuccess()
    {
        when(repository.save(any(Category.class))).thenReturn(category1);
        service.save(category1);
        verify(repository, times(1)).save(any());
    }
    @Test
    public void deleteCategoryByID_IfSuccess()
    {
        when(repository.findById(category1.getProduct_categories_id())).thenReturn(Optional.of(category1));
        service.deleteById(category1.getProduct_categories_id());
        verify(repository).delete(category1);
    }
//    @Test
//    public void deleteCategoryByID_IfUnSuccess() {
//
//        given(repository.findById(category1.getProduct_categories_id())).willReturn(Optional.ofNullable(null));
//
//        try {
//            service.deleteById(category1.getProduct_categories_id());
//        } catch (NullPointerException e) {
//            throw new CanNotDeleteEntityException("Can't delete Category with id " + category1.getProduct_categories_id(),
//                    e);
//        }
//
//
//    }

}
