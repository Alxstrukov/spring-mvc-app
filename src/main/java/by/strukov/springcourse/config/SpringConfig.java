package by.strukov.springcourse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration//помечает класс как конфигурационный
@ComponentScan("by.strukov.springcourse")//указывает в каком пакете сканировать классы с бинами
@EnableWebMvc
/*@EnableWebMvc - эта аннотация включает инфраструктуру Spring MVC.
Регистрируются обработчики запросов (HandlerMappings).
Подключаются конвертеры и форматеры (например, преобразование String в Date).
Включается поддержка @Controller, @RestController, @RequestBody и т.д.
Настраивается сериализация JSON (через HttpMessageConverters).*/
@PropertySource("classpath:database.properties")//указывает из какого файла .properties считывать данные
public class SpringConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;
    private final Environment environment;
    //environment - объект этого класса предоставляет методы для чтения данных из файла .properties

    @Autowired
    public SpringConfig(ApplicationContext applicationContext, Environment environment) {
        this.applicationContext = applicationContext;
        this.environment = environment;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8"); // Важно!
        registry.viewResolver(resolver);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(environment.getProperty("driver"));
//environment.getProperty("driver") - если драйвер не будет найден, то приложение не будет работать
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.driver-class-name")));
        //(Objects.requireNonNull(environment.getProperty("driver"))); - если драйвер не будет найден,
        // то кинет исключение NullPointerException
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
/*        Важный момент: в файле database.properties название ключей обязательно нужно
            начинать с spring.datasource., иначе Spring не считывает данные из файла,
            либо переименовать файл в application.properties*/

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
