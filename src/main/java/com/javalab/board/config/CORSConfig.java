package com.javalab.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS 설정 클래스
 *  - 현재 서버가 아닌 다른 서버에서 요청시 CORS에 막혀서 응답을 못함
 *  - 다음과 같이 특정 도메인에서 오는 요청에 응답하도록 설정해주면 됨.
 *
 */
@Configuration
public class CORSConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://port-0-finalprojectfinal-20zynm2mlk26fzy4.sel4.cloudtype.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}