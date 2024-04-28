import {Navigate, Outlet} from "react-router";
import {useSelector} from "react-redux";

export const ProtectedRoute = () => {
    const isLoggedIn = useSelector(state => state.auth.isLoggedIn)
    if(!isLoggedIn){
        return <Navigate to={"/login"}/>
    }

    return <Outlet/>
}