package com.nlthanhcse.facebook.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<String> roles;
}
