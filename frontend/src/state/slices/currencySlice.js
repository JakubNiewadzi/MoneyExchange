import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {currencyApi} from "../../api/currencyApi";

const SLICE_NAME = 'currency'

export const fetchCurrencies = createAsyncThunk(
    'currency/fetchCurrencies',
    async (calculatingCurrency) => {
        if (calculatingCurrency === 1) {
            const response = await currencyApi.getAll()
            return {
                calculatingCurrency: calculatingCurrency,
                data: response.data
            }
        }
        const response = await currencyApi.getWithExchangeRate(calculatingCurrency)
        return {
            calculatingCurrency: calculatingCurrency,
            data: response.data
        }
    }
)

const initialState = {
    currencies: [],
    calculatingCurrency: 1,
    status: 'idle',
    error: null
}

const currencySlice = createSlice({
    name: SLICE_NAME,
    initialState,
    reducers: {
        setCurrencies: (state, action) => {
            state.currencies = action.payload
        },
    },
    extraReducers: builder => {
        builder
            .addCase(fetchCurrencies.pending, (state) => {
                state.status = 'loading'
            })
            .addCase(fetchCurrencies.fulfilled, (state, action) => {
                state.status = 'succeeded'
                state.currencies = action.payload.data
                state.calculatingCurrency = action.payload.calculatingCurrency
            })
            .addCase(fetchCurrencies.rejected, (state, action) => {
                state.status = 'failed'
                state.error = action.error.message
                state.currencies = initialState.currencies
                state.calculatingCurrency = initialState.calculatingCurrency
            })
    }
})

export const {setCurrencies} = currencySlice.actions
export const currencyReducer = currencySlice.reducer