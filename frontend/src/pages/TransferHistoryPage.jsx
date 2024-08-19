import {useEffect, useState} from "react";
import Cookies from "js-cookie";
import {useDispatch, useSelector} from "react-redux";
import Button from "@mui/material/Button";
import {IoIosArrowBack, IoIosArrowForward} from "react-icons/io";
import {Checkbox, MenuItem, Select} from "@mui/material";
import {
    changeTransferAmountPerPage,
    changeTransferPage,
    fetchTransferHistory,
    setFilter
} from "../state/slices/transferSlice";

export const TransferHistoryPage = () => {

    const pageNumber = useSelector(state => state.transfer.pageNumber);
    const amountPerPage = useSelector(state => state.transfer.amountPerPage);
    const totalAmount = useSelector(state => state.transfer.totalAmount);
    const pageAmount = useSelector(state => state.transfer.pageAmount);
    const transferHistory = useSelector(state => state.transfer.transferHistory);
    const all = useSelector(state => state.transfer.all);
    const received = useSelector(state => state.transfer.received);
    const sent = useSelector(state => state.transfer.sent);

    const [reload, setReload] = useState(false);

    const dispatch = useDispatch();

    const token = Cookies.get('authToken');

    useEffect(() => {
        if (all === true) {
            dispatch(fetchTransferHistory({
                token: token,
                pageNumber: pageNumber,
                amountPerPage: amountPerPage
            }));
        } else if (received === true) {
            
        }
    }, [pageNumber, amountPerPage, reload, all, received, sent]);

    useEffect(() => {
        onChangePage(0);
    }, [pageAmount]);

    const onChangeFilter = (e) => {
        const name = e.target.name;

        if (name === 'all') {
            dispatch(setFilter({all: true, received: false, sent: false}));
        } else if (name === 'received') {
            dispatch(setFilter({all: false, received: true, sent: false}));
        } else if (name === 'sent') {
            dispatch(setFilter({all: false, received: false, sent: true}));
        }
    };

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
        <div className='flex w-full flex-col font-semibold bg-darkGray mt-48 pt-2 pb-8 text-3xl'>
            <div className='flex md:flex-row flex-col'>
                <div className='md:w-1/2 my-8 pl-14  w-full'>Here you can find all of your <b
                    className='text-blue-400'>  {totalAmount}  </b> transfers
                </div>
                <div className='flex flex-row w-1/2 justify-end mr-4'>
                    <div className='flex h-full items-center'>All</div>
                    <Checkbox checked={all} className={all ? 'text-blue-400' : 'text-white'} name='all'
                              onClick={onChangeFilter}/>
                    <div className='flex h-full items-center'>Received</div>
                    <Checkbox checked={received} className={received ? 'text-blue-400' : 'text-white'} name='received'
                              onClick={onChangeFilter}/>
                    <div className='flex h-full items-center'>Sent</div>
                    <Checkbox checked={sent} className={sent ? 'text-blue-400' : 'text-white'} name='sent'
                              onClick={onChangeFilter}/>
                </div>
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
                    <div>elo</div>
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