package cn.xz.property.filter;

import cn.xz.property.util.R;
import cn.xz.property.util.UserHolder;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 检查用户是否完成登录，也就是登录状态
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        Long empID = (Long) request.getSession().getAttribute("employee");
        String adminID = (String) request.getSession().getAttribute("admin");
        String header = request.getHeader("authorization");
        log.info("authorization={}",header);
        //1、获取本次请求的URI
        String requestURI = request.getRequestURI();
        log.info("拦截到请求，{}",requestURI);
        String[] urls=new String[]{
            "/backend/**",
            "/front/**",
            "/common/**",
             "/login/**",
             "/room/**",
             "/parking/**",
             "/payment-type/**",
             "/payment/**",
             "/userStatistic/**",
             "/notice/**",
             "/repair/**",
             "/user/**",
             "/statistic/**",
             "/complaint/**",
              "/building/**",
              "/unit/**",
             "/user/login",
              //对接口文件页面访问实行不过滤
             "/doc.html",
              "/webjars/**",
              "/swagger-resources",
              "/v2/api-docs"
        };
        //2、判断本次请求是否需要处理
        boolean check=check(urls,requestURI);

        //3、如果不需要处理，则直接返回
        if (check){
            filterChain.doFilter(request,response);
            return;
        }

        //4、判断登录状态，如果已经登录，则直接放行
        if(adminID!=null){

            //设置当前线程的线程局部变量的值（用户id)
            UserHolder.saveUser(Long.getLong(adminID));

            filterChain.doFilter(request,response);
            return;
        }

        //5、如果未登录则返回登录结果,通过输出流的方式来给客户端响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    private boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}
