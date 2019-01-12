package org.roorkee.rkerestapi.controller;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MessageRequest {

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
