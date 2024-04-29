import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router";
import Button from "@mui/material/Button";
import {InputLabel, TextField} from "@mui/material";
import {login} from "../services/authService";
import {performLogin} from "../state/slices/authSlice";
import {FormContainer} from "../components/FormContainer";

export const LoginPage = () => {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')

    const [emailError, setEmailError] = useState('')
    const [passwordError, setPasswordError] = useState('')

    const navigate = useNavigate()
    const dispatch = useDispatch()
    const isLoggedIn = useSelector(state => state.auth.isLoggedIn)

    useEffect(() => {
        if (isLoggedIn) navigate("/")
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
            const tokens = await login({email: email, password: password})
            if (tokens !== undefined) dispatch(performLogin({
                authToken: tokens?.authToken,
                refreshToken: tokens?.refreshToken,
                email: email
            }))
        }
    }

    const handleChange = (e) => {
        const name = e.target.name
        const data = e.target.value
        name === "email" ? setEmail(data) : setPassword(data)
    }

    return <FormContainer handleSubmit={handleSubmit}>
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
        <Button type="submit" variant="contained" className="bg-black mt-4 w-full">
            Login
        </Button>
    </FormContainer>
}