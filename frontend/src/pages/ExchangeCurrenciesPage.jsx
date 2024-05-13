import {useDispatch, useSelector} from "react-redux";
import Button from "@mui/material/Button";
import {FaArrowsRotate} from "react-icons/fa6";
import {MenuItem, Select} from "@mui/material";
import {useEffect, useState} from "react";
import {currencyExchangeApi} from "../api/currencyExchangeApi";
import Cookies from "js-cookie";
import {fetchCurrencyAccounts} from "../state/slices/currencyAccountsSlice";
import {useNavigate} from "react-router";

export const ExchangeCurrenciesPage = () => {

    const currencies = useSelector(state => state.currency.currencies)
    const currencyAccounts = useSelector(state => state.currencyAccount.currencyAccounts)
    const [currencyOne, setCurrencyOne] = useState(1)
    const [currencyTwo, setCurrencyTwo] = useState(2)

    const [currencyOneAccount, setCurrencyOneAccount] = useState(currencyAccounts.find(currencyAccount => currencyAccount.currencyId === currencyOne))
    const [currencyTwoAccount, setCurrencyTwoAccount] = useState(currencyAccounts.find(currencyAccount => currencyAccount.currencyId === currencyTwo))

    const [currencyOneAmount, setCurrencyOneAmount] = useState(10.00)
    const [currencyTwoAmount, setCurrencyTwoAmount] = useState('')

    const [errorOne, setErrorOne] = useState('')
    const [errorTwo, setErrorTwo] = useState('')


    const authToken = Cookies.get('authToken')
    const dispatch = useDispatch()
    const navigate = useNavigate()

    const handleChange = (e) => {
        const name = e.target.name
        const value = e.target.value
        name === 'select-one' ? setCurrencyOne(value) : setCurrencyTwo(value)
    }

    useEffect(() => {
        setCurrencyTwoAmount((currencyOneAmount * currencies.find(element => element.id === currencyOne)?.exchangeRate
            / currencies.find(element => element.id === currencyTwo)?.exchangeRate).toFixed(2))

        const nextCurrencyAccountOne = currencyAccounts.find(currencyAccount => currencyAccount.currencyId === currencyOne)
        const nextCurrencyAccountTwo = currencyAccounts.find(currencyAccount => currencyAccount.currencyId === currencyTwo)

        setCurrencyOneAccount(nextCurrencyAccountOne)
        setCurrencyTwoAccount(nextCurrencyAccountTwo)

        if (nextCurrencyAccountOne?.status === 'SUSPENDED') {
            setErrorOne('This account is suspended!')
        }else {
            setErrorOne('')
        }
        if (nextCurrencyAccountTwo?.status === 'SUSPENDED') {
            setErrorTwo('This account is suspended!')
        }else{
            setErrorTwo('')
        }

    }, [currencyOne, currencyTwo]);


    useEffect(() => {
        dispatch(fetchCurrencyAccounts(
            {
                token: authToken,
                isFilterActive: false
            }
        ))
    }, []);


    const handleInputChange = (e) => {
        const name = e.target.name
        let value = e.target.value

        if (!/^\d*\.?\d{0,2}$/.test(value) && !isNaN(value)) {
            value = value.slice(0, -1);
        }

        if (name === 'amount-from') {
            setCurrencyOneAmount(value)
            if(currencyOneAccount.balance - value < 0){
                setErrorOne("You do not have enough money on this account!")
            }else{
                setErrorOne("")
            }
            setCurrencyTwoAmount((value * currencies.find(element => element.id === currencyOne)?.exchangeRate
                / currencies.find(element => element.id === currencyTwo)?.exchangeRate).toFixed(2))
        } else {
            setCurrencyTwoAmount(value)
            setCurrencyOneAmount((value * currencies.find(element => element.id === currencyTwo).exchangeRate
                / currencies.find(element => element.id === currencyOne)?.exchangeRate).toFixed(2))
            if(currencyOneAccount.balance - currencyOneAmount < 0){
                setErrorOne("You do not have enough money on this account!")
            }else{
                setErrorOne("")
            }
        }
    }

    const onSwitch = () => {
        const newCurrencyOne = currencyTwo
        setCurrencyTwo(currencyOne)
        setCurrencyOne(newCurrencyOne)
    }

    const onExchange = async () => {
        const response = await currencyExchangeApi.exchangeCurrency(authToken, currencyOne, currencyTwo, currencyOneAmount)
        if(response!==undefined){
            navigate("/myAccounts")
        }
    }

    return <div className='flex flex-col justify-center bg'>
        <div
            className='flex flex-col bg-darkGray w-full space items-center h-[50vh] justify-center rounded-lg shadow-lg mt-48'>
            <div className='text-3xl font-semibold'>Exchange your currencies, cause it's what this site is all
                about.
            </div>
            <div className='flex mt-8 md:flex-row flex-col w-1/2 justify-around'>
                <div className='flex flex-col w-2/5'>
                    <label>From:</label>
                    <Select
                        name='select-one'
                        className="text-white font-semibold w-full bg-lightGray rounded-bl focus:outline-none focus:ring ring-background"
                        value={currencyOne}
                        onChange={handleChange}>
                        {currencies.map(currency => {
                            return <MenuItem key={currency.id} value={currency.id}>{currency.code}</MenuItem>
                        })}
                    </Select>
                </div>
                <div className='flex items-end'>
                    <Button
                        className="text-white w-1/12 h-3/4 bg-background rounded-full ring ring-blue-400"
                        onClick={onSwitch}><FaArrowsRotate size={25}/></Button>
                </div>
                <div className='flex flex-col w-2/5'>
                    <label>To:</label>
                    <Select
                        name='select-two'
                        className="text-white font-semibold w-full bg-lightGray rounded-bl focus:outline-none focus:ring ring-background"
                        value={currencyTwo}
                        onChange={handleChange}>
                        {currencies.map(currency => {
                            return <MenuItem key={currency.id} value={currency.id}>{currency.code}</MenuItem>
                        })}
                    </Select>
                </div>
            </div>
            <div className='flex mt-8 md:flex-row flex-col w-1/2 justify-around'>
                <div className='flex flex-col w-2/5'>
                    <label>This amount of money:</label>
                    <input
                        className="border appearance-none w-full bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:ring ring-background focus:border-background"
                        type='number'
                        name='amount-from'
                        placeholder='11.11'
                        value={currencyOneAmount}
                        step='0.01'
                        min='0'
                        onChange={handleInputChange}/>
                    {errorOne ?
                        <span className='text-red-500 font-semibold mt-2'>{errorOne}</span> :
                        ''}
                </div>
                <div className="flex items-center w-1/10 font-semibold text-sm">Converts to:</div>
                <div className='flex flex-col w-2/5'>
                    <label>This amount of money:</label>
                    <input
                        className="border appearance-none w-full bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:ring ring-background"
                        type='number'
                        name='amount-to'
                        placeholder='11.11'
                        step='0.01'
                        value={currencyTwoAmount}
                        min='0'
                        onChange={handleInputChange}/>
                    {errorTwo ?
                        <span className='text-red-500 font-semibold mt-2'>{errorTwo}</span> : ''}
                </div>
            </div>
            <div className='mt-8 flex w-1/2 justify-center'>
                <Button onClick={onExchange}
                        className="text-white text-lg font-semibold w-1/6 bg-background rounded-full ring ring-blue-400">Convert!</Button>
            </div>
        </div>
        <div className='flex flex-col w-full space items-center h-[40vh] rounded-lg p-10 text-3xl font-semibold'>
            Don't want to exchange right now? Plan your exchange in advance!
        </div>
    </div>

}