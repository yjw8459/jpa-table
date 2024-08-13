package com.osstem.o2o;

import com.osstem.o2o.entity.ETC;
import com.osstem.o2o.entity.Employee;
import com.osstem.o2o.entity.OTC;
import com.osstem.o2o.entity.Product;
import com.osstem.o2o.entity.sample.Category;
import com.osstem.o2o.entity.sample.Item;
import com.osstem.o2o.repository.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
class O2oApplicationTests {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ETCRepository etcRepository;

    @Autowired
    OTCRepository otcRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ItemRepository itemRepository;

    private static final Logger log = LoggerFactory.getLogger(O2oApplicationTests.class);
    @Test
    void contextLoads() {

    }

    @Test
    @Transactional
    void 다대다_매핑_1() {

        // initialize
        Category category = Category.builder()
                .name("카테고리1")
                .build();
        Item item1 = Item.builder()
                .name("아이템1")
                .build();
        Item item2 = Item.builder()
                .name("아이템2")
                .build();
        Item item3 = Item.builder()
                .name("아이템3")
                .build();

        // set Items
        category.addItem(item1);
        category.addItem(item2);
        category.addItem(item3);

        // persist
        categoryRepository.save(category);
        categoryRepository.flush();
        Category findCategory = categoryRepository.findById(category.getId()).get();
        Item findItem = itemRepository.findById(item1.getId()).get();
        log.info("categories: {}", findItem.getCategories().size());
        log.info("items: {}", findCategory.getItems().size());

    }

    @Test
    @Transactional
    void 다대다_매핑_2() {

        // initialize
        Category category = Category.builder()
                .name("카테고리1")
                .build();
        Item item1 = Item.builder()
                .name("아이템1")
                .build();
        Item item2 = Item.builder()
                .name("아이템2")
                .build();
        Item item3 = Item.builder()
                .name("아이템3")
                .build();

        // 양방향 매핑
        category.addItem(item1);
        category.addItem(item2);
        category.addItem(item3);
        item1.addCategory(category);
        item2.addCategory(category);
        item3.addCategory(category);

        // persist
        categoryRepository.save(category);
        categoryRepository.flush();

        Optional<Item> findItem = itemRepository.findById(item1.getId());
        Set<Category> categories = findItem.get().getCategories();
        log.info("categories Size: {}",categories.size());
    }

    @Test
    void 다대다_매핑_3() {

        // initialize
//        Category category = new Category("카테고리1")
//        Category category = new Category("카테고리1");
//        Item item1 = new Item("아이템1");
//        Item item2 = new Item("아이템2");
//        Item item3 = new Item("아이템3");
        Category category = Category.builder()
                .name("카테고리1")
                .build();
        Item item1 = Item.builder()
                .name("아이템1")
                .build();
        Item item2 = Item.builder()
                .name("아이템2")
                .build();
        Item item3 = Item.builder()
                .name("아이템3")
                .build();

        // set Items
        category.addItem(item1);
        category.addItem(item2);
        category.addItem(item3);
        item1.addCategory(category);
        item2.addCategory(category);
        item3.addCategory(category);

        // persist
        categoryRepository.save(category);


        Category findCategory = categoryRepository.findById(category.getId()).get();
        Set<Item> items = findCategory.getItems();
        log.info(findCategory.toString());
        log.info(items.toString());
    }

    @Test
    void 품목_옵션_테스트() {
        ETC etc = new ETC();
//         ETC.builder().name1("test");
        etc.setName("test");
        Product product = new Product();

        OTC otc = new OTC();
        otc.setName("dsada");

        Product product2 = new Product();

        product.setProductOption(etc);
        product2.setProductOption(otc);

        etcRepository.save(etc);
        productRepository.save(product);

        otcRepository.save(otc);
        productRepository.save(product2);

        log.info(product.getId().toString());
        log.info(etc.getId().toString());
        log.info(product2.getId().toString());
        log.info(otc.getId().toString());
    }

    @Test
    void 권한_테스트() {
        Employee employee = new Employee();

    }

}
