package com.BankingAppSpringBoot.BankingApp.dto;

import java.math.BigDecimal;

public class TransferRequest {


    private String senderRib;
    private String receiverRib;
    private BigDecimal amount;

    public TransferRequest() {}

    public TransferRequest(String senderRib, String receiverRib, BigDecimal amount) {
        this.senderRib = senderRib;
        this.receiverRib = receiverRib;
        this.amount = amount;
    }

    public String getSenderRib() {
        return senderRib;
    }

    public void setSenderRib(String senderRib) {
        this.senderRib = senderRib;
    }

    public String getReceiverRib() {
        return receiverRib;
    }

    public void setReceiverRib(String receiverRib) {
        this.receiverRib = receiverRib;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
