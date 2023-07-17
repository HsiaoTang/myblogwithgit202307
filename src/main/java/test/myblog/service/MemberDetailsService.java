package test.myblog.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import test.myblog.model.Member;
import test.myblog.persist.dao.impl.MemberDAO;

@Service
public class MemberDetailsService implements UserDetailsService{
	
	@Autowired
	private MemberDAO md;
	
	@Override
	public UserDetails loadUserByUsername(String mUsername) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		List<Member> lm = md.findMemberByUsername(mUsername);
		Optional<Member> om = lm.stream()
								  .filter(m -> m.getM_username().equals(mUsername))
								  .findFirst();
		if(!om.isPresent()) {
			throw new UsernameNotFoundException("Not found!");
		}
		
		String mAuthority = lm.get(0).getM_authority();
		String mPassword = lm.get(0).getM_password();
		return new User(mUsername, mPassword, createAuthorities(mAuthority));
	}
	
	private static final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
	private static final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");

	public static Collection<? extends GrantedAuthority> createAuthorities(String mAuthority) {
		if (mAuthority.contains("admin")) {
			return ADMIN_ROLES;
		}
		return USER_ROLES; 
	}

}
