package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Company;
import com.example.jwt.JwtUtil;
import com.example.repository.CompanyRepository;
import com.example.service.CompanyService;

@RestController
@RequestMapping(value = "/company")

@Controller
public class CompanyController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	CompanyRepository cRepository;

	@Autowired
	CompanyService cService;

	@Autowired
	JwtUtil jUtil;

	// 카트 조회수
	// 127.0.0.1:8080/HOST/company/update_wish_hit.json?type=
	@PutMapping(value = "/update_wish_hit.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> updatehit(@RequestParam(name = "type", defaultValue = "1") int type,
			@RequestHeader("token") String token) {
		// System.out.println(type);
		// System.out.println(token);

		Map<String, Object> map = new HashMap<>();
		try {
			String id = jUtil.extractUsername(token);
			Company company = cRepository.getById(id);

			if (type == 1) {
				if (company.getCompanycarthit() == 1) {
					company.setCompanycarthit(0);// type 1 = user
				} else if (company.getCompanycarthit() == 0) {
					company.setCompanycarthit(1);// type 1 = user
				}
				cRepository.save(company);
				map.put("status", 1);
			} else if (type == 2) {
				company.setCompanycarthit(company.getCompanycarthit() + 2);// type 1 = user
				map.put("status", 2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", e.hashCode());
		}
		return map;
	}

	// 127.0.0.1:8080/HOST/company/select.json
	@PostMapping(value = "/select.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> select(@RequestBody Company company) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (cService.selectCompanyUserOne(company.getCompanyid()) != null) {
				map.put("status", 200);
			} else {
				map.put("status", 1010);
			}
			// map.put("status", 200L);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", e.hashCode());
		}
		return map;
	}

	// 127.0.0.1:8080/HOST/company/mylist.json
	@GetMapping(value = "/mylist.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object selectPorductListGET(@RequestHeader("token") String token) {
		Map<String, Object> map = new HashMap<>();
		System.out.println(token);
		try {
			String userid = jUtil.extractUsername(token);
			// map.put("userid", userid);
			map.put("list", cService.selectCompanyUserOne(userid));
			map.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", e.hashCode());
		}
		return map;
	}

	// 127.0.0.1:8080/HOST/company/join.json

	// 개인 : 127.0.0.1:8080/HOST/company/join.json?type=1
	// 회원 : 127.0.0.1:8080/HOST/company/join.json?type=2
	// 회원가입
	@PostMapping(value = "/join.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> CompanyJoinPOST(@RequestParam(name = "type", defaultValue = "2") int type,
			@RequestBody Company company) {

		// System.out.println(type);
		// System.out.println(company.toString());

		Map<String, Object> map = new HashMap<>();
		try {
			if (type == 1) {

				company.setRole("USER");
			} else if (type == 2) {
				company.setRole("SELLER");
			}
			cService.insertCompanyUser(company);
			map.put("status", 200L);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", e.hashCode());
		}
		return map;
	}

	// 127.0.0.1:8080/HOST/company/join.json
	@GetMapping(value = "/join.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object joinGET() {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("status", 200);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", e.hashCode());
		}
		return map;
	}

	// 127.0.0.1:8080/HOST/company/login.json
	@PostMapping(value = "/login.json")
	public Map<String, Object> loginPOST(@RequestBody Company company,
			@RequestParam(name = "type", defaultValue = "2") int type) throws IOException {
		Map<String, Object> map = new HashMap<>();
		try {

			int ret = cService.loginCompanyUser(company, type);
			if (ret == 1) {
				map.put("status", 200); // 로그인 성공 시
				map.put("token", jUtil.generateToken(company.getCompanyid()));
				map.put("type", type);
			} else if (ret == 0) {
				map.put("status", 0); // 개인 != 회원, 회원 != 개인
			} else if (ret == -1) {
				map.put("status", -1); // DB에 존재하지 않는 회원
			}
		} catch (Exception e) {
			map.put("status", e.hashCode());
		}
		return map;
	}

	// 127.0.0.1:8080/HOST/company/login.json
	@GetMapping(value = "/login.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object loginGET() {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("status", 200);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", e.hashCode());
		}
		return map;
	}

	// 내 정보 수정
	@PostMapping(value = "/listupdate", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> mylistupdate(@RequestBody Company company, @RequestHeader("TOKEN") String token) {
		Map<String, Object> map = new HashMap<>();
		try {
			String userid = jUtil.extractUsername(token);
			Company company2 = cService.selectCompanyUserOne(userid);

			company2.setCompanyno(company.getCompanyno());
			company2.setPhone(company.getPhone());
			company2.setCountry(company.getCountry());
			company2.setCity(company.getCity());
			cService.updateCompanyUser(company2);
			map.put("success", 200);
		} catch (Exception e) {
			map.put("fail", e.hashCode());
		}
		return map;
	}

	// 127.0.0.1:8080/HOST/company/update // 비밀번호 변경
	@PostMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> updatepw(@RequestBody Company company, @RequestHeader("TOKEN") String token) {
		Map<String, Object> map = new HashMap<>();
		try {
			String userid = jUtil.extractUsername(token);
			Company company1 = cService.selectCompanyUserOne(userid); // 원래 회원정보
			BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
			String newpw = bcpe.encode(company.getNewpassword()); // 새 비밀번호
			String oldpw = company1.getPassword(); // 원래비밀번호

			// if (bcpe.matches(company.getPassword(), test1)) {
			if (bcpe.matches(company.getPassword(), oldpw)) { // 원래비밀번호 확인후 새비밀번호

				company1.setPassword(newpw); // 새비밀번호

				cService.updateCompanyUser(company1);
				map.put("success", 200);
			}
			System.out.println(map);
		} catch (Exception e) {
			map.put("fail", e.hashCode());
		}
		return map;
	}

	// 회원탈퇴
	// 127.0.0.1:8080/HOST/company/delete
	@DeleteMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> companyDelete(@RequestBody Company company, @RequestHeader("TOKEN") String token) {
		Map<String, Object> map = new HashMap<>();
		try {
			Company company2 = cRepository.findById(company.getCompanyid()).get();
			BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
			System.out.println(company.toString());
			if (bcpe.matches(company.getPassword(), company2.getPassword())) { // 암호화된 암호와 암호화되지 암호가 일치하면 아이디삭제
				cService.deleteCompanyUser(company.getCompanyid());
				map.put("claer", 200);
			}
		} catch (Exception e) {
			map.put("syatus", 500);
		}
		return map;

	}

	// 수정버전(메뉴)
	// @PostMapping(value = "/company/update1")
	// public String companyupdate1(HttpServletRequest request, HttpServletResponse
	// response,
	// @RequestParam(name = "menu") int menu,
	// @RequestParam(name = "companyname", required = false) String companyname,
	// @RequestParam(name = "phone", required = false) String phone,
	// @RequestParam(name = "companyno", required = false) Long companyno,
	// @RequestParam(name = "password", required = false) String password,
	// @RequestParam(name = "newpassword", required = false) String newpassword,
	// Authentication auth) {
	// if (auth != null) { // 로그인시
	// User user = (User) auth.getPrincipal();
	// Company company = cRepository.getById(user.getUsername());
	// if (menu == 1) {
	// // 정보 수정
	// company.setCompanyname(companyname);
	// company.setPhone(phone);
	// company.setCompanyno(companyno);
	// cRepository.save(company);
	// return "redirect:/company/menu3";
	// } else if (menu == 2) {
	// // 암호변경
	// BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
	// if (bcpe.matches(password, company.getNewpassword())) {
	// company.setPassword(bcpe.encode(newpassword));
	// cRepository.save(company);
	// }
	// return "redirect:/company/menu3";
	// } else if (menu == 3) {
	// // 탈퇴 및 로그아웃
	// BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
	// if (bcpe.matches((password), company.getPassword())) {
	// cRepository.deleteById(company.getCompanyid());
	// new SecurityContextLogoutHandler().logout(request, response, auth);
	// return "redirect:/";
	// }
	// return "redirect:/company/menu3";
	// }
	// return "redirect:/company/menu3";
	// } else {
	// return "redirect:/member/login";
	// }

	// }
}
