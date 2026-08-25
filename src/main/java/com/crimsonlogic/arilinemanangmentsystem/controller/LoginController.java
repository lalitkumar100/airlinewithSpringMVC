package com.crimsonlogic.arilinemanangmentsystem.controller;


	
	import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.GetMapping;

	@Controller
	public class LoginController {

     @GetMapping("/login")
     public String showLoginPage() {
         return "user/login"; // Maps to login.jsp
     }

     @GetMapping("/logout")
     public String logout(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {
         javax.servlet.http.HttpSession session = request.getSession(false);
         if (session != null) {
             session.invalidate();
         }
         
         // Clear JWT cookie
         javax.servlet.http.Cookie jwtCookie = new javax.servlet.http.Cookie("jwtToken", null);
         jwtCookie.setPath("/");
         jwtCookie.setHttpOnly(true);
         jwtCookie.setMaxAge(0);
         response.addCookie(jwtCookie);
         
         return "redirect:/users/login";
     }
     
     @GetMapping("user/menu")
     public String userMenu() {
         return "user/user-menu"; // Maps to login.jsp
     
	}
	}


