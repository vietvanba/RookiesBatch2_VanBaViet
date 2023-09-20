package com.example.RookieShop.service;

import com.example.RookieShop.dto.CategoryDTO;
import com.example.RookieShop.dto.ProductDTO;
import com.example.RookieShop.dto.ProductImageDTO;
import com.example.RookieShop.exception.*;
import com.example.RookieShop.model.*;
import com.example.RookieShop.repository.OrderDetailRepository;
import com.example.RookieShop.repository.ProductImageRepository;
import com.example.RookieShop.repository.ProductRepository;
import com.example.RookieShop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;
    @Autowired
    CategoryRepository CategoryRepository;

    @Autowired
    ProductImageRepository imageRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    ImageUploader imageUploader;

    public Product save(Product product, int idCate) {
        Category category = CategoryRepository.findById(idCate).orElse(null);
        if (category == null)
            throw new NotFoundEntityException("Not found category with id: " + idCate);
        try {
            product.setProduct_categories(category);
            product.setCreated_date(new Timestamp(System.currentTimeMillis()));
            return repository.save(product);
        } catch (Exception e) {
            throw new CanNotSaveEntityException("Can't save product to database.Please " +
                    "check again", e);
        }
    }

    public Product getByID(int id) {
        Product product = repository.findById(id).orElse(null);
        if (product == null)
            throw new NotFoundEntityException("Can't not found the product with id" + id);
        return product;


    }

    public List<Product> getByCategoriesID(int id) {
        List<Product> list = repository.findByCategoriesId(id);
        if (list == null)
            throw new NotFoundEntityException("Can't not found the product with id" + id);
        return list;


    }

    public List<Product> getAll() {
        List<Product> list = repository.findAll();
        if (list == null) {
            throw new NotFoundEntityException("Can't get any product");
        }
        return list;
    }

    @Transactional
    public String deleteById(int id) {
        Product product = repository.findById(id).orElse(null);
        if (product == null)
            throw new NotFoundEntityException("Not found product with id: " + id);
        if(orderDetailRepository.existsByProduct(product))
            throw new ExistsProductInOrderDetail("Exist Product in order detail! Can't " +
                    "delete");
        try {

            imageRepository.deleteAll(product.getImages());
            repository.delete(product);
            return "Delete successful";
        } catch (Exception e) {
            throw new CanNotDeleteEntityException("Can't delete product with id " + id, e);
        }
    }

    public Product udpateById(Product product, int id) {

        try {

            Product p = UpdateProduct(product, id);
            return repository.save(p);

        } catch (Exception e) {
            throw new CanNotUpdateEntityException("Can't update product with id: " + id
                    , e);
        }
    }

    public List<Product> search(String key) {
        List<Product> list = new ArrayList<>();
        List<String> strings = new ArrayList<>(Arrays.asList(key.split("\\\\s\\\\s")));
        strings.forEach(x -> {
            Optional<List<Product>> listProduct = repository.findListProductByKey(x.toLowerCase());
            if (listProduct.isPresent()) {
                listProduct.get().forEach(element -> {
                    if (!list.contains(element)) {
                        list.add(element);
                    }
                });
            }
        });
        if (list.size() == 0)
            throw new NotFoundEntityException("Not found any product with key: " + key);
        return list;
    }

    public Product UpdateProduct(Product product, int id) {

        Product p = repository.findById(id).orElse(null);
        if (p == null)
            throw new NotFoundEntityException("Can't not found the product with " +
                    "product_id: " + id
            );
        try {
            if (product.getProduct_name() != null)
                if (!p.getProduct_name().equals(product.getProduct_name()))
                    p.setProduct_name(product.getProduct_name());
            if (product.getProduct_categories() != null)
                if (!p.getProduct_categories().equals(product.getProduct_categories()))
                    p.setProduct_categories(product.getProduct_categories());
            if (product.getPrice() != 0)
                if (!(p.getPrice() == product.getPrice()))
                    p.setPrice(product.getPrice());

            if (p.getDescription() != null)
                if (!p.getDescription().equals(product.getDescription()))
                    p.setDescription(product.getDescription());

            if (p.getNumber() != 0)
                if (!(p.getNumber() == product.getNumber()))
                    p.setNumber(product.getNumber());
            if (p.getRate() != 0)
                if (!(p.getRate() == product.getRate()))
                    p.setRate(product.getRate());
            if (p.getNumber_rate() != 0)
                if (!(p.getNumber_rate() == product.getNumber_rate()))
                    p.setNumber_rate(product.getNumber_rate());

            p.setUpdated_date(new Timestamp(System.currentTimeMillis()));

            if (product.getImages().size()!=0) {
                List<ProductImage> productImage = product.getImages();

                p.getImages().forEach(x -> {
                    imageRepository.delete(x);
                });

                productImage.forEach(x -> {
                    x.setProduct(p);

                    x.setUrl(imageUploader.uploadImage(x.getUrl()));
                    imageRepository.save(x);
                });
                p.setImages(productImage);
            }
            return repository.save(p);
        } catch (Exception e) {
            throw new CanNotSaveEntityException("Can't save product", e);
        }


    }

    @Transactional
    public Product rate(int idProduct, int idOrderDetail, float rate) {
        OrderDetail orderDetail =
                orderDetailRepository.findById(idOrderDetail).orElse(null);
        if (orderDetail == null)
            throw new NotFoundEntityException("Not found order detail with id " + idOrderDetail);
        Product product = repository.findById(idProduct).orElse(null);
        if (product == null)
            throw new NotFoundEntityException("Not found product detail with id " + idProduct);
        if (rate > 0 && rate <= 5) {
            if(orderDetail.getRate()==0)
            {
                orderDetail.setRate(rate);
                float rateTemp = product.getRate() * product.getNumber_rate();
                product.setNumber_rate(product.getNumber_rate() + 1);
                product.setRate((rateTemp + rate) / product.getNumber_rate());
            }else
            {
                throw new CanNotRatingProductException("Only rated 1 time");
            }

        }
        orderDetailRepository.save(orderDetail);
        return repository.save(product);
    }
}
