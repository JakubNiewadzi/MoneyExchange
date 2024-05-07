import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";

const SLICE_NAME = 'currencyExchange'

export const fetchCurrencyExchangeHistory = createAsyncThunk(
    'currencyExchange/fetchCurrencyExchangeHistory',
    async (token) => {
        ///TODO: get api
    }
)

const initialState = {
    currencyExchangeHistory: [],
    status: 'idle',
    error: null
}

const currencyExchangeSlice = createSlice({
        name: SLICE_NAME,
        initialState,
        reducers: {},
        extraReducers: (builder) => {
            builder.addCase(fetchCurrencyExchangeHistory.pending, (state) => {
                state.status = 'loading'
            })
            builder.addCase(fetchCurrencyExchangeHistory.rejected, (state, action) => {
                state.status = 'failed'
                state.error = action.payload
            })
            builder.addCase(fetchCurrencyExchangeHistory.fulfilled, (state, action) => {
                state.status = 'succeeded'
                state.currencyExchangeHistory = action.payload
            })
        }
    }
)

export const currencyExchangeReducer = currencyExchangeSlice.reducer