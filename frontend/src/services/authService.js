import {authApi} from "../api/authApi";
import {accountApi} from "../api/accountApi";
import Cookies from 'js-cookie'

export const login = async (loginRequest) => {
    const tokenResponse = await authApi.login(loginRequest)

    if (tokenResponse) {
        const accountResponse = await accountApi
            .getLoggedIn(tokenResponse?.data?.authToken)

        Cookies.set("refreshToken", tokenResponse?.data?.refreshToken)
        Cookies.set("authToken", tokenResponse?.data?.authToken)

        return {
            accountNumber: accountResponse?.data?.accountNumber
        }
    }
}

export const register = async (registerRequest) => {
    const tokenResponse = await authApi.register(registerRequest)
    if (tokenResponse) {
        const accountResponse = await accountApi
            .getLoggedIn(tokenResponse?.data?.authToken)

        Cookies.set("refreshToken", tokenResponse?.data?.refreshToken)
        Cookies.set("authToken", tokenResponse?.data?.authToken)

        return {
            accountNumber: accountResponse?.data?.accountNumber
        }
    }
}