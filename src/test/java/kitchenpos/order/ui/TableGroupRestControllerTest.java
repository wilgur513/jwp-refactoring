package kitchenpos.order.ui;

import static fixture.MenuFixtures.후라이드치킨_메뉴;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import common.IntegrationTest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.order.ui.OrderRestController;
import kitchenpos.order.ui.TableGroupRestController;
import kitchenpos.order.ui.TableRestController;
import kitchenpos.order.ui.request.OrderLineItemRequest;
import kitchenpos.order.ui.request.OrderRequest;
import kitchenpos.order.ui.request.OrderTableRequest;
import kitchenpos.order.ui.request.TableGroupRequest;
import kitchenpos.order.ui.request.TableIdRequest;
import kitchenpos.order.ui.response.TableGroupResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class TableGroupRestControllerTest {

    @Autowired
    private TableGroupRestController sut;

    @Autowired
    private TableRestController tableRestController;

    @Autowired
    private OrderRestController orderRestController;

    @DisplayName("그룹핑을 원하는 테이블은 2개 이상이어야 한다.")
    @Test
    void groupingOrderTableSizeMustOverTwo() {
        // arrange
        changeOrderTableStatus(1L, true);

        // act & assert
        assertThatThrownBy(() -> groupOrderTables(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("그룹핑을 원하는 테이블은 존재해야 한다.")
    @Test
    void groupingOrderTableMustExist() {
        // arrange
        changeOrderTableStatus(1L, true);
        changeOrderTableStatus(2L, true);

        // act & assert
        long notFoundOrderTableId = -1L;
        assertThatThrownBy(() -> groupOrderTables(notFoundOrderTableId, 1L, 2L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("그룹핑을 원하는 테이블은 중복될 수 없다.")
    @Test
    void groupingOrderTableMustDistinct() {
        // arrange
        changeOrderTableStatus(1L, true);
        changeOrderTableStatus(2L, true);

        // act & assert
        assertThatThrownBy(() -> groupOrderTables(1L, 2L, 2L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("그룹핑을 원하는 테이블은 빈 테이블이어야 한다.")
    @Test
    void groupingOrderTableMustEmpty() {
        // arrange
        changeOrderTableStatus(1L, false);
        changeOrderTableStatus(2L, true);

        // act & assert
        assertThatThrownBy(() -> groupOrderTables(1L, 2L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("그룹핑을 원하는 테이블은 기존 그룹이 없어야 한다.")
    @Test
    void groupingOrderTableMustNotHasGroup() {
        // arrange
        changeOrderTableStatus(1L, true);
        changeOrderTableStatus(2L, true);
        groupOrderTables(1L, 2L);

        changeOrderTableStatus(3L, true);

        // act & assert
        assertThatThrownBy(() -> groupOrderTables(1L, 3L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("그룹 해제를 원하는 테이블은 계산 완료여야 한다.")
    @Test
    void ungroupingOrderTablesMustCompletion() {
        // arrange
        changeOrderTableStatus(1L, true);
        changeOrderTableStatus(2L, true);
        TableGroupResponse tableGroup = groupOrderTables(1L, 2L);

        createOrder(1L, new OrderLineItemRequest(후라이드치킨_메뉴.id(), 1L));

        // act & assert
        assertThatThrownBy(() -> sut.ungroup(tableGroup.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private void changeOrderTableStatus(final long orderTableId, final boolean isEmpty) {
        OrderTableRequest orderTableRequest = new OrderTableRequest(isEmpty);
        tableRestController.changeEmpty(orderTableId, orderTableRequest);
    }

    private TableGroupResponse groupOrderTables(final long... tableIds) {
        List<TableIdRequest> orderTables = Arrays.stream(tableIds)
                .mapToObj(TableIdRequest::new)
                .collect(Collectors.toList());
        TableGroupRequest request = new TableGroupRequest(orderTables);
        return sut.create(request).getBody();
    }

    private void createOrder(final long orderTableId, final OrderLineItemRequest... itemRequests) {
        OrderRequest request = new OrderRequest(orderTableId, List.of(itemRequests));
        orderRestController.create(request);
    }

}