import {Navigate, Outlet, useLocation} from "react-router";
import {useSelector} from "react-redux";
import {Navbar} from "../components/Navbar";

export const ProtectedRoute = () => {
    const isLoggedIn = useSelector(state => state.auth.isLoggedIn)
    const location = useLocation()

    if (!isLoggedIn) {
        return <Navigate to={"/login"} state={{prevUrl: location.pathname}}/>
    }

    return <>
        <Navbar/>
        <Outlet/>
    </>
}