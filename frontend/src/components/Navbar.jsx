import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import {NavLink} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {logout} from "../state/slices/authSlice";
import {useNavigate} from "react-router";

export const Navbar = () => {
    const isLoggedIn = useSelector(state => state.auth.isLoggedIn)
    const dispatch = useDispatch()
    const navigate = useNavigate()

    const handleLogout = () => {
        dispatch(logout())
        navigate("/")
    }

    return <Box>
        <AppBar className="bg-customBlue" position="static">
            <Toolbar className="flex justify-between">
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
                            <Button color="inherit">Exchange currencies</Button>
                            <Button color="inherit">Transfer money</Button>
                        </div>
                    </div>
                </div>
                <div>
                    {!isLoggedIn ?
                        (<><Button component={NavLink} to="/login" color="inherit">Sign in</Button>
                            <Button component={NavLink} to="/register" color="inherit">Sign up</Button></>) :
                        (<Button onClick={handleLogout} color="inherit">Sign out</Button>)}

                </div>
            </Toolbar>
        </AppBar>
    </Box>
}