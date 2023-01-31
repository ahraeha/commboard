package com.jjez.commboard.user.service.impl;

import com.jjez.commboard.components.MailComponents;
import com.jjez.commboard.user.entity.User;
import com.jjez.commboard.user.model.UserInput;
import com.jjez.commboard.user.repository.UserRepository;
import com.jjez.commboard.user.service.UserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final MailComponents mailComponents;

  @Override
  public boolean register(UserInput parameter) {

    Optional<User> optionalUser =
        userRepository.findByEmail(parameter.getEmail());
    if (optionalUser.isPresent()) {
      return false;
    }

    String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());

    String uuid = UUID.randomUUID().toString();

    User user = User.builder()
        .email(parameter.getEmail())
        .name(parameter.getName())
        .password(encPassword)
        .birth(parameter.getBirth())
        .created_at(LocalDateTime.now())
        .emailAuthYn(false)
        .emailAuthKey(uuid)
        .build();

    userRepository.save(user);

    String email = parameter.getEmail();
    String subject = "commboard 사이트 가입을 축하드립니다.";
    String text = "<p>commboard 사이트 가입을 축하드립니다.<p><p>아래 링크를 클릭하셔서 가입을 완료 하세요.</p>"
        + "<div><a target='_black' href='http://localhost:8080/user/?id=" + uuid
        + "'> 가입 완료 </a></div>";
    mailComponents.sendMail(email, subject, text);

    return true;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (!optionalUser.isPresent()) {
      throw new RuntimeException("회원 정보가 존재하지 않습니다.");
    }

    User member = optionalUser.get();

    if (!member.isEmailAuthYn()) {
      throw new RuntimeException("이메일 활성화 이후에 로그인을 해주세요.");
    }
    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

    if (member.isAdminYn()) {
      grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    return new org.springframework.security.core.userdetails.User(member.getEmail(), member.getPassword(), grantedAuthorities);
  }
}
