import {useSelector} from "react-redux";
import Button from "@mui/material/Button";
import {FaArrowsRotate} from "react-icons/fa6";
import {MenuItem, Select} from "@mui/material";
import {useEffect, useState} from "react";

export const ExchangeCurrenciesPage = () => {

    const currencies = useSelector(state => state.currency.currencies)
    const [currencyOne, setCurrencyOne] = useState(1)
    const [currencyTwo, setCurrencyTwo] = useState(2)

    const [currencyOneAmount, setCurrencyOneAmount] = useState(0)
    const [currencyTwoAmount, setCurrencyTwoAmount] = useState(0)

    const handleChange = (e) => {
        const name = e.target.name
        const value = e.target.value
        name === 'select-one' ? setCurrencyOne(value) : setCurrencyTwo(value)
    }

    useEffect(() => {
        setCurrencyTwoAmount(currencyOneAmount * currencies.find(element => element.id===currencyOne).exchangeRate
            / currencies.find(element => element.id===currencyTwo).exchangeRate)
    }, [currencyOne, currencyTwo]);

    const handleInputChange = (e) => {
        const name = e.target.name
        const value = e.target.value

        if(name === 'amount-from'){
            setCurrencyOneAmount(value)
            setCurrencyTwoAmount(value * currencies.find(element => element.id===currencyOne).exchangeRate
                / currencies.find(element => element.id===currencyTwo).exchangeRate)
        }
        else{
            setCurrencyTwoAmount(value)
            setCurrencyOneAmount(value * currencies.find(element => element.id===currencyTwo).exchangeRate
                / currencies.find(element => element.id===currencyOne).exchangeRate)

        }
    }

    const onSwitch = () => {
        const newCurrencyOne = currencyTwo
        setCurrencyTwo(currencyOne)
        setCurrencyOne(newCurrencyOne)
    }

    return <div className='flex flex-col justify-center bg'>
        <div className='flex flex-col bg-darkGray w-full space items-center h-[40vh] rounded-lg shadow-lg mt-48'>
            <div className='mt-16 text-3xl font-semibold'>Exchange your currencies, cause it's what this site is all
                about.
            </div>
            <div className='flex mt-8 md:flex-row flex-col w-1/2 justify-around'>
                <div className='flex flex-col w-2/5'>
                    <span>From:</span>
                    <Select
                        name='select-one'
                        className="text-white font-semibold w-full bg-lightGray rounded-bl focus:outline-none focus:shadow-outline"
                        value={currencyOne}
                        onChange={handleChange}>
                        {currencies.map(currency => {
                            return <MenuItem key={currency.id} value={currency.id}>{currency.code}</MenuItem>
                        })}
                    </Select>
                </div>
                <div className='flex items-end'>
                    <Button
                        className="text-white w-1/12 h-3/4 bg-lightGray rounded-full focus:outline-none focus:shadow-outline"
                        onClick={onSwitch}><FaArrowsRotate size={25}/></Button>
                </div>
                <div className='flex flex-col w-2/5'>
                    <span>To:</span>
                    <Select
                        name='select-two'
                        className="text-white font-semibold w-full bg-lightGray rounded-bl focus:outline-none focus:shadow-outline"
                        value={currencyTwo}
                        onChange={handleChange}>
                        {currencies.map(currency => {
                            return <MenuItem key={currency.id} value={currency.id}>{currency.code}</MenuItem>
                        })}
                    </Select>
                </div>
            </div>
            <div className='flex mt-8 md:flex-row flex-col w-1/2 justify-around'>
                <input
                    className="border w-2/5 bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:border-background"
                    type='number'
                    name='amount-from'
                    placeholder='11.11'
                    value={currencyOneAmount}
                    step='0.01'
                    min='0'
                    onChange={handleInputChange}/>
                <div className=" w-1/12 font-normal text-md"></div>
                <input
                    className="border w-2/5 bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:border-background"
                    type='number'
                    name='amount-to'
                    placeholder='11.11'
                    step='0.01'
                    value={currencyTwoAmount}
                    min='0'
                    onChange={handleInputChange}/>
            </div>
        </div>
        <div className='flex flex-col w-full space items-center h-[40vh] rounded-lg p-10 text-3xl font-semibold'>
            Don't want to exchange right now? Plan your exchange in advance!
        </div>
    </div>

}