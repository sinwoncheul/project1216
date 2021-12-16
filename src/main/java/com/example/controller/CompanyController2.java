package com.example.controller;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Company;
import com.example.entity.Image;
import com.example.entity.Image2;
import com.example.entity.Insertcompany;
import com.example.entity.Option;
import com.example.entity.Product;
import com.example.jwt.JwtUtil;
import com.example.repository.CompanyRepository;
import com.example.repository.CompanyRepository2;
import com.example.repository.ImageRepository;
import com.example.repository.ImageRepository2;
import com.example.repository.OptionRepository;
import com.example.repository.ProductRepository;
import com.example.service.CompanyService;
import com.example.service.CompanyService2;
import com.example.service.OptionService;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/company2")
public class CompanyController2 {

    @Autowired
    CompanyService2 cService2;

    @Autowired
    CompanyRepository cRepository;

    @Autowired
    CompanyService cService;

    @Autowired
    CompanyRepository2 cRepository2;

    @Autowired
    ImageRepository iRepository;

    @Autowired
    ImageRepository2 iRepository2;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OptionRepository optionRepository;

    @Autowired
    OptionService optionService;

    // 127.0.0.1:8080/HOST/company2/optionselect.json
    @GetMapping(value = "/optionselect.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectPorductListGET() {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("list", optionService.selectOptionList());
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 상품이미지 조회 + 옵션 정보 조회 (상세페이지로 호출)
    // 127.0.0.1:8080/HOST/company2/detailpage_image?no=번호
    @RequestMapping(value = "/detailpage_image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> selectImage1(@RequestParam("no") long no) throws IOException {
        // Map<String, Object> map = new HashMap<>();
        try {
            Image2 image2 = iRepository2.findByProduct_productno(no);
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

        } catch (Exception e) {
            return null;
        }
    }

    // 127.0.0.1:8080/HOST/company2/wishhitcompanytotal.json
    @GetMapping(value = "/wishhitcompanytotal.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object wishhitcompanytotal() {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("desclist", cRepository2.findAllByOrderByInsertcompanynoDesc());
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/company2/home.json
    @GetMapping(value = "/home.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object Company2GETObject() {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        return map;
    }

    // 127.0.0.1:8080/HOST/company2/insert.json
    @PostMapping(value = "/insert.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object company2Post(@RequestBody Insertcompany insertcompany) {
        Map<String, Object> map = new HashMap<>();
        try {
            long no = cService2.insertCompany(insertcompany);
            map.put("status", 200);
            map.put("no", no);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/company2/insertcompany_image.json
    @PostMapping(value = "/insertcompany_image.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertPDImgagePost(@RequestParam(name = "no") long no,
            @RequestParam(name = "file") MultipartFile[] files) throws IOException {
        // System.out.println(files.getOriginalFilename());

        Map<String, Object> map = new HashMap<>();
        try {
            Insertcompany insertcompany = cRepository2.getById(no);
            List<Image> list = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                Image iProjection = new Image();
                iProjection.setInsertcompany(insertcompany);
                iProjection.setImage(files[i].getBytes());
                iProjection.setImagename(files[i].getOriginalFilename());
                iProjection.setImagesize(files[i].getSize());
                iProjection.setImagetype(files[i].getContentType());
                list.add(iProjection);
            }
            iRepository.saveAll(list);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 555);
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/company2/insertcompany_image.json
    @GetMapping(value = "/insertcompany_image.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertCompanyImageGET() {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 555);
        }

        return map;
    }

    // 127.0.0.1:8080/HOST/company2/select.json?
    @GetMapping(value = "/select.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectCompany2ListGET(@RequestParam(name = "page", defaultValue = "1") int page) {
        Map<String, Object> map = new HashMap<>();
        try {

            map.put("list", cService2.selectCompanyList());
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/company2/selectone.json
    @GetMapping(value = "/selectone.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectoneCompany2GET(@RequestHeader("TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            map.put("list", cService2.selectinsertcompany(userid));
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/company2/select_image?insertcompanyno=

    @RequestMapping(value = "/select_image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> selectImage(@RequestParam("insertcompanyno") Long insertcompanyno)
            throws IOException {
        // Map<String, Object> map = new HashMap<>();
        try {
            Image image = iRepository.findByInsertcompany_insertcompanyno(insertcompanyno);
            if (image.getImage().length > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (image.getImagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (image.getImagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                }

                // 클래스명 response = new 클래스명( 생성자선택 )
                ResponseEntity<byte[]> response = new ResponseEntity<>(image.getImage(), headers, HttpStatus.OK);
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

    // RequestBody Map<> => RequestBody Board
    // /ROOT/api/update_hit { no:9 } => @RequestParam()
    @RequestMapping(value = "/update_hit", method = {
            RequestMethod.PUT }, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> updateOne(@RequestBody Map<String, Long> map1) {

        Map<String, Integer> map = new HashMap<>();
        try {
            Insertcompany insertcompany = cRepository2.getById(map1.get("no"));
            // insertcompany.setInserthit(insertcompany.getInserthit() + 1);
            cRepository2.save(insertcompany);
            map.put("result", 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", 0);
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/company2/insertcompanyupdate
    @PostMapping(value = "/insertcompanyupdate", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> mylistupdate(@RequestBody Insertcompany insertcompany,
            @RequestHeader("TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            List<Insertcompany> insertcompany2 = cService2.selectinsertcompany(userid);

            // insertcompany2.setInsertcompanyname(insertcompany.getInsertcompanyname());
            // insertcompany2.setInsertcompanyaddress(insertcompany.getInsertcompanyaddress());
            // insertcompany2.setInsertcompanytel(insertcompany.getInsertcompanytel());
            // insertcompany2.setInsertcompanycontent(insertcompany.getInsertcompanycontent());
            // cService2.updateCompany(insertcompany2);
            map.put("success", 200);
        } catch (Exception e) {
            map.put("fail", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/company2/update_image?insertcompanyno=?
    @PostMapping(value = "/update_image", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdateBoardImage(@RequestParam(name = "insertcompanyno") long insertcompanyno,
            @RequestParam(name = "updatefile") MultipartFile files) throws IOException {
        // System.out.println(files.getOriginalFilename());
        Map<String, Object> map = new HashMap<>();
        try {
            if (files != null) {
                Image image1 = iRepository.findByInsertcompany_insertcompanyno(insertcompanyno);
                image1.setImage(files.getBytes());
                image1.setImagename(files.getOriginalFilename());
                image1.setImagesize(files.getSize());
                image1.setImagetype(files.getContentType());
                iRepository.save(image1);

            }
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/company2/product_delete
    @DeleteMapping(value = "/product_delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteCompany2Delete(@RequestParam("no") Long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            cService2.deleteCompany(no);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/company2/insertcompany.json
    @PostMapping(value = "/insertcompany.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insertcompany(@RequestBody Insertcompany insertcompany, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            Company comp = cRepository.findById(userid).orElse(null);
            insertcompany.setCompany(comp);
            long no = cService2.insertCompany(insertcompany);
            map.put("status", 200);
            map.put("no", no);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/company2/selectone.json
    @GetMapping(value = "/selectno.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectinsertcompanyno(@RequestHeader("TOKEN") String token,
            @RequestParam("insertcompanyno") Long insertcompany) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            map.put("list", cService2.selectinsertcompany(userid));
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 기업 상세페이지
    // 127.0.0.1:8080/HOST/company2/select_insertcompany_content.json?no=1
    @GetMapping(value = "/select_insertcompany_content.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> SelectInsertcompanyContent(@RequestParam(name = "no") long no) throws IOException {
        Map<String, Object> map = new HashMap<>();
        try {
            Insertcompany insertcompany = cRepository2.findByInsertcompanyno(no);
            List<Product> product = productRepository
                    .findAllByInsertcompany_insertcompanyno(insertcompany.getInsertcompanyno());
            // System.out.println(product.getCompany().toString());

            for (Product tmp : product) {
                tmp.setCompany(null);
                tmp.setInsertcompany(null);
            }
            insertcompany.setCompany(null);
            map.put("status", 200);
            map.put("product", product);
            map.put("insertcompany", insertcompany);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

}
