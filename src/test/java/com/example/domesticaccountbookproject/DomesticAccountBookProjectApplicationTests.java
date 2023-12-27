package com.example.domesticaccountbookproject;

import com.example.domesticaccountbookproject.controller.DomesticAccountBookController;
import com.example.domesticaccountbookproject.repository.SpentDetailRepository;
import com.example.domesticaccountbookproject.service.DomesticAccountBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DomesticAccountBookProjectApplicationTests {

    @Autowired
    private DomesticAccountBookController domesticAccountBookController;

    @Autowired
    private DomesticAccountBookService domesticAccountBookService;

    @Autowired
    private SpentDetailRepository spentDetailRepository;

    @Test
    void contextLoads() {
        assertThat(domesticAccountBookController).isNotNull();
        assertThat(domesticAccountBookService).isNotNull();
        assertThat(spentDetailRepository).isNotNull();
    }

}
