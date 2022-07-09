package ua.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Payload {

    private String issuer;
    private String subject;
    private List<String> audience;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
    private Date notBefore;
    private Map<String,String> claims;

    public String getIssuer() {
        return issuer;
    }
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getAudience() {
        return audience;
    }
    public void setAudience(List<String> audience) {
        this.audience = audience;
    }
    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }
    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
    public Date getNotBefore() {
        return notBefore;
    }
    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }
    public Map<String, String> getClaims() {
        return claims;
    }
    public void setClaims(Map<String, String> claims) {
        this.claims = claims;
    }
    public void setAudience(String... audienceStr) {
        List<String> audiences = new ArrayList<String>();
        for (String string : audienceStr) {
            audiences.add(string);
        }
        this.audience = audiences;
    }

}
