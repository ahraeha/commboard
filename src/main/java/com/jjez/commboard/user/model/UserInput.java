package com.jjez.commboard.user.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class UserInput {

  private String email;
  private String name;
  private String password;
  private String birth;

}
