import {backendApi} from "./backendApi";

const AUTH_ENDPOINT = "/api/v1/auth";

const authClient = backendApi(AUTH_ENDPOINT);

export const authApi = {
    login(loginRequest) {
        console.log("Logging in");
        return authClient.post('/login', loginRequest);
    },
    register(registerRequest) {
        console.log("Registering");
        return authClient.post('/register', registerRequest);
    }
};