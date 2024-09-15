import {formatDate} from "../services/dateService";

export const TransferHistoryRecord = ({receiver, senderCurrency, amount, receiverCurrency, time, status}) => {
    return <tr className="border-b border-lightGray hover:bg-background">
        <th scope="row" className="px-6 py-4">
            {receiver}
        </th>
        <th className="px-6 py-4">
            {senderCurrency}
        </th>
        <th className="px-6 py-4">
            {amount.toFixed(2)}
        </th>
        <th className="px-6 py-4">
            {receiverCurrency}
        </th>
        <th className="px-6 py-4">
            {formatDate(time)}
        </th>
        <th>
            {status}
        </th>
    </tr>;

};