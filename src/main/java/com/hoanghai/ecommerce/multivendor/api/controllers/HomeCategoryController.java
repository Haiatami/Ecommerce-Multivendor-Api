package com.hoanghai.ecommerce.multivendor.api.controllers;

import com.hoanghai.ecommerce.multivendor.api.entities.Home;
import com.hoanghai.ecommerce.multivendor.api.entities.HomeCategory;
import com.hoanghai.ecommerce.multivendor.api.services.HomeService;
import com.hoanghai.ecommerce.multivendor.api.services.HomeCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/homes/categories")
@RequiredArgsConstructor
public class HomeCategoryController {
    private final HomeCategoryService homeCategoryService;
    private final HomeService homeService;

    @PostMapping()
    public ResponseEntity<Home> createHomeCategories(@RequestBody List<HomeCategory> homeCategories) {
        List<HomeCategory> categories = homeCategoryService.createCategories(homeCategories);

        Home home = homeService.createHomePageData(categories);
        return new ResponseEntity<>(home, HttpStatus.ACCEPTED);
    }

    @GetMapping("/admin/home-category")
    public ResponseEntity<List<HomeCategory>> getHomeCategories() {
        List<HomeCategory> homeCategories = homeCategoryService.getAllHomeCategories();

        return ResponseEntity.ok(homeCategories);
    }

    @PatchMapping("/admin/home-category/{id}")
    public ResponseEntity<HomeCategory> updateHomeCategory(@PathVariable Long id,
                                                           @RequestBody HomeCategory homeCategory) throws Exception {
        HomeCategory updateHomeCategory = homeCategoryService.updateHomeCategory(homeCategory, id);

        return ResponseEntity.ok(updateHomeCategory);
    }
}
