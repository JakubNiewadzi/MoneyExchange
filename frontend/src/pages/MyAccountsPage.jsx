import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {fetchCurrencyAccounts} from "../state/slices/currencyAccountsSlice";
import {Checkbox} from "@mui/material";

export const MyAccountsPage = () => {

    const currencyAccounts = useSelector(state => state.currencyAccount.currencyAccounts)
    const authToken = useSelector(state => state.auth.authToken)
    const accountNumber = useSelector(state => state.auth.accountNumber)

    const [activeFilter, setActiveFilter] = useState(false)

    const dispatch = useDispatch()

    useEffect(() => {
        console.log(activeFilter)
        dispatch(fetchCurrencyAccounts({token: authToken, isFilterActive: activeFilter}))
    }, [activeFilter]);

    const onChangeFilter = () => {
        setActiveFilter(!activeFilter)
    }

    return <div className='flex justify-center'>
        <div className='w-11/12 mt-8 flex flex-col bg-darkBlue p-8 text-3xl text-white rounded-lg'>
            <div className='flex flex-row justify-between'>
                <div>Currencies for account with number</div>
                <div>
                    Only active accounts
                    <Checkbox onClick={onChangeFilter}/>
                </div>
            </div>
            <div className='text-2xl mt-4 mb-8 font-bold'>{accountNumber} </div>
            <div></div>

        </div>
    </div>
}