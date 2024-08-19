import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {
    changeAccountsAmountPerPage,
    changeAccountsPage,
    changeFilter,
    fetchCurrencyAccountsPage
} from "../state/slices/currencyAccountsSlice";
import {Checkbox, CircularProgress, MenuItem, Select} from "@mui/material";
import {CurrencyAccountRecord} from "../components/CurrencyAccountRecord";
import {currencyAccountApi} from "../api/currencyAccountApi";
import Cookies from "js-cookie";
import Button from "@mui/material/Button";
import {IoIosArrowBack, IoIosArrowForward} from "react-icons/io";

export const MyAccountsPage = () => {

    const currencyAccounts = useSelector(state => state.currencyAccount.currencyAccounts);
    const accountNumber = useSelector(state => state.auth.accountNumber);
    const status = useSelector(state => state.currencyAccount.status);
    const isFilterActive = useSelector(state => state.currencyAccount.isFilterActive);
    const pageAmount = useSelector(state => state.currencyAccount.pageAmount);
    const totalAmount = useSelector(state => state.currencyAccount.totalAmount);
    const pageNumber = useSelector(state => state.currencyAccount.pageNumber);
    const amountPerPage = useSelector(state => state.currencyAccount.amountPerPage);

    const [activeFilter, setActiveFilter] = useState(isFilterActive);
    const [reload, setReload] = useState(false);

    const authToken = Cookies.get('authToken');

    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(fetchCurrencyAccountsPage(
            {
                token: authToken,
                isFilterActive: activeFilter,
                pageNumber: pageNumber,
                pageSize: amountPerPage
            }
        ))
    }, [activeFilter, reload, amountPerPage, pageNumber]);

    useEffect(() => {
        onChangePage(0);
    }, [pageAmount]);

    const onChangeFilter = () => {
        setActiveFilter(!activeFilter);
        dispatch(changeFilter());
    };

    const onActivate = async (currencyId) => {
        await currencyAccountApi.activateCurrencyAccount(authToken, currencyId);
        setReload(!reload);
    };

    const onSuspend = async (currencyId) => {
        await currencyAccountApi.suspendCurrencyAccount(authToken, currencyId);
        setReload(!reload);
    };

    const onChangePage = (amount) => {
        const newPage = pageNumber + amount;
        if (newPage < 0) {
            dispatch(changeAccountsPage(0));
        } else if (newPage >= pageAmount && pageAmount > 0) {
            dispatch(changeAccountsPage(pageAmount - 1));
        } else {
            dispatch(changeAccountsPage(newPage));
        }
    };

    const onChangeAmountPerPage = (e) => {
        const value = e.target.value;
        dispatch(changeAccountsAmountPerPage(value));
    };


    return <div className='flex justify-center'>
        <div className='font-semibold w-full mt-56 mb-8 flex flex-col bg-darkGray p-8 text-3xl
        rounded-lg shadow-lg'>
            <div className='flex flex-row justify-between'>
                <div >Currencies for account with number</div>
                <div>
                    Only active accounts
                    <Checkbox checked={activeFilter} className={activeFilter ? 'text-blue-400' : 'text-white'} onClick={onChangeFilter}/>
                </div>
            </div>
            <div className='text-3xl mt-4 mb-8 font-bold text-blue-400'>{accountNumber} </div>
            <hr/>
            <div className='mt-8 relative overflow-x-auto'>
                {status === 'succeeded' ? currencyAccounts?.length !== 0 ?
                        <table className='table-auto min-w-full text-left rtl:text-right'>
                            <thead className='bg-darkGray border-b-4 border-lightGray'>
                            <tr>
                                <th scope="col" className="px-6 py-3">
                                    Code
                                </th>
                                <th scope="col" className="px-6 py-3">
                                    Balance
                                </th>
                                <th scope="col" className="px-6 py-3">
                                    Status
                                </th>
                                <th scope="col" className="flex justify-center px-6 py-3">
                                    Action
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            {currencyAccounts?.map(currencyAccount =>
                                <CurrencyAccountRecord key={currencyAccount.currencyId}
                                                       currencyId={currencyAccount.currencyId}
                                                       code={currencyAccount.currencyCode}
                                                       balance={currencyAccount.balance}
                                                       status={currencyAccount.status}
                                                       onActivate={onActivate}
                                                       onSuspend={onSuspend}/>)}
                            </tbody>
                        </table> :
                        <span>
                        You do not have any currency accounts open
                    </span> :
                    <div className='w-full mt-14 flex justify-center p-8'>
                        <CircularProgress className='text-white' size={120}/>
                    </div>
                }
            </div>
            <div className='flex w-full pt-4 pl-6 pr-24'>
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