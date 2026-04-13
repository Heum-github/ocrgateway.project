package com.cjh.domain.ocr.shared.config;
 
public class ApiURISetting {
    private String uri;
    private String loginUri;
    private boolean use;
 
    public boolean isUse() {
        return use;
    }
 
    public void setUse(boolean use) {
        this.use = use;
    }
 
    public String getUri() {
        return uri;
    }
 
    public void setUri(String uri) {
        this.uri = uri;
    }
 
    public String getLoginUri() {
        return loginUri;
    }

	public void setLoginUri(String loginUri) {
		this.loginUri = loginUri;
	}

	
}
