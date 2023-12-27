package com.example.domesticaccountbookproject.controller;

import com.example.domesticaccountbookproject.entity.SpentDetail;
import com.example.domesticaccountbookproject.exception.SpentDetailNotFoundException;
import com.example.domesticaccountbookproject.service.DomesticAccountBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DomesticAccountBookController {

    @Autowired
    private DomesticAccountBookService domesticAccountBookService;

    @GetMapping("/")
    public String index() {
        Integer spentAmount = domesticAccountBookService.getTotalSpentAmount();

        return "Welcome to the Domestic Account Book Service site. " +
                "The total spent amount is " + spentAmount + ". " +
                "Remaining amount is " + (91000 - spentAmount) + ".";
    }

    @GetMapping("/spentdetails")
    public List<SpentDetail> getAllSpentDetails() {
        return domesticAccountBookService.getAllSpentDetails();
    }

    @GetMapping("/spentdetails/{usageId}")
    public SpentDetail getSpentDetailById(@PathVariable("usageId") Long usageId) {
        return domesticAccountBookService.getSpentDetail(usageId).orElseThrow(
                () -> new SpentDetailNotFoundException(usageId));
    }

    @GetMapping("/spentdetails/{year}/{month}/{date}")
    public String getSpentDetailsByDate(@PathVariable("year") String year, @PathVariable("month") String month, @PathVariable("date") String date) {
        return domesticAccountBookService.getSpentDetailsByDate(year, month, date);
    }

    @PostMapping("/spentdetails")
    public void addSpentDetail(@RequestBody SpentDetail spentDetail) {
        domesticAccountBookService.addSpentDetail(spentDetail);
    }

    @PutMapping("/spentdetails/{usageId}")
    public void updateSpentDetail(
            @RequestBody SpentDetail spentDetail, @PathVariable("usageId") Long usageId) {
        domesticAccountBookService.updateSpentDetail(spentDetail, usageId);
    }

    @DeleteMapping("/spentdetails/{usageId}")
    public void deleteSpentDetail(@PathVariable("usageId") Long usageId) {
        domesticAccountBookService.deleteSpentDetail(usageId);
    }

    @DeleteMapping("/spentdetails/{date}")
    public void deleteSpentDetailsByDate(@PathVariable("date") String usedDate) {
        domesticAccountBookService.deleteSpentDetailsByDate(usedDate);
    }
}
