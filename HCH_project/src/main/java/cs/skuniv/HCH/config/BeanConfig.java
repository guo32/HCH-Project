package cs.skuniv.HCH.config;

import java.io.IOException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cs.skuniv.HCH.dao.CoffeeDao;
import cs.skuniv.HCH.dao.CommentDao;
import cs.skuniv.HCH.dao.FavoriteDao;
import cs.skuniv.HCH.dao.MachineDao;
import cs.skuniv.HCH.dao.MemberDao;
import cs.skuniv.HCH.service.CoffeeRegisterService;
import cs.skuniv.HCH.service.CoffeeSearchDetailService;
import cs.skuniv.HCH.service.CommentRegisterService;
import cs.skuniv.HCH.service.FavoriteService;
import cs.skuniv.HCH.service.MachineRegisterService;
import cs.skuniv.HCH.service.MemberLoginService;
import cs.skuniv.HCH.service.MemberRegisterService;

@Configuration
public class BeanConfig {

	/* 데이터베이스 */
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost/hch?characterEncoding=utf8&serverTimezone=UTC");
		ds.setUsername("root");
		ds.setPassword("1234");
		ds.setInitialSize(2);
		ds.setMaxActive(10);
		ds.setTestWhileIdle(true);
		ds.setMinEvictableIdleTimeMillis(60000 * 3);
		ds.setTimeBetweenEvictionRunsMillis(10 * 1000);
		
		return ds;
	}
	
	/* 파일 업로드 */
	public CommonsMultipartResolver multipartResolver() throws IOException {
		CommonsMultipartResolver mr = new CommonsMultipartResolver();
		
		mr.setMaxUploadSize(1024 * 1024 * 10);
		mr.setMaxUploadSizePerFile(1024 * 1024 * 2);
		mr.setMaxInMemorySize(1024 * 1024);
		mr.setUploadTempDir(new FileSystemResource("C:\\upload"));
		mr.setDefaultEncoding("utf-8");
		
		return mr;
	}
	
	/* 회원 */
	@Bean
	public MemberDao memberDao() {
		return new MemberDao(dataSource());
	}
	
	@Bean
	public MemberRegisterService memberRegSvc() {
		return new MemberRegisterService(memberDao());
	}
	
	@Bean
	public MemberLoginService memberLoginSvc() {
		return new MemberLoginService(memberDao());
	}
	
	/* 커피 원두 */
	@Bean
	public CoffeeDao coffeeDao() {
		return new CoffeeDao(dataSource());
	}
	
	@Bean
	public CoffeeRegisterService coffeeRegSvc() {
		return new CoffeeRegisterService(coffeeDao());
	}
	
	@Bean
	public CoffeeSearchDetailService coffeeSearchSvc() {
		return new CoffeeSearchDetailService(coffeeDao());
	}
	
	/* 가전 */
	@Bean
	public MachineDao machineDao() {
		return new MachineDao(dataSource());
	}
	
	@Bean
	public MachineRegisterService machineRegSvc() {
		return new MachineRegisterService(machineDao());
	}
	
	/* 댓글 */
	@Bean
	public CommentDao commentDao() {
		return new CommentDao(dataSource());
	}
	
	@Bean
	public CommentRegisterService commentRegisterService() {
		return new CommentRegisterService(commentDao());
	}
	
	/* 관심 */
	@Bean
	public FavoriteDao favoriteDao() {
		return new FavoriteDao(dataSource());
	}
	
	@Bean
	public FavoriteService favoriteSvc() {
		return new FavoriteService(favoriteDao());
	}
	
}
