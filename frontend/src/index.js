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
import {NotFoundPage} from "./pages/NotFoundPage";
import {RegisterPage} from "./pages/RegisterPage";
import {MyAccountsPage} from "./pages/MyAccountsPage";
import {TransactionForm} from "./forms/TransactionForm";
import {ExchangeCurrenciesPage} from "./pages/ExchangeCurrenciesPage";
import {ExchangeHistoryPage} from "./pages/ExchangeHistoryPage";
import {TransferMoneyPage} from "./pages/TransferMoneyPage";
import {TransferHistoryPage} from "./pages/TransferHistoryPage";


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
                children: [
                    {
                        element: <MyAccountsPage/>,
                        index: true
                    },
                    {
                        path: "deposit/:id",
                        element: <TransactionForm action="Deposit"/>,
                        index: false
                    },
                    {
                        path: "withdraw/:id",
                        element: <TransactionForm action="Withdraw"/>,
                        index: false
                    },
                ]
            },
            {
                path: "exchangeCurrencies",
                element: <ExchangeCurrenciesPage/>
            },
            {
                path: "exchangeHistory",
                element: <ExchangeHistoryPage/>
            },
            {
                path: "transferMoney",
                element: <TransferMoneyPage/>
            },
            {
                path: "transferHistory",
                element: <TransferHistoryPage/>
            },
        ]
    }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <Provider store={store}>
            <div className='min-h-screen bg-background text-white font-sans'>
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
