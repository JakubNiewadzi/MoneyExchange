import {Navbar} from "../components/Navbar";
import {useDispatch} from "react-redux";
import Button from "@mui/material/Button";
import {useEffect} from "react";
import Cookies from "js-cookie";
import {fetchAccountInfo} from "../state/slices/authSlice";

export const HomePage = () => {

    const dispatch = useDispatch()

    useEffect(() => {
        const authToken = Cookies.get('authToken')
        const refreshToken = Cookies.get('refreshToken')
        if (authToken && refreshToken) {
            dispatch(fetchAccountInfo(authToken))
        }
    }, []);


    return <div className='bg-background'>
        <Navbar/>
        <div className='w-full h-[25vh]'></div>
        <div className='w-full h-[50vh] flex flex-col md:flex-row bg-darkGray p-8'>
            <div className='flex flex-col text-4xl font-bold md:w-2/5 text-center md:text-right'>
                <div>Welcome to the <b className='text-blue-300'>MODERN</b> money exchanging
                    experience.
                </div>
                <div className='md:h-full md:flex md:flex-col md:items-center md:justify-center md:w-full'>
                    <div>
                        Join now!
                    </div>
                    <div className='md:flex md:justify-center w-full mt-4 md:mt-2'>
                        <Button color='inherit'
                                className='md:w-32 z-0 flex w-full rounded-2xl bg-background ring ring-lightGray'>Sign
                            up</Button>
                    </div>
                </div>
            </div>
            <div className='text-4xl font-bold md:w-3/5 mt-8 md:mt-0'></div>
        </div>
        <div className='w-full h-[50vh] flex flex-col md:flex-row p-8'>
            <div className='text-4xl font-bold md:w-3/5 text-center md:text-right mb-8 md:mb-0'></div>
            <div className='text-4xl font-bold md:w-2/5 text-center md:text-left'>Manage <b
                className='text-blue-300'>ANY</b> currency you can imagine.
            </div>
        </div>
        <div className='w-full h-[50vh] flex flex-col md:flex-row bg-darkGray p-8'>
            <div className='text-4xl font-bold md:w-2/5 text-center md:text-right'>Mismanaged your account? You can
                revert any exchange made by mistake up to <b className='text-blue-300'>ONE HOUR</b> after making it.
            </div>
            <div className='text-4xl font-bold md:w-3/5 mt-8 md:mt-0'></div>
        </div>
    </div>
}