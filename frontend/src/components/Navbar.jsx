import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import {NavLink} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {logout} from "../state/slices/authSlice";
import {useNavigate} from "react-router";
import Cookies from "js-cookie";
import {useEffect, useState} from "react";
import {fetchCurrencies} from "../state/slices/currencySlice";
import {CurrencyCard} from "./CurrencyCard";
import {MdArrowDropDown, MdArrowDropUp} from "react-icons/md";

export const Navbar = () => {
    const isLoggedIn = useSelector(state => state.auth.isLoggedIn)
    const calculatingCurrency = useSelector(state => state.currency.calculatingCurrency)
    const currencies = useSelector(state => state.currency.currencies)

    const dispatch = useDispatch()
    const navigate = useNavigate()

    const [exchangeRateCalculator, setExchangeRateCalculator] = useState(calculatingCurrency)
    const [offset, setOffset] = useState(0);
    const [contentWidth, setContentWidth] = useState(0);
    const [visible, setVisible] = useState(true);

    useEffect(() => {
        dispatch(fetchCurrencies(exchangeRateCalculator))
    }, [exchangeRateCalculator]);

    const handleClick = (currencyId) => {
        if (currencyId !== exchangeRateCalculator) {
            setExchangeRateCalculator(currencyId)
        }
    }

    useEffect(() => {
        if (currencies.length !== 0 && visible) {
            const bannerContent = document.getElementById('banner-content');
            setContentWidth(bannerContent.offsetWidth);
            const interval = setInterval(() => {
                setOffset(prevOffset => {
                    const newOffset = prevOffset - 2;
                    if (newOffset <= -contentWidth) {
                        return 0;
                    } else {
                        return newOffset;
                    }
                });
            }, 25);

            return () => clearInterval(interval);
        }
    }, [currencies, contentWidth, visible]);

    const handleLogout = () => {
        Cookies.remove("refreshToken")
        Cookies.remove("authToken")
        dispatch(logout())
        navigate("/")
    }

    return <div className="absolute">
        <AppBar className="bg-darkGray">
            <Toolbar className="flex justify-between ">
                <div className='flex h-full'>
                    <div className="items-center">
                        <Typography variant="h6">
                            Money Exchange
                        </Typography>
                    </div>
                    <div className="ml-6 items-center">
                        <div>
                            <Button component={NavLink} to="/" color="inherit">Home</Button>
                            <Button component={NavLink} to="/myAccounts" color="inherit">My accounts</Button>
                            <Button component={NavLink} to="/exchangeCurrencies" color="inherit">Exchange
                                currencies</Button>
                            <Button color="inherit">Transfer money</Button>
                        </div>
                    </div>
                </div>
                <div>
                    {visible ?
                        <Button className='rounded-full' color="inherit" onClick={() => setVisible(!visible)}><MdArrowDropUp
                            size={30}/></Button> :
                        <Button className='rounded-full' color="inherit" onClick={() => setVisible(!visible)}><MdArrowDropDown size={30}/>
                        </Button>
                    }
                    {!isLoggedIn ?
                        (<><Button component={NavLink} to="/login" color="inherit">Sign in</Button>
                            <Button component={NavLink} to="/register" color="inherit">Sign up</Button></>) :
                        (<Button onClick={handleLogout} color="inherit">Sign out</Button>)}
                </div>
            </Toolbar>
        </AppBar>
        {visible ? <div className="overflow-hidden fixed z-50 flex mt-16 w-full">
            <div className="flex" id="banner-content" style={{transform: `translateX(${offset}px)`}}>
                {currencies.map((currency) =>
                    <CurrencyCard key={currency.id}
                                  name={currency.name}
                                  code={currency.code}
                                  exchangeRate={currency.exchangeRate}
                                  id={currency.id}
                                  onClick={handleClick}/>
                )}
            </div>
        </div> : ''}
        {/*{status === 'loading' ?*/}
        {/*    <div className='w-full flex justify-center'>*/}
        {/*        <CircularProgress size={40}/>*/}
        {/*    </div> : ''}*/}

    </div>
}