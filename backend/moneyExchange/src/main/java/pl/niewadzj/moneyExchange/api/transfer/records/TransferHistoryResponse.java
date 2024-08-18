package pl.niewadzj.moneyExchange.api.transfer.records;

import lombok.Builder;

import java.util.List;

@Builder
public record TransferHistoryResponse(List<TransferResponse> transferResponseList,
                                      long amount,
                                      int pages) {
}
