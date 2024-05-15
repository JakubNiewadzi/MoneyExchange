import {backendApi} from "./backendApi";
import {bearerAuth} from "./bearerAuth";

const currencyExchangeClient = backendApi("/api/v1/currencyExchange");

export const currencyExchangeApi = {
    exchangeCurrency(token, currencyFromId, currencyToId, amount) {
        console.log(`Exchanging currency from ${currencyFromId} to ${currencyToId}`);
        return currencyExchangeClient.post("/exchangeCurrency", {
            currencyFromId: currencyFromId,
            currencyToId: currencyToId,
            amount: amount
        }, {
            headers: {
                Authorization: bearerAuth(token)
            }
        });
    },
    revertExchange(token, exchangeId) {
        console.log(`Trying to revert exchange with id ${exchangeId}`);
        return currencyExchangeClient.post("/revertExchange", null, {
            params: {
                id: exchangeId
            },
            headers: {
                Authorization: bearerAuth(token)
            }
        });
    },
    getExchanges(token, pageNumber, pageSize) {
        console.log("Getting exchanges history for a user");
        return currencyExchangeClient.get("/getExchanges", {
            params: {
                pagNo: pageNumber,
                pageSize: pageSize
            },
            headers: {
                Authorization: bearerAuth(token)
            }
        });
    }
};