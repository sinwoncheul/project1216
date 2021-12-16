package com.example.service;

import java.util.List;

import com.example.entity.Option;

import org.springframework.stereotype.Service;

@Service
public interface OptionService {

    public Option insertOption(Option option);

    public List<Option> selectOptionList();

    public List<Option> selectOptionproductno(Long productno);

    public int deleteOption(long no);

    public int updateProductOption(Option option);

    public Option selectProductOptionOne(long no);

}
