package org.delcom.ifs25043springbootcashflow.config;

import java.nio.file.Path;
import javax.sql.DataSource;
import org.delcom.ifs25043springbootcashflow.adapter.presenter.FinancePresenter;
import org.delcom.ifs25043springbootcashflow.adapter.repository.JdbcTransactionRepository;
import org.delcom.ifs25043springbootcashflow.adapter.repository.JdbcUserRepository;
import org.delcom.ifs25043springbootcashflow.adapter.repository.SchemaInitializer;
import org.delcom.ifs25043springbootcashflow.adapter.storage.FileStorage;
import org.delcom.ifs25043springbootcashflow.domain.repository.ITransactionRepository;
import org.delcom.ifs25043springbootcashflow.domain.repository.IUserRepository;
import org.delcom.ifs25043springbootcashflow.usecase.AuthUseCase;
import org.delcom.ifs25043springbootcashflow.usecase.FinanceUseCase;
import org.delcom.ifs25043springbootcashflow.usecase.UserUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** Composition root: seluruh wiring dependency ada di sini, bukan tersebar di view. */
@Configuration
public class AppConfig {

    @Bean
    public IUserRepository userRepository(DataSource dataSource) {
        SchemaInitializer.initialize(dataSource);
        return new JdbcUserRepository(dataSource);
    }

    @Bean
    public ITransactionRepository transactionRepository(DataSource dataSource, IUserRepository users) {
        // parameter users memastikan tabel users dibuat lebih dulu (FK transactions -> users)
        return new JdbcTransactionRepository(dataSource);
    }

    @Bean
    public AuthUseCase authUseCase(IUserRepository users) {
        return new AuthUseCase(users);
    }

    @Bean
    public UserUseCase userUseCase(IUserRepository users) {
        return new UserUseCase(users);
    }

    @Bean
    public FinanceUseCase financeUseCase(ITransactionRepository transactions) {
        return new FinanceUseCase(transactions);
    }

    @Bean
    public FinancePresenter financePresenter() {
        return new FinancePresenter();
    }

    @Bean
    public FileStorage fileStorage(@Value("${app.upload-dir}") String uploadDir) {
        return new FileStorage(uploadDir);
    }

    @Bean
    public ApplicationRunner seedRootAdmin(
            AuthUseCase authUseCase,
            @Value("${app.root-username}") String rootUsername,
            @Value("${app.root-password}") String rootPassword) {
        return args -> authUseCase.ensureRootAdmin(rootUsername, rootPassword);
    }

    @Bean
    public WebMvcConfigurer uploadsResourceHandler(FileStorage fileStorage) {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                Path root = fileStorage.root();
                registry.addResourceHandler("/uploads/**")
                        .addResourceLocations(root.toUri().toString());
            }
        };
    }
}
