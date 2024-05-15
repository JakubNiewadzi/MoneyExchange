import {backendApi} from "./backendApi";
import {bearerAuth} from "./bearerAuth";

const accountAccountClient = backendApi("/api/v1/currencyAccount");

export const currencyAccountApi = {
    activateCurrencyAccount(token, id) {
        console.log("Activating currency account with id: " + id);
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
        console.log("Suspending currency account with id: " + id);
        return accountAccountClient.patch("/suspendCurrencyAccount", null, {
            params: {
                currencyId: id
            },
            headers: {
                Authorization: bearerAuth(token)
            }
        })
    },
    deposit(token, amount, id) {
        console.log("Depositing to a currency account with id: " + id);
        return accountAccountClient.put("/deposit",
            {
                currencyId: id,
                amount: amount
            },
            {
                headers: {
                    Authorization: bearerAuth(token)
                }
            })
    },
    withdraw(token, amount, id) {
        console.log("Withdrawing from a currency account with id: " + id);
        return accountAccountClient.put("/withdraw",
            {
                currencyId: id,
                amount: amount
            },
            {
                headers: {
                    Authorization: bearerAuth(token)
                }
            })
    },

};