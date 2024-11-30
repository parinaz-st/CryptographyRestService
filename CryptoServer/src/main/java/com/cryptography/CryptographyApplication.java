package com.cryptography;

import com.cryptography.config.CertificateFieldsConfig;
import com.cryptography.config.CustomUserDetailManagerImpl;
import com.cryptography.repository.UserRepository;
import org.codetracker.api.CodeTracker;
import org.codetracker.api.History;
import org.codetracker.api.MethodTracker;
import org.codetracker.change.Change;
import org.codetracker.element.Method;
import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitService;
import org.refactoringminer.util.GitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@SpringBootApplication
@EnableConfigurationProperties(CertificateFieldsConfig.class)
public class CryptographyApplication implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CustomUserDetailManagerImpl userDetailManager;
    private final static String FOLDER_TO_CLONE = "tmp/";

    public static void main(String[] args){
        SpringApplication.run(CryptographyApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
//        UserDetails userDetails = org.springframework.security.core.userdetails.User
//                .withUsername("admin")
//                .password("Asdfgh123456")
//                .roles("ADMIN")
//                .build();
//        userDetailManager.createUser(userDetails);


        GitService gitService = new GitServiceImpl();
        // METHOD TRACKING EXAMPLE
        try{

            try (Repository repository = gitService.cloneIfNotExists("E:\\Sattarzadeh\\Codes\\github\\CryptoService-Refactored",
                    "https://github.com/parinaz-st/CryptographyRestService.git")){

                MethodTracker methodTracker = CodeTracker.methodTracker()
                        .repository(repository)
                        .filePath("src/main/java/com/cryptography/service/CryptoService.java")
                        .startCommitId("46807692320c832f023259a5ec5b9056b9d5dfac")
                        .methodName("ceateUser")
                        .methodDeclarationLineNumber(28)
                        .build();

                History<Method> methodHistory = methodTracker.track();

                for (History.HistoryInfo<Method> historyInfo : methodHistory.getHistoryInfoList()) {
                    System.out.println("======================================================");
                    System.out.println("Commit ID: " + historyInfo.getCommitId());
                    System.out.println("Date: " +
                            LocalDateTime.ofEpochSecond(historyInfo.getCommitTime(), 0, ZoneOffset.UTC));
                    System.out.println("Before: " + historyInfo.getElementBefore().getName());
                    System.out.println("After: " + historyInfo.getElementAfter().getName());

                    for (Change change : historyInfo.getChangeList()) {
                        System.out.println(change.getType().getTitle() + ": " + change);
                    }
                }
                System.out.println("======================================================");
            }
        }
        catch (Exception ex){
            throw ex;
        }
    }
}
