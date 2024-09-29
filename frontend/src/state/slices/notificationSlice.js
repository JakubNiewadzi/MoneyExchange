import {createSlice} from "@reduxjs/toolkit";

const SLICE_NAME = "notification";

const initialState = {
    notifications: [],
};

const notificationSlice = createSlice({
        name: SLICE_NAME,
        initialState,
        reducers: {
            addNotification(state, action) {
                state.notifications = [...state.notifications, action.payload]
            },
            removeNotifications(state, action) {
                state.notifications = action.payload
            },
            clearNotifications(state) {
                state.notifications = [];
            }
        },
    }
);

export const {addNotification, clearNotifications, removeNotifications} = notificationSlice.actions;

export const notificationReducer = notificationSlice.reducer;