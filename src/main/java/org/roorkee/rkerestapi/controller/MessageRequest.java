package org.roorkee.rkerestapi.controller;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MessageRequest {

    @NotNull
    private Long fromUserId;

    @NotBlank
    private String toEmail;

    @NotBlank
    private String toName;

    @NotBlank
    private String subject;

    @NotBlank
    private String textBody;

    @NotBlank
    private String htmlBody;

}
