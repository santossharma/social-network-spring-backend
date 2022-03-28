package com.socialnetwork.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SignUpDTO {
    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String login;

    @NotEmpty
    private char[] password;

}
