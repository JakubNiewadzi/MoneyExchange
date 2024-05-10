import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {changeFilter, fetchCurrencyAccounts} from "../state/slices/currencyAccountsSlice";
import {Checkbox, CircularProgress} from "@mui/material";
import {CurrencyAccountRecord} from "../components/CurrencyAccountRecord";
import {currencyAccountApi} from "../api/currencyAccountApi";
import Cookies from "js-cookie";

export const MyAccountsPage = () => {

    const currencyAccounts = useSelector(state => state.currencyAccount.currencyAccounts)
    const accountNumber = useSelector(state => state.auth.accountNumber)
    const status = useSelector(state => state.currencyAccount.status)
    const isFilterActive = useSelector(state => state.currencyAccount.isFilterActive)

    const [activeFilter, setActiveFilter] = useState(isFilterActive)
    const [reload, setReload] = useState(false)

    console.log(isFilterActive)
    console.log(activeFilter)

    const authToken = Cookies.get('authToken')

    const dispatch = useDispatch()

    useEffect(() => {
        console.log(authToken)
        dispatch(fetchCurrencyAccounts(
            {
                token: authToken,
                isFilterActive: activeFilter
            }
        ))
    }, [activeFilter, reload]);

    const onChangeFilter = () => {
        setActiveFilter(!activeFilter)
        dispatch(changeFilter())
    }

    const onActivate = async (currencyId) => {
        await currencyAccountApi.activateCurrencyAccount(authToken, currencyId)
        setReload(!reload)
    }

    const onSuspend = async (currencyId) => {
        await currencyAccountApi.suspendCurrencyAccount(authToken, currencyId)
        setReload(!reload)
    }


    return <div className='flex justify-center'>
        <div className='w-11/12 mt-56 mb-8 flex flex-col bg-darkGray p-8 text-3xl
        rounded-lg shadow-lg'>
            <div className='flex flex-row justify-between'>
                <div>Currencies for account with number</div>
                <div>
                    Only active accounts
                    <Checkbox checked={activeFilter} className='text-white' onClick={onChangeFilter}/>
                </div>
            </div>
            <div className='text-3xl mt-4 mb-8 font-bold'>{accountNumber} </div>
            <hr/>
            <div className='mt-8 relative overflow-x-auto'>
                {status === 'succeeded' ? currencyAccounts.length !== 0 ?
                        <table className='table-auto min-w-full text-left rtl:text-right'>
                            <thead className='uppercase bg-darkGray'>
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
                            {currencyAccounts.map(currencyAccount =>
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
                        <CircularProgress size={120}/>
                    </div>
                }
            </div>
        </div>
    </div>
}