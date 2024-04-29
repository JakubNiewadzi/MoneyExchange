import {authApi} from "../api/authApi";
import {getTableSortLabelUtilityClass} from "@mui/material";

export const login = async (loginRequest) => {
    const tokenResponse = await authApi.login(loginRequest)

    const tokens = {
        authToken: tokenResponse.data.authToken,
        refreshToken: tokenResponse.data.refreshToken
    }

    if (tokens.authToken && tokens.refreshToken) {
        return tokens
    }

}