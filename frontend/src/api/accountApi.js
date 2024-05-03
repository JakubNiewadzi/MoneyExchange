import {backendApi} from "./backendApi";
import {bearerAuth} from "./bearerAuth";

const accountClient = backendApi("/api/v1/account")

export const accountApi = {
    getLoggedIn(token) {
        console.log("Getting singed in user account")
        return accountClient.get('/getAccount', {
            headers: {
                Authorization: bearerAuth(token)
            }
        })
    },
    getCurrencyAccounts(token) {
        console.log("Getting currency accounts for signed in user")
        return accountClient.get('/getCurrencyAccounts', {
            headers: {
                Authorization: bearerAuth(token)
            }
        })
    },
    getActiveCurrencyAccounts(token) {
        console.log("Getting active currency accounts for signed in user")
        return accountClient.get('/getActiveCurrencyAccounts', {
            headers: {
                Authorization: bearerAuth(token)
            }
        })
    },
}