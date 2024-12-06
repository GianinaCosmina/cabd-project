package org.example;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PriceDifferenceReport {
    private int itemId;
    private String name;
    private BigDecimal currentPrice;
    private BigDecimal previousPrice; // poate fi null
    private BigDecimal priceDifference;
    private Timestamp tStart;
    private Timestamp tEnd;

    public PriceDifferenceReport(int itemId, String name, BigDecimal currentPrice, BigDecimal previousPrice, BigDecimal priceDifference, Timestamp tStart, Timestamp tEnd) {
        this.itemId = itemId;
        this.name = name;
        this.currentPrice = currentPrice;
        this.previousPrice = previousPrice;
        this.priceDifference = priceDifference;
        this.tStart = tStart;
        this.tEnd = tEnd;
    }

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public BigDecimal getPreviousPrice() {
        return previousPrice;
    }

    public BigDecimal getPriceDifference() {
        return priceDifference;
    }

    public Timestamp getTStart() {
        return tStart;
    }

    public Timestamp getTEnd() {
        return tEnd;
    }

    @Override
    public String toString() {
        return "PriceDifferenceReport{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", currentPrice=" + currentPrice +
                ", previousPrice=" + previousPrice +
                ", priceDifference=" + priceDifference +
                ", tStart=" + tStart +
                ", tEnd=" + tEnd +
                '}';
    }
}
