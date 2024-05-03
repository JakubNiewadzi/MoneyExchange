import {Navbar} from "../components/Navbar";
import {CurrencyCard} from "../components/CurrencyCard";
import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {fetchCurrencies} from "../state/slices/currencySlice";

export const HomePage = () => {

    const calculatingCurrency = useSelector(state => state.currency.calculatingCurrency)
    const [exchangeRateCalculator, setExchangeRateCalculator] = useState(calculatingCurrency)
    const currencies = useSelector(state => state.currency.currencies)
    const dispatch = useDispatch()
    const isLoggedIn = useSelector(state => state.auth.isLoggedIn)
    const accountNumber = useSelector(state => state.auth.accountNumber)

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
            {/*<div className='w-full mt-8 mb-4 flex p-8'>*/}
            {/*    {isLoggedIn ? <span>Welcome user!</span> : <span>Hello user! Sign in to get access to this site</span>}*/}
            {/*</div>*/}
            <div className='w-full mt-8 mb-4 flex justify-center bg-darkBlue p-8'>
                Currency exchange rates
            </div>
            <ul>
                <div className='w-full flex p-4 flex-wrap justify-around max-w-full'>
                    {currencies.map((currency) =>
                        <CurrencyCard name={currency.name}
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