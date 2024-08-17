import {FaRegUserCircle} from "react-icons/fa";
import Button from "@mui/material/Button";
import Cookies from "js-cookie";
import {logout} from "../state/slices/authSlice";
import {useDispatch} from "react-redux";
import {useNavigate} from "react-router";
import {useState} from "react";

export const ProfileButton = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [openAvatarDropdown, setOpenAvatarDropdown] = useState(false);

    const handleLogout = () => {
        Cookies.remove("refreshToken");
        Cookies.remove("authToken");
        dispatch(logout());
        navigate("/");
    };

    const toggleAvatarDropdown = () => {
        setOpenAvatarDropdown(!openAvatarDropdown);
        console.log(openAvatarDropdown);
    };


    return <Button onClick={toggleAvatarDropdown} color="inherit"
                   className="relative rounded-full transition-all duration-500">
        <FaRegUserCircle size={30}/>
        <div
            className={`fixed ${openAvatarDropdown ? 'block' : 'hidden'} bg-background rounded shadow-md mt-48 mr-36
                                         space-y-2`}>
            <div className='p-2 flex flex-col text-xs'>
                <div className='hover:bg-lightGray border-b border-lightGray p-2 w-44'
                     onClick={() => navigate("/myAccounts")}>My accounts
                </div>
                <div className='hover:bg-lightGray border-b border-lightGray p-2 w-44'
                     onClick={() => navigate("/exchangeHistory")}>Exchange history
                </div>
                <div className='hover:bg-lightGray border-b border-lightGray p-2 w-44'
                     onClick={() => navigate("/transferHistory")}>Transfer history
                </div>
                <div className='hover:bg-lightGray p-2 w-44'
                     onClick={handleLogout}>Sign Out
                </div>
            </div>
        </div>
    </Button>
};