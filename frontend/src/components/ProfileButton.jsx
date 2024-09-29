import {FaRegUserCircle} from "react-icons/fa";
import Button from "@mui/material/Button";
import Cookies from "js-cookie";
import {logout} from "../state/slices/authSlice";
import {useDispatch} from "react-redux";
import {useNavigate} from "react-router";
import {useState} from "react";

export const ProfileButton = ({openProfile, handleProfileDropBar}) => {

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleLogout = () => {
        Cookies.remove("refreshToken");
        Cookies.remove("authToken");
        dispatch(logout());
        navigate("/");
    };

    return <Button onClick={handleProfileDropBar} color="inherit"
                   className="relative rounded-full transition-all duration-500">
        <FaRegUserCircle size={30}/>
        <div
            className={`fixed ${openProfile ? 'block' : 'hidden'} bg-background rounded shadow-md top-12 right-2
                                         space-y-2`}>
            <div className='p-2 flex flex-col text-xs w-44'>
                <div className='hover:bg-lightGray border-b border-lightGray p-2'
                     onClick={() => navigate("/myAccounts")}>My accounts
                </div>
                <div className='hover:bg-lightGray border-b border-lightGray p-2'
                     onClick={() => navigate("/exchangeHistory")}>Exchange history
                </div>
                <div className='hover:bg-lightGray border-b border-lightGray p-2'
                     onClick={() => navigate("/transferHistory")}>Transfer history
                </div>
                <div className='hover:bg-lightGray border-b border-lightGray p-2'
                     onClick={() => navigate("/activeMessages")}>Active plans
                </div>
                <div className='hover:bg-lightGray p-2'
                     onClick={handleLogout}>Sign Out
                </div>
            </div>
        </div>
    </Button>
};