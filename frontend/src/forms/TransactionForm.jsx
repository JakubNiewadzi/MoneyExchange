import {FormContainer} from "../components/FormContainer";
import {InputLabel, TextField} from "@mui/material";
import {useState} from "react";
import Button from "@mui/material/Button";
import {useNavigate, useParams} from "react-router";
import {useSelector} from "react-redux";
import {currencyAccountApi} from "../api/currencyAccountApi";

export const TransactionForm = ({action}) => {

    const [amount, setAmount] = useState(0.0)
    const [amountError, setAmountError] = useState('')

    const params = useParams()
    const authToken = useSelector(state => state.auth.authToken)
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
        <InputLabel className="mb-2">Amount of money</InputLabel>
        <TextField type='number'
                   className="w-full"
                   name="amount"
                   placeholder="123.33"
                   value={amount}
                   onChange={handleChange}
                   error={!!amountError}
                   helperText={amountError}
                   focused
                   inputProps={{
                       maxLength: 13,
                       step: "0.01",
                       min: "0"
                   }}/>
        <Button type="submit" variant="contained" className="bg-darkBlue mt-4 w-full">
            Submit
        </Button>
    </FormContainer>

}