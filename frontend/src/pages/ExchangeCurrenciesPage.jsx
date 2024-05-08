import {MenuItem, Select} from "@mui/material";
import {useSelector} from "react-redux";

export const ExchangeCurrenciesPage = () => {

    const currencies = useSelector(state => state.currency.currencies)

    return <div className='flex flex-row w-full justify-center mt-14'>
        <div className='relative'>
            <button className="text-white px-4 py-2 rounded-bl focus:outline-none focus:shadow-outline" >AA</button>
        </div>
    </div>

}