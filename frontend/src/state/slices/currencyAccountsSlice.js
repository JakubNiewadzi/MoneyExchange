import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {accountApi} from "../../api/accountApi";

export const fetchCurrencyAccounts = createAsyncThunk(
    'currencyAccounts/fetchCurrencyAccounts',
    async (args) => {
        console.log(args.token)
        console.log(args.isFilterActive)
        if (args.isFilterActive) {
            console.log("eaeae")
            const response = await accountApi.getCurrencyAccounts(args.token)
            return response.data
        }
        console.log("eaeaegfdgdfdgf")
        const response = await accountApi.getActiveCurrencyAccounts(args.token)
        return response.data
    }
)

const SLICE_NAME = 'currencyAccount'

const initialState = {
    currencyAccounts: [],
    isFilterActive: false,
    status: 'idle',
    error: null
}


const currencyAccountSlice = createSlice({
    name: SLICE_NAME,
    initialState,
    reducers: {
        releaseCurrencyAccounts: (state) => {
            state.currencyAccounts = initialState.currencyAccounts
            state.activeFilter = initialState.activeFilter
        }
    },
    extraReducers: builder => {
        builder
            .addCase(fetchCurrencyAccounts.pending, (state) => {
                state.status = 'loading'
            })
            .addCase(fetchCurrencyAccounts.fulfilled, (state, action) => {
                state.status = 'succeeded'
                state.currencyAccounts = action.payload
            })
            .addCase(fetchCurrencyAccounts.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
                state.currencyAccounts = initialState.currencyAccounts
                state.isFilterActive = initialState.isFilterActive
            })
    }
})

export const {releaseCurrencyAccounts} = currencyAccountSlice.actions
export const currencyAccountsReducer = currencyAccountSlice.reducer