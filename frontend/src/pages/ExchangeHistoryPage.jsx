import {useEffect, useState} from "react";
import Cookies from "js-cookie";
import {currencyExchangeApi} from "../api/currencyExchangeApi";
import {CurrencyAccountRecord} from "../components/CurrencyAccountRecord";

export const ExchangeHistoryPage = () => {

    const [pageNumber, setPageNumber] = useState(0);
    const [exchangeHistroy, setExchangeHistory] = useState([]);


    const token = Cookies.get('authToken');

    useEffect(() => {
        const fetchData = async () => {
            await currencyExchangeApi.getExchanges(token, pageNumber, 10)
                .then(res => setExchangeHistory(res.data))
                .catch(err => console.log(err));
        };
        fetchData();

    }, [pageNumber]);

    return <div className='flex flex-col justify-center'>
        <div className='flex w-full bg-darkGray mt-64'>
            <table className='flex w-full'>
                <thead className='flex w-full'>
                <tr className='flex flex-row w-full m-4 table-auto'>
                    <th scope="col" className="px-6 py-3">From</th>
                    <th scope="col" className="px-6 py-3">Amount</th>
                    <th scope="col" className="px-6 py-3">To</th>
                    <th scope="col" className="px-6 py-3">Amount</th>
                    <th scope="col" className="px-6 py-3">Time</th>
                    <th scope="col" className="px-6 py-3">Status</th>
                    <th scope="col" className="px-6 py-3">Actions</th>
                </tr>
                </thead>
                <tbody>
                {exchangeHistroy.map(currencyAccountRecord =>
                    <CurrencyAccountRecord/>
                ) }
                </tbody>
            </table>
        </div>
    </div>

};