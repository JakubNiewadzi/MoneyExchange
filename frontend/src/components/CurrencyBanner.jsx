import {useEffect, useState} from "react";
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import {CurrencyCard} from "./CurrencyCard";
import {useDispatch, useSelector} from "react-redux";
import {fetchCurrencies, setCurrencies} from "../state/slices/currencySlice";

export const CurrencyBanner = ({visible}) => {

    const calculatingCurrency = useSelector(state => state.currency.calculatingCurrency);
    const currencies = useSelector(state => state.currency.currencies);

    const [isConnected, setIsConnected] = useState(false);
    const [offset, setOffset] = useState(0);
    const [contentWidth, setContentWidth] = useState(0);
    const [exchangeRateCalculator, setExchangeRateCalculator] = useState(calculatingCurrency);
    const [bannerCurrencies, setBannerCurrencies] = useState([]);

    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(fetchCurrencies(exchangeRateCalculator));
        setBannerCurrencies(currencies);
    }, [exchangeRateCalculator]);

    useEffect(() => {
        setBannerCurrencies([...currencies, ...currencies.slice(0, 12)]);
    }, [currencies]);


    const handleClick = (currencyId) => {
        if (currencyId !== exchangeRateCalculator) {
            setExchangeRateCalculator(currencyId);
        }
    };

    useEffect(() => {
        if (bannerCurrencies.length !== 0 && visible) {
            const bannerContent = document.getElementById('banner-content');
            setContentWidth(bannerContent.offsetWidth);
            const interval = setInterval(() => {
                setOffset(prevOffset => {
                    const newOffset = prevOffset - 2;
                    if (newOffset <= -contentWidth + window.screen.width) {
                        return 0;
                    } else {
                        return newOffset;
                    }
                });
            }, 25);

            return () => clearInterval(interval);
        }
    }, [currencies, contentWidth, visible]);

    useEffect(() => {
        const socket = new SockJS(process.env.REACT_APP_API_WEBSOCKET_URL + "/currencyUpdates");
        const client = Stomp.over(socket);

        client.connect({}, () => {
            console.log('Websocket connection established');
            setIsConnected(true);
            client.subscribe('/topic/currencyUpdates', (message) => {
                const receivedMessage = JSON.parse(message.body);
                dispatch(setCurrencies(receivedMessage));
            })
        });

        return () => {
            if (isConnected) {
                client.disconnect();
                setIsConnected(false);
            }
        }
    }, []);

    return (visible ? <div className="overflow-hidden fixed z-50 flex mt-16 w-full">
            <div className="flex" id="banner-content" style={{transform: `translateX(${offset}px)`}}>
                {bannerCurrencies.map((currency, index) =>
                    <CurrencyCard key={index}
                                  name={currency.name}
                                  code={currency.code}
                                  exchangeRate={currency.exchangeRate}
                                  id={currency.id}
                                  calculatingCurrency={currencies?.find(currency => currency.id === calculatingCurrency)?.code}
                                  onClick={handleClick}/>
                )}
            </div>
        </div> : <div></div>
    );
};