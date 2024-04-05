package pl.niewadzj.moneyExchange.api.transfer.mapper;

import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.api.transfer.records.TransferResponse;
import pl.niewadzj.moneyExchange.entities.transfer.Transfer;

import java.util.function.Function;

@Service
public class TransferMapper implements Function<Transfer, TransferResponse> {
    @Override
    public TransferResponse apply(Transfer transfer) {
        return TransferResponse.builder()
                .id(transfer.getId())
                .providerAccountNumber(transfer.getProviderAccount().getAccountNumber())
                .receiverAccountNumber(transfer.getReceiverAccount().getAccountNumber())
                .providerCurrencyCode(transfer.getProviderCurrency().getCode())
                .receiverCurrencyCode(transfer.getReceiverCurrency().getCode())
                .currencyProvided(transfer.getCurrencyProvided())
                .currencyReceived(transfer.getCurrencyReceived())
                .exchangeRate(transfer.getExchangeRate())
                .transferDateTime(transfer.getTransferDateTime())
                .transferStatus(transfer.getTransferStatus())
                .build();
    }
}
