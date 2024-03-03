package pl.niewadzj.moneyExchange.api.currency.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.niewadzj.moneyExchange.entities.currency.Currency;

import static pl.niewadzj.moneyExchange.api.currency.constants.ApiCallConstants.SUPPLIER_URL;

@Service
public class ApiConsumer {

    public Currency getCurrency() throws JsonProcessingException {
        final RestTemplate restTemplate = new RestTemplate();
        String currencyJson = restTemplate.getForObject(SUPPLIER_URL, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(currencyJson);
        jsonNode = jsonNode.elements().next();
        String name = jsonNode.get("rates").toString();
        System.out.println(name);
        return null;
    }

}
