package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Company;
import com.example.entity.Image2;
import com.example.entity.Insertcompany;
import com.example.entity.Option;
import com.example.entity.Product;
import com.example.jwt.JwtUtil;
import com.example.repository.CompanyRepository;
import com.example.repository.CompanyRepository2;
import com.example.repository.ImageRepository2;
import com.example.repository.ProductRepository;
import com.example.service.CompanyService2;
import com.example.service.OptionService;
import com.example.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    ProductService pService;

    @Autowired
    OptionService oService;

    @Autowired
    CompanyService2 cService2;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ProductRepository pRepository;

    @Autowired
    CompanyRepository cRepository;

    @Autowired
    CompanyRepository2 cRepository2;

    @Autowired
    ImageRepository2 iRepository2;

    // 127.0.0.1:8080/HOST/product/home.json
    @GetMapping(value = "/home.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object homeGEObject() {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        return map;
    }

    // 127.0.0.1:8080/HOST/product/insertproduct_image.json?productno=
    @PostMapping(value = "/insertproduct_image.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertImage(@RequestParam(name = "productno") long productno,
            @RequestParam(name = "files", required = false) MultipartFile[] files) throws IOException {
        Map<String, Object> map = new HashMap<>();
        System.out.println("------------------------------------------");
        System.out.println(files);
        try {
            Product product = pRepository.getById(productno);

            for (int i = 0; i < files.length; i++) {
                List<Image2> list = new ArrayList<>();
                Image2 image2 = new Image2();
                image2.setProduct(product);
                image2.setImage(files[i].getBytes());
                image2.setImagename(files[i].getOriginalFilename());
                image2.setImagesize(files[i].getSize());
                image2.setImagetype(files[i].getContentType());
                // iRepository2.save(image2);
                list.add(image2);
                iRepository2.saveAll(list);

            }
            map.put(("status"), 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;

    }

    // 127.0.0.1:8080/HOST/product/insert_image.json
    @PostMapping(value = "/insert_image.json")
    public Map<String, Object> insertPDImgagePost(@RequestParam(name = "productno") long productno,
            @RequestParam(name = "file") MultipartFile[] files) throws IOException {

        Map<String, Object> map = new HashMap<>();
        try {
            Product product = pRepository.getById(productno);
            List<Image2> list = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                Image2 image2 = new Image2();
                image2.setProduct(product);
                image2.setImage(files[i].getBytes());
                image2.setImagename(files[i].getOriginalFilename());
                image2.setImagesize(files[i].getSize());
                image2.setImagetype(files[i].getContentType());
                list.add(image2);
            }
            iRepository2.saveAll(list);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 555);
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/insert.json
    // {
    // "producttitle": "a",
    // "productcontent": "b",
    // "producttotalprice": 1,
    // "productquantity": 2,
    // "deliverydate": "c",
    // "deliverypay": "d",

    // "optioncolor" : "j",
    // "optionsize": "k",
    // "optionprice" : 1,
    // "optionquantity" : 2

    // }
    // @PostMapping(value = "/insert.json", consumes = MediaType.ALL_VALUE, produces
    // = MediaType.APPLICATION_JSON_VALUE)
    // public Object insertProductPost(@RequestBody Map<String, String> map1,
    // @RequestHeader("token") String token) { // Product(2)
    // // +
    // // Option(3)
    // Map<String, Object> map = new HashMap<>();
    // try {
    // // product
    // Product product = new Product();
    // product.setProducttitle((String) map1.get("producttitle"));
    // product.setProductcontent((String) map1.get("productcontent"));
    // product.setProducttotalprice(Long.parseLong(map1.get("producttotalprice")));
    // product.setProductquantity(Long.parseLong(map1.get("productquantity")));
    // product.setDeliverypay((String) map1.get("deliverypay"));
    // product.setDeliverydate((String) map1.get("deliverydate"));

    // String userid = jwtUtil.extractUsername(token);
    // Company comp = cRepository.findById(userid).orElse(null);
    // product.setCompany(comp);

    // Product product2 = pService.insertProduct(product);
    // // System.out.println(map1.get("optionquantity"));
    // // System.out.println(map1.get("optionprice"));
    // // option (외래키 사용중)
    // Option option = new Option();
    // option.setProduct(product2);
    // option.setOptioncolor((String) map1.get("optioncolor"));
    // option.setOptionsize((String) map1.get("optionsize"));
    // option.setOptionprice(Long.parseLong(map1.get("optionprice")));
    // option.setOptionquantity(Long.parseLong(map1.get("optionquantity")));

    // Option option2 = oService.insertOption(option);
    // System.out.println(option2);

    // product2.getProductno();
    // // System.out.println(option);
    // map.put("status", 200);
    // } catch (Exception e) {
    // e.printStackTrace();
    // map.put("status", e.hashCode());
    // }
    // return map;
    // }

    // 상품 한개 조회
    // 127.0.0.1:8080/HOST/product/selectone1.json?no=
    @GetMapping(value = "/selectone1.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectone1(@RequestHeader("token") String token, @RequestParam("no") long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            // String userid = jwtUtil.extractUsername(token);
            // Company comp = cRepository.findById(userid).orElse(null);
            Product product = pService.selectItemAlone(no);
            // Insertcompany inc = cRepository2.findByInsertcompanyno(no);
            // map.put("insertcompanyno", inc);
            // map.put("token", comp);
            // map.put("list", cService2.selectCompanyList());
            map.put("productno", product);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/insertoption.json?productno=
    @PostMapping(value = "/insertoption.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insertOption(@RequestBody Option option, @RequestParam("productno") long no,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {

            String userid = jwtUtil.extractUsername(token);
            Company comp = cRepository.findById(userid).orElse(null);
            Product product = pService.selectItemAlone(no);

            // Option option = new Option();
            // option.setProduct(product);
            // option.setOptioncolor((String) map1.get("optioncolor"));
            // option.setOptionsize((String) map1.get("optionsize"));
            // option.setOptionprice(Long.parseLong(map1.get("optionprice")));
            // option.setOptionquantity(Long.parseLong(map1.get("optionquantity")));
            option.setProduct(product);
            Option option2 = oService.insertOption(option);
            System.out.println(option2);

            // product2.getProductno();
            // System.out.println(option);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    //
    // 127.0.0.1:8080/HOST/product/selectoption.json?productno=
    @GetMapping(value = "/selectoption.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectOption(@RequestParam("productno") long no,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {

            String userid = jwtUtil.extractUsername(token);
            Company comp = cRepository.findById(userid).orElse(null);
            // Product product = pService.findbyProduct_productno(no);

            List<Option> option2 = oService.selectOptionproductno(no);
            System.out.println(option2);

            map.put("product", option2);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/insertproduct.json
    @PostMapping(value = "/insertproduct.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insertProduct(@RequestBody Map<String, String> map1,
            @RequestHeader("token") String token) {

        System.out.println("TOKEN:" + token);
        System.out.println("insertcompanyno : " + map1.get("insertcompanyno"));

        Long no = Long.parseLong(map1.get("insertcompanyno"));

        Map<String, Object> map = new HashMap<>();
        try {
            // product
            Product product = new Product();
            product.setProducttitle((String) map1.get("producttitle"));
            product.setProductcontent((String) map1.get("productcontent"));
            product.setProducttotalprice(Long.parseLong(map1.get("producttotalprice")));
            product.setProductquantity(Long.parseLong(map1.get("productquantity")));
            product.setDeliverypay((String) map1.get("deliverypay"));
            product.setDeliverydate((String) map1.get("deliverydate"));

            String userid = jwtUtil.extractUsername(token);
            Company comp = cRepository.findById(userid).orElse(null);
            Insertcompany imc = cRepository2.findByInsertcompanyno(no);
            product.setCompany(comp);
            product.setInsertcompany(imc);

            Product product2 = pService.insertProduct(product);
            map.put("product", product2);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/select_token.json
    @PostMapping(value = "/select_token.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectPorductGET(@RequestHeader("token") String token) {

        String userid = jwtUtil.extractUsername(token);
        System.out.println(userid);

        Map<String, Object> map = new HashMap<>();
        try {
            map.put("list", pRepository.findAllByCompany_companyid(userid));
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/select.json
    @GetMapping(value = "/select.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectPorductListGET() {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("list", pService.selectProductList());
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/selectone.json
    // @GetMapping(value = "/selectone.json", consumes = MediaType.ALL_VALUE,
    // produces = MediaType.APPLICATION_JSON_VALUE)
    // public Object selectoneProductGET(@RequestParam("no") Long no) {
    // Map<String, Object> map = new HashMap<>();
    // try {
    // pService.selectItemOne(no);
    // map.put("status", 200);
    // } catch (Exception e) {
    // e.printStackTrace();
    // map.put("status", e.hashCode());
    // }
    // return map;
    // }

    // 127.0.0.1:8080/HOST/product/update.json
    @PutMapping(value = "/update.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateProductPUT(@RequestBody Product product) {
        Map<String, Object> map = new HashMap<>();
        try {
            pService.updateItem(product);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/product_update
    @PostMapping(value = "/product_update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> productupdate(@RequestParam("productno") Long no, @RequestBody Product product) {
        Map<String, Object> map = new HashMap<>();
        try {

            Product product2 = pService.selectItemAlone(no);

            product2.setProducttitle(product.getProducttitle());
            product2.setProductquantity(product.getProductquantity());
            product2.setProducttotalprice(product.getProducttotalprice());
            product2.setProductcontent(product.getProductcontent());
            pService.updateItem(product2);
            map.put("success", 200);
        } catch (Exception e) {
            map.put("fail", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/product_delete
    @DeleteMapping(value = "/product_delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteProductDelete(@RequestParam("no") Long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            pService.deleteItem(no);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/selectone.json
    @GetMapping(value = "/selectone.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectone(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            Company comp = cRepository.findById(userid).orElse(null);

            // Insertcompany inc = cRepository2.findByInsertcompanyno(no);
            // map.put("insertcompanyno", inc);
            map.put("token", comp);
            map.put("list", cService2.selectCompanyList());
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/update_productimage?productno=
    @PostMapping(value = "/update_productimage", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdateBoardImage(@RequestParam(name = "productno") long productno,
            @RequestParam(name = "updateproducts") MultipartFile files) throws IOException {
        // System.out.println(files.getOriginalFilename());
        Map<String, Object> map = new HashMap<>();
        try {
            if (files != null) {
                Image2 image1 = iRepository2.findByProduct_productno(productno);
                image1.setImage(files.getBytes());
                image1.setImagename(files.getOriginalFilename());
                image1.setImagesize(files.getSize());
                image1.setImagetype(files.getContentType());
                iRepository2.save(image1);
            }
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/myselect_image?productno=번호
    @RequestMapping(value = "/myselect_image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> selectImage1(@RequestParam("productno") long productno) throws IOException {
        // Map<String, Object> map = new HashMap<>();
        try {
            Image2 image2 = iRepository2.findByProduct_productno(productno);
            if (image2.getImage().length > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (image2.getImagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (image2.getImagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (image2.getImagetype().equals("image/gif")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
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

    // 127.0.0.1/HOST/product/select_category_product.json?page=1&producttitle=&productcontent=
    @GetMapping(value = "/select_category_product.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> Select_Category_Product(@RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "producttitle", defaultValue = "") String producttitle,
            @RequestParam(name = "productcontent", defaultValue = "") String productcontent) {
        Map<String, Object> map = new HashMap<>();
        try {
            PageRequest pageable = PageRequest.of(page - 1, 12);

            List<Product> list = pRepository
                    .findByProducttitleIgnoreCaseContainingOrProductcontentIgnoreCaseContainingOrderByProductnoDesc(
                            producttitle, productcontent, pageable);

            long pages = pRepository.countByProductcontentOrProducttitleContaining(productcontent, producttitle);

            map.put("producttitlelist", list);
            map.put("productpages", (pages - 1) / 10 + 1);
            map.put("stauts", 200);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/select_category_product_count.json
    @GetMapping(value = "/select_category_product_count.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> Category_Product_Count(
            @RequestParam(name = "producttitle", defaultValue = "") String producttitle,
            @RequestParam(name = "productcontent", defaultValue = "") String productcontent) {
        Map<String, Object> map = new HashMap<>();
        try {
            long[] cnt = new long[] { 0L, 0L, 0L, 0L, 0L, 0L };
            if (producttitle.equalsIgnoreCase("") || productcontent.equalsIgnoreCase("")) {
                long list = pRepository.countByProductcontentOrProducttitleContaining(productcontent, producttitle);
                cnt[0] = list;
            }
            if (producttitle.equalsIgnoreCase("삼성") || productcontent.equalsIgnoreCase("삼성")) {
                long samsung = pRepository.countByProductcontentOrProducttitleContaining(productcontent, producttitle);
                cnt[1] = samsung;
            }
            if (producttitle.equalsIgnoreCase("lg") || productcontent.equalsIgnoreCase("lg")) {
                long lg = pRepository.countByProductcontentOrProducttitleContaining(productcontent, producttitle);
                cnt[2] = lg;
            }
            if (producttitle.equalsIgnoreCase("애플") || productcontent.equalsIgnoreCase("애플")) {
                long apple = pRepository.countByProductcontentOrProducttitleContaining(productcontent, producttitle);
                cnt[3] = apple;
            }
            if (producttitle.equalsIgnoreCase("현대") || productcontent.equalsIgnoreCase("현대")) {
                long hyundai = pRepository.countByProductcontentOrProducttitleContaining(productcontent, producttitle);
                cnt[4] = hyundai;
            }
            if (producttitle.equalsIgnoreCase("인텔") || productcontent.equalsIgnoreCase("인텔")) {
                long intel = pRepository.countByProductcontentOrProducttitleContaining(productcontent, producttitle);
                cnt[5] = intel;
            }
            map.put("list", cnt);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

}
