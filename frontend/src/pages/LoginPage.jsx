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
                refreshToken: authData?.refreshToken,
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

    return <FormContainer handleSubmit={handleSubmit} className="bg-black">
        <h2 className="text-center text-2xl font-semibold mb-4">Login</h2>
        <InputLabel className="mb-2">Email</InputLabel>
        <TextField className="w-full"
                   name="email"
                   placeholder="Give us your email"
                   value={email}
                   onChange={handleChange}
                   error={!!emailError}
                   helperText={emailError}
                   focused/>
        <InputLabel className="mb-2 mt-4">Password</InputLabel>
        <TextField className="w-full"
                   name="password"
                   type="password"
                   placeholder="Give us your password :<"
                   value={password}
                   onChange={handleChange}
                   error={!!passwordError}
                   helperText={passwordError}
                   focused/>
        <div className='flex flex-row mb-2 justify-between'>
            <Button type="submit" variant="contained" className="bg-darkBlue mt-4 w-2/5">
                Login
            </Button>
            <Button component={NavLink} to="/" variant="contained" className="bg-lightBlue mt-4 w-2/5">
                Back
            </Button>
        </div>
    </FormContainer>
}