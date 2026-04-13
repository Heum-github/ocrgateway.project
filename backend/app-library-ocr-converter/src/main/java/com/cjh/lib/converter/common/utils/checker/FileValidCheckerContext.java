package com.cjh.lib.converter.common.utils.checker;

public class FileValidCheckerContext {
    private FileValidChecker checker;

    public void setChecker(FileValidChecker checker) {
        this.checker = checker;
    }

    public Boolean executeCheck(String filePath) {
        if (checker == null) {
            return false;
        }
        return checker.isValid(filePath);
    }
}
