import {createSlice} from "@reduxjs/toolkit";

const sliceName = 'auth'

const initialState = {
    authToken: null,
    refreshToken: null,
    email: null,
    isLoggedIn: false
}


const authSlice = createSlice({
    name: sliceName,
    initialState,
    reducers: {
        performLogin: (state, action) => {
            state.authToken = action.payload.authToken
            state.refreshToken = action.payload.refreshToken
            state.email = action.payload.email
            state.isLoggedIn = true
        },
        logout: (state) => {
            state.authToken = initialState.authToken
            state.refreshToken = initialState.refreshToken
            state.email = initialState.email
            state.isLoggedIn = initialState.isLoggedIn
        }
    }
})

export const {performLogin, logout} = authSlice.actions
export const authReducer = authSlice.reducer