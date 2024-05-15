import axios from "axios";
import Cookies from "js-cookie";

const REFRESH_ENDPOINT = "/api/v1/auth/refresh";

export const backendApi = (url) => {
    const client = axios.create({
        baseURL: process.env.REACT_APP_API_BACKEND_URL + url,
        headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json'
        }
    });

    client.interceptors.response.use((response) => {
        return response;
    }, async (error) => {
        console.log('An error occurred while calling backend', error);
        if (error.response) {
            if (error.response.status === 404) {
                return {
                    status: error.response.status
                };
            } else if (error.response.status === 401) {
                const refreshToken = Cookies.get("refreshToken");
                await axios.get(process.env.REACT_APP_API_BACKEND_URL + REFRESH_ENDPOINT,
                    {
                        params: {
                            refreshToken: refreshToken
                        }
                    }).then(res => {
                    console.log(res);
                    Cookies.set("authToken", res?.data?.authToken);
                })
                    .catch(refreshError => {
                        console.error('Error refreshing token:', refreshError);
                        Cookies.remove("authToken");
                        Cookies.remove("refreshToken");
                        return Promise.reject(error);
                    })
            }else {
                return error.response;
            }
        }
    });

    return client;
};