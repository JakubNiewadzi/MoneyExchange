import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {accountApi} from "../../api/accountApi";

const SLICE_NAME = 'auth'

export const fetchAccountInfo = createAsyncThunk(
    'auth/fetchAccountInfo',
    async (token) =>  {
        const response = await accountApi
            .getAccount(token)

        return response.data
    }
)

const initialState = {
    status: 'idle',
    error: null,
    isLoggedIn: false,
    email: null,
    accountNumber: null,
    firstName: null,
    lastName: null
}


const authSlice = createSlice({
    name: SLICE_NAME,
    initialState,
    reducers: {
        logout: (state) => {
            state.email = initialState.email
            state.isLoggedIn = initialState.isLoggedIn
            state.accountNumber = initialState.accountNumber
            state.firstName = initialState.firstName
            state.lastName = initialState.lastName
        }
    },
    extraReducers: builder => {
        builder.addCase(fetchAccountInfo.pending, state => {
            state.status = 'loading'
        })
        builder.addCase(fetchAccountInfo.fulfilled, (state, action) => {
            state.status = 'succeeded'
            state.isLoggedIn = true
            state.email = action.payload.email
            state.accountNumber = action.payload.accountNumber
            state.firstName = action.payload.firstName
            state.lastName = action.payload.lastName
        })
        builder.addCase(fetchAccountInfo.rejected, (state, action) => {
            state.status = 'failed'
            state.error = action.error.message;
        })
    }
})

export const {logout} = authSlice.actions
export const authReducer = authSlice.reducer