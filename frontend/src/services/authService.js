import {authApi} from "../api/authApi";
import {accountApi} from "../api/accountApi";

export const login = async (loginRequest) => {
    const tokenResponse = await authApi.login(loginRequest)

    if (tokenResponse) {
        const accountResponse = await accountApi
            .getLoggedIn(tokenResponse?.data?.authToken)

        return {
            authToken: tokenResponse?.data?.authToken,
            refreshToken: tokenResponse?.data?.refreshToken,
            accountNumber: accountResponse?.data?.accountNumber
        }
    }
}

export const register = async (registerRequest) => {
    const tokenResponse = await authApi.register(registerRequest)

    if (tokenResponse) {
        const accountResponse = await accountApi
            .getLoggedIn(tokenResponse?.data?.authToken)

        return {
            authToken: tokenResponse?.data?.authToken,
            refreshToken: tokenResponse?.data?.refreshToken,
            accountNumber: accountResponse?.data?.accountNumber
        }
    }
}