import {useEffect, useState} from "react";
import Button from "@mui/material/Button";
import {register} from "../services/authService";
import {fetchAccountInfo} from "../state/slices/authSlice";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router";
import {NavLink} from "react-router-dom";
import Cookies from "js-cookie";

export const RegisterPage = () => {
    const isLoggedIn = useSelector(state => state.auth.isLoggedIn);
    const navigate = useNavigate();
    const [registrationRequest, setRegistrationRequest] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: ''
    });

    const [errors, setErrors] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        repeatPassword: '',
        fetchError: ''
    });


    const [repeatPassword, setRepeatPassword] = useState('');

    const dispatch = useDispatch();
    const handleChange = (e) => {
        const name = e.target.name;
        const data = e.target.value;
        name !== 'repeatPassword' ? setRegistrationRequest({
            ...registrationRequest,
            [name]: data
        }) : setRepeatPassword(data);
    };

    useEffect(() => {
        if (isLoggedIn) navigate('/');
    }, [isLoggedIn]);

    const validateForm = () => {
        let valid = true;
        return valid;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (validateForm()) {
            const tokenResponse = await register(registrationRequest);
            console.log(tokenResponse);
            const authToken = Cookies.get("authToken");
            if (authToken !== undefined) {
                dispatch(fetchAccountInfo(authToken));
            }
            if (tokenResponse.status === 400 && typeof tokenResponse.data === 'string') {
                setErrors({...errors, fetchError: tokenResponse.data});
            }
        }
    };

    return <div className='justify-center items-center flex h-[100vh] w-full'>
        <form className='border-lightGray border rounded-lg md:w-1/3 w-full p-8 m-2' onSubmit={handleSubmit}>
            <h2 className="text-center text-2xl font-semibold mb-4">Register</h2>
            <div className='flex flex-col md:flex-row md:justify-between mb-2'>
                <div className='flex flex-col md:w-1/2 md:mr-2'>
                    <div className="mb-2 font-semibold">First Name</div>
                    <input
                        className="border w-full bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:border-darkGray"
                        name="firstName"
                        placeholder="Give us your name"
                        value={registrationRequest.firstName}
                        onChange={handleChange}/>
                </div>
                <div className='flex flex-col md:w-1/2 md:ml-2'>
                    <div className="mb-2 font-semibold">Last Name</div>
                    <input
                        className="border w-full bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:border-darkGray"
                        name="lastName"
                        placeholder="Give us your last name"
                        value={registrationRequest.lastName}
                        onChange={handleChange}/>
                </div>
            </div>
            <div className="mb-2 mt-4 font-semibold">Email</div>
            <input
                className="border w-full bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:border-customBlue"
                name="email"
                placeholder="Give us your email"
                value={registrationRequest.email}
                onChange={handleChange}/>
            <div className="mb-2 mt-4 font-semibold">Password</div>
            <input
                className="border w-full bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:border-customBlue"
                name="password"
                type="password"
                placeholder="Give us your password :<"
                value={registrationRequest.password}
                onChange={handleChange}/>
            <div className="mb-2 mt-4 font-semibold">Repeat password</div>
            <input
                className="border w-full bg-lightGray border-lightGray rounded-md py-2 px-4 focus:outline-none focus:border-customBlue"
                name="repeatPassword"
                type="password"
                placeholder="Give us your password :<"
                value={repeatPassword}
                onChange={handleChange}/>
            <div className='text-red-500 font-semibold mt-2'>{errors.fetchError}</div>
            <div className='flex flex-col md:flex-row mb-2 md:justify-between'>
                <Button type="submit" variant="contained" className="bg-darkGray mt-4 md:w-1/3">
                    Register
                </Button>
                <Button component={NavLink} to="/" variant="contained" className="bg-lightGray mt-4 md:w-1/3">
                    Back
                </Button>
            </div>
        </form>
    </div>

};