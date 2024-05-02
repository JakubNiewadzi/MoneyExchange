import {Navbar} from "../components/Navbar";
import {CurrencyCard} from "../components/CurrencyCard";
import {useEffect, useState} from "react";
import {currencyApi} from "../api/currencyApi";
import {useDispatch, useSelector} from "react-redux";
import {setCurrencies} from "../state/slices/currencySlice";

export const HomePage = () => {

    const calculatingCurrency = useSelector(state => state.currency.calculatingCurrency)
    const [exchangeRateCalculator, setExchangeRateCalculator] = useState(calculatingCurrency)
    const currencies = useSelector(state => state.currency.currencies)
    const dispatch = useDispatch()

    useEffect(() => {
        if (exchangeRateCalculator === '' || exchangeRateCalculator === 1) {
            currencyApi.getAll()
                .then(res => {
                    dispatch(setCurrencies({currencies: res.data, calculatingCurrency: 1}))
                    setExchangeRateCalculator(1)
                }).catch(err => console.error('[Fetch Error]:', err))
        } else {
            currencyApi.getWithExchangeRate(exchangeRateCalculator)
                .then(res => {
                    dispatch(setCurrencies({currencies: res.data, calculatingCurrency: exchangeRateCalculator}))
                }).catch(err => console.error('[Fetch Error]:', err))
        }
    }, [exchangeRateCalculator]);

    const handleClick = (currencyId) => {
        if (currencyId !== exchangeRateCalculator) {
            setExchangeRateCalculator(currencyId)
        }
        console.log(currencyId)
    }

    return <div><Navbar/>
        <div  className ='text-3xl text-white'>
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