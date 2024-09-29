import {HiBell, HiBellAlert} from "react-icons/hi2";
import Button from "@mui/material/Button";
import {useEffect, useState} from "react";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import {bearerAuth} from "../api/bearerAuth";
import {addNotification, clearNotifications, removeNotifications} from "../state/slices/notificationSlice";
import {useDispatch, useSelector} from "react-redux";
import Cookies from "js-cookie";
import {handleNotificationString} from "../services/notificationService";

export const NotificationButton = ({openNotifications, handleNotificationDropBar}) => {

    const email = useSelector(state => state.auth.email);
    const notifications = useSelector(state => state.notification.notifications);

    const [isConnected, setIsConnected] = useState(false);

    const authToken = Cookies.get('authToken');
    const dispatch = useDispatch();


    useEffect(() => {
        if (email === null) {
            return;
        }
        const socket = new SockJS(process.env.REACT_APP_API_BACKEND_URL + "/messages");
        const client = Stomp.over(socket);

        client.connect({Authorization: bearerAuth(authToken)}, () => {
            console.log('WebSocket private connection established');
            setIsConnected(true);
            client.subscribe(`/queue/${email}/messages`, (message) => {
                message = handleNotificationString(message.body);
                if (!notifications.includes(message)) {
                    dispatch(addNotification(message));
                }
            });
        });

        return () => {
            if (isConnected) {
                client.disconnect();
                setIsConnected(false);
            }
        }
    }, []);

    const removeNotification = (index) => {
        let newNotifications = [...notifications];
        newNotifications = newNotifications.splice(index, 1);
        dispatch(removeNotifications(newNotifications));
    };

    return <>
        <Button color="inherit"
                     onClick={handleNotificationDropBar}
                     className="relative rounded-full transition-all duration-500">

        {notifications.length === 0 ? <HiBell size={30}/> : <HiBellAlert size={30}/>}
    </Button>
        <div
            className={`fixed ${openNotifications ? 'block' : 'hidden'} bg-background rounded shadow-md top-12 right-2
                                         space-y-2`}>
            <div className='p-2 flex flex-col text-xs items-center max-w-72 text-center'>
                {notifications.length === 0 ?
                    <div className='p-2'>
                        You do not have any messages at the moment
                    </div>
                    :
                    <>
                        {notifications.map((notification, key) => <div
                            onClick={() => removeNotification(key)}
                            className='hover:bg-lightGray border-b border-lightGray p-2'>
                            {notification}
                        </div>)}
                        <div className='p-2'>
                            <Button color="inherit"
                                    className="relative border-solid border-2 border-lightGray"
                                    onClick={() => dispatch(clearNotifications())}>
                                Clear Notifications
                            </Button>
                        </div>
                    </>
                }
            </div>
        </div>
    </>
};