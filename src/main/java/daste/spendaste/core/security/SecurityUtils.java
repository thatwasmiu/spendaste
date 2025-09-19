package daste.spendaste.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

public class SecurityUtils {

    public static UserPrincipal getCurrentLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails))
            return new UserPrincipal(-1L, "internalSystem", "", Collections.emptyList());
        return (UserPrincipal) authentication.getPrincipal();
    }

    public static Long getUserId() {
        return getCurrentLoginUser().getId();
    }

    public static String getUsername() {
        return getCurrentLoginUser().getUsername();
    }

    public static String getTenant() {
        return getCurrentLoginUser().getTenant();
    }

}
