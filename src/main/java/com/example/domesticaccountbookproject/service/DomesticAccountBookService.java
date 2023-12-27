package com.example.domesticaccountbookproject.service;

import com.example.domesticaccountbookproject.entity.SpentDetail;
import com.example.domesticaccountbookproject.repository.SpentDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DomesticAccountBookService {

    @Autowired
    private SpentDetailRepository spentDetailRepository;

    @Cacheable("getTotalSpentAmount")
    public Integer getTotalSpentAmount() {
        Integer totalAmount = 0;
        for (int i = 0; i < getAllSpentDetails().size(); i++) {
            totalAmount += getAllSpentDetails().get(i).getSpentAmount();
        }
        return totalAmount;
    }

    @Cacheable("getAllSpentDetails")
    public List<SpentDetail> getAllSpentDetails() {
        List<SpentDetail> allSpentDetails = new ArrayList<>();
        spentDetailRepository.findAll().forEach(allSpentDetails::add);
        return allSpentDetails;
    }

    @Cacheable(value = "getSpentDetail", key = "'/spentdetail' + #usageId")
    public Optional<SpentDetail> getSpentDetail(Long usageId) {
        return spentDetailRepository.findById(usageId);
    }

    @Cacheable(value = "getSpentDetailsByDate", key = "'/spentdetails' + #usedDate")
    public String getSpentDetailsByDate(String year, String month, String date) {
        String usedDate = year + "-" + month + "-" + date;
        Integer totalAmount = 0;
        List<String> usageCategories = new ArrayList<>();
        for (int i = 0; i < getAllSpentDetails().size(); i++) {
            if (getAllSpentDetails().get(i).getUsedDate().equals(usedDate)) {
                totalAmount += getAllSpentDetails().get(i).getSpentAmount();

                if (!usageCategories.contains(getAllSpentDetails().get(i).getUsageCategory())) {
                    usageCategories.add(getAllSpentDetails().get(i).getUsageCategory());
                }
            }
        }

        if (totalAmount > 0) {
            return "The total spent amount on " + returnMonth(month) + " " + Integer.parseInt(date) + "th, " + year + " is " + totalAmount + "円."
                    + "\nThis amount was spent on " + usageCategories.get(0) + " and so on.";
        } else {
            return "The spent detail on " + returnMonth(month) + " " + Integer.parseInt(date) + "th, " + year + " is not found.";
        }
    }

    @Cacheable(value = "getSpentDetailsByUsage", key = "'/spentdetails' + #usageCategory")
    public String getSpentDetailsByUsage(String usageCategory) {
        Integer totalAmount = 0;
        Boolean isCategoryExist = Boolean.FALSE;
        for (int i = 0; i < getAllSpentDetails().size(); i++) {
            if (getAllSpentDetails().get(i).getUsageCategory().equals(usageCategory)) {
                isCategoryExist = Boolean.TRUE;
                totalAmount += getAllSpentDetails().get(i).getSpentAmount();
            }
        }

        if (isCategoryExist.equals(Boolean.FALSE)) {
            return "The category does not exist. Try searching another categories included in the spent data.";
        }

        if (totalAmount > 0) {
            return "The total spent amount for " + usageCategory + " is " + totalAmount + "円.";
        } else {
            return "The total spent amount for " + usageCategory + " is 0円. Currently, no expense on this usage.";
        }
    }

    @CacheEvict(value = "getAllSpentDetails", allEntries = true)
    public void addSpentDetail(SpentDetail spentDetail) {
        spentDetailRepository.save(spentDetail);
    }

    @Caching(evict = {
            @CacheEvict(value = "getTotalSpentAmount"),
            @CacheEvict(value = "getAllSpentDetails", allEntries = true),
            @CacheEvict(value = "getSpentDetail", key = "'/spentdetail' + #usageId"),
            @CacheEvict(value = "getSpentDetailsByDate", key = "'/spentdetails' + #usedDate"),
            @CacheEvict(value = "getSpentDetailsByUsage", key = "'/spentdetails' + #usageCategory")
    })
    public void updateSpentDetail(SpentDetail spentDetail, Long usageId) {
        spentDetailRepository.findById(usageId).ifPresent((updatedSpentDetail) -> {
            if (spentDetail.getUsedDate() != null) {
                updatedSpentDetail.setUsedDate(spentDetail.getUsedDate());
            }

            if (spentDetail.getUsedPurpose() != null) {
                updatedSpentDetail.setUsedPurpose(spentDetail.getUsedPurpose());
            }

            if (spentDetail.getUsageCategory() != null) {
                updatedSpentDetail.setUsageCategory(spentDetail.getUsageCategory());
            }

            if (spentDetail.getSpentAmount() != null) {
                updatedSpentDetail.setSpentAmount(spentDetail.getSpentAmount());
            }

            spentDetailRepository.save(updatedSpentDetail);
        });
    }

    public void deleteSpentDetail(Long usageId) {
        spentDetailRepository.deleteById(usageId);
    }

    public void deleteSpentDetailsByDate(String usedDate) {
        for (int i = 0; i < getAllSpentDetails().size(); i++) {
            if (getAllSpentDetails().get(i).getUsedDate().equals(usedDate)) {
                spentDetailRepository.delete(getAllSpentDetails().get(i));
            }
        }
    }

    public String returnMonth(String month) {
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
        return months.get(Integer.parseInt(month) - 1);
    }
}
