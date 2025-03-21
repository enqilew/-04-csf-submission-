package vttp.batch5.csf.assessment.server.models;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "orders")
public class MongoOrder {
    private String id;
    private String orderId;
    private String paymentId;
    private String username;
    private double total;
    private Date timestamp;
    private List<OrderItem> items;

    public MongoOrder(String orderId, String paymentId, OrderRequest orderRequest) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.username = orderRequest.getUsername();
        this.total = orderRequest.getTotal();
        this.timestamp = new Date();

        // ✅ Convert OrderRequest.Item -> OrderItem, but add name field correctly
        this.items = orderRequest.getItems().stream()
            .map(item -> new OrderItem(
                item.getId(),
                fetchItemName(item.getId()), // Fetch name from DB if needed
                item.getPrice(),
                item.getQuantity()
            ))
            .collect(Collectors.toList());
    }

    // Simulate fetching name from DB if needed
    private String fetchItemName(String itemId) {
        // TODO: Implement this method to fetch name from Menu DB or cache
        return "Unknown Item";  // Default name if not found
    }

    public String getOrderId() { return orderId; }
    public String getPaymentId() { return paymentId; }
    public String getUsername() { return username; }
    public double getTotal() { return total; }
    public Date getTimestamp() { return timestamp; }
    public List<OrderItem> getItems() { return items; }

    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public void setUsername(String username) { this.username = username; }
    public void setTotal(double total) { this.total = total; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
