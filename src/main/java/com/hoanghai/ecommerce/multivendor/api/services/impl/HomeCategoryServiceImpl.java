package com.hoanghai.ecommerce.multivendor.api.services.impl;

import com.hoanghai.ecommerce.multivendor.api.entities.HomeCategory;
import com.hoanghai.ecommerce.multivendor.api.repositorys.HomeCategoryRepository;
import com.hoanghai.ecommerce.multivendor.api.services.HomeCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeCategoryServiceImpl implements HomeCategoryService {

    private final HomeCategoryRepository homeCategoryRepository;

    @Override
    public HomeCategory createHomeCategory(HomeCategory homeCategory) {
        return homeCategoryRepository.save(homeCategory);
    }

    @Override
    public List<HomeCategory> createCategories(List<HomeCategory> homeCategory) {
        if(homeCategoryRepository.findAll().isEmpty()){
            return homeCategoryRepository.saveAll(homeCategory);
        }
        return homeCategoryRepository.findAll();
    }

    @Override
    public HomeCategory updateHomeCategory(HomeCategory homeCategory, Long id) throws Exception {
        HomeCategory existingHomeCategory = homeCategoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Category not found"));

        if(homeCategory.getImage() != null){
            existingHomeCategory.setImage(homeCategory.getImage());
        }
        if(homeCategory.getCategoryId() != null){
            existingHomeCategory.setCategoryId(homeCategory.getCategoryId());
        }

        return homeCategoryRepository.save(existingHomeCategory);
    }

    @Override
    public List<HomeCategory> getAllHomeCategories() {
        return homeCategoryRepository.findAll();
    }
}
