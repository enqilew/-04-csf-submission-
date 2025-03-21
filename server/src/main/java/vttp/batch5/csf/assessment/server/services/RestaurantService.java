package vttp.batch5.csf.assessment.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vttp.batch5.csf.assessment.server.models.MenuItem;

@Service
public class RestaurantService {

  @Autowired
  private MongoTemplate mongoTemplate;

  // TODO: Task 2.2
  // You may change the method's signature
  public List<MenuItem> getMenu() {
        // Create a query to fetch all menu items
        Query query = new Query();
        // Sort by 'name' in ascending order
        query.with(Sort.by(Sort.Order.asc("name")));
        // Fetch the menu items from the 'menus' collection
        return mongoTemplate.find(query, MenuItem.class, "menus");
    }
  
  // TODO: Task 4
  public String processPayment(String orderId, String payer, double total) {
        final String PAYMENT_API_URL = "https://payment-service-production-a75a.up.railway.app/api/payment";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Authenticate", payer);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonPayload = String.format(
            "{ \"order_id\": \"%s\", \"payer\": \"%s\", \"payee\": \"YourName\", \"payment\": %.2f }",
            orderId, payer, total
        );

        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);
        ResponseEntity<String> response = restTemplate.exchange(PAYMENT_API_URL, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

}
