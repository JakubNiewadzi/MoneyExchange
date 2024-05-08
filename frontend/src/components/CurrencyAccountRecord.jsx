import Button from "@mui/material/Button";
import {useNavigate} from "react-router";

export const CurrencyAccountRecord = ({currencyId, code, balance, status, onActivate, onSuspend}) => {

    const navigate = useNavigate()

    return <tr className="border-b bg-background">
        <th scope="row" className="px-6 py-4">
            {code}
        </th>
        <th className="px-6 py-4">
            {balance.toFixed(2)}
        </th>
        <th className="px-6 py-4">
            {status}
        </th>
        <th className="px-6 py-4">
            {status === 'ACTIVE' ? (
                <div className='flex justify-between'>
                    <Button onClick={() => navigate(`deposit/${currencyId}`)} variant="contained" className="bg-darkGray w-1/4">Deposit</Button>
                    <Button onClick={() => navigate(`withdraw/${currencyId}`)} variant="contained" className="bg-darkGray w-1/4">Withdraw</Button>
                    <Button onClick={() => onSuspend(currencyId)} variant="contained" className="bg-lightGray w-1/4">Suspend</Button>

                </div>
            ) :<div className='flex justify-center'> <Button onClick={() => onActivate(currencyId)} variant="contained" className="bg-darkGray w-full">Activate</Button> </div>
            }

        </th>
    </tr>
}