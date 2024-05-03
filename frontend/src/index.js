import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import {HomePage} from "./pages/HomePage";
import {LoginPage} from "./pages/LoginPage";
import {Provider} from "react-redux";
import {store} from "./state/Store";
import {ProtectedRoute} from "./routes/ProtectedRoute";
import {TestPage} from "./pages/TestPage";
import {NotFoundPage} from "./pages/NotFoundPage";
import {RegisterPage} from "./pages/RegisterPage";
import {MyAccountsPage} from "./pages/MyAccountsPage";


const router = createBrowserRouter([
    {
        path: "/",
        element: <HomePage/>
    },
    {
        path: "/login",
        element: <LoginPage/>
    },
    {
        path: "/register",
        element: <RegisterPage/>
    },
    {
        path: "*",
        element: <NotFoundPage/>
    },
    {
        path: "/",
        element: <ProtectedRoute/>,
        children: [
            {
                path: "myAccounts",
                element: <MyAccountsPage/>
            }
        ]
    }
])

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <Provider store={store}>
            <div className='min-h-screen bg-background'>
                <RouterProvider router={router}/>
            </div>
        </Provider>
    </React.StrictMode>
)
;

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
