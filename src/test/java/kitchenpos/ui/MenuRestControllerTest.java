package kitchenpos.ui;

import static kitchenpos.fixture.MenuFixture.createMenu;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import kitchenpos.RestControllerTest;
import kitchenpos.application.MenuService;
import kitchenpos.domain.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(MenuRestController.class)
class MenuRestControllerTest extends RestControllerTest {

    @MockBean
    private MenuService mockMenuService;

    @DisplayName("메뉴 생성 요청을 처리한다.")
    @Test
    void create() throws Exception {
        Menu requestMenu = createMenu();
        when(mockMenuService.create(any())).then(AdditionalAnswers.returnsFirstArg());
        mockMvc.perform(post("/api/menus")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestMenu))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(requestMenu)));
    }

    @DisplayName("메뉴 목록 반환 요청을 처리한다.")
    @Test
    void list() throws Exception {
        List<Menu> expected = Collections.singletonList(createMenu());
        when(mockMenuService.list()).thenReturn(expected);
        mockMvc.perform(get("/api/menus")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }
}