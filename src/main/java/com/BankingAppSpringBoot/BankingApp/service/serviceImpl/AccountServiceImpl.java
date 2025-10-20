package com.BankingAppSpringBoot.BankingApp.service.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.BankingAppSpringBoot.BankingApp.dto.AccountDto;
import com.BankingAppSpringBoot.BankingApp.entity.Account;
import com.BankingAppSpringBoot.BankingApp.entity.Transaction;
import com.BankingAppSpringBoot.BankingApp.entity.User;
import com.BankingAppSpringBoot.BankingApp.entity.Transaction.TransactionType;
import com.BankingAppSpringBoot.BankingApp.exception.BadRequestException;
import com.BankingAppSpringBoot.BankingApp.exception.ResourceNotFoundException;
import com.BankingAppSpringBoot.BankingApp.mapper.AccountMapper;
import com.BankingAppSpringBoot.BankingApp.repository.AccountRepository;
import com.BankingAppSpringBoot.BankingApp.repository.TransactionRepository;
import com.BankingAppSpringBoot.BankingApp.repository.UserRepository;
import com.BankingAppSpringBoot.BankingApp.service.AccountService;
import com.twilio.base.Resource;

import jakarta.transaction.Transactional;

@Service  // ✅ This is what makes it a Spring Bean
public class AccountServiceImpl  implements AccountService{

    private AccountRepository accountRepository;
    private UserRepository userRepository;
    private TransactionRepository transactionRepository;
    private final SmsService   smsService;

    public AccountServiceImpl( AccountRepository accountRepository, UserRepository userRepository,  TransactionRepository transactionRepository, SmsService   smsService){
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.smsService=smsService;

    }

    @Override
    public AccountDto createAccount(AccountDto accountDto){


        User user = userRepository.findById(accountDto.getUserId())
                                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + accountDto.getUserId()));



        Account account = AccountMapper.toEntity(accountDto);
        account.setUser(user);

        try{
        accountRepository.save(account);
        }catch(Exception e){
            throw new BadRequestException("Failed to create account : " + e.getMessage());

        }
        

        return AccountMapper.toDto(account);
    }

    @Override
    public AccountDto getAccountById(Long id){
        Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " +id));
        return AccountMapper.toDto(account);
        
    }

    @Override
    public void deleteAccount(Long id){
        Account account = accountRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " +id));

        try{
        accountRepository.delete(account);
        }catch(Exception e){
            throw new BadRequestException("Failed to delete account: " + e.getMessage());
        }
    }

    @Override
    public List<AccountDto> getAllAccounts(){
        List<AccountDto> accounts = accountRepository.findAll().stream()
        .map(AccountMapper::toDto) // works because toDto is static
        .collect(Collectors.toList());
        return accounts;
    }


    @Transactional
    public AccountDto withDraw(String rib, BigDecimal amount){

        Account account = accountRepository.findByRib(rib)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found with rib " + rib));
        
        if(account.getBalance().compareTo(amount) < 0 ){
            throw new BadRequestException("Insufficient balance");
        }

        try{
        account.setBalance(account.getBalance().subtract(amount));
        Transaction tx  = new Transaction();

        tx.setAccount(account);
        
        tx.setAmount(amount);
        tx.setType(TransactionType.WITHDRAW);
        tx.setTimestamp(LocalDateTime.now());
        transactionRepository.save(tx);
        accountRepository.save(account);

        String message = String.format(
            "Dear %s, %.2f TND has been withdrawn from your account %s. Remaining balance: %.2f TND.",
            account.getUser().getFullName(),
            amount,
            account.getRib(),
            account.getBalance()
        );
       smsService.sendSms(account.getUser().getPhoneNumber(), message);
    }catch(Exception e){
        throw new BadRequestException("Failed to process withdraw: " + e.getMessage());

    }


        return AccountMapper.toDto(account);
    }

    @Transactional
    public AccountDto deposit(String rib , BigDecimal amount){
     
        Account account = accountRepository.findByRib(rib)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found with rib " + rib));
        

                            try{
        account.setBalance(account.getBalance().add(amount));   
        Transaction tx = new Transaction();

        tx.setAccount(account);
        tx.setAmount(amount);
        tx.setType(TransactionType.DEPOSIT);
        tx.setTimestamp(LocalDateTime.now());
        transactionRepository.save(tx);

        accountRepository.save(account);

        String message = String.format(
            "Dear %s, %.2f TND has been deposit to your account %s. your  balance: %.2f TND.",
            account.getUser().getFullName(),
            amount,
            rib,
            account.getBalance()
        );

        smsService.sendSms(account.getUser().getPhoneNumber(), message);

                            }catch (Exception e) {
                                throw new BadRequestException("Failed to process deposit: " + e.getMessage());
                            }
        return AccountMapper.toDto(account);
    }

    @Transactional
    public void transferMoney(String senderRib, String receiverRib, BigDecimal amount){

        Account senderAc = accountRepository.findByRib(senderRib)
        .orElseThrow(() -> new ResourceNotFoundException("sender  not found with rib " + senderRib));



        Account receiverAc = accountRepository.findByRib(receiverRib)
                                    .orElseThrow(() -> new ResourceNotFoundException("Receiver  not found with rib " + receiverRib));
                        

        if(senderAc.getBalance().compareTo(amount) <0 ){
            throw new BadRequestException("Sender has insufficient balance");
        }

        try{
        Transaction sendTran = new Transaction();
        Transaction receivTran = new Transaction();

        
        senderAc.setBalance(senderAc.getBalance().subtract(amount));
        receiverAc.setBalance(receiverAc.getBalance().add(amount));
        
        accountRepository.save(senderAc);
        accountRepository.save(receiverAc);

        sendTran.setAccount(senderAc);
        sendTran.setAmount(amount);
        sendTran.setType(TransactionType.WITHDRAW);
        sendTran.setTimestamp(LocalDateTime.now());
        transactionRepository.save(sendTran);

        receivTran.setAccount(receiverAc);
        receivTran.setAmount(amount);
        receivTran.setType(TransactionType.DEPOSIT);
        receivTran.setTimestamp(LocalDateTime.now());
        transactionRepository.save(receivTran);

        }catch (Exception e) {
            throw new BadRequestException("Failed to transfer money: " + e.getMessage());
        }
    }




}
