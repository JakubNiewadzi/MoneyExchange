import {configureStore} from "@reduxjs/toolkit";
import {authReducer} from "./slices/authSlice";
import {currencyReducer} from "./slices/currencySlice";
import {currencyAccountsReducer} from "./slices/currencyAccountsSlice";
import {currencyExchangeReducer} from "./slices/currencyExchangeSlice";
import {transferReducer} from "./slices/transferSlice";
import {messageReducer} from "./slices/messageSlice";

export const store = configureStore({
        reducer: {
            auth: authReducer,
            currency: currencyReducer,
            currencyAccount: currencyAccountsReducer,
            currencyExchange: currencyExchangeReducer,
            transfer: transferReducer,
            message: messageReducer,
        }
    }
);