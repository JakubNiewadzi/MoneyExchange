package pl.niewadzj.moneyExchange.api.currency.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.niewadzj.moneyExchange.api.currency.records.RatesResponse;
import pl.niewadzj.moneyExchange.entities.currency.Currency;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.niewadzj.moneyExchange.api.currency.constants.ApiCallConstants.SUPPLIER_URL;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiConsumer {

    private final ObjectMapper objectMapper;

    public List<Currency> getCurrency() {
        String currencyJson = fetchCurrencyFromApi();
        log.debug("Fetched currency JSON from api: {}", currencyJson);
        try {
            RatesResponse ratesResponse = mapJsonToResponse(currencyJson);
            return ratesResponse
                    .rates()
                    .stream()
                    .map(rate -> Currency
                                .builder()
                                .name(rate.currency())
                                .code(rate.code())
                                .rateDate(ratesResponse.effectiveDate())
                                .exchangeRate(rate.mid())
                                .build()
                    ).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String fetchCurrencyFromApi(){
        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(SUPPLIER_URL, String.class);
    }

    private RatesResponse mapJsonToResponse(String currencyJson) throws JsonProcessingException {
        JsonNode jsonNode;
        jsonNode = objectMapper.readTree(currencyJson);
        jsonNode = jsonNode.elements().next();
        return objectMapper.readValue(jsonNode.toString(), RatesResponse.class);
    }

}
