package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.Option;
import com.example.repository.OptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    OptionRepository oRepository;

    @Override
    public Option insertOption(Option option) {
        try {
            return oRepository.save(option);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Option> selectOptionList() {
        return oRepository.findAll();

    }

    @Override
    public int deleteOption(long no) {
        oRepository.deleteById(no);
        return 0;
    }

    @Override
    public int updateProductOption(Option option) {
        oRepository.save(option);
        return 0;
    }

    @Override
    public Option selectProductOptionOne(long no) {
        Optional<Option> pro = oRepository.findById(no);
        return pro.orElse(null);
    }

    @Override
    public List<Option> selectOptionproductno(Long productno) {

        return oRepository.findAllByProduct_productno(productno);
    }

}
