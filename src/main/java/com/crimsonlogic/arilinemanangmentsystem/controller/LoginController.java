package com.crimsonlogic.arilinemanangmentsystem.controller;


	
	import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.GetMapping;

	/**
	 * REST/MVC Controller for managing login controller operations.
	 * Handles HTTP requests and delegates to the appropriate services.
	 */
	@Controller
	public class LoginController {

     /**
      * Executes the show login page operation.
      * @return String the result of the operation
      */
     @GetMapping("/login")
     public String showLoginPage() {
         return "user/login"; // Maps to login.jsp
     }

     /**
      * Executes the logout operation.
      * @param request the request
      * @param response the response
      * @return String the result of the operation
      */
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
     
     /**
      * Executes the user menu operation.
      * @return String the result of the operation
      */
     @GetMapping("user/menu")
     public String userMenu() {
         return "user/user-menu"; // Maps to login.jsp
     
	}
	}


