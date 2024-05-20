import {useEffect, useState} from "react";
import Cookies from "js-cookie";
import {useDispatch, useSelector} from "react-redux";
import {changeAmountPerPage, changePage, fetchCurrencyExchangeHistory} from "../state/slices/currencyExchangeSlice";
import {CurrencyHistoryRecord} from "../components/CurrencyHistoryRecord";
import Button from "@mui/material/Button";
import {IoIosArrowBack, IoIosArrowForward} from "react-icons/io";
import {currencyExchangeApi} from "../api/currencyExchangeApi";
import {MenuItem, Select} from "@mui/material";

export const ExchangeHistoryPage = () => {

    const pageNumber = useSelector(state => state.currencyExchange.pageNumber);
    const amountPerPage = useSelector(state => state.currencyExchange.amountPerPage);
    const totalAmount = useSelector(state => state.currencyExchange.totalAmount);
    const pageAmount = useSelector(state => state.currencyExchange.pageAmount);
    const exchangeHistory = useSelector(state => state.currencyExchange.currencyExchangeHistory);

    const [reload, setReload] = useState(false);

    const dispatch = useDispatch();

    const token = Cookies.get('authToken');

    useEffect(() => {
        console.log(pageNumber);
        dispatch(fetchCurrencyExchangeHistory({
            token: token,
            pageNumber: pageNumber,
            amountPerPage: amountPerPage
        }));
    }, [pageNumber, amountPerPage, reload]);

    console.log(exchangeHistory);

    const onChangePage = (amount) => {
        const newPage = pageNumber + amount;
        if (newPage >= 0 && newPage < pageAmount)
            dispatch(changePage(newPage));
    };

    const onRevert = async (id) => {
        await currencyExchangeApi.revertExchange(token, id);
        setReload(!reload);
    };

    const onChangeAmountPerPage = (e) => {
        const value = e.target.value;
        console.log(value);
        dispatch(changeAmountPerPage(value));
    };

    return <div className='flex flex-col justify-center'>
        <div className='flex w-full flex-col bg-darkGray mt-48 py-8 text-3xl'>
            <div className='w-full mb-8 pl-14 font-semibold'>Here you can find any of your <b
                className='text-blue-400'>  {totalAmount}  </b> exchanges
            </div>
            <hr color="blue"/>
            <table className='table-auto w-full mt-2'>
                <thead className='w-full border-b-4 border-lightGray'>
                <tr className=' w-full m-4'>
                    <th scope="col" className="px-6 py-3">From</th>
                    <th scope="col" className="px-6 py-3">Amount</th>
                    <th scope="col" className="px-6 py-3">To</th>
                    <th scope="col" className="px-6 py-3">Amount</th>
                    <th scope="col" className="px-6 py-3">Time</th>
                    <th scope="col" className="px-6 py-3">Actions</th>
                </tr>
                </thead>
                <tbody>
                {exchangeHistory.map(currencyAccountRecord =>
                    <CurrencyHistoryRecord key={currencyAccountRecord.id}
                                           decreasedCurrencyCode={currencyAccountRecord.decreasedCurrencyCode}
                                           amountDecreased={currencyAccountRecord.amountDecreased}
                                           increasedCurrencyCode={currencyAccountRecord.increasedCurrencyCode}
                                           amountIncreased={currencyAccountRecord.amountIncreased}
                                           exchangeDateTime={currencyAccountRecord.exchangeDateTime}
                                           id={currencyAccountRecord.id}
                                           status={currencyAccountRecord.status}
                                           onRevert={onRevert}/>
                )}
                </tbody>
            </table>
            <div className='flex w-full pt-4 px-16'>
                <div>
                    <div className='text-xl font-semibold flex w-52 items-center'>Records per page</div>
                    <Select
                        name='select'
                        className="text-white font-semibold w-full bg-lightGray rounded-bl focus:outline-none focus:ring ring-background"
                        value={amountPerPage}
                        onChange={onChangeAmountPerPage}>
                        <MenuItem value={5}>5</MenuItem>
                        <MenuItem value={10}>10</MenuItem>
                        <MenuItem value={20}>20</MenuItem>
                    </Select>
                </div>
                <div className='flex w-full justify-end'>
                    <Button color='inherit'
                            className='rounded-full h-16 mx-2'
                            onClick={() => onChangePage(-1)}>
                        <IoIosArrowBack size={40}/>
                    </Button>
                    <div className='h-16 w-8 flex items-center justify-center'>{pageNumber + 1}</div>
                    <Button color='inherit'
                            className='rounded-full h-16 mx-2'
                            onClick={() => onChangePage(1)}>
                        <IoIosArrowForward size={40}/>
                    </Button>
                    <div className='h-16 flex items-center'>{pageAmount}</div>
                </div>
            </div>
        </div>
    </div>

};