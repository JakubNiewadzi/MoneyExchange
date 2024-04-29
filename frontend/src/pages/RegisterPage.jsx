import {FormContainer} from "../components/FormContainer";
import {useState} from "react";
import {InputLabel, TextField} from "@mui/material";
import Button from "@mui/material/Button";
import {register} from "../services/authService";
import {performLogin} from "../state/slices/authSlice";
import {useDispatch} from "react-redux";

export const RegisterPage = () => {
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

    const validateForm = () => {
        let valid = true
        return valid
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        if(validateForm()){
            const tokens = await register(registrationRequest)
            if (tokens !== undefined) dispatchEvent(dispatch(performLogin({authToken: tokens?.authToken, refreshToken: tokens?.refreshToken, email: registrationRequest.email})))
        }
    }

    return <FormContainer handleSubmit={handleSubmit}>
        <h2 className="text-center text-2xl font-semibold mb-4">Register</h2>
        <div className='flex flex-row mb-2 justify-between'>
            <div className='flex flex-col'>
                <InputLabel className="mb-2">First Name</InputLabel>
                <TextField className="w-full"
                           name="firstName"
                           placeholder="Give us your name"
                           value={registrationRequest.firstName}
                           onChange={handleChange}
                           error={!!errors.firstName}
                           helperText={errors.firstName}
                           focused/>
            </div>
            <div className='flex flex-col'>
            <InputLabel className="mb-2">Last Name</InputLabel>
            <TextField className="w-full"
                       name="lastName"
                       placeholder="Give us your last name"
                       value={registrationRequest.lastName}
                       onChange={handleChange}
                       error={!!errors.lastName}
                       helperText={errors.lastName}
                       focused/>
            </div>
        </div>
        <InputLabel className="mb-2 mt-4">Email</InputLabel>
        <TextField className="w-full"
                   name="email"
                   placeholder="Give us your email"
                   value={registrationRequest.email}
                   onChange={handleChange}
                   error={!!errors.email}
                   helperText={errors.email}
                   focused/>
        <InputLabel className="mb-2 mt-4">Password</InputLabel>
        <TextField className="w-full"
                   name="password"
                   type="password"
                   placeholder="Give us your password :<"
                   value={registrationRequest.password}
                   onChange={handleChange}
                   error={!!errors.password}
                   helperText={errors.password}
                   focused/>
        <InputLabel className="mb-2 mt-4">Repeat Password</InputLabel>
        <TextField className="w-full"
                   name="repeatPassword"
                   type="password"
                   placeholder="Give us your password :<"
                   value={repeatPassword}
                   onChange={handleChange}
                   error={!!errors.repeatPassword}
                   helperText={errors.repeatPassword}
                   focused/>
        <Button type="submit" variant="contained" className="bg-black mt-4 w-full">
            Submit
        </Button>
    </FormContainer>
}