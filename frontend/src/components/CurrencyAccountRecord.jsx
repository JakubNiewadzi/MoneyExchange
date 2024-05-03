import Button from "@mui/material/Button";

export const CurrencyAccountRecord = ({currencyId, code, balance, status, onActivate}) => {

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
                <>
                    <Button variant="contained" className="bg-darkBlue w-1/4">Deposit</Button>
                    <Button variant="contained" className="bg-darkBlue w-1/4">Withdraw</Button>
                    <Button variant="contained" className="bg-lightBlue w-1/4">Suspend</Button>

                </>
            ) : <Button onClick={() => onActivate(currencyId)} variant="contained" className="bg-darkBlue w-2/5">Activate</Button>
            }

        </th>
    </tr>
}