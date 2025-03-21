package vttp.batch5.csf.assessment.server.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.batch5.csf.assessment.server.models.MenuItem;

// Use the following class for MySQL database
@Repository
public class RestaurantRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<MenuItem> getMenuItems() {
        Query query = new Query();
        query.with(Sort.by(Sort.Order.asc("name")));  // Sort by 'name' in ascending order
        return mongoTemplate.find(query, MenuItem.class, "menus");  // Fetch from 'menus' collection
    }

}
