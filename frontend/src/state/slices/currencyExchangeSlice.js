import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {currencyExchangeApi} from "../../api/currencyExchangeApi";

const SLICE_NAME = 'currencyExchange';

export const fetchCurrencyExchangeHistory = createAsyncThunk(
    'currencyExchange/fetchCurrencyExchangeHistory',
    async (args) => {
        const response = await currencyExchangeApi.getExchanges(args.token, args.pageNumber, args.amountPerPage);
        return response.data;
    }
);

const initialState = {
    currencyExchangeHistory: [],
    pageNumber: 0,
    amountPerPage: 5,
    totalAmount: 0,
    pageAmount: 0,
    status: 'idle',
    error: null,
};

const currencyExchangeSlice = createSlice({
        name: SLICE_NAME,
        initialState,
        reducers: {
            changePage(state, action){
                state.pageNumber = action.payload;
            }
        },
        extraReducers: (builder) => {
            builder.addCase(fetchCurrencyExchangeHistory.pending, (state) => {
                state.status = 'loading';
            });
            builder.addCase(fetchCurrencyExchangeHistory.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.payload;
            });
            builder.addCase(fetchCurrencyExchangeHistory.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.currencyExchangeHistory = action.payload.currencyExchangeResponses;
                state.totalAmount = action.payload.amount;
                state.pageAmount = action.payload.pages;
            });
        }
    }
);

export const currencyExchangeReducer = currencyExchangeSlice.reducer;

export const {changePage} = currencyExchangeSlice.actions;