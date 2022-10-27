import Photo from "./Photo";
import {useState} from "react";
import Carousel from 'react-bootstrap/Carousel';

export default function ProfileCarousel({photos}) {

    const profilePhotoIndex = (() => {
        for(let i = 0; i < photos.length; i++) {
            if(photos[i].profilePhoto){
                return i;
            }
        }
    })()

    const [index, setIndex] = useState(profilePhotoIndex);
    const handleSelect = (selectedIndex, e) => {
        setIndex(selectedIndex);
    };

    return (
        <Carousel activeIndex={index} onSelect={handleSelect} interval= {null}>

            {photos.map(photo => {
                return(
                    <Carousel.Item key={photo.name}>
                        <Photo filename={ photo.name }/>
                    </Carousel.Item>
                )
            })}
        </Carousel>
    )
}
