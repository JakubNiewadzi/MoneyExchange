import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useLocation, useNavigate} from "react-router";
import Button from "@mui/material/Button";
import {InputLabel, TextField} from "@mui/material";
import {login} from "../services/authService";
import {performLogin} from "../state/slices/authSlice";
import {FormContainer} from "../components/FormContainer";
import {NavLink} from "react-router-dom";

export const LoginPage = () => {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')

    const [emailError, setEmailError] = useState('')
    const [passwordError, setPasswordError] = useState('')

    const navigate = useNavigate()
    const dispatch = useDispatch()
    const location = useLocation()
    const isLoggedIn = useSelector(state => state.auth.isLoggedIn)

    useEffect(() => {
        if (isLoggedIn)
            location?.state?.prevUrl ? navigate(location.state.prevUrl) : navigate("/")
    }, [isLoggedIn]);


    const validateForm = () => {
        let valid = true
        setEmailError('')
        setPasswordError('')
        if (!email) {
            valid = false
            setEmailError("Email is required")
        }
        if (!password) {
            valid = false
            setPasswordError("Password is required")
        }
        return valid
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        if (validateForm()) {
            const authData = await login({email: email, password: password})
            if (authData !== undefined) dispatch(performLogin({
                authToken: authData?.authToken,
                email: email,
                accountNumber: authData?.accountNumber
            }))
        }
    }

    const handleChange = (e) => {
        const name = e.target.name
        const data = e.target.value
        name === "email" ? setEmail(data) : setPassword(data)
    }

    return <FormContainer handleSubmit={handleSubmit}>
        <h2 className="text-center text-2xl font-semibold mb-4">Sign in</h2>
        <div className="mb-2 font-semibold">Email</div>
        <input type="text"
               className="border w-full bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:border-customBlue"
               name="email"
               placeholder="Give us your email"
               value={email}
               onChange={handleChange}
        />

        <div className="mb-2 mt-4 font-semibold">Password</div>
        <input className="border w-full bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:border-customBlue"
               name="password"
               type="password"
               placeholder="Give us your password :<"
               value={password}
               onChange={handleChange}
        />
        <div className='flex flex-row my-2 justify-between'>
            <Button type="submit" variant="contained" className="bg-darkGray mt-4 w-2/5">
                Sign in
            </Button>
            <Button component={NavLink} to="/" variant="contained" className="bg-lightGray mt-4 w-2/5">
                Back
            </Button>
        </div>
    </FormContainer>
}