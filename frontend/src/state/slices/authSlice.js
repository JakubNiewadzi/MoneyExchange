import {createSlice} from "@reduxjs/toolkit";

const sliceName = 'auth'

const initialState = {
    authToken: null,
    refreshToken: null,
    user: null,
    isLoggedIn: false
}


const authSlice = createSlice({
    name: sliceName,
    initialState,
    login: (state, action) => {
        state.authToken = action.payload.authToken
        state.refreshToken = action.payload.refreshToken
        state.user = action.payload.username
        state.isLoggedIn = true
    },
    logout: (state) => {
        state.authToken = initialState.authToken
        state.refreshToken = initialState.refreshToken
        state.user = initialState.user
        state.isLoggedIn = initialState.isLoggedIn
    }
})

export const authReducer = authSlice.reducer