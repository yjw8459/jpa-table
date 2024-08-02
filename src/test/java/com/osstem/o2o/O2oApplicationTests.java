package com.osstem.o2o;

import com.osstem.o2o.entity.ETC;
import com.osstem.o2o.entity.Employee;
import com.osstem.o2o.entity.Product;
import com.osstem.o2o.repository.ETCRepository;
import com.osstem.o2o.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class O2oApplicationTests {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ETCRepository etcRepository;
    @Test
    void contextLoads() {

    }

    @Test
    void 품목_옵션_테스트() {
        ETC etc = new ETC();
//         ETC.builder().name1("test");
        etc.setName("test");
        Product product = new Product();
        product.setProductOption(etc);
        etcRepository.save(etc);
        productRepository.save(product);
    }

    @Test
    void 권한_테스트() {
        Employee employee = new Employee();

    }

}
