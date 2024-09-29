import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import {NavLink} from "react-router-dom";
import {useSelector} from "react-redux";
import {useState} from "react";
import {MdArrowDropDown, MdArrowDropUp} from "react-icons/md";
import {CurrencyBanner} from "./CurrencyBanner";
import {ProfileButton} from "./ProfileButton";
import {NotificationButton} from "./NotificationButton";

export const Navbar = () => {
    const isLoggedIn = useSelector(state => state.auth.isLoggedIn);

    const [visible, setVisible] = useState(true);
    const [profileDropBarOpen, setProfileDropBarOpen] = useState(false);
    const [notificationDropBar, setNotificationDropBarOpen] = useState(false);

    const handleProfileDropBar = () => {
        setNotificationDropBarOpen(false);
        setProfileDropBarOpen(!profileDropBarOpen);
    };

    const handleNotificationsDropBar = () => {
        setProfileDropBarOpen(false);
        setNotificationDropBarOpen(!notificationDropBar);
    };

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
                                <Button component={NavLink} to="/exchangeCurrencies" color="inherit">Exchange
                                    currencies</Button>
                                <Button component={NavLink} to="/transferMoney" color="inherit">Transfer money</Button>
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
                                <NotificationButton handleNotificationDropBar={handleNotificationsDropBar}
                                                    openNotifications={notificationDropBar}/>
                                <ProfileButton handleProfileDropBar={handleProfileDropBar}
                                               openProfile={profileDropBarOpen}/>
                            </>
                        }
                    </div>
                </Toolbar>
            </AppBar>
            <CurrencyBanner visible={visible}/>
        </div>
    );

};