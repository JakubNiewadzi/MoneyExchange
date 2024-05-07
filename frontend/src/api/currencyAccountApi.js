import {backendApi} from "./backendApi";
import {bearerAuth} from "./bearerAuth";

const accountAccountClient = backendApi("/api/v1/currencyAccount")

export const currencyAccountApi = {
    activateCurrencyAccount(token, id) {
        console.log("Activating currency account with id: " + id)
        return accountAccountClient.patch("/activateCurrencyAccount", null, {
            params: {
                currencyId: id
            },
            headers: {
                Authorization: bearerAuth(token)
            }
        })
    },
    suspendCurrencyAccount(token, id) {
        console.log("Suspending currency account with id: " + id)
        return accountAccountClient.patch("/suspendCurrencyAccount", null, {
            params: {
                currencyId: id
            },
            headers: {
                Authorization: bearerAuth(token)
            }
        })
    },

}