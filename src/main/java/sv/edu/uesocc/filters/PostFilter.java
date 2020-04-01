package sv.edu.uesocc.filters;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;
import javax.servlet.http.Cookie;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import io.micrometer.core.instrument.util.IOUtils;

public class PostFilter extends ZuulFilter {
 
  @Override
  public String filterType() {
    return "post";
  }
 
  @Override
  public int filterOrder() {
    return 1;
  }
 
  @Override
  public boolean shouldFilter() {
    return true;
  }
  
  private ObjectMapper mapper = new ObjectMapper();
 
//  @Override
//  public Object run() {
//   System.out.println("Inside Response Filter");
// 
//    return null;
//  }
  

  @Override
  public Object run() {
      RequestContext ctx = RequestContext.getCurrentContext();
      try {
          InputStream is = ctx.getResponseDataStream();
          String responseBody = IOUtils.toString(is, Charset.forName("UTF8"));
          if (responseBody.contains("refresh_token")) {
              Map<String, Object> responseMap = mapper.readValue(
                responseBody, new TypeReference<Map<String, Object>>() {});
              String refreshToken = responseMap.get("refresh_token").toString();
              responseMap.remove("refresh_token");
              responseBody = mapper.writeValueAsString(responseMap);

              Cookie cookie = new Cookie("refreshToken", refreshToken);
              cookie.setHttpOnly(true);
              cookie.setSecure(true);
              cookie.setPath(ctx.getRequest().getContextPath() + "/oauth/token");
              cookie.setMaxAge(2592000); // 30 days
              ctx.getResponse().addCookie(cookie);
          }
          ctx.setResponseBody(responseBody);
      } catch (IOException e) {
          System.out.println("Error occured in zuul post filter"+ e);
      }
      return null;
  }
  
}