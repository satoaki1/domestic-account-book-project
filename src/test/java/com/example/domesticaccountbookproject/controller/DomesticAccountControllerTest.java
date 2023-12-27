package com.example.domesticaccountbookproject.controller;

import com.example.domesticaccountbookproject.entity.SpentDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DomesticAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetItem() throws Exception {
        Long usageId = 3L;
        String responseJsonString = mockMvc.perform(get("/spentdetails/{usageId}", usageId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        SpentDetail responseSpentDetail = objectMapper.readValue(responseJsonString, SpentDetail.class);

        assertThat(responseSpentDetail.getUsageId()).isEqualTo(usageId);
        assertThat(responseSpentDetail.getUsedDate()).isEqualTo("2023-12-19");
        assertThat(responseSpentDetail.getSpentAmount()).isEqualTo(910);
        assertThat(responseSpentDetail.getUsageCategory()).isEqualTo("Transportation");
    }
}
