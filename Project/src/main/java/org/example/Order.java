package org.example;

public class Order {
    private int id;
    private int customerId;
    private int itemId;
    private String comments;

    public Order(int customerId, int itemId, String comments) {
        this.customerId = customerId;
        this.itemId = itemId;
        this.comments = comments;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", itemId=" + itemId +
                ", comments='" + comments + '\'' +
                '}';
    }
}