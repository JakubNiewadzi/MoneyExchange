import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {transferApi} from "../../api/transferApi";

const SLICE_NAME = 'transfer';

export const fetchTransferHistory = createAsyncThunk(
    'transfer/fetchTransferHistory',
    async (args) => {
        const response = await transferApi
            .getTransfers(args.token, args.pageNumber, args.amountPerPage);

        return response.data;
    }
);

export const fetchTransferHistoryForSender = createAsyncThunk(
    'transfer/fetchTransferHistoryForSender',
    async (args) => {
        const response = await transferApi
            .getTransfersForProviderUser(args.token, args.pageNumber, args.amountPerPage);

        return response.data;
    }
);

export const fetchTransferHistoryForReceiver = createAsyncThunk(
    'transfer/fetchTransferHistoryForReceiver',
    async (args) => {
        const response = await transferApi
            .getTransfersForReceiverUser(args.token, args.pageNumber, args.amountPerPage);

        return response.data;
    }
);

const initialState = {
    transferHistory: [],
    pageNumber: 0,
    amountPerPage: 5,
    totalAmount: 0,
    pageAmount: 0,
    all: true,
    received: false,
    sent: false,
    status: 'idle',
    error: null,
};

const transferSlice = createSlice({
        name: SLICE_NAME,
        initialState,
        reducers: {
            changeTransferPage(state, action) {
                state.pageNumber = action.payload;
            },
            changeTransferAmountPerPage(state, action) {
                state.amountPerPage = action.payload;
            },
            setFilter(state, action) {
                state.all = action.payload.all;
                state.received = action.payload.received;
                state.sent = action.payload.sent;
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
                state.transferHistory = action.payload.transferResponseList;
                state.pageAmount = action.payload.pages;
                state.totalAmount = action.payload.amount;
            });
            builder.addCase(fetchTransferHistoryForSender.pending, (state) => {
                state.status = 'loading';
            });
            builder.addCase(fetchTransferHistoryForSender.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.payload;
            });
            builder.addCase(fetchTransferHistoryForSender.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.transferHistory = action.payload.transferResponseList;
                state.pageAmount = action.payload.pages;
                state.totalAmount = action.payload.amount;
            });
            builder.addCase(fetchTransferHistoryForReceiver.pending, (state) => {
                state.status = 'loading';
            });
            builder.addCase(fetchTransferHistoryForReceiver.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.payload;
            });
            builder.addCase(fetchTransferHistoryForReceiver.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.transferHistory = action.payload.transferResponseList;
                state.pageAmount = action.payload.pages;
                state.totalAmount = action.payload.amount;
            });
        }
    }
);

export const transferReducer = transferSlice.reducer;

export const {changeTransferPage, changeTransferAmountPerPage, setFilter} = transferSlice.actions;