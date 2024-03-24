package pl.niewadzj.schedulerservice.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.niewadzj.schedulerservice.api.currency.records.RatesExternalResponse;
import pl.niewadzj.schedulerservice.entities.currency.Currency;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static pl.niewadzj.schedulerservice.scheduler.constants.SchedulerConstants.SUPPLIER_URL;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiConsumer {

    private final ObjectMapper objectMapper;

    public List<Currency> getCurrency() {
        String currencyJson = fetchCurrencyFromApi();
        log.debug("Fetched currency JSON from api: {}", currencyJson);
        try {
            RatesExternalResponse ratesExternalResponse = mapJsonToResponse(currencyJson);

            return ratesExternalResponse
                    .rates()
                    .stream()
                    .map(rate -> Currency
                            .builder()
                            .name(rate.currency())
                            .code(rate.code())
                            .rateDate(LocalDateTime.now())
                            .exchangeRate(rate.mid())
                            .build()
                    ).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            log.debug("Fetch error");
            throw new RuntimeException(e);
        }
    }

    private String fetchCurrencyFromApi() {
        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(SUPPLIER_URL, String.class);
    }

    private RatesExternalResponse mapJsonToResponse(String currencyJson) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper
                .readTree(currencyJson);

        jsonNode = jsonNode
                .elements()
                .next();

        return objectMapper
                .readValue(jsonNode.toString(), RatesExternalResponse.class);
    }

}
