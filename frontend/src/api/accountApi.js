import {backendApi} from "./backendApi";
import {bearerAuth} from "./bearerAuth";

const accountClient = backendApi("/api/v1/account");

export const accountApi = {
    getAccount(token) {
        console.log("Getting singed in user account");
        return accountClient.get('/getAccount', {
            headers: {
                Authorization: bearerAuth(token)
            }
        })
    },
    getCurrencyAccountsPage(token, pageNumber, pageSize) {
        console.log("Getting currency accounts for signed in user");
        return accountClient.get('/getCurrencyAccounts', {
            params: {
                pageNo: pageNumber,
                pageSize: pageSize
            },
            headers: {
                Authorization: bearerAuth(token)
            }
        })
    },
    getActiveCurrencyAccountsPage(token, pageNumber, pageSize) {
        console.log("Getting active currency accounts for signed in user");
        return accountClient.get('/getActiveCurrencyAccounts', {
            params: {
                pageNo: pageNumber,
                pageSize: pageSize
            },
            headers: {
                Authorization: bearerAuth(token)
            }
        })
    },
    getAllCurrencyAccounts(token) {
        console.log("Getting all currency accounts for signed in user");
        return accountClient.get('/getAllCurrencyAccountsFourUser', {
            headers: {
                Authorization: bearerAuth(token)
            }
        })
    }
};