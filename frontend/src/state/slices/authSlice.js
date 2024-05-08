import {createSlice} from "@reduxjs/toolkit";

const SLICE_NAME = 'auth'

const initialState = {
    email: null,
    isLoggedIn: false,
    accountNumber: null
}


const authSlice = createSlice({
    name: SLICE_NAME,
    initialState,
    reducers: {
        performLogin: (state, action) => {
            state.email = action.payload.email
            state.isLoggedIn = true
            state.accountNumber = action.payload.accountNumber

        },
        logout: (state) => {
            state.email = initialState.email
            state.isLoggedIn = initialState.isLoggedIn
            state.accountNumber = initialState.accountNumber
        }
    }
})

export const {performLogin, logout} = authSlice.actions
export const authReducer = authSlice.reducer