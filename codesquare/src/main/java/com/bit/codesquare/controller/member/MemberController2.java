//package com.bit.codesquare.controller.member;
//
//import java.security.Principal;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.bit.codesquare.dto.member.InstructorInfo;
//import com.bit.codesquare.dto.member.Member;
//import com.bit.codesquare.mapper.member.MemberMapper;
//import com.bit.codesquare.service.MemberService;
//import com.bit.codesquare.util.CodesquareUtil;
//
////@Controller
////@RequestMapping("/member")
//public class MemberController2 {
//	private final static Logger logger = LoggerFactory.getLogger(MemberController2.class);
//
//	@Autowired
//	MemberMapper mm;
//
//	@Autowired
//	CodesquareUtil csu;
//
//	@Autowired
//	MemberService ms;
//	
//
//	@RequestMapping("/login")
//	public String login(HttpServletRequest request) {
//
//		String referrer = request.getHeader("Referer");
//		request.getSession().setAttribute("prevPage", referrer);
//
//		return "member/login/login";
//	}
//
//	@GetMapping("/signUp")
//	public String signUp() {
//		return "member/login/signUp";
//	}
//
//	@PostMapping("/signUp")
//	public String signUp(Model model, @ModelAttribute Member member, @RequestParam String userId,
//			@RequestParam String password) {
//		member.setAuthorId(1);
//		member.setNickName(userId);
//		member.setPassword(new BCryptPasswordEncoder().encode(password));
//		mm.signUp(member);
//		model.addAttribute("user", mm.getUser(userId));
//		return "member/login/signUpDone";
//	}
//
//	@PostMapping("idCheck")
//	@ResponseBody
//	public int idCheck(@RequestBody String userId) {
//		int count = 0;
//		count = mm.idCheck(userId);
//		return count;
//	}
//
//
//
//	@GetMapping("/changeNick")
//	public String changeNick(Model model, Principal principal) {
//		String userId = principal.getName();
//		model.addAttribute("user", mm.getUser(userId));
//		// logger.info(userId);
//		return "member/myPage/changeNick";
//	}
//
//	@PostMapping("/nickChange")
//	@ResponseBody
//	public int changeNick(Authentication auth, HttpSession session, @RequestBody String nickName) {
//		String userId = auth.getName();
//
//		// 다른정보 가지고올때
////		SecurityMember sm = (SecurityMember) auth.getPrincipal();
////		sm.getNickName();
//		
//		
//		int count = 0;
//		count = mm.nickCheck(nickName);
//
//		// logger.info(nickName);
//
//		if (count == 0) {
//			Member member = mm.getUser(userId);
//			member.setNickName(nickName);
//			// us.changeNick(user);
//			logger.info(member.toString());
//			mm.changeNick(member);
//			session.setAttribute("nickName", member.getNickName());
//		}
//
//		// logger.info("count:"+count);
//		return count;
//	}
//
//	@PostMapping("/emailChange")
//	@ResponseBody
//	public int emailChange(Principal principal, @RequestBody String email) {
//		String userId = principal.getName();
//
//		int count = 0;
//		count = mm.emailCheck(email);
//
//		// logger.info(nickName);
//		// logger.info(count+"d");
//		if (count == 0) {
//			Member member = mm.getUser(userId);
//			member.setEmail(email);
//			// us.changeNick(user);
//			// logger.info(user.toString());
//			mm.changeEmail(member);
//		}
//		return count;
//	}
//
//	@GetMapping("/findIdPw")
//	public String findIdPw() {
//		return "member/login/findIdPw";
//	}
//	
//	@PostMapping("/emailCheck")
//	@ResponseBody
//	public int emailCheck(@RequestBody String email) {
//
//		int count = 0;
//		count = mm.emailCheck(email);
//	
//		return count;
//	}
//	
//	@PostMapping("findId")
//	@ResponseBody
//	public String findId(@RequestBody String email) {
//		return mm.findId(email);
//	}
//
//	@PostMapping("findPw")
//	@ResponseBody
//	public int findPw(@RequestBody Map<String, String> data) {
//		String userId = data.get("userId");
//		String email = data.get("email");
//		int count = 0;
//		count = mm.findPw(userId, email);
//	
//		return count;
//	}
//	
//
//	
//	@PostMapping("/findPwMail")
//	@ResponseBody
//	public void mailSending(@RequestBody String userId) {
//		ms.mailSending(userId);
//		
//	}
//	
//	
//	
//	
//	
//
//	@GetMapping("/myPage")
//	public String myPage(Model model,@ModelAttribute Member user, Authentication auth, HttpSession session) {
//		csu.getSession(auth, session);
//		model.addAttribute("user", mm.getUser(auth.getName()));
//		return "member/myPage/myPage";
//	}
////
////	@GetMapping("/modifyMyInfo")
////	public String ModifyMyInfo(Model model, Authentication auth) {
////		return "member/myPage/modifyMyInfo";
////	}
//
//	@GetMapping("/changePw")
//	public String changePw(Model model, Authentication auth) {
//		
//		model.addAttribute("user", mm.getUser(auth.getName()));
//		// logger.info(userId);
//		return "member/myPage/changePw";
//	}
//
//	@PostMapping("/changePw")
//	@ResponseBody
//	public int changePwDone(@ModelAttribute Member user, @RequestBody Map<String, String> data) {
//		int count = 0;
//		String password = data.get("password");
//		String userId = data.get("userId");
////		logger.info(userId);
////		logger.info(password);
//		user.setPassword(new BCryptPasswordEncoder().encode(password));
//		user.setUserId(userId);
//		count = mm.changePw(user);
//
//		return count;
//	}
//
//	@GetMapping("/toInstructor")
//	public String toInstructor(Model model, Authentication auth, @ModelAttribute InstructorInfo instructorInfo) {
//		
//		model.addAttribute("user", mm.getUser(auth.getName()));
//		model.addAttribute("instructorInfo", mm.getInstructorInfo(auth.getName()));
//		
//		int count = 0;
//		count = mm.checkInstructor(auth.getName());
//		if (count == 0) {
//			return "member/myPage/toInstructor";
//		}
//		return "member/myPage/instructorPending";
//	}
//
//	@PostMapping("/toInstructor")
//	@ResponseBody
//	public String toInstructor(@ModelAttribute InstructorInfo instructorInfo, @RequestBody Map<String, String> data) {
//
//
//		instructorInfo.setUserId(data.get("userId"));
//		instructorInfo.setIntroContent(data.get("introContent"));
//		mm.toInstructor(instructorInfo);
//
//		return "수정해야해";
//	}
//
//	@GetMapping("/modifyInstructorInfo")
//	public String modifyInstructorInfo(Model model,  Authentication auth,
//			@ModelAttribute InstructorInfo instructorInfo) {
//		
//		model.addAttribute("user", mm.getUser(auth.getName()));
//		// logger.info(mm.getUser(userId).toString());
//		model.addAttribute("instructorInfo", mm.getInstructorInfo(auth.getName()));
//		// logger.info(mm.getInstructorInfo(userId).toString());
//		return "member/myPage/modifyInstructorInfo";
//	}
//
//	@PostMapping("/modifyInstructorInfo")
//	public String modifyInstructorInfo(Model model,  @ModelAttribute InstructorInfo instructorInfo,
//			@RequestBody Map<String, String> data) {
//		String userId = data.get("userId");
//		String introContent = data.get("introContent");
//		String history = data.get("history");
////		logger.info(data.toString());
//		model.addAttribute("userId", mm.getUser(userId));
////		logger.info(userId);
//
//		instructorInfo.setUserId(userId);
//		instructorInfo.setIntroContent(introContent);
//		instructorInfo.setHistory(history);
//		mm.modifyInstructorInfo(instructorInfo);
//
//		return "redirect:modifyInstructorInfo";
//	}
//
//	@GetMapping("/myReservedList")
//	public String myReservedList(Model model, Authentication auth) {
//		
//		// model.addAttribute("user", us.getUser(userId));
//
//		model.addAttribute("list", mm.getReservedList(auth.getName()));
//		return "member/myPage/myReservedList";
//	}
//
//	@GetMapping("/myAppliedList")
//	public String myAppliedList(Model model, Authentication auth) {
//		logger.info(mm.getAppliedList(auth.getName()).toString());
//		model.addAttribute("list", mm.getAppliedList(auth.getName()));
//		return "member/myPage/myAppliedList";
//	}
//
//
//	
//	@GetMapping("/myWantedList")
//	public String myWantedList(Model model, Authentication auth) {
//		
//		//logger.info(ms.getWantedList(auth.getName()).toString());
//		logger.info(ms.getWantedList(auth.getName()).toString());
//		 model.addAttribute("list", ms.getWantedList(auth.getName()));
//
//		
//		return "member/myPage/myWantedList";
//	}
//
//	@GetMapping("/myBoardList")
//	public String myBoardList() {
////		String userId = principal.getName();
////		model.addAttribute("list",us.getWantedList(userId));
//		return "member/myPage/myBoardList";
//	}
//
////	@RequestMapping("/logout")
////	public void logout() {
////
////	}
//
//}
