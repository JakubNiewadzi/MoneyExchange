import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {transferApi} from "../../api/transferApi";

const SLICE_NAME = 'transfer';

export const fetchTransferHistory = createAsyncThunk(
    'transfer/fetchTransferHistory',
    async (args) => {
        const response = await transferApi.getTransfers(args.token, args.pageNumber, args.amountPerPage);
        return response.data;
    }
);

const initialState = {
    transferHistory: [],
    pageNumber: 0,
    amountPerPage: 5,
    totalAmount: 0,
    pageAmount: 0,
    status: 'idle',
    error: null,
};

const transferSlice = createSlice({
        name: SLICE_NAME,
        initialState,
        reducers: {
            changeTransferPage(state, action){
                state.pageNumber = action.payload;
            },
            changeTransferAmountPerPage(state, action){
                state.amountPerPage = action.payload;
            }
        },
        extraReducers: (builder) => {
            builder.addCase(fetchTransferHistory.pending, (state) => {
                state.status = 'loading';
            });
            builder.addCase(fetchTransferHistory.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.payload;
            });
            builder.addCase(fetchTransferHistory.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.transferHistory = action.payload;
            });
        }
    }
);

export const transferReducer = transferSlice.reducer;

export const {changeTransferPage, changeTransferAmountPerPage} = transferSlice.actions;