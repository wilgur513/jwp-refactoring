package kitchenpos.application;

import java.util.List;
import kitchenpos.dao.MenuGroupRepository;
import kitchenpos.domain.MenuGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroup create(final String name) {
        return menuGroupRepository.save(new MenuGroup(name));
    }

    public List<MenuGroup> list() {
        return menuGroupRepository.findAll();
    }
}
