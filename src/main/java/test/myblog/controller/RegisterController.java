package test.myblog.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import test.myblog.model.Member;
import test.myblog.persist.dao.DAOException;
import test.myblog.service.MemberService;

@RestController
@RequestMapping("/api/v1/register")
@CrossOrigin(origins="http://localhost:5173")
public class RegisterController {
	
	@Autowired
	private MemberService ms;
	
	@PostMapping("/saveMember")
	public ResponseEntity<Member> saveMember(@RequestBody Member m) throws DAOException, IOException {
		Member createdM = ms.createMember(ms.saveMemberProfileImg(m));
		return ResponseEntity.ok(createdM);
	}
	
	@PostMapping("/checkUsername")
	public String checkUsername(@RequestParam("m_username") String mUsername) throws DAOException {
		return ms.checkIfUsernameUsed(mUsername);
	}
}
