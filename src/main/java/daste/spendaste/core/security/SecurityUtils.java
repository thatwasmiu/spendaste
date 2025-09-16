package daste.spendaste.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

public class SecurityUtils {

    public static UserPrincipal getCurrentLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||authentication.getPrincipal() instanceof UserDetails)
            return new UserPrincipal(-1L, "internalSystem", "", Collections.emptyList());
        return (UserPrincipal) authentication.getPrincipal();
    }

    public static Long getCurrentLoginUserId() {
        return getCurrentLoginUser().getId();
    }

    public static String getCurrentLoginUsername() {
        return getCurrentLoginUser().getUsername();
    }

}
