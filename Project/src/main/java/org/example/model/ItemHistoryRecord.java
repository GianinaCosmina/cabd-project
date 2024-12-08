package org.example.model;

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

    public ItemHistoryRecord() {
    }

    public ItemHistoryRecord(int id, String name, String description, BigDecimal price, Timestamp tStart, Timestamp tEnd, int itemId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.tStart = tStart;
        this.tEnd = tEnd;
        this.itemId = itemId;
    }

    public ItemHistoryRecord(String name, String description, BigDecimal price, Timestamp tStart, Timestamp tEnd, int itemId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.tStart = tStart;
        this.tEnd = tEnd;
        this.itemId = itemId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Timestamp gettStart() {
        return tStart;
    }

    public void settStart(Timestamp tStart) {
        this.tStart = tStart;
    }

    public Timestamp gettEnd() {
        return tEnd;
    }

    public void settEnd(Timestamp tEnd) {
        this.tEnd = tEnd;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
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
