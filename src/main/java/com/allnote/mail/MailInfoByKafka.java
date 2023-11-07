package com.allnote.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MailInfoByKafka {
    private String subject;
    private String recipient;
    private Map<String, Object> contextMap;
    private String templateName;
}
