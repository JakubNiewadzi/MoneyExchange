export const CurrencyCard = ({code, exchangeRate, id, onClick}) => {
    return <li key={id} className="flex flex-col justify-center align-middle bg-darkGray items-center
    w-40 h-24 hover:bg-lightGray cursor-pointer ring-1.5 ring-background"
               onClick={() => onClick(id)}>
        <span className="mb-2">
           {code}
        </span>
        <span>
            Rates: {exchangeRate}
        </span>
    </li>

};