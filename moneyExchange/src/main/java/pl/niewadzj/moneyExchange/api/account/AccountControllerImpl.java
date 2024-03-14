package pl.niewadzj.moneyExchange.api.account;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.niewadzj.moneyExchange.api.account.interfaces.AccountController;
import pl.niewadzj.moneyExchange.api.account.interfaces.AccountService;
import pl.niewadzj.moneyExchange.api.account.records.BalanceResponse;
import pl.niewadzj.moneyExchange.api.account.records.TopUpRequest;
import pl.niewadzj.moneyExchange.entities.user.User;

import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.ACCOUNT_MAPPING;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.TOP_UP_MAPPING;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ACCOUNT_MAPPING)
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @Override
    @PutMapping(TOP_UP_MAPPING)
    public BalanceResponse topUpAccount(@RequestBody @Valid TopUpRequest topUpRequest,
                                        @AuthenticationPrincipal User user) {
        return accountService.topUpAccount(topUpRequest, user);
    }


}
