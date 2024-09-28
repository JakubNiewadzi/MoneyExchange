import {MenuItem, Select} from "@mui/material";
import Button from "@mui/material/Button";
import {FaArrowsRotate} from "react-icons/fa6";
import {useDispatch, useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {fetchAllCurrencyAccounts} from "../state/slices/currencyAccountsSlice";
import Cookies from "js-cookie";
import {FormContainer} from "../components/FormContainer";
import {useLocation, useNavigate} from "react-router";
import {messageApi} from "../api/messageApi";
import {formatDate, requestDate} from "../services/dateService";

export const PlanExchangePage = () => {

    const currencies = useSelector(state => state.currency.currencies);
    const [currencyOne, setCurrencyOne] = useState(1);
    const [currencyTwo, setCurrencyTwo] = useState(2);
    const [amount, setAmount] = useState(0.0);
    const [value, setValue] = useState(0.0);
    const [currentRate, setCurrentRate] = useState(0.0);
    const [date, setDate] = useState('');

    const authToken = Cookies.get('authToken');
    const dispatch = useDispatch();
    const location = useLocation();
    const navigate = useNavigate();
    const path = location.pathname;


    const handleCurrencyChange = (e) => {
        const name = e.target.name;
        const value = e.target.value;
        name === 'select-one' ? setCurrencyOne(value) : setCurrencyTwo(value);
    };


    useEffect(() => {
        dispatch(fetchAllCurrencyAccounts(authToken));
    }, []);

    useEffect(() => {
        setCurrentRate((currencies.find(element => element.id === currencyTwo)?.exchangeRate
            / currencies.find(element => element.id === currencyOne)?.exchangeRate).toFixed(2))
    }, [currencyOne, currencyTwo]);

    const onSwitch = () => {
        const newCurrencyOne = currencyTwo;
        setCurrencyTwo(currencyOne);
        setCurrencyOne(newCurrencyOne);
    };

    const handleChange = (e) => {
        const id = e.target.id;
        const value = e.target.value;

        if (id === 'datetime') {
            setDate(value);
        } else if (id === 'amount') {
            setAmount(value);
        } else {
            setValue(value);
        }
    };



    const dateInput = <div className='flex flex-col'>
        <label className="my-2">Select a date and time: </label>
        <input className="border w-full bg-lightGray border-lightGray
               rounded-sm py-2 px-4 focus:outline-none focus:ring-2
                        ring-blue-400"
               type="datetime-local"
               id="datetime"
               value={date}
               onChange={(e) => handleChange(e)}
        />
    </div>;


    const valueInput =
        <div className='flex md:flex-row flex-col'>
            <div className='flex flex-col md:mr-4'>
                <label className="my-2">Target exchange rate: </label>
                <input type="number"
                       className="border w-full bg-lightGray border-lightGray
               rounded-sm py-2 px-4 focus:outline-none focus:ring-2
                        ring-blue-400"
                       id='value'
                       name="value"
                       placeholder="123.33"
                       value={value}
                       onChange={handleChange}
                       step='0.01'
                       min='0'
                />
            </div>
            <div className='flex flex-col'>
                <label className="my-2">Current exchange rate: </label>
                <input type="number"
                       className="border w-full bg-lightGray border-lightGray
               rounded-sm py-2 px-4 focus:outline-none focus:ring-2
                        ring-blue-400"
                       id='current'
                       name="current"
                       placeholder="123.33"
                       value={currentRate}
                       step='0.01'
                       min='0'
                       disabled
                />
            </div>
        </div>;

    const handleSubmit = async (e) => {
        e.preventDefault();

        path === '/planDate' ?
            await messageApi.createDateMessage(authToken, {
                sourceCurrencyId: currencyOne,
                targetCurrencyId: currencyTwo,
                amount: amount,
                triggerDate: requestDate(date),
            })
            :
            await messageApi.createValueMessage(authToken, {
                sourceCurrencyId: currencyOne,
                targetCurrencyId: currencyTwo,
                amount: amount,
                valueExchangeRate: value,
            });

        navigate("/activeMessages");
    };

    return <FormContainer handleSubmit={handleSubmit}>
        <h2 className="text-center text-2xl font-semibold mb-4">Plan your exchange</h2>
        <div
            className='flex mt-2 md:flex-row flex-col w-full md:justify-around md:items-stretch font-semibold items-center'>
            <div className='flex flex-col md:w-2/5 w-full'>
                <label>From:</label>
                <Select
                    name='select-one'
                    className="text-white font-semibold w-full bg-lightGray rounded-bl focus:outline-none focus:ring
                        ring-blue-400"
                    value={currencyOne}
                    onChange={handleCurrencyChange}>
                    {currencies.map(currency => {
                        return <MenuItem key={currency.id} value={currency.id}>{currency.code}</MenuItem>
                    })}
                </Select>
            </div>
            <div className='flex items-end md:mx-2 mx-0 md:my-0 mt-6'>
                <Button
                    className="text-white w-1/12 h-3/4 bg-background rounded-full ring ring-blue-400"
                    onClick={onSwitch}><FaArrowsRotate size={25}/></Button>
            </div>
            <div className='flex flex-col md:w-2/5 w-full'>
                <label>To:</label>
                <Select
                    name='select-two'
                    className="text-white font-semibold w-full bg-lightGray rounded-bl focus:outline-none focus:ring
                        ring-blue-400"
                    value={currencyTwo}
                    onChange={handleCurrencyChange}>
                    {currencies.map(currency => {
                        return <MenuItem key={currency.id} value={currency.id}>{currency.code}</MenuItem>
                    })}
                </Select>
            </div>
        </div>
        <div className="my-2">Amount of money</div>
        <input type="number"
               className="border w-full bg-lightGray border-lightGray
               rounded-sm py-2 px-4 focus:outline-none focus:ring-2
                        ring-blue-400"
               id='amount'
               name="amount"
               placeholder="123.33"
               value={amount}
               onChange={handleChange}
               step='0.01'
               min='0'
        />
        {path === '/planDate' ? dateInput : valueInput}
        <Button type="submit" variant="contained" className="bg-darkGray mt-4 w-full">
            Submit
        </Button>
    </FormContainer>

};
