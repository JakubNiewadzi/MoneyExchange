import {configureStore} from "@reduxjs/toolkit";
import {authReducer} from "./slices/authSlice";
import {currencyReducer} from "./slices/currencySlice";
import {currencyAccountsReducer} from "./slices/currencyAccountsSlice";

export const store = configureStore({
        reducer: {
            auth: authReducer,
            currency: currencyReducer,
            currencyAccount: currencyAccountsReducer,
            currencyExchange: currencyAccountsReducer,
        }
    }
);