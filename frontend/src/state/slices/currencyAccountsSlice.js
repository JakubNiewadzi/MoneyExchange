import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {accountApi} from "../../api/accountApi";

export const fetchCurrencyAccounts = createAsyncThunk(
    'currencyAccounts/fetchCurrencyAccounts',
    async (args) => {
        if (!args.isFilterActive) {
            const response = await accountApi.getCurrencyAccounts(args.token, args.pageNumber, args.pageSize);
            console.log(response.data)
            return response.data
        }
        const response = await accountApi.getActiveCurrencyAccounts(args.token, args.pageNumber, args.pageSize);
        return response.data
    }
);

const SLICE_NAME = 'currencyAccount';

const initialState = {
    currencyAccounts: [],
    pageNumber: 0,
    amountPerPage: 5,
    totalAmount: 0,
    pageAmount: 0,
    isFilterActive: false,
    status: 'idle',
    error: null
};


const currencyAccountSlice = createSlice({
    name: SLICE_NAME,
    initialState,
    reducers: {
        releaseCurrencyAccounts: (state) => {
            state.currencyAccounts = initialState.currencyAccounts;
            state.isFilterActive = initialState.isFilterActive;
        },
        changeFilter: (state) => {
            state.isFilterActive = !state.isFilterActive;
        },
        changeAccountsPage(state, action){
            state.pageNumber = action.payload;
        },
        changeAccountsAmountPerPage(state, action){
            console.log(action.payload)
            state.amountPerPage = action.payload;
        }
    },
    extraReducers: builder => {
        builder
            .addCase(fetchCurrencyAccounts.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(fetchCurrencyAccounts.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.currencyAccounts = action.payload.currencyAccountResponses;
                state.totalAmount = action.payload.amount;
                state.pageAmount = action.payload.pages;
            })
            .addCase(fetchCurrencyAccounts.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
                state.currencyAccounts = initialState.currencyAccounts;
                state.isFilterActive = initialState.isFilterActive;
            })
    }
});

export const {releaseCurrencyAccounts, changeFilter, changeAccountsAmountPerPage, changeAccountsPage} = currencyAccountSlice.actions;
export const currencyAccountsReducer = currencyAccountSlice.reducer;