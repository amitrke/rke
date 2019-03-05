package org.roorkee.rkerestapi;

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.roorkee.rkerestapi.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter implements Filter {

    @Autowired
    private CacheService cacheService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String tokenHdr = req.getHeader("idtoken");
        if (req.getMethod().equals("OPTIONS")
                || !req.getRequestURI().contains("/api/")
                || !req.getRequestURI().contains("/api/articles/search")
                || !req.getRequestURI().contains("/api/photogallery/")
                || (StringUtils.isNotEmpty(tokenHdr) && cacheService.get(DigestUtils.md5Hex(tokenHdr)) != null)){
            chain.doFilter(request, response);
        }
        else{
            res.setStatus(401);
        }
    }

    @Override
    public void destroy() {

    }
}
