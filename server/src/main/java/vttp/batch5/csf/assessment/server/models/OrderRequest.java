package vttp.batch5.csf.assessment.server.models;

import java.util.List;

public class OrderRequest {
    private String username;
    private String password;
    private List<Item> items;
    private double total;

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


    public static class Item {
        private String id;
        private double price;
        private int quantity;

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public double getPrice() {
            return price;
        }
        public void setPrice(double price) {
            this.price = price;
        }
        public int getQuantity() {
            return quantity;
        }
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

    }
}

