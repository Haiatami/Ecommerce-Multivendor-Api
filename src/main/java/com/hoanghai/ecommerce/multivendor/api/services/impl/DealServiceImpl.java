package com.hoanghai.ecommerce.multivendor.api.services.impl;

import com.hoanghai.ecommerce.multivendor.api.entities.Deal;
import com.hoanghai.ecommerce.multivendor.api.entities.HomeCategory;
import com.hoanghai.ecommerce.multivendor.api.repositorys.DealRepository;
import com.hoanghai.ecommerce.multivendor.api.repositorys.HomeCategoryRepository;
import com.hoanghai.ecommerce.multivendor.api.services.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {
    private final DealRepository dealRepository;
    private final HomeCategoryRepository homeCategoryRepository;

    @Override
    public List<Deal> getDeals() {
        return dealRepository.findAll();
    }

    @Override
    public Deal createDeal(Deal deal) {
        HomeCategory homeCategory = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);

        Deal newDeal = dealRepository.save(deal);
        newDeal.setCategory(homeCategory);
        newDeal.setDiscount(deal.getDiscount());

        return dealRepository.save(newDeal);
    }

    @Override
    public Deal updateDeal(Deal deal, Long id) throws Exception {
        Deal existingDeal = dealRepository.findById(id).orElse(null);

        HomeCategory homeCategory = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);

        if(existingDeal != null){
            if(deal.getDiscount() != null){
                existingDeal.setDiscount(deal.getDiscount());
            }
            if(homeCategory != null){
                existingDeal.setCategory(homeCategory);
            }
            return dealRepository.save(existingDeal);
        }
        throw new Exception("Deal not found");
    }

    @Override
    public void deleteDeal(Long id) throws Exception {
        Deal deal1 = dealRepository.findById(id).orElseThrow(() -> new Exception("Deal not found"));

        dealRepository.delete(deal1);
    }
}
