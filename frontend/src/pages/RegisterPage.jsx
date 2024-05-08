import {FormContainer} from "../components/FormContainer";
import {useEffect, useState} from "react";
import {InputLabel, TextField} from "@mui/material";
import Button from "@mui/material/Button";
import {register} from "../services/authService";
import {performLogin} from "../state/slices/authSlice";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router";
import {NavLink} from "react-router-dom";

export const RegisterPage = () => {
    const isLoggedIn = useSelector(state => state.auth.isLoggedIn)
    const navigate = useNavigate()
    const [registrationRequest, setRegistrationRequest] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: ''
    })

    const [errors, setErrors] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        repeatPassword: ''
    })

    const [repeatPassword, setRepeatPassword] = useState('')

    const dispatch = useDispatch()
    const handleChange = (e) => {
        const name = e.target.name
        const data = e.target.value
        name !== 'repeatPassword' ? setRegistrationRequest({...registrationRequest, [name]: data}) : setRepeatPassword(data)
    }

    useEffect(() => {
        if (isLoggedIn) navigate('/')
    }, [isLoggedIn]);
    
    const validateForm = () => {
        let valid = true
        return valid
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        if(validateForm()){
            const authData = await register(registrationRequest)
            if (authData !== undefined) dispatch(performLogin({
                authToken: authData?.authToken,
                email: registrationRequest.email,
                accountNumber: authData?.accountNumber
            }))
        }
    }

    return <FormContainer handleSubmit={handleSubmit}>
        <h2 className="text-center text-2xl font-semibold mb-4">Register</h2>
        <div className='flex flex-row mb-2 justify-between'>
            <div className='flex flex-col'>
                <div className="mb-2 font-semibold">First Name</div>
                <input className="border w-full bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:border-darkGray"
                           name="firstName"
                           placeholder="Give us your name"
                           value={registrationRequest.firstName}
                           onChange={handleChange}/>
            </div>
            <div className='flex flex-col'>
                <div className="mb-2 font-semibold">Last Name</div>
                <input className="border w-full bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:border-darkGray"
                           name="lastName"
                       placeholder="Give us your last name"
                       value={registrationRequest.lastName}
                       onChange={handleChange}/>
            </div>
        </div>
        <div className="mb-2 mt-4 font-semibold">Email</div>
        <input className="border w-full bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:border-customBlue"
                   name="email"
                   placeholder="Give us your email"
                   value={registrationRequest.email}
                   onChange={handleChange}/>
        <div className="mb-2 mt-4 font-semibold">Password</div>
        <input className="border w-full bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:border-customBlue"
                   name="password"
                   type="password"
                   placeholder="Give us your password :<"
                   value={registrationRequest.password}
                   onChange={handleChange}/>
        <div className="mb-2 mt-4 font-semibold">Repeat password</div>
        <input className="border w-full bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:border-customBlue"
                   name="repeatPassword"
                   type="password"
                   placeholder="Give us your password :<"
                   value={repeatPassword}
                   onChange={handleChange}/>
        <div className='flex flex-row mt-4 mb-2s justify-between'>
            <Button type="submit" variant="contained" className="bg-darkGray mt-4 w-2/5">
                Register
            </Button>
            <Button component={NavLink} to="/" variant="contained" className="bg-lightGray mt-4 w-2/5">
                Back
            </Button>
        </div>
    </FormContainer>
}