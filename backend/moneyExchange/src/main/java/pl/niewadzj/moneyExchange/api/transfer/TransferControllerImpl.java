package pl.niewadzj.moneyExchange.api.transfer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.niewadzj.moneyExchange.api.transfer.interfaces.TransferController;
import pl.niewadzj.moneyExchange.api.transfer.interfaces.TransferService;
import pl.niewadzj.moneyExchange.api.transfer.records.TransferRequest;
import pl.niewadzj.moneyExchange.api.transfer.records.TransferResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

import static pl.niewadzj.moneyExchange.api.transfer.constants.TransferMappings.MAKE_TRANSFER_MAPPING;
import static pl.niewadzj.moneyExchange.api.transfer.constants.TransferMappings.TRANSFER_MAPPING;

@RestController
@RequiredArgsConstructor
@RequestMapping(TRANSFER_MAPPING)
public class TransferControllerImpl implements TransferController {

    private final TransferService transferService;

    @Override
    @PostMapping(MAKE_TRANSFER_MAPPING)
    public final TransferResponse makeTransfer(
            @RequestBody TransferRequest transferRequest,
            @AuthenticationPrincipal User user) {
        return transferService.makeTransfer(transferRequest, user);
    }
}
