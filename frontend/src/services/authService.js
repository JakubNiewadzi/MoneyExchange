import {authApi} from "../api/authApi";
import Cookies from 'js-cookie'

export const login = async (loginRequest) => {
    const tokenResponse = await authApi.login(loginRequest);

    if (tokenResponse) {
        Cookies.set("refreshToken", tokenResponse?.data?.refreshToken);
        Cookies.set("authToken", tokenResponse?.data?.authToken);
    }

    return tokenResponse
};

export const register = async (registerRequest) => {
    const tokenResponse = await authApi.register(registerRequest);
    if (tokenResponse) {
        Cookies.set("refreshToken", tokenResponse?.data?.refreshToken);
        Cookies.set("authToken", tokenResponse?.data?.authToken);
    }

    return tokenResponse
};