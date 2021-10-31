package kitchenpos.ui;

import static kitchenpos.fixture.OrderTableFixture.createOrderTable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import kitchenpos.RestControllerTest;
import kitchenpos.application.TableService;
import kitchenpos.domain.OrderTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(TableRestController.class)
class TableRestControllerTest extends RestControllerTest {

    @MockBean
    private TableService mockTableService;

    @DisplayName("테이블 생성 요청을 처리한다.")
    @Test
    void create() throws Exception {
        OrderTable requestTable = createOrderTable();
        when(mockTableService.create(any())).then(AdditionalAnswers.returnsFirstArg());
        mockMvc.perform(post("/api/tables")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestTable))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(requestTable)));
    }

    @DisplayName("테이블 목록 반환 요청을 처리한다.")
    @Test
    void list() throws Exception {
        List<OrderTable> expected = Collections.singletonList(createOrderTable());
        when(mockTableService.list()).thenReturn(expected);
        mockMvc.perform(get("/api/tables")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @DisplayName("테이블 empty 상태 변경 요청을 처리한다.")
    @Test
    void changeEmpty() throws Exception {
        Long orderTableId = 1L;
        OrderTable requestTable = createOrderTable();
        when(mockTableService.changeEmpty(any(), any())).then(AdditionalAnswers.returnsSecondArg());
        mockMvc.perform(put("/api/tables/{orderTableId}/empty", orderTableId)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestTable))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(requestTable)));
    }

    @DisplayName("테이블 손님 수 변경 요청을 처리한다.")
    @Test
    void changeNumberOfGuests() throws Exception {
        Long orderTableId = 1L;
        OrderTable requestTable = createOrderTable();
        when(mockTableService.changeNumberOfGuests(any(), any())).then(AdditionalAnswers.returnsSecondArg());
        mockMvc.perform(put("/api/tables/{orderTableId}/number-of-guests", orderTableId)
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestTable))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(requestTable)));
    }
}