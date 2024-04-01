package pl.niewadzj.moneyExchange.api.currencyAccount;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.niewadzj.moneyExchange.api.currencyAccount.interfaces.CurrencyAccountController;
import pl.niewadzj.moneyExchange.api.currencyAccount.interfaces.CurrencyAccountService;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.BalanceResponse;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.CurrencyAccountResponse;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.TransactionRequest;
import pl.niewadzj.moneyExchange.entities.user.User;

import static pl.niewadzj.moneyExchange.api.currencyAccount.constants.CurrencyAccountMappings.ACTIVATE_SUSPENDED_ACCOUNT_MAPPING;
import static pl.niewadzj.moneyExchange.api.currencyAccount.constants.CurrencyAccountMappings.CURRENCY_ACCOUNT_MAPPING;
import static pl.niewadzj.moneyExchange.api.currencyAccount.constants.CurrencyAccountMappings.DEPOSIT_MAPPING;
import static pl.niewadzj.moneyExchange.api.currencyAccount.constants.CurrencyAccountMappings.GET_CURRENCY_ACCOUNT;
import static pl.niewadzj.moneyExchange.api.currencyAccount.constants.CurrencyAccountMappings.SUSPEND_ACCOUNT_MAPPING;
import static pl.niewadzj.moneyExchange.api.currencyAccount.constants.CurrencyAccountMappings.WITHDRAW_MAPPING;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(CURRENCY_ACCOUNT_MAPPING)
public class CurrencyAccountControllerImpl implements CurrencyAccountController {

    private final CurrencyAccountService currencyAccountService;

    @Override
    @PutMapping(DEPOSIT_MAPPING)
    public final BalanceResponse depositToAccount(@RequestBody @Valid TransactionRequest transactionRequest,
                                                  @AuthenticationPrincipal User user) {
        return currencyAccountService.depositToAccount(transactionRequest, user);
    }

    @Override
    @PutMapping(WITHDRAW_MAPPING)
    public final BalanceResponse withdrawFromAccount(@RequestBody @Valid TransactionRequest transactionRequest,
                                                     @AuthenticationPrincipal User user) {
        return currencyAccountService.withdrawFromAccount(transactionRequest, user);
    }

    @Override
    @GetMapping(GET_CURRENCY_ACCOUNT)
    public final CurrencyAccountResponse getCurrencyAccountByCurrencyId(@RequestParam Long currencyId,
                                                                        @AuthenticationPrincipal User user) {
        return currencyAccountService.getCurrencyAccountByCurrencyId(currencyId, user);
    }

    @Override
    @PatchMapping(SUSPEND_ACCOUNT_MAPPING)
    public final void suspendCurrencyAccount(@RequestParam Long currencyId, @AuthenticationPrincipal User user) {
        currencyAccountService.suspendCurrencyAccount(currencyId, user);
    }

    @Override
    @PatchMapping(ACTIVATE_SUSPENDED_ACCOUNT_MAPPING)
    public final void activateSuspendedCurrencyAccount(@RequestParam Long currencyId, @AuthenticationPrincipal User user) {
        currencyAccountService.activateSuspendedCurrencyAccount(currencyId, user);
    }


}
