package com.securitystart2.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Role based redirection configuration
 *
 * Created by mesut on 28.8.2016.
 */
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RedirectStrategy strategy = new DefaultRedirectStrategy();

    public RedirectStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(RedirectStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetURL = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            System.out.println("Can not redirect!");
            return;
        }

        strategy.sendRedirect(request, response, targetURL);
    }

    /**
     * This method extracts the roles of currently logged-in user and returns
     * appropriate URL according to user role.
     * @param authentication {@link Authentication} instance.
     * @return Role based target url.
     */
    private String determineTargetUrl(Authentication authentication) {
        String url = "";
        List<String> roles = new ArrayList<String>();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }

        if (isDba(roles))
            url = "/dba";
        else if (isAdmin(roles))
            url = "/admin";
        else if (isUser(roles))
            url = "/home";
        else
            url = "/Access_Denied";

        return url;
    }

    private boolean isUser(List<String> roles) {
        if (roles.contains("ROLE_USER"))
            return true;
        return false;
    }

    private boolean isAdmin(List<String> roles) {
        if (roles.contains("ROLE_ADMIN"))
            return true;
        return false;
    }

    private boolean isDba(List<String> roles) {
        if (roles.contains("ROLE_DBA"))
            return true;
        return false;
    }
}
