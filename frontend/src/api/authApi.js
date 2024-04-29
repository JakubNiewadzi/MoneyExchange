import {backendApi} from "./backendApi";

const authClient = backendApi("/api/v1/auth")

export const authApi = {
    login(loginRequest) {
        console.log("Logging in")
        return authClient.post('/login', loginRequest)
    },
    register(registerRequest) {
        console.log("Registering")
        return authClient.post('/register', registerRequest)
    }
}