import {useEffect, useState} from "react";
import Cookies from "js-cookie";
import {useDispatch, useSelector} from "react-redux";
import {changePage, fetchCurrencyExchangeHistory} from "../state/slices/currencyExchangeSlice";
import {CurrencyHistoryRecord} from "../components/CurrencyHistoryRecord";
import Button from "@mui/material/Button";
import {IoIosArrowBack, IoIosArrowForward} from "react-icons/io";
import {currencyExchangeApi} from "../api/currencyExchangeApi";

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

    return <div className='flex flex-col justify-center'>
        <div className='flex w-full flex-col bg-darkGray mt-48 py-8 text-3xl'>
            <table className='table-auto w-full'>
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
                                           onRevert={onRevert}/>
                )}
                </tbody>
            </table>
            <div className='flex w-full justify-end pt-4 pr-8'>
                <Button color='inherit'
                        className='rounded-full h-16 mx-2'
                        onClick={() => onChangePage(-1)}>
                    <IoIosArrowBack size={40}/>
                </Button>
                <div className='h-16 w-8 flex items-center justify-center'>{pageNumber + 1}</div>
                <Button color='inherit'
                        className='rounded-full h-16 mx-2'
                        onClick={() => onChangePage(1)}>
                    <IoIosArrowForward  size={40}/>
                </Button>
                <div className='h-16 flex items-center'>{pageAmount}</div>
            </div>
        </div>
    </div>

};