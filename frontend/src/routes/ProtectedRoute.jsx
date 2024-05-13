import {Navigate, Outlet, useLocation, useNavigate} from "react-router";
import {useDispatch, useSelector} from "react-redux";
import {Navbar} from "../components/Navbar";
import {useEffect} from "react";
import Cookies from "js-cookie";
import {fetchAccountInfo, logout} from "../state/slices/authSlice";

export const ProtectedRoute = () => {
    const isLoggedIn = useSelector(state => state.auth.isLoggedIn)
    const dispatch = useDispatch()
    const navigate = useNavigate()
    const location = useLocation()

    useEffect(() => {
        const authToken = Cookies.get('authToken')
        const refreshToken = Cookies.get('refreshToken')
        if (authToken && refreshToken) {
            dispatch(fetchAccountInfo(authToken))
        }else{
            dispatch(logout())
            navigate("/")
        }

    }, []);

    if (!isLoggedIn) {
        return <Navigate to={"/login"} state={{prevUrl: location.pathname}}/>
    }

    return <>
        <Navbar/>
        <Outlet/>
    </>
}