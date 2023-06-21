//package com.trip.project.controller;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.trip.project.dto.LoginDTO;
//import com.trip.project.service.LoginService;
//
//@Controller
//@RequestMapping("/login")
//public class LoginController {
//
//	@Autowired
//	private LoginService lservice;
//	
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
//
//	// 로그인 메인 페이지
//	@RequestMapping
//	public String loginMain() {
//
//		return "login";
//	}
//
//	@PostMapping("/logincheck")
//	public String login(HttpSession session, Model model, LoginDTO dto) {
//		LoginDTO res = lservice.login(dto);
//		
//		if(dto.getUserID() == null) {
//			model.addAttribute("message","유저없음");
//			System.out.println("idcheck");
//			return "redirect:/login" ;
//		}
//		
//		System.out.println(passwordEncoder.matches(dto.getUserPW(), res.getUserPW()));
//		if(!passwordEncoder.matches(dto.getUserPW(), res.getUserPW())) {
//			System.out.println("password.");
//			System.out.println(dto.getUserPW());
//			System.out.println(res.getUserPW());
//			model.addAttribute("message", "비밀번호일치하지않습니다.");
//			return "redirect:/login";
//		}
//		session.setAttribute("login", res);
//		return "redirect:/";
//		
////		if (res != null && res.getUserPW().equals(dto.getUserPW())) {
////			session.setAttribute("res", res);
////			return "redirect:/";
////		} else {
////			model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
////			return "redirect:/login";
////		}
//	}
//	
//	// 로그아웃 - 메인 
//	@GetMapping("/logout")
//	public String logout(HttpSession session, HttpServletRequest request) {
//		session = request.getSession(false);
//	    if (session != null) {
//	        session.invalidate();
//	    }
//		return "redirect:/";
//	}
//
//	// 아이디 찾기 페이지
//	@RequestMapping("/idfindform")
//	public String idFindForm() {
//
//		return "idfindform";
//	}
//
//	// 아이디 찾기
//	@RequestMapping("/idfind")
//	public String idFind() {
//
//		return "idfind";
//	}
//
//	// 비밀번호 찾기 페이지
//	@RequestMapping("/pwfindform")
//	public String pwFindForm() {
//
//		return "pwfindform";
//	}
//
//	// 비밀번호 찾기
//	@RequestMapping("/pwfind")
//	public String pwFind() {
//
//		return "pwfind";
//	}
//
//	// 회원가입 페이지
//	@RequestMapping("/registerform")
//	public String registerForm() {
//
//		return "registerform";
//
//	}
//
//	// 회원가입
//	@RequestMapping("/register")
//	public String register(Model model, LoginDTO dto) {
//		int res = lservice.regist(dto);
//		
//		
//		if (res != 0) {
//			System.out.println(dto.getUserName());
//			model.addAttribute("message", "회원가입 완료.");
//			return "redirect:/";
//		} else {
//			model.addAttribute("error", "재등록.");
//			return "redirect:/registerform";
//		}
//		
//	}
//
//	// 사용자 마이페이지 메인
//	@RequestMapping("/usermain")
//	public String userMain() {
//
//		return "usermain";
//	}
//
//	// 사용자 회원 정보 수정 페이지
//	@RequestMapping("/userupdateform")
//	public String userUpdateForm() {
//
//		return "userupdateform";
//	}
//
//	// 사용자 회원 정보 수정
//	@RequestMapping("userupdate")
//	public String userupdate() {
//
//		return "userupdate";
//	}
//
//	// 사용자 회원 탈퇴
//	@RequestMapping("userdelete")
//	public String userDelete() {
//
//		return "userdelete";
//	}
//
//}
