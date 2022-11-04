package kitchenpos.product.application;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.product.domain.repository.ProductRepository;
import kitchenpos.product.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product create(final String name, final BigDecimal price) {
        return productRepository.save(new Product(name, price));
    }

    public List<Product> list() {
        return productRepository.findAll();
    }
}