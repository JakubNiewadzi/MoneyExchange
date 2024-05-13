import {FormContainer} from "../components/FormContainer";
import {useState} from "react";
import Button from "@mui/material/Button";
import {useNavigate, useParams} from "react-router";
import {currencyAccountApi} from "../api/currencyAccountApi";
import Cookies from "js-cookie";

export const TransactionForm = ({action}) => {

    const [amount, setAmount] = useState(0.0)
    const [amountError, setAmountError] = useState('')

    const params = useParams()
    const authToken = Cookies.get('authToken')
    const navigate = useNavigate()


    const handleChange = (e) => {
        const data = e.target.value
        setAmount(data)
    }

    const onSubmit = async (e) => {
        e.preventDefault()
        console.log(amount)
        action === "Deposit" ? await currencyAccountApi.deposit(authToken, amount, params.id) :
            await currencyAccountApi.withdraw(authToken, amount, params.id)
        navigate("/myAccounts")
    }

    return <FormContainer handleSubmit={onSubmit}>
        <h2 className="text-center text-2xl font-semibold mb-4">{action}</h2>
        <div className="mb-2 font-semibold">Amount of money</div>
        <input type="number"
               className="border w-full bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:border-background"
               name="amount"
               placeholder="123.33"
               value={amount}
               onChange={handleChange}
               step='0.01'
               min='0'
        />
        <Button type="submit" variant="contained" className="bg-darkGray mt-4 w-full">
            Submit
        </Button>
    </FormContainer>

}