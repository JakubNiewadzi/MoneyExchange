export const CurrencyCard = ({name, code, exchangeRate, id, onClick}) => {
    ///TODO: complete rework cause its ugly
    return <li key={id} className="flex flex-col justify-center align-middle bg-darkBlue p-4 items-center m-4
    w-80 h-64 hover:bg-lightBlue rounded-lg cursor-pointer shadow-md ring-2 ring-opacity-40"
               onClick={() => onClick(id)}>
        <span className="mb-4">
           Code: {code}
        </span>
        <span>
            Rates: {exchangeRate}
        </span>
    </li>

}