import {useState} from "react";
import {MenuItem, Select} from "@mui/material";
import {useDispatch, useSelector} from "react-redux";
import Cookies from "js-cookie";
import Button from "@mui/material/Button";

export const TransferMoneyPage = () => {

    const currencies = useSelector(state => state.currency.currencies);
    const currencyAccounts = useSelector(state => state.currencyAccount.currencyAccounts);

    const [receiverAccountNumber, setReceiverAccountNumber] = useState(20113213213213);
    const [transferredMoney, setTransferredMoney] = useState(11.11);
    const [currencyOne, setCurrencyOne] = useState(1);
    const [currencyTwo, setCurrencyTwo] = useState(2);

    const authToken = Cookies.get('authToken');
    const dispatch = useDispatch();
    const handleInputChange = (e) => {
        const name = e.target.name;
        let value = e.target.value;

        if (name === 'number-form') {
            setReceiverAccountNumber(value);
        }
        else if(name === 'amount-transferred'){
            setTransferredMoney(value);
        }
    };


    return <div className='flex flex-col justify-center'>
        <div
            className='flex flex-col bg-darkGray w-full space items-center py-8 justify-center rounded-lg shadow-lg mt-48'>
            <div className='flex flex-col md:w-1/2 w-full'>
                <div className='text-3xl font-semibold text-center'>
                    Transfer your money to somebody else's account.
                </div>
                <div
                    className='flex flex-col mt-4 md:justify-around md:items-stretch'>
                    <label>Receiver account number:</label>
                    <input className="border mt-2 appearance-none w-full bg-lightGray border-lightGray rounded-md py-2 px-4
                    focus:outline-none focus:ring ring-background focus:border-background"
                           type='number'
                           name='number-form'
                           placeholder='20113213213213'
                           value={receiverAccountNumber}
                           step='1'
                           min='0'
                           max='9999999999999999999999'
                           onChange={handleInputChange}/>
                </div>
                <div className='flex flex-row mt-4 justify-between'>
                    <div className='flex flex-col md:w-2/5 w-4/5'>
                        <label>Provider currency:</label>
                        <Select
                            name='select-one'
                            className="text-white mt-2 font-semibold w-full bg-lightGray rounded-bl focus:outline-none focus:ring ring-background"
                            value={currencyOne}
                            onChange={handleInputChange}>
                            {currencies.map(currency => {
                                return <MenuItem key={currency.id} value={currency.id}>{currency.code}</MenuItem>
                            })}
                        </Select>
                    </div>
                    <div className='flex flex-col md:w-2/5 w-4/5'>
                        <label>Receiver currency:</label>
                        <Select
                            name='select-two'
                            className="text-white mt-2 font-semibold w-full bg-lightGray rounded-bl focus:outline-none focus:ring ring-background"
                            value={currencyTwo}
                            onChange={handleInputChange}>
                            {currencies.map(currency => {
                                return <MenuItem key={currency.id} value={currency.id}>{currency.code}</MenuItem>
                            })}
                        </Select>
                    </div>
                </div>
                <div className='flex flex-col mt-4'>
                    <label>Transferred Money:</label>
                    <input
                        className="border mt-2 appearance-none w-full bg-lightGray border-lightGray rounded-md
                        py-2 px-4 focus:outline-none focus:ring ring-background focus:border-background"
                        type='number'
                        name='amount-transferred'
                        placeholder='11.11'
                        value={transferredMoney}
                        step='0.01'
                        min='0'
                        onChange={handleInputChange}/>
                </div>
            </div>
            <div className='mt-8 flex w-1/2 justify-center'>
                <Button
                        className="text-white text-lg font-semibold bg-background rounded-full ring ring-blue-400">Transfer!</Button>
            </div>
        </div>
    </div>
};