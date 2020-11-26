package kitchenpos.menu.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.menugroup.domain.MenuGroupRepository;
import kitchenpos.price.domain.Price;
import kitchenpos.product.application.ProductService;
import kitchenpos.product.domain.Products;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductService productService;

    @Transactional
    public Menu create(final Menu menu) {
        if (!menuGroupRepository.existsById(menu.getMenuGroupId())) {
            throw new IllegalArgumentException();
        }

        final Products products = productService.findAllByIdIn(menu.extractProductIds());
        final Price sum = products.calculateTotalPrice(menu.extractProductQuantity());

        menu.validateCheaperThan(sum);

        return menuRepository.save(menu);
    }

    public List<Menu> list() {
        return menuRepository.findAll();
    }
}