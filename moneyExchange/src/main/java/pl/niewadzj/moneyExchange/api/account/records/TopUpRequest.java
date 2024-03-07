package pl.niewadzj.moneyExchange.api.account.records;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record TopUpRequest(@NotNull Long currencyId,
                           @Positive
                           Float amount){}
