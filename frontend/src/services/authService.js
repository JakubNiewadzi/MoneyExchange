import {authApi} from "../api/authApi";

export const login = async (loginRequest) => {
    const tokenResponse = await authApi.login(loginRequest)

    if (tokenResponse) {
        return {
            authToken: tokenResponse?.data?.authToken,
            refreshToken: tokenResponse?.data?.refreshToken
        }
    }
}

export const register = async (registerRequest) => {
    const tokenResponse = await authApi.register(registerRequest)
    console.log(tokenResponse)
    if (tokenResponse) {
        return {
            authToken: tokenResponse?.data?.authToken,
            refreshToken: tokenResponse?.data?.refreshToken
        }
    }
}