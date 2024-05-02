import {backendApi} from "./backendApi";

const currencyClient = backendApi("/api/v1/currency")

export const currencyApi = {
    getAll() {
        console.log("Getting all currencies")
        return currencyClient.get('/getAll' )
    },
    getWithExchangeRate(id){
        console.log("Getting with exchange rate")
        return currencyClient.get('/getWightExchangeRate', {
            params: {
                id: id
            }
        })
    }
}