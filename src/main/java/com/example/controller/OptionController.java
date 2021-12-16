package com.example.controller;

import com.example.repository.OptionRepository;
import com.example.service.OptionService;
import com.example.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/option")
public class OptionController {

	@Autowired
	OptionRepository oRepository;

	@Autowired
	OptionService oService;

	@Autowired
	ProductService pService;

}
