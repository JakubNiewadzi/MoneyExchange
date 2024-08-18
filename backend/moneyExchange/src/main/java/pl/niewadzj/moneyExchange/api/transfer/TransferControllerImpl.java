package pl.niewadzj.moneyExchange.api.transfer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.niewadzj.moneyExchange.api.transfer.interfaces.TransferController;
import pl.niewadzj.moneyExchange.api.transfer.interfaces.TransferService;
import pl.niewadzj.moneyExchange.api.transfer.records.MakeTransferResponse;
import pl.niewadzj.moneyExchange.api.transfer.records.TransferHistoryResponse;
import pl.niewadzj.moneyExchange.api.transfer.records.TransferRequest;
import pl.niewadzj.moneyExchange.api.transfer.records.TransferResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

import java.util.List;

import static pl.niewadzj.moneyExchange.api.transfer.constants.TransferConstants.PAGE_NUMBER;
import static pl.niewadzj.moneyExchange.api.transfer.constants.TransferConstants.PAGE_SIZE;
import static pl.niewadzj.moneyExchange.api.transfer.constants.TransferMappings.GET_TRANSFERS_FOR_PROVIDER;
import static pl.niewadzj.moneyExchange.api.transfer.constants.TransferMappings.GET_TRANSFERS_FOR_RECEIVER;
import static pl.niewadzj.moneyExchange.api.transfer.constants.TransferMappings.GET_TRANSFERS_FOR_USER;
import static pl.niewadzj.moneyExchange.api.transfer.constants.TransferMappings.MAKE_TRANSFER_MAPPING;
import static pl.niewadzj.moneyExchange.api.transfer.constants.TransferMappings.TRANSFER_MAPPING;

@RestController
@RequiredArgsConstructor
@RequestMapping(TRANSFER_MAPPING)
public class TransferControllerImpl implements TransferController {

    private final TransferService transferService;

    @Override
    @PostMapping(MAKE_TRANSFER_MAPPING)
    public final MakeTransferResponse makeTransfer(@RequestBody TransferRequest transferRequest,
                                                   @AuthenticationPrincipal User user) {
        return transferService.makeTransfer(transferRequest, user);
    }

    @Override
    @GetMapping(GET_TRANSFERS_FOR_USER)
    public final TransferHistoryResponse getTransfersForUser(@RequestParam(defaultValue = PAGE_NUMBER, required = false) int pageNo,
                                                             @RequestParam(defaultValue = PAGE_SIZE, required = false) int pageSize,
                                                             @AuthenticationPrincipal User user) {
        return transferService.getTransfersForUser(pageNo, pageSize, user);
    }

    @Override
    @GetMapping(GET_TRANSFERS_FOR_PROVIDER)
    public final TransferHistoryResponse getTransfersForProviderUser(@RequestParam(defaultValue = PAGE_NUMBER, required = false) int pageNo,
                                                                    @RequestParam(defaultValue = PAGE_SIZE, required = false) int pageSize,
                                                                    @AuthenticationPrincipal User user) {
        return transferService.getTransfersForProviderUser(pageNo, pageSize, user);
    }

    @Override
    @GetMapping(GET_TRANSFERS_FOR_RECEIVER)
    public final TransferHistoryResponse getTransfersForReceiverUser(@RequestParam(defaultValue = PAGE_NUMBER, required = false) int pageNo,
                                                                    @RequestParam(defaultValue = PAGE_SIZE, required = false) int pageSize,
                                                                    @AuthenticationPrincipal User user) {
        return transferService.getTransfersForReceiverUser(pageNo, pageSize, user);
    }
}
