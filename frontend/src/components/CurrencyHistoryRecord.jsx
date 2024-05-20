import Button from "@mui/material/Button";
import {formatDate, hasHourPassed} from "../services/dateService";

export const CurrencyHistoryRecord = ({
                                          decreasedCurrencyCode,
                                          amountDecreased,
                                          increasedCurrencyCode,
                                          amountIncreased,
                                          exchangeDateTime,
                                          onRevert,
                                          id,
                                          status
                                      }) => {

    return <tr className="border-b border-lightGray hover:bg-background">
        <th scope="row" className="px-6 py-4">
            {decreasedCurrencyCode}
        </th>
        <th className="px-6 py-4">
            {amountDecreased.toFixed(2)}
        </th>
        <th className="px-6 py-4">
            {increasedCurrencyCode}
        </th>
        <th className="px-6 py-4">
            {amountIncreased.toFixed(2)}
        </th>
        <th className="px-6 py-4">
            {formatDate(exchangeDateTime)}
        </th>
        <th>
            { status !== 'REVERTED' ?
                <div><Button color='inherit' className='bg-lightGray w-2/3' onClick={() => onRevert(id)}>Revert</Button>
                </div>
                :  hasHourPassed(exchangeDateTime)? <div>
                    Already reverted
                </div> : <div>Too late to revert</div>
            }
        </th>
    </tr>;

};