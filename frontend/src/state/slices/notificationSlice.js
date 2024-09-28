import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {messageApi} from "../../api/messageApi";
import {fetchDateMessages, fetchValueMessages} from "./messageSlice";

const SLICE_NAME = "notification";

const initialState = {
    notifications: [],
};

const notificationSlice = createSlice({
        name: SLICE_NAME,
        initialState,
        reducers: {
            addNotification(state, action) {
                state.notifications = [...state.notifications, action]
            },
            clearNotifications(state) {
                state.notifications = [];
            }
        },
    }
);

export const {addNotification, clearNotifications} = notificationSlice.actions;

export const notificationReducer = notificationSlice.reducer;