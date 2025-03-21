package vttp.batch5.csf.assessment.server.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import vttp.batch5.csf.assessment.server.models.MenuItem;
import vttp.batch5.csf.assessment.server.models.MongoOrder;
import vttp.batch5.csf.assessment.server.models.OrderRequest;
import vttp.batch5.csf.assessment.server.repositories.CustomerRepository;
import vttp.batch5.csf.assessment.server.repositories.MongoOrderRepository;
import vttp.batch5.csf.assessment.server.repositories.RestaurantRepository;
import vttp.batch5.csf.assessment.server.services.RestaurantService;

@RestController
public class RestaurantController {

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private RestaurantService restaurantService;

  // TODO: Task 2.2
  // You may change the method's signature

  @GetMapping("/api/menu")
  public ResponseEntity<List<MenuItem>> getMenus() {
    List<MenuItem> menuItems = restaurantRepository.getMenuItems();
    return ResponseEntity.ok(menuItems);
    //return ResponseEntity.ok("{}");
  }

  // TODO: Task 4

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MongoOrderRepository mongoOrderRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    

  private boolean validateUser(String username, String password) {

    return customerRepository.existsByUsernameAndPassword(username, password);
  }

  private String generateOrderId() {
    return UUID.randomUUID().toString().substring(0, 8);
  }

  private String processPayment(String orderId, OrderRequest orderRequest) {
    return restaurantService.processPayment(orderId, orderRequest.getUsername(), orderRequest.getTotal());
  }

  private void saveOrderToMySQL(String orderId, String paymentId, OrderRequest orderRequest) {
    String sql = "INSERT INTO place_orders (order_id, payment_id, username, total) VALUES (?, ?, ?, ?)";
    jdbcTemplate.update(sql, orderId, paymentId, orderRequest.getUsername(), orderRequest.getTotal());

    
    mongoOrderRepository.save(new MongoOrder(orderId, paymentId, orderRequest));
  }


  // Do not change the method's signature
  public ResponseEntity<String> postFoodOrder(@RequestBody String payload) {
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderRequest orderRequest = objectMapper.readValue(payload, OrderRequest.class);

        boolean isValidUser = validateUser(orderRequest.getUsername(), orderRequest.getPassword());
        if (!isValidUser) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Invalid username and/or password\"}");
        }

        String orderId = generateOrderId();

        String paymentId = processPayment(orderId, orderRequest);

        saveOrderToMySQL(orderId, paymentId, orderRequest);
        mongoOrderRepository.save(new MongoOrder(orderId, paymentId, orderRequest));

        return ResponseEntity.status(HttpStatus.OK).body(
                "{\"orderId\": \"" + orderId + "\", \"paymentId\": \"" + paymentId + "\", \"timestamp\": \"" + System.currentTimeMillis() + "\"}"
        );
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"" + e.getMessage() + "\"}");
    }
  }



}
