package test.myblog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import test.myblog.config.JwtUtils;
import test.myblog.model.AuthenticationRequest;
import test.myblog.service.MemberDetailsService;

@RestController
@RequestMapping("/api/v1/login")
@CrossOrigin(origins="http://localhost:5173")
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MemberDetailsService mds;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/authenticate")
	public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request){
		 authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getM_username(), request.getM_password()));
		 final UserDetails user = mds.loadUserByUsername(request.getM_username());
		 if(user != null) {
			 return ResponseEntity.ok(jwtUtils.generateToken(user));
		 }
		 return ResponseEntity.status(400).body("Authentication failed");
	}

}
