package kitchenpos.application;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kitchenpos.dao.MenuGroupRepository;
import kitchenpos.dao.MenuRepository;
import kitchenpos.dao.ProductRepository;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.PendingMenuProduct;
import kitchenpos.domain.PendingMenuProducts;
import kitchenpos.domain.Product;
import kitchenpos.ui.request.MenuProductRequest;
import kitchenpos.ui.request.MenuRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final ProductRepository productRepository
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Menu create(final MenuRequest request) {
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(IllegalArgumentException::new);
        final PendingMenuProducts products = createPendingMenuProducts(request);
        final Menu menu = menuGroup.createMenu(request.getName(), request.getPrice(), products);
        return menuRepository.save(menu);
    }

    private PendingMenuProducts createPendingMenuProducts(final MenuRequest request) {
        final List<Product> products = findAllProductsByIdIn(request);
        final Map<Long, Long> quantities = request.getMenuProducts().stream()
                .collect(toMap(MenuProductRequest::getProductId, MenuProductRequest::getQuantity));
        final List<PendingMenuProduct> pendingMenuProducts = products.stream()
                .map(p -> new PendingMenuProduct(p.getId(), p.getPrice(), quantities.get(p.getId())))
                .collect(Collectors.toList());
        return new PendingMenuProducts(pendingMenuProducts);
    }

    private List<Product> findAllProductsByIdIn(final MenuRequest request) {
        final List<Long> productIds = request.getMenuProducts().stream()
                .map(MenuProductRequest::getProductId)
                .collect(Collectors.toList());
        final List<Product> products = productRepository.findAllByIdIn(productIds);
        if (products.size() != productIds.size()) {
            throw new IllegalArgumentException();
        }
        return products;
    }

    @Transactional
    public Menu findById(final long id) {
        return menuRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Menu> list() {
        return menuRepository.findAll();
    }
}
