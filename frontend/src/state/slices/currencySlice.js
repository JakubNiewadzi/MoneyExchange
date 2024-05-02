import {createSlice} from "@reduxjs/toolkit";

const sliceName = 'currency'

const initialState = {
    currencies: [],
    calculatingCurrency: ''
}

const currencySlice = createSlice({
    name: sliceName,
    initialState,
    reducers: {
        setCurrencies: (state, action) => {
            state.currencies = action.payload.currencies
            state.calculatingCurrency = action.payload.calculatingCurrency
        },
    }
})

export const {setCurrencies} = currencySlice.actions
export const currencyReducer = currencySlice.reducer