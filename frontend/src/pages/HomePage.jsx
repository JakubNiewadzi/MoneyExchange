import {Navbar} from "../components/Navbar";
import {CurrencyCard} from "../components/CurrencyCard";
import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {fetchCurrencies} from "../state/slices/currencySlice";
import {CircularProgress} from "@mui/material";

export const HomePage = () => {

    const accountNumber = useSelector(state => state.auth.accountNumber)

    const dispatch = useDispatch()

    return <div className='bg-background'><Navbar/>
        <div className='w-full h-[25vh]'></div>
        <div className='w-full h-[50vh] flex bg-darkGray p-8'>
            <div className='text-4xl font-bold w-2/5 text-right'>Welcome to the<b className='text-blue-300'> MODERN </b>money
                exchanging experience.
            </div>
            <div className='text-4xl font-bold w-2/5'></div>
        </div>
        <div className='w-full h-[50vh] flex p-8'>
            <div className='text-4xl font-bold w-3/5'></div>
            <div className='text-4xl font-bold w-1/4'> Manage <b className='text-blue-300'>ANY</b> currency you can
                imagine.
            </div>
        </div>
        <div className='w-full h-[50vh] flex bg-darkGray p-8'>
            <div className='text-4xl font-bold w-2/5 text-right'>Mismanaged your account? You can revert any exchange made by mistake up to <b className='text-blue-300'>ONE
                HOUR</b> after making it
            </div>
            <div className='text-4xl font-bold w-2/5'></div>
        </div>
    </div>
}