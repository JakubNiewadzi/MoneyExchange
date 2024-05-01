import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import {NavLink} from "react-router-dom";
import {useSelector} from "react-redux";

export const Navbar = () => {
    const isLoggedIn = useSelector(state => state.auth.isLoggedIn)

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
                            <Button color="inherit">My accounts</Button>
                            <Button color="inherit">Exchange currencies</Button>
                            <Button color="inherit">Transfer money</Button>
                            <Button color="inherit">Exchange rates</Button>
                        </div>
                    </div>
                </div>
                <div>
                    {!isLoggedIn ?
                        (<><Button component={NavLink} to="/login" color="inherit">Sign in</Button>
                            <Button component={NavLink} to="/register" color="inherit">Sign up</Button></>) :
                        (<Button component={NavLink} to="/logout" color="inherit">Logout</Button>)}

                </div>
            </Toolbar>
        </AppBar>
    </Box>
}