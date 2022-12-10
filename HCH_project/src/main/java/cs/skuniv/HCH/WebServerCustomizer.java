package cs.skuniv.HCH;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

	@Override
	public void customize(ConfigurableWebServerFactory factory) {
		/* 404 에러 페이지 등록 */
		ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-404");
		/* 내부 서버 에러 : 500 에러 페이지 등록 */
		ErrorPage error500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-500");
		/* 실행 중 예외 발생 : 500 에러 페이지 등록 */
		ErrorPage errorRunTime = new ErrorPage(RuntimeException.class, "/error-500");
		
		factory.addErrorPages(error404, error500, errorRunTime);
	}
	
}
