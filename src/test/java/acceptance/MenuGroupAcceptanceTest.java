package acceptance;

import static fixture.MenuGroupFixtures.두마리메뉴_그룹;
import static fixture.MenuGroupFixtures.순살파닭두마리메뉴_그룹;
import static fixture.MenuGroupFixtures.신메뉴_그룹;
import static fixture.MenuGroupFixtures.한마리메뉴_그룹;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import common.AcceptanceTest;
import io.restassured.RestAssured;
import java.util.List;
import java.util.Map;
import kitchenpos.domain.MenuGroup;
import kitchenpos.ui.request.MenuGroupRequest;
import kitchenpos.ui.response.MenuGroupResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class MenuGroupAcceptanceTest extends AcceptanceTest {

    @DisplayName("메뉴 그룹 목록을 조회한다.")
    @Test
    void findMenuGroups() {
        // act
        List<MenuGroupResponse> menuGroups = getMenuGroups();

        // assert
        assertThat(menuGroups)
                .extracting(MenuGroupResponse::getId, MenuGroupResponse::getName)
                .hasSize(4)
                .containsExactlyInAnyOrder(
                        tuple(두마리메뉴_그룹.id(), 두마리메뉴_그룹.이름()),
                        tuple(한마리메뉴_그룹.id(), 한마리메뉴_그룹.이름()),
                        tuple(순살파닭두마리메뉴_그룹.id(), 순살파닭두마리메뉴_그룹.이름()),
                        tuple(신메뉴_그룹.id(), 신메뉴_그룹.이름())
                );
    }

    @Test
    @DisplayName("메뉴 그룹을 생성한다.")
    void createMenuGroups() {
        // arrange
        String name = "추천 메뉴";
        MenuGroupResponse createdMenuGroup = createMenuGroup(name);

        // act
        List<MenuGroupResponse> menuGroups = getMenuGroups();

        // assert
        assertThat(createdMenuGroup.getId()).isNotNull();
        assertThat(createdMenuGroup.getName()).isEqualTo(name);
        assertThat(menuGroups)
                .extracting(MenuGroupResponse::getId, MenuGroupResponse::getName)
                .hasSize(5)
                .containsExactlyInAnyOrder(
                        tuple(두마리메뉴_그룹.id(), 두마리메뉴_그룹.이름()),
                        tuple(한마리메뉴_그룹.id(), 한마리메뉴_그룹.이름()),
                        tuple(순살파닭두마리메뉴_그룹.id(), 순살파닭두마리메뉴_그룹.이름()),
                        tuple(신메뉴_그룹.id(), 신메뉴_그룹.이름()),
                        tuple(createdMenuGroup.getId(), createdMenuGroup.getName())
                );
    }

    private List<MenuGroupResponse> getMenuGroups() {
        return RestAssured.given().log().all()
                .when().log().all()
                .get("/api/menu-groups")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", MenuGroupResponse.class);
    }

    private MenuGroupResponse createMenuGroup(String name) {
        return RestAssured.given().log().all()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(new MenuGroupRequest(name))
                .when().log().all()
                .post("/api/menu-groups")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().as(MenuGroupResponse.class);
    }
}
