import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {messageApi} from "../../api/messageApi";

const SLICE_NAME = "message";

export const fetchDateMessages = createAsyncThunk(
    'message/fetchDateMessages',
    async (args) => {
        const response = await messageApi
            .getDateMessages(args.token, args.pageNumber, args.pageSize);
        return response.data;
    }
);

export const fetchValueMessages = createAsyncThunk(
    'message/fetchValueMessages',
    async (args) => {
        const response = await messageApi
            .getValueMessages(args.token, args.pageNumber, args.pageSize);
        return response.data;
    }
);


const initialState = {
    dateMessages: [],
    valueMessages: [],
    pageNumber: 0,
    amountPerPage: 5,
    totalAmount: 0,
    pageAmount: 0,
    status: 'idle',
    error: null,
};

const messageSlice = createSlice({
        name: SLICE_NAME,
        initialState,
        reducers: {
            changePage(state, action) {
                state.pageNumber = action.payload;
            },
            changeAmountPerPage(state, action) {
                state.amountPerPage = action.payload;
            },
        },
        extraReducers: (builder) => {
            builder.addCase(fetchDateMessages.pending, state => {
                state.status = 'loading';
            });
            builder.addCase(fetchDateMessages.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.payload;
            });
            builder.addCase(fetchDateMessages.fulfilled, (state, action) => {
                state.dateMessages = action.payload.dateMessages;
                state.totalAmount = action.payload.amount;
                state.pageAmount = action.payload.pages;
            });
            builder.addCase(fetchValueMessages.pending, state => {
                state.status = 'loading';
            });
            builder.addCase(fetchValueMessages.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.payload;
            });
            builder.addCase(fetchValueMessages.fulfilled, (state, action) => {
                state.valueMessages = action.payload.valueMessages;
                state.totalAmount = action.payload.amount;
                state.pageAmount = action.payload.pages;
            });
        }
    }
);