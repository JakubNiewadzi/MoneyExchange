import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {accountApi} from "../../api/accountApi";

export const fetchCurrencyAccountsPage = createAsyncThunk(
    'currencyAccounts/fetchCurrencyAccountsPage',
    async (args) => {
        if (!args.isFilterActive) {
            const response = await accountApi.getCurrencyAccountsPage(args.token, args.pageNumber, args.pageSize);
            return response.data
        }
        const response = await accountApi.getActiveCurrencyAccountsPage(args.token, args.pageNumber, args.pageSize);
        return response.data
    }
);

export const fetchAllCurrencyAccounts = createAsyncThunk(
    'currencyAccounts/fetchAllCurrencyAccounts',
    async (token) => {
        const response = await accountApi.getAllCurrencyAccounts(token);
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
        changeAccountsPage(state, action) {
            state.pageNumber = action.payload;
        },
        changeAccountsAmountPerPage(state, action) {
            state.amountPerPage = action.payload;
        }
    },
    extraReducers: builder => {
        builder
            .addCase(fetchCurrencyAccountsPage.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(fetchCurrencyAccountsPage.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.currencyAccounts = action.payload.currencyAccountResponses;
                state.totalAmount = action.payload.amount;
                state.pageAmount = action.payload.pages;
            })
            .addCase(fetchCurrencyAccountsPage.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
                state.currencyAccounts = initialState.currencyAccounts;
                state.isFilterActive = initialState.isFilterActive;
            })

            .addCase(fetchAllCurrencyAccounts.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(fetchAllCurrencyAccounts.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.currencyAccounts = action.payload;
            })
            .addCase(fetchAllCurrencyAccounts.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
                state.currencyAccounts = initialState.currencyAccounts;
            })
    }
});

export const {
    releaseCurrencyAccounts,
    changeFilter,
    changeAccountsAmountPerPage,
    changeAccountsPage
} = currencyAccountSlice.actions;
export const currencyAccountsReducer = currencyAccountSlice.reducer;