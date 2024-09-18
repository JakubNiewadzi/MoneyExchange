import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {fetchDateMessages, fetchValueMessages} from "../state/slices/messageSlice";
import Cookies from "js-cookie";

export const ActiveMessagesPage = () => {

    const dateMessages = useSelector(state => state.message.dateMessages);
    const valueMessages = useSelector(state => state.message.valueMessages);
    const pageNumber = useSelector(state => state.message.pageNumber);
    const amountPerPage = useSelector(state => state.message.amountPerPage);

    const dispatch = useDispatch();
    const authToken = Cookies.get('authToken');
    useEffect(() => {
        dispatch(fetchValueMessages({token: authToken, pageNumber: pageNumber, pageSize: amountPerPage}));
        dispatch(fetchDateMessages({token: authToken, pageNumber: pageNumber, pageSize: amountPerPage}));
    }, []);

    useEffect(() => {
        console.log(dateMessages);
        console.log(valueMessages);
    }, [dateMessages, valueMessages]);

    return <div></div>;

};