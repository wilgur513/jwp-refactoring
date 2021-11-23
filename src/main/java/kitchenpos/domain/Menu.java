package kitchenpos.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Menu extends BaseEntity {

    private String name;

    private BigDecimal price;

    @ManyToOne
    private MenuGroup menuGroup;

    @OneToMany(mappedBy = "menu", fetch = FetchType.EAGER)
    private List<MenuProduct> menuProducts;

    public Menu() {}

    public Menu(Long id, String name, BigDecimal price, MenuGroup menuGroup, List<MenuProduct> menuProducts) {
        super(id);
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.menuProducts = menuProducts;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void validatePrice(BigDecimal sum) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 음수가 될 수 없습니다.");
        }

        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException("요청 가격과 저장된 가격이 다릅니다.");
        }
    }

    public void addMenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts.addAll(menuProducts);
    }
}
