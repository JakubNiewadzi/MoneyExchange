import {Navbar} from "../components/Navbar";
import {Parallax, ParallaxLayer} from "@react-spring/parallax";

export const HomePage = () => {
    return <><Navbar/>
            <Parallax pages={2}>
                <ParallaxLayer>
                    <img src={require('../images/coins.jpg')} height='1000px' alt='coins'/>
                </ParallaxLayer>
                <ParallaxLayer offset={1}>
                    <h2>Stronka</h2>
                </ParallaxLayer>
            </Parallax></>
}