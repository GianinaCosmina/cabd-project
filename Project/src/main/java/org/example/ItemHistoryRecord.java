package org.example;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ItemHistoryRecord {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private Timestamp tStart;
    private Timestamp tEnd;
    private int itemId;

    public ItemHistoryRecord(int id, String name, String description, BigDecimal price, Timestamp tStart, Timestamp tEnd, int itemId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.tStart = tStart;
        this.tEnd = tEnd;
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "HistoryRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", tStart=" + tStart +
                ", tEnd=" + tEnd +
                ", itemId=" + itemId +
                '}';
    }

}
