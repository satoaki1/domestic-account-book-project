package com.example.domesticaccountbookproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "money_usages")
@NoArgsConstructor
@AllArgsConstructor
public class SpentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_id")
    private Long usageId;

    @Column(name = "used_date")
    private String usedDate;

    @Column(name = "used_purpose")
    private String usedPurpose;

    @Column(name = "usage_category")
    private String usageCategory;

    @Column(name = "spent_amount")
    private Integer spentAmount;

    public Long getUsageId() {
        return usageId;
    }

    public void setUsageId(Long usageId) {
        this.usageId = usageId;
    }

    public String getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(String usedDate) {
        this.usedDate = usedDate;
    }

    public String getUsedPurpose() {
        return usedPurpose;
    }

    public void setUsedPurpose(String usedPurpose) {
        this.usedPurpose = usedPurpose;
    }

    public String getUsageCategory() {
        return usageCategory;
    }

    public void setUsageCategory(String usageCategory) {
        this.usageCategory = usageCategory;
    }

    public Integer getSpentAmount() {
        return spentAmount;
    }

    public void setSpentAmount(Integer spentAmount) {
        this.spentAmount = spentAmount;
    }
}
