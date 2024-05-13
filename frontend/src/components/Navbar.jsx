import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import {NavLink} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {logout} from "../state/slices/authSlice";
import {useNavigate} from "react-router";
import Cookies from "js-cookie";
import {useState} from "react";
import {MdArrowDropDown, MdArrowDropUp} from "react-icons/md";
import {FaBell, FaRegUserCircle} from "react-icons/fa";
import {CurrencyBanner} from "./CurrencyBanner";

export const Navbar = () => {
    const isLoggedIn = useSelector(state => state.auth.isLoggedIn)

    const dispatch = useDispatch()
    const navigate = useNavigate()

    const [openAvatarDropdown, setOpenAvatarDropdown] = useState(false)

    const [visible, setVisible] = useState(true);


    const handleLogout = () => {
        Cookies.remove("refreshToken")
        Cookies.remove("authToken")
        dispatch(logout())
        navigate("/")
    }

    const toggleAvatarDropdown = () => {
        setOpenAvatarDropdown(!openAvatarDropdown)
        console.log(openAvatarDropdown)
    }
    return (
        <div className="absolute">
            <AppBar className="bg-darkGray">
                <Toolbar className="flex justify-between">
                    <div className="flex h-full">
                        <div className="items-center">
                            <Typography variant="h6">
                                Money Exchange
                            </Typography>
                        </div>
                        <div className="ml-6 items-center hidden md:block">
                            <div>
                                <Button component={NavLink} to="/" color="inherit">Home</Button>
                                <Button component={NavLink} to="/myAccounts" color="inherit">My accounts</Button>
                                <Button component={NavLink} to="/exchangeCurrencies" color="inherit">Exchange
                                    currencies</Button>
                                <Button color="inherit">Transfer money</Button>
                            </div>
                        </div>
                    </div>
                    <div className='flex flex-row'>
                        {visible ?
                            <Button className="rounded-full" color="inherit" onClick={() => setVisible(!visible)}>
                                <MdArrowDropUp size={30}/>
                            </Button> :
                            <Button className="rounded-full" color="inherit" onClick={() => setVisible(!visible)}>
                                <MdArrowDropDown size={30}/>
                            </Button>
                        }
                        {!isLoggedIn ?
                            <>
                                <Button component={NavLink} to="/login" color="inherit">Sign in</Button>
                                <Button component={NavLink} to="/register" color="inherit">Sign up</Button>
                            </> :
                            <>
                                <Button color="inherit"
                                        className="relative rounded-full transition-all duration-500">
                                    <FaBell size={30}/>

                                </Button>
                                <Button onClick={toggleAvatarDropdown} color="inherit"
                                        className="relative rounded-full transition-all duration-500">
                                    <FaRegUserCircle size={30}/>
                                    <div
                                        className={`fixed ${openAvatarDropdown ? 'block' : 'hidden'} bg-background rounded shadow-md mt-24 mr-20
                                         space-y-2`}>
                                        <div className='p-2 flex flex-col'>
                                            <div className='hover:bg-lightGray p-2 rounded w-32'
                                                 onClick={handleLogout}>Sign Out
                                            </div>
                                        </div>
                                    </div>
                                </Button>
                            </>
                        }
                    </div>
                </Toolbar>
            </AppBar>
            <CurrencyBanner visible={visible}/>
        </div>
    );

}