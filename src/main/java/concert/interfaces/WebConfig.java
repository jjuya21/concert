package concert.interfaces;

import concert.interceptor.ValidationTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ValidationTokenInterceptor validationTokenInterceptor;

    @Autowired
    public WebConfig(ValidationTokenInterceptor validationTokenInterceptor) {
        this.validationTokenInterceptor = validationTokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(validationTokenInterceptor)
                .addPathPatterns("/v1/api/**");
    }
}