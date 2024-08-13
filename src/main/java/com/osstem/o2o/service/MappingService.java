package com.osstem.o2o.service;

import com.osstem.o2o.entity.sample.Category;
import com.osstem.o2o.entity.sample.Item;
import com.osstem.o2o.repository.CategoryRepository;
import com.osstem.o2o.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MappingService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void case1() {
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
    }
}
