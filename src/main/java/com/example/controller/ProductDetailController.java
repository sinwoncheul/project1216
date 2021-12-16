package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.entity.Image2;
import com.example.entity.ProductDetail;
import com.example.repository.ImageRepository;
import com.example.repository.ImageRepository2;
import com.example.repository.ProductDtailRepository;
import com.example.repository.ProductRepository;
import com.example.service.ProductDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/product")
public class ProductDetailController {

    @Autowired
    ProductDetailService pDetailService;

    @Autowired
    ProductRepository pRepository;

    @Autowired
    ProductDtailRepository pDtailRepository;

    @Autowired
    ImageRepository iRepository;

    @Autowired
    ImageRepository2 iRepository2;

    // 127.0.0.1:8080/HOST/product/insert_image.json
    // @PostMapping(value = "/insert_image.json")
    // public Map<String, Object> insertPDImgagePost(@RequestParam(name =
    // "productnameno") long productnameno,
    // @RequestParam(name = "file") MultipartFile[] files) throws IOException {

    // Map<String, Object> map = new HashMap<>();
    // try {
    // ProductDetail productDetail = pDtailRepository.getById(productnameno);
    // List<Image2> list = new ArrayList<>();
    // for (int i = 0; i < files.length; i++) {
    // Image2 image2 = new Image2();
    // // image2.setProductDetail(productDetail);
    // image2.setImage(files[i].getBytes());
    // image2.setImagename(files[i].getOriginalFilename());
    // image2.setImagesize(files[i].getSize());
    // image2.setImagetype(files[i].getContentType());
    // list.add(image2);
    // }
    // iRepository2.saveAll(list);
    // map.put("status", 200);
    // } catch (Exception e) {
    // e.printStackTrace();
    // map.put("status", 555);
    // }
    // return map;
    // }

    // 127.0.0.1:8080/HOST/product/Detailinsert.json
    @PostMapping(value = "/Detailinsert.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insertProductDetailPost(@RequestBody ProductDetail productDetail) {
        Map<String, Object> map = new HashMap<>();
        try {

            pDetailService.insertProduct(productDetail);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/Detailselect.json
    @GetMapping(value = "/Detailselect.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectPorductDetailListGET() {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("list", pDetailService.selectProductList());
            // System.out.println(pDetailService.selectProductList());
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/select_image?no=번호
    // <img :src=`/board/select_image?no=${no}` / >
    @RequestMapping(value = "/Detailselect_image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> selectImage(@RequestParam("no") long no) throws IOException {
        // Map<String, Object> map = new HashMap<>();
        try {
            Image2 image2 = iRepository2.getById(no);
            if (image2.getImage().length > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (image2.getImagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (image2.getImagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                }

                ResponseEntity<byte[]> response = new ResponseEntity<>(image2.getImage(), headers, HttpStatus.OK);
                return response;
            }
            return null;
        }
        // 오라클에 이미지를 읽을 수 없을 경우
        catch (Exception e) {
            // InputStream is =
            // resourceLoader.getResource("classpath:/static/images/default.jpg").getInputStream();

            // HttpHeaders headers = new HttpHeaders();
            // headers.setContentType(MediaType.IMAGE_JPEG);
            // ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(),
            // headers, HttpStatus.OK);
            return null;
        }
    }

    // 127.0.0.1:8080/HOST/product/Detailselectone.json
    @GetMapping(value = "/Detailselectone.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectoneProductDetailGET(@RequestParam("no") Long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            pDetailService.selectProductDetailmOne(no);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/detailupdate.json
    @PutMapping(value = "/detailupdate.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateProductDetailPUT(@RequestBody ProductDetail productDetail) {
        Map<String, Object> map = new HashMap<>();
        try {
            pDetailService.updateProductDetail(productDetail);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/Detailproduct_delete
    @DeleteMapping(value = "/Detailproduct_delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteProductDetailDelete(@RequestParam("no") Long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            pDetailService.deleteProductDetail(no);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
}
