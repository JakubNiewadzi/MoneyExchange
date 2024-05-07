import {Navbar} from "../components/Navbar";
import {CurrencyCard} from "../components/CurrencyCard";
import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {fetchCurrencies} from "../state/slices/currencySlice";
import {CircularProgress} from "@mui/material";

export const HomePage = () => {

    const calculatingCurrency = useSelector(state => state.currency.calculatingCurrency)
    const currencies = useSelector(state => state.currency.currencies)
    const status = useSelector(state => state.currency.status)
    const isLoggedIn = useSelector(state => state.auth.isLoggedIn)
    const accountNumber = useSelector(state => state.auth.accountNumber)

    const [exchangeRateCalculator, setExchangeRateCalculator] = useState(calculatingCurrency)
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(fetchCurrencies(exchangeRateCalculator))
    }, [exchangeRateCalculator]);

    const handleClick = (currencyId) => {
        if (currencyId !== exchangeRateCalculator) {
            setExchangeRateCalculator(currencyId)
        }
    }

    return <div><Navbar/>
        <div className='text-3xl text-white'>
            <div className='w-full mt-8 mb-4 flex justify-center bg-darkBlue p-8'>
                Currency exchange rates
            </div>
            {status === 'loading' ?
                <div className='w-full mt-14 flex justify-center p-8'>
                    <CircularProgress size={120}/>
                </div> : ''}
            <ul>
                <div className='w-full flex p-4 flex-wrap justify-around max-w-full'>
                    {currencies.map((currency) =>
                        <CurrencyCard key={currency.id}
                                      name={currency.name}
                                      code={currency.code}
                                      exchangeRate={currency.exchangeRate.toFixed(6)}
                                      id={currency.id}
                                      onClick={handleClick}/>
                    )}
                </div>
            </ul>
        </div>
    </div>
}