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
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${codetracker.repository.local.path}")
    private String repositoryLocalPath;
    @Value("${codetracker.repository.github.path}")
    private String repositoryGithubPath;

    private final static String FOLDER_TO_CLONE = "tmp/";

    public static void main(String[] args){
        SpringApplication.run(CryptographyApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("admin")
                .password("Asdfgh123456")
                .roles("ADMIN")
                .build();
        userDetailManager.createUser(userDetails);


        GitService gitService = new GitServiceImpl();
        // METHOD TRACKING EXAMPLE
        try{

            try (Repository repository = gitService.cloneIfNotExists(repositoryLocalPath,
                    repositoryGithubPath)){

                MethodTracker methodTracker = CodeTracker.methodTracker()
                        .repository(repository)
                        .filePath("CryptoServer/src/main/java/com/cryptography/service/CryptoService.java")
//                        .startCommitId("d0d9edcfb611ee35978938f7e35250f1ae982057")
                        .startCommitId("8e0ada4854818a764d337d40c25f6060aa8ea1b2")
                        .methodName("ceateUser")
                        .methodDeclarationLineNumber(43)
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
//            try (Repository repository = gitService.cloneIfNotExists(FOLDER_TO_CLONE + "checkstyle\\checkstyle",
//                    "https://github.com/checkstyle/checkstyle.git")){
//
//                MethodTracker methodTracker = CodeTracker.methodTracker()
//                        .repository(repository)
//                        .filePath("src/main/java/com/puppycrawl/tools/checkstyle/Checker.java")
//                        .startCommitId("119fd4fb33bef9f5c66fc950396669af842c21a3")
//                        .methodName("fireErrors")
//                        .methodDeclarationLineNumber(384)
//                        .build();
//
//                History<Method> methodHistory = methodTracker.track();
//
//                for (History.HistoryInfo<Method> historyInfo : methodHistory.getHistoryInfoList()) {
//                    System.out.println("======================================================");
//                    System.out.println("Commit ID: " + historyInfo.getCommitId());
//                    System.out.println("Date: " +
//                            LocalDateTime.ofEpochSecond(historyInfo.getCommitTime(), 0, ZoneOffset.UTC));
//                    System.out.println("Before: " + historyInfo.getElementBefore().getName());
//                    System.out.println("After: " + historyInfo.getElementAfter().getName());
//
//                    for (Change change : historyInfo.getChangeList()) {
//                        System.out.println(change.getType().getTitle() + ": " + change);
//                    }
//                }
//                System.out.println("======================================================");
//            }
//
        }
        catch (Exception ex){
            throw ex;
        }
    }
}
