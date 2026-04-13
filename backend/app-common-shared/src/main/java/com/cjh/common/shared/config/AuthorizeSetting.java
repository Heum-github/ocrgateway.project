package com.cjh.common.shared.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "authorize")
@Component
@Getter	@Setter
public class AuthorizeSetting {

    private String type;
    private User user = new User();

    public static class User {
        private Password password = new Password();

        public Password getPassword() {
            return password;
        }

        public void setPassword(Password password) {
            this.password = password;
        }

        public static class Password {
            private String defaultPassword;
            private Integer length;
            private Boolean hasSymbol;
            private Boolean duplicateWithId;
            private Integer maxContinuousLength;
            private Integer changeDays;
            private Boolean reuseCheck;
            private Integer errorMaxCount;

            public String getDefaultPassword() {
                return defaultPassword;
            }

            public void setDefaultPassword(String defaultPassword) {
                this.defaultPassword = defaultPassword;
            }

            public Integer getLength() {
                return length;
            }

            public void setLength(Integer length) {
                this.length = length;
            }

            public Boolean getHasSymbol() {
                return hasSymbol;
            }

            public void setHasSymbol(Boolean hasSymbol) {
                this.hasSymbol = hasSymbol;
            }

            public Boolean getDuplicateWithId() {
                return duplicateWithId;
            }

            public void setDuplicateWithId(Boolean duplicateWithId) {
                this.duplicateWithId = duplicateWithId;
            }

            public Integer getMaxContinuousLength() {
                return maxContinuousLength;
            }

            public void setMaxContinuousLength(Integer maxContinuousLength) {
                this.maxContinuousLength = maxContinuousLength;
            }

            public Integer getChangeDays() {
                return changeDays;
            }

            public void setChangeDays(Integer changeDays) {
                this.changeDays = changeDays;
            }

            public Boolean getReuseCheck() {
                return reuseCheck;
            }

            public void setReuseCheck(Boolean reuseCheck) {
                this.reuseCheck = reuseCheck;
            }

            public Integer getErrorMaxCount() {
                return errorMaxCount;
            }

            public void setErrorMaxCount(Integer errorMaxCount) {
                this.errorMaxCount = errorMaxCount;
            }
        }
	}
}
