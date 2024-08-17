import {useEffect, useState} from "react";
import Cookies from "js-cookie";
import {useDispatch, useSelector} from "react-redux";
import {CurrencyHistoryRecord} from "../components/CurrencyHistoryRecord";
import Button from "@mui/material/Button";
import {IoIosArrowBack, IoIosArrowForward} from "react-icons/io";
import {MenuItem, Select} from "@mui/material";
import {changeTransferAmountPerPage, changeTransferPage, fetchTransferHistory} from "../state/slices/transferSlice";

export const TransferHistoryPage = () => {

    const pageNumber = useSelector(state => state.transfer.pageNumber);
    const amountPerPage = useSelector(state => state.transfer.amountPerPage);
    const totalAmount = useSelector(state => state.transfer.totalAmount);
    const pageAmount = useSelector(state => state.transfer.pageAmount);
    const transferHistory = useSelector(state => state.transfer.transferHistory);


    console.log(transferHistory)
    const [reload, setReload] = useState(false);

    const dispatch = useDispatch();

    const token = Cookies.get('authToken');

    useEffect(() => {
        dispatch(fetchTransferHistory({
            token: token,
            pageNumber: pageNumber,
            amountPerPage: amountPerPage
        }));
    }, [pageNumber, amountPerPage, reload]);

    useEffect(() => {
        onChangePage(0);
    }, [pageAmount]);

    const onChangePage = (amount) => {
        const newPage = pageNumber + amount;
        if (newPage < 0) {
            dispatch(changeTransferPage(0));
        } else if (newPage > pageAmount) {
            dispatch(changeTransferPage(pageAmount - 1));
        } else {
            dispatch(changeTransferPage(newPage));
        }
    };

    const onChangeAmountPerPage = (e) => {
        const value = e.target.value;
        dispatch(changeTransferAmountPerPage(value));
    };

    return <div className='flex flex-col justify-center'>
        <div className='flex w-full flex-col bg-darkGray mt-48 py-8 text-3xl'>
            <div className='w-full mb-8 pl-14 font-semibold'>Here you can find all of your <b
                className='text-blue-400'>  {totalAmount}  </b> transfers
            </div>
            <hr color="blue"/>
            <table className='table-auto w-full mt-2'>
                <thead className='w-full border-b-4 border-lightGray'>
                <tr className=' w-full m-4'>
                    <th scope="col" className="px-6 py-3">Receiver</th>
                    <th scope="col" className="px-6 py-3">Provider currency</th>
                    <th scope="col" className="px-6 py-3">Amount</th>
                    <th scope="col" className="px-6 py-3">Receiver currency</th>
                    <th scope="col" className="px-6 py-3">Time</th>
                    <th scope="col" className="px-6 py-3">Actions</th>
                </tr>
                </thead>
                <tbody>
                {transferHistory.map(transferRecord =>
                    <CurrencyHistoryRecord key={transferRecord.id}
                                           decreasedCurrencyCode={transferRecord.decreasedCurrencyCode}
                                           amountDecreased={transferRecord.amountDecreased}
                                           increasedCurrencyCode={transferRecord.increasedCurrencyCode}
                                           amountIncreased={transferRecord.amountIncreased}
                                           exchangeDateTime={transferRecord.exchangeDateTime}
                                           id={transferRecord.id}
                                           status={transferRecord.status}/>
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