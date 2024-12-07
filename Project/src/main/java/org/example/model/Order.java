package org.example.model;

public class Order {
    private int orderId;
    private int customerId;
    private int itemId;
    private int quantity;
    private String comments;

    public Order() {
    }

    public Order(int orderId, int customerId, int itemId, int quantity, String comments) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.comments = comments;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    @Override
    public String toString() {
        return "Order { " +
                "orderId = " + orderId +
                ", customerId = " + customerId +
                ", itemId = " + itemId +
                ", quantity = " + quantity +
                ", comments = '" + comments + '\'' +
                " }";
    }
}
