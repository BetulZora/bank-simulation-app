package com.cydeo;

import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class BankSimulationAppApplication {

    public static void main(String[] args) {

        //using the container temporarily before we develop the front end
        ApplicationContext container = SpringApplication.run(BankSimulationAppApplication.class, args);


        // get beans for account and transaction services
        AccountService accountService = container.getBean(AccountService.class);
        TransactionService transactionService = container.getBean(TransactionService.class);






        // create sender and receiver account
      //  AccountDTO sender = accountService.createNewAccount(BigDecimal.valueOf(70), new Date(), AccountType.CHECKING, 2L);
       // AccountDTO sender1 = accountService.createNewAccount(BigDecimal.valueOf(70), new Date(), AccountType.CHECKING, 1L);
    //    AccountDTO receiver = accountService.createNewAccount(BigDecimal.valueOf(50), new Date(), AccountType.SAVING, 2L);
     //   AccountDTO receiver2 = accountService.createNewAccount(BigDecimal.valueOf(50), new Date(), AccountType.CHECKING, 3L);

        /*
        accountService.listAllAccounts().forEach(System.out::println);

        transactionService.makeTransfer(sender, receiver, BigDecimal.valueOf(10), new Date(), "Initial Transfer");

        System.out.println(transactionService.findAllTransaction().get(0));
        accountService.listAllAccounts().forEach(System.out::println);
*/
    }

    //since ModelMapper class is not our class we have to explicitly configure in our Spring configuration class
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
