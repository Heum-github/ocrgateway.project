package com.cjh.common.shared.utils;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class RedirectMessageUtils {
    public static void addErrorMessageAndRedirect(RedirectAttributes redirectAttributes, String errorMessage) {
        redirectAttributes.addFlashAttribute("error", errorMessage);
    }
}
